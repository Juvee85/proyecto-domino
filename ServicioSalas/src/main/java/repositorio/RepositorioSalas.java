package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.Sala;
import eventos.UnirseSalaRespuestaEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;
import java.util.List;

/**
 * Manejador que procesa las solicitudes de unirse a una sala de juego
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class UnirseSalaManejador extends ManejadorEvento {
    private final static RepositorioSalas repositorio = RepositorioSalas.getInstance();
    
    /**
     * Crea un nuevo manejador para procesar solicitudes de unirse a una sala
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
     * @param nombreSala Nombre de la sala a la que se quiere unir
     * @param idJugador ID del jugador que quiere unirse
     * @return true si se unió exitosamente, false en caso contrario
     */
    private boolean unirseSala(String nombreSala, String idJugador) {
        List<Sala> salas = repositorio.getSalas();
        Sala salaObjetivo = salas.stream()
            .filter(s -> s.getNombre().equalsIgnoreCase(nombreSala))
            .findFirst()
            .orElse(null);
            
        if (salaObjetivo != null && 
            salaObjetivo.getJugadoresEnSala() < salaObjetivo.getMaxJugadores()) {
            
            // Incrementar el contador de jugadores en la sala
            salaObjetivo.setJugadoresEnSala(salaObjetivo.getJugadoresEnSala() + 1);
            
            // Agregar el jugador a la lista de jugadores si es necesario
            List<String> jugadores = salaObjetivo.getJugadores();
            if (!jugadores.contains(idJugador)) {
                jugadores.add(idJugador);
            }
            
            return true;
        }
        return false;
    }
    
    @Override
    public void run() {
        DataOutputStream respuesta = null;
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);
            
            // Extraer datos del evento
            String nombreSala = jsonNode.get("nombre_sala").asText();
            String idJugador = jsonNode.get("id_jugador").asText();
            
            // Procesar la solicitud
            boolean exitoso = this.unirseSala(nombreSala, idJugador);
            
            // Crear y enviar respuesta
            UnirseSalaRespuestaEvento evento = new UnirseSalaRespuestaEvento(
                exitoso,
                exitoso ? "Te has unido exitosamente a la sala " + nombreSala : 
                         "No se pudo unir a la sala " + nombreSala + ". La sala puede estar llena o no existir.",
                nombreSala,
                idJugador
            );
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(UnirseSalaManejador.class.getName())
                  .log(Level.SEVERE, "Error al procesar solicitud de unirse a sala", ex);
        }
    }
}