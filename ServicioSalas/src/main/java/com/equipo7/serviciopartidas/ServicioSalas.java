package com.equipo7.serviciopartidas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadores.FabricaManejadorEventoAbstracto;
import manejadores.ManejadorEvento;
import manejadores.fabrica.FabricaManejadorEvento;
import servicio.ContratoServicio;

/**
 *
 * @author neri
 */
public class ServicioSalas extends Thread {

    private static final String BUS_HOSTNAME = "localhost";
    private static final int BUS_PUERTO = 15_001;

    //private static final Scanner in = new Scanner(System.in);
    private Socket socket = null;

    public ServicioSalas() {

    }

    /**
     * Devuelve el contrato de servicio propio del servicio
     *
     * @return
     */
    public static ContratoServicio getContrato() {
        ContratoServicio contrato = new ContratoServicio();

        contrato.setHost("localhost");
        contrato.setNombreServicio("Servicio Salas");
        contrato.setEventosEscuchables(Arrays.asList(
                "CrearSalaSolicitud",
                "EliminarSalaSolicitud",
                "ObtenerSalasSolicitud",
                "UnirseSalaSolicitud",
                "AbandonarSalaSolicitud",
                "CambiarEstadoJugadorListoSolicitud",
                "IniciarPartidaSolicitud"
        ));

        return contrato;
    }

    @Override
    public void run() {

        FabricaManejadorEventoAbstracto fabricaManejadorEventos = new FabricaManejadorEvento();

        try {
            socket = new Socket(ServicioSalas.BUS_HOSTNAME, ServicioSalas.BUS_PUERTO);
            System.out.println("[*] CONECTADO AL BUS(%s, %d)...".formatted(ServicioSalas.BUS_HOSTNAME, ServicioSalas.BUS_PUERTO));
        } catch (IOException ex) {
            Logger.getLogger(ServicioSalas.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            // buffer para mandar mensaje al server
            DataOutputStream respuesta = new DataOutputStream(socket.getOutputStream());

            // mensaje del servidor
            DataInputStream mensaje = new DataInputStream(socket.getInputStream());

            // SE ENVIA EL CONTRATO DE SERVICIO 
            String contratoServicioJSON = mapper.writeValueAsString(ServicioSalas.getContrato());
            System.out.println("CONTRATO ENVIADO: " + contratoServicioJSON);
            respuesta.writeUTF(contratoServicioJSON);
            respuesta.flush();

            // TODO: Evaluar la respuesta del servidor
            while (true) {
                String mensajeJSON = mensaje.readUTF();

                JsonNode jsonNode = mapper.readTree(mensajeJSON);

                // Acceder a los valores directamente
                String nombreEvento = jsonNode.get("nombre_evento").asText();

                System.out.println("Nombre del evento: " + nombreEvento);

                ManejadorEvento manejador = fabricaManejadorEventos.obtenerManejador(nombreEvento, socket, mensajeJSON);

                if (manejador != null) {
                    manejador.start();
                }
            }
        } catch (IOException ex) {
            System.out.println("[ERROR SERVICIO SALAS]: Ocurrio un error -> " + ex.getMessage());
            ex.printStackTrace();
        } finally {

        }
    }
}
