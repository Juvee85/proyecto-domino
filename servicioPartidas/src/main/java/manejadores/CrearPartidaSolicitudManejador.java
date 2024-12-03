package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.Jugador;
import entidades.Sala;
import eventos.CrearPozoPartidaSolicitud;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorios.RepositorioPartidas;
import repositorios.excepciones.RepositorioPartidasException;

/**
 * Maneja la solicitud para crear una nueva partida
 *
 *
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class CrearPartidaSolicitudManejador extends ManejadorEvento {

    private static final Logger LOGGER = Logger.getLogger(CrearPartidaSolicitudManejador.class.getName());
    private static final RepositorioPartidas repositorio = RepositorioPartidas.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor del manejador de creación de partida
     *
     * @param clienteSck Socket del cliente
     * @param eventoSerializado Evento serializado en JSON
     */
    public CrearPartidaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.clienteSck = clienteSck;
        this.eventoSerializado = eventoSerializado;
    }

    @Override
    public void run() {
        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
            // Deserializar el evento JSON
            JsonNode jsonNode = objectMapper.readTree(eventoSerializado);

            // Validar campos requeridos
//            validarCampos(jsonNode);
            // Extraer información de la solicitud
            JsonNode jugadoresNode = jsonNode.get("sala").get("jugadores");
            Sala sala = objectMapper.treeToValue(jsonNode.get("sala"), Sala.class);

            // Crear lista de jugadores
            List<Jugador> jugadores = new ArrayList<>();
            for (JsonNode jugadorNode : jugadoresNode) {
                Jugador jugador = objectMapper.treeToValue(jugadorNode, Jugador.class);
                jugadores.add(jugador);
            }

            // Crear la partida
            crearPartida(sala, jugadores, respuesta);

        } catch (IOException | RepositorioPartidasException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error al procesar creación de partida", e);
//            enviarRespuestaError(e.getMessage());
        }
    }

    // TODO: hacer una validación correcta
    /**
     * Valida que los campos necesarios estén presentes en el JSON
     *
     * @param jsonNode Nodo JSON a validar
     * @throws IllegalArgumentException Si falta algún campo requerido
     */
//    private void validarCampos(JsonNode jsonNode) {
//        if (!jsonNode.has("sala_id") || !jsonNode.has("jugadores")) {
//            throw new IllegalArgumentException("Campos requeridos incompletos para crear partida");
//        }
//    }
    /**
     * Crea la partida en el repositorio
     *
     * @param salaId Identificador de la sala
     * @param jugadores Lista de jugadores
     * @param respuesta Stream para enviar datos al cliente
     * @throws RepositorioPartidasException Si hay un error al crear la partida
     * @throws IOException Si hay un error al enviar la respuesta
     */
    private void crearPartida(Sala sala, List<Jugador> jugadores, DataOutputStream respuesta)
            throws RepositorioPartidasException, IOException {
        // Validar que haya al menos un jugador
        if (jugadores.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un jugador para crear la partida");
        }

        // Crear la partida con el primer jugador como anfitrión
        Jugador anfitrion = jugadores.get(0);
        repositorio.crearPartida(sala.getNombre(), anfitrion);

        // Agregar el resto de los jugadores
        for (Jugador jugador : jugadores) {
            repositorio.añadirJugador(sala.getNombre(), jugador.getNombre());
        }

        // Preparar y enviar respuesta de éxito
        CrearPozoPartidaSolicitud evento = new CrearPozoPartidaSolicitud();
        evento.setSala(sala);
        evento.setJugadores(jugadores);
        evento.setEstado("completado");
        evento.setMensaje("Partida creada exitosamente");

        String respuestaJson = objectMapper.writeValueAsString(evento);
        respuesta.writeUTF(respuestaJson);
        respuesta.flush();

        LOGGER.info("Partida creada exitosamente para la sala: " + sala.getNombre());
    }

    /**
     * Envía una respuesta de error al cliente
     *
     * @param mensaje Mensaje de error a enviar
     */
//    private void enviarRespuestaError(String mensaje) {
//        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
//            SalaErrorEvento error = new SalaErrorEvento(mensaje);
//            String errorJson = objectMapper.writeValueAsString(error);
//            respuesta.writeUTF(errorJson);
//
//            LOGGER.warning("Error al procesar solicitud de creación de partida: " + mensaje);
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error al enviar respuesta de error", e);
//        }
//    }
}
