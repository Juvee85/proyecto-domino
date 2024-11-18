package manejadores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import eventos.SalaErrorEvento;
import eventos.UnirseSalaRespuestaEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;

/**
 * Se encarga de manejar el evento cuando un usuario quiere unirse a una sala
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class UnirseSalaManejador extends ManejadorEvento {

    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * Crea un nuevo manejador para procesar solicitudes de unirse a una sala
     *
     * @param clienteSck Socket del cliente
     * @param eventoSerializado Evento serializado en formato JSON
     */
    public UnirseSalaManejador(Socket clienteSck, String eventoSerializado) {
        this.setName(String.format("Thread [%s]", this.getClass().getSimpleName()));
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Procesa la solicitud de unirse a una sala específica
     *
     * @param nombreSala Nombre de la sala a la que se quiere unir
     * @param idJugador ID del jugador que quiere unirse
     * @return Un evento de respuesta indicando el éxito de la operación
     * @throws Exception si no se puede unir a la sala
     */
    private UnirseSalaRespuestaEvento unirseSala(String nombreSala, String idJugador) throws Exception {
        List<Sala> salas = repositorio.getSalas();
        Sala salaObjetivo = salas.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                .findFirst()
                .orElseThrow(() -> new Exception("La sala especificada no existe"));

        if (salaObjetivo.getJugadoresEnSala() >= salaObjetivo.getMaxJugadores()) {
            throw new Exception("La sala está llena");
        }

        // Incrementar el contador de jugadores en la sala
        salaObjetivo.setJugadoresEnSala(salaObjetivo.getJugadoresEnSala() + 1);

        return new UnirseSalaRespuestaEvento(true, "Te has unido exitosamente a la sala " + nombreSala, nombreSala, idJugador);
    }

    /**
     * Envia el error al consumidor de los servicios (jugador)
     */
    private void enviaRespuestaError(String mensaje) {
        SalaErrorEvento error = new SalaErrorEvento(mensaje);

        try {
            String errorSerializado = objectMapper.writeValueAsString(error);
            respuesta.writeUTF(errorSerializado);
            respuesta.flush();
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        } catch (IOException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        }
    }

    @Override
    public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());

            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Extraer datos del evento
            String nombreSala = jsonNode.get("nombre_sala").asText();
            String idJugador = jsonNode.get("id_jugador").asText();
            Sala salaObjetivo = repositorio.getSalas().stream()
                    .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
                    .findFirst()
                    .orElseThrow(() -> new Exception("La sala especificada no existe"));

            
            // Procesar la solicitud
            UnirseSalaRespuestaEvento evento = this.unirseSala(nombreSala, idJugador);
            evento.setJugadores(salaObjetivo.getJugadores());
            String eventoJSON = objectMapper.writeValueAsString(evento);

            System.out.println("[*] Jugador %s se unió a la sala %s".formatted(idJugador, nombreSala));

            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (Exception ex) {
            Logger.getLogger(UnirseSalaManejador.class.getName())
                    .log(Level.SEVERE, "Error al procesar solicitud de unirse a sala", ex);
            this.enviaRespuestaError(ex.getMessage());
        }
    }
}
