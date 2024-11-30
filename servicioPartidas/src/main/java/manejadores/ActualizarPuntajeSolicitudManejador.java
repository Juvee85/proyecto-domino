package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventos.ActualizarPuntajeRespuestaEvento;
import eventos.SalaErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorios.RepositorioPartidas;
import repositorios.excepciones.RepositorioPartidasException;

/**
 * Gestiona las solicitudes para actualizar el puntaje de un usuario en una
 * sesión de juego.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ActualizarPuntajeSolicitudManejador extends ManejadorEvento {

    private static final Logger LOGGER = Logger.getLogger(ActualizarPuntajeSolicitudManejador.class.getName());
    private static final int PUNTAJE_MAXIMO = 1_000_000; // Límite máximo permitido para el puntaje.
    private final RepositorioPartidas repositorio;
    private final ObjectMapper objectMapper;

    /**
     * Constructor del manejador con dependencias por defecto.
     *
     * @param clienteSck Socket para la comunicación con el cliente.
     * @param eventoSerializado Evento en formato JSON.
     */
    public ActualizarPuntajeSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this(clienteSck, eventoSerializado, RepositorioPartidas.getInstance(), new ObjectMapper());
    }

    /**
     * Constructor del manejador con dependencias inyectables (útil para
     * pruebas).
     *
     * @param clienteSck Socket para la comunicación con el cliente.
     * @param eventoSerializado Evento en formato JSON.
     * @param repositorio Repositorio para datos de las partidas.
     * @param objectMapper Mapeador de objetos JSON.
     */
    ActualizarPuntajeSolicitudManejador(Socket clienteSck, String eventoSerializado,
            RepositorioPartidas repositorio, ObjectMapper objectMapper) {
        this.clienteSck = Objects.requireNonNull(clienteSck, "El socket del cliente no puede ser nulo");
        this.eventoSerializado = Objects.requireNonNull(eventoSerializado, "El evento serializado no puede ser nulo");
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio no puede ser nulo");
        this.objectMapper = Objects.requireNonNull(objectMapper, "El ObjectMapper no puede ser nulo");
    }

    @Override
    public void run() {
        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
            DatosActualizacion datos = extraerDatos(eventoSerializado);
            repositorio.actualizarPuntaje(datos.salaId, datos.usuario, datos.puntaje);
            enviarRespuestaExito(respuesta, datos);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al procesar la actualización del puntaje", e);
            enviarRespuestaError(e.getMessage());
        }
    }

    /**
     * Extrae los datos necesarios del evento serializado en JSON.
     *
     * @param eventoSerializado Evento en formato JSON.
     * @return Datos de la actualización de puntaje.
     * @throws IOException Si ocurre un error al procesar el JSON.
     */
    private DatosActualizacion extraerDatos(String eventoSerializado) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(eventoSerializado);
        validarCampos(jsonNode);

        String salaId = jsonNode.get("sala_id").asText();
        String usuario = jsonNode.get("usuario").asText();
        int puntaje = jsonNode.get("puntaje").asInt();

        validarPuntaje(puntaje);
        return new DatosActualizacion(salaId, usuario, puntaje);
    }

    /**
     * Valida que los campos necesarios estén presentes en el JSON.
     *
     * @param jsonNode Nodo JSON con los datos del evento.
     */
    private void validarCampos(JsonNode jsonNode) {
        if (!jsonNode.has("sala_id") || !jsonNode.has("usuario") || !jsonNode.has("puntaje")) {
            throw new IllegalArgumentException("Faltan campos requeridos para actualizar el puntaje");
        }
    }

    /**
     * Valida que el puntaje sea válido (no negativo y no exceda el máximo
     * permitido).
     *
     * @param puntaje Puntaje a validar.
     */
    private void validarPuntaje(int puntaje) {
        if (puntaje < 0) {
            throw new IllegalArgumentException("El puntaje no puede ser negativo");
        }
        if (puntaje > PUNTAJE_MAXIMO) {
            throw new IllegalArgumentException("El puntaje excede el límite máximo permitido");
        }
    }

    /**
     * Envía una respuesta exitosa al cliente.
     *
     * @param respuesta Flujo de salida para enviar la respuesta.
     * @param datos Datos de la actualización.
     * @throws IOException Si ocurre un error al enviar la respuesta.
     */
    private void enviarRespuestaExito(DataOutputStream respuesta, DatosActualizacion datos)
            throws IOException {
        ActualizarPuntajeRespuestaEvento evento = new ActualizarPuntajeRespuestaEvento();
        evento.setEstado("completado");
        evento.setMensaje("Puntaje actualizado correctamente");
        evento.setSalaId(datos.salaId);
        evento.setUsuario(datos.usuario);
        evento.setPuntajeActualizado(datos.puntaje);

        respuesta.writeUTF(objectMapper.writeValueAsString(evento));
        respuesta.flush();

        LOGGER.info(String.format("Puntaje actualizado para el usuario %s en la sala %s: %d",
                datos.usuario, datos.salaId, datos.puntaje));
    }

    /**
     * Envía una respuesta de error al cliente.
     *
     * @param mensaje Mensaje de error.
     */
    private void enviarRespuestaError(String mensaje) {
        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
            SalaErrorEvento error = new SalaErrorEvento(mensaje);
            respuesta.writeUTF(objectMapper.writeValueAsString(error));
            LOGGER.warning("Error al procesar la solicitud: " + mensaje);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al enviar la respuesta de error", e);
        }
    }

    /**
     * Registro que representa los datos necesarios para actualizar el puntaje.
     */
    private record DatosActualizacion(String salaId, String usuario, int puntaje) {

    }
}
