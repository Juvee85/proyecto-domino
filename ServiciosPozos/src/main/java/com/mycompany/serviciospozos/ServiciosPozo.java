package com.mycompany.serviciospozos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadores.fabrica.FabricaManejadorEvento;
import servicio.ContratoServicio;

/**
 *
 * @author diana
 */
public class ServiciosPozo extends Thread {
    
    private static final String BUS_HOSTNAME = "localhost";
    private static final int BUS_PUERTO = 15_001;
    
     private static final Scanner in = new Scanner(System.in);
    
     private Socket socket = null;

    public ServiciosPozo() {
    }
    
     /**
     * Método estático para obtener el contrato de servicio, que describe el host,
     * el nombre del servicio y los eventos que este servicio puede escuchar.
     *
     * @return contrato del servicio Pozo con sus propiedades
     */
    public static ContratoServicio getContrato(){
         ContratoServicio contrato = new ContratoServicio();

        contrato.setHost(BUS_HOSTNAME);
        contrato.setNombreServicio("Servicio Pozo");
        contrato.setEventosEscuchables(Arrays.asList(
                "CrearPozoPartidaSolicitud",
                "ActualizarPozoSolicitud",
                "EliminarPozoSolicitud",
                "ObtenerPozosSolicitud",
                "TomarFichaSolicitud"
        ));

        return contrato;
    }
     /**
     * Método principal que se ejecuta en un hilo para conectar el servicio al bus 
     * de eventos, enviar su contrato y procesar los eventos recibidos.
     */
    @Override
    public void run(){
    
        // Se obtiene una fábrica de manejadores de eventos
        FabricaManejadorEventoAbstracto fabricaManejadorEventos = new FabricaManejadorEvento();
        
        try{
            // Conecta al bus de eventos usando los parámetros predefinidos
            socket = new Socket(ServiciosPozo.BUS_HOSTNAME, ServiciosPozo.BUS_PUERTO);
            System.out.println("[*]CONECTADO AL BUS(%s, %d)...".formatted(ServiciosPozo.BUS_HOSTNAME, ServiciosPozo.BUS_PUERTO));
        }catch(IOException ex){
            Logger.getLogger(ServiciosPozo.class.getName()).log(Level.SEVERE, null, ex);
        }
         // Se usa Jackson para manejar los mensajes JSON
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        
        try {// Flujos para enviar y recibir mensajes con el bus de eventos
            DataOutputStream respuesta = new DataOutputStream(socket.getOutputStream());
            DataInputStream mensaje = new DataInputStream(socket.getInputStream());

            // Envío del contrato de servicio al bus en formato JSON
            String contratoServicioJSON = mapper.writeValueAsString(ServiciosPozo.getContrato());
            System.out.println("CONTRATO ENVIADO: " + contratoServicioJSON);
            respuesta.writeUTF(contratoServicioJSON);
            respuesta.flush();

            // Bucle principal para recibir y procesar eventos
            while (true) {
                String mensajeJSON = mensaje.readUTF(); // Lee el mensaje JSON recibido
                JsonNode jsonNode = mapper.readTree(mensajeJSON);
                String nombreEvento = jsonNode.get("nombre_evento").asText(); // Extrae el nombre del evento

                System.out.println("Nombre del evento: " + nombreEvento);

                // Obtiene un manejador de evento específico para el tipo de evento recibido
                ManejadorEvento manejador = fabricaManejadorEventos.obtenerManejador(nombreEvento, socket, mensajeJSON);

                if (manejador != null) {
                    manejador.start(); // Inicia el manejador de eventos en un nuevo hilo
                }
            }
        } catch (IOException ex) {
            // Captura y muestra errores en caso de fallos de conexión o de E/S
            System.out.println("[ERROR SERVICIO POZO]: Ocurrió un error -> " + ex.getMessage());
            
        }
    }
}
