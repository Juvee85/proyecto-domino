//package manejadores;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
////import eventos.ActualizarPuntajeRespuestaEvento;
//import eventos.SalaErrorEvento;
//import repositorios.RepositorioPartidas;
//import repositorios.excepciones.RepositorioPartidasException;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * Maneja la solicitud para actualizar el puntaje de un usuario en una partida.
// */
//public class ActualizarPuntajeSolicitudManejador extends ManejadorEvento {
//    private static final Logger LOGGER = Logger.getLogger(ActualizarPuntajeSolicitudManejador.class.getName());
//    private static final RepositorioPartidas repositorio = RepositorioPartidas.getInstance();
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    /**
//     * Constructor del manejador.
//     *
//     * @param clienteSck Socket del cliente.
//     * @param eventoSerializado Evento en formato JSON.
//     */
//    public ActualizarPuntajeSolicitudManejador(Socket clienteSck, String eventoSerializado) {
//        this.clienteSck = clienteSck;
//        this.eventoSerializado = eventoSerializado;
//    }
//
//    @Override
//    public void run() {
//        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
//            // Deserialización del evento
//            JsonNode jsonNode = objectMapper.readTree(eventoSerializado);
//            
//            // Validación de campos requeridos
//            validarCampos(jsonNode);
//            
//            String sala = jsonNode.get("sala_id").asText();
//            String usuario = jsonNode.get("usuario").asText();
//            int puntaje = jsonNode.get("puntaje").asInt();
//            
//            // Validación del puntaje
//            validarPuntaje(puntaje);
//            
//            // Actualización en el repositorio
//            repositorio.actualizarPuntaje(sala, usuario, puntaje);
//            
//            // Enviar respuesta de éxito
////            enviarRespuestaExito(respuesta);
//        } catch (IOException | RepositorioPartidasException | IllegalArgumentException e) {
//            // Manejo de errores y envío de respuesta de error
//            LOGGER.log(Level.SEVERE, "Error al procesar actualización de puntaje", e);
//            enviarRespuestaError(e.getMessage());
//        }
//    }
//
//    /**
//     * Valida que los campos necesarios estén presentes en el JSON.
//     *
//     * @param jsonNode Nodo JSON a validar
//     * @throws IllegalArgumentException Si falta algún campo requerido
//     */
//    private void validarCampos(JsonNode jsonNode) {
//        if (!jsonNode.has("sala_id") || 
//            !jsonNode.has("usuario") || 
//            !jsonNode.has("puntaje")) {
//            throw new IllegalArgumentException("Campos requeridos incompletos");
//        }
//    }
//
//    /**
//     * Valida que el puntaje sea válido.
//     *
//     * @param puntaje Puntaje a validar
//     * @throws IllegalArgumentException Si el puntaje no es válido
//     */
//    private void validarPuntaje(int puntaje) {
//        if (puntaje < 0) {
//            throw new IllegalArgumentException("El puntaje no puede ser negativo");
//        }
//        
//        // Ejemplo de validación adicional (límite máximo de puntaje)
//        if (puntaje > 1_000_000) {
//            throw new IllegalArgumentException("Puntaje excede el límite máximo permitido");
//        }
//    }
//
//    /**
//     * Envía una respuesta indicando que la solicitud se procesó con éxito.
//     *
//     * @param respuesta Stream para enviar datos al cliente.
//     * @throws IOException Si ocurre un error al enviar la respuesta.
//     */
////    private void enviarRespuestaExito(DataOutputStream respuesta) throws IOException {
//////        ActualizarPuntajeRespuestaEvento evento = new ActualizarPuntajeRespuestaEvento();
////        evento.setEstado("completado");
////        evento.setMensaje("Puntaje actualizado correctamente");
////        
////        String respuestaJson = objectMapper.writeValueAsString(evento);
////        respuesta.writeUTF(respuestaJson);
////        
////        LOGGER.info("Puntaje actualizado exitosamente");
////    }
//
//    /**
//     * Envía una respuesta indicando que ocurrió un error al procesar la solicitud.
//     *
//     * @param mensaje Mensaje de error a enviar al cliente.
//     */
//    private void enviarRespuestaError(String mensaje) {
//        try (DataOutputStream respuesta = new DataOutputStream(clienteSck.getOutputStream())) {
//            SalaErrorEvento error = new SalaErrorEvento(mensaje);
//            String errorJson = objectMapper.writeValueAsString(error);
//            respuesta.writeUTF(errorJson);
//            
//            LOGGER.warning("Error al procesar solicitud: " + mensaje);
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error al enviar respuesta de error", e);
//        }
//    }
//}