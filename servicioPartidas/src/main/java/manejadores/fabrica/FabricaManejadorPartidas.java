//package manejadores;
//
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.BiFunction;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class FabricaManejadorPartidas {
//    private static final Logger LOGGER = Logger.getLogger(FabricaManejadorPartidas.class.getName());
//    
//    private static final Map<String, BiFunction<Socket, String, ManejadorEvento>> manejadores = new HashMap<>();
//    
//    static {
//        // Registro de manejadores de eventos
////        manejadores.put("ActualizarPuntajeSolicitud", ActualizarPuntajeSolicitudManejador::new);
//        // Aquí puedes agregar más manejadores según sea necesario
//    }
//    
//    /**
//     * Obtiene el manejador de eventos correspondiente al nombre del evento.
//     * 
//     * @param nombreEvento Nombre del evento a manejar
//     * @param socket Socket de comunicación
//     * @param eventoSerializado Evento serializado en formato JSON
//     * @return Manejador de eventos o null si no se encuentra
//     */
//    public ManejadorEvento obtenerManejador(String nombreEvento, Socket socket, String eventoSerializado) {
//        BiFunction<Socket, String, ManejadorEvento> constructor = manejadores.get(nombreEvento);
//        
//        if (constructor != null) {
//            try {
//                return constructor.apply(socket, eventoSerializado);
//            } catch (Exception e) {
//                LOGGER.log(Level.SEVERE, "Error al crear manejador para el evento: " + nombreEvento, e);
//                return null;
//            }
//        }
//        
//        LOGGER.warning("Evento no reconocido: " + nombreEvento);
//        return null;
//    }
//    
//    /**
//     * Método para registrar dinámicamente nuevos manejadores de eventos.
//     * 
//     * @param nombreEvento Nombre del evento
//     * @param constructor Función para crear el manejador
//     */
//    public void registrarManejador(String nombreEvento, BiFunction<Socket, String, ManejadorEvento> constructor) {
//        manejadores.put(nombreEvento, constructor);
//    }
//}