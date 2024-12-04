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
import manejadores.fabrica.FabricaManejadorPartidas;
import servicio.ContratoServicio;

/**
 * Servicio para gestión de partidas.
 *
 * @author Sebastian Murrieta Verduzco
 */
public class ServicioPartidas extends Thread {

    private static final String BUS_HOSTNAME = "localhost";
    private static final int BUS_PUERTO = 15_001;
    private Socket socket = null;

    public ServicioPartidas() {
    }

    /**
     * Devuelve el contrato de servicio propio del servicio
     *
     * @return Contrato de servicio
     */
    public static ContratoServicio getContrato() {
        ContratoServicio contrato = new ContratoServicio();
        contrato.setHost("localhost");
        contrato.setNombreServicio("Servicio Partidas");
        contrato.setEventosEscuchables(Arrays.asList(
                "ActualizarPuntajeSolicitud",
                "CrearPartidaSolicitud",
                "FinalizarPartidaSolicitud"
        ));
        return contrato;
    }

    @Override
    public void run() {
        FabricaManejadorEventoAbstracto fabricaManejadorEventos = new FabricaManejadorPartidas();

        try {
            socket = new Socket(BUS_HOSTNAME, BUS_PUERTO);
            System.out.println("[*] CONECTADO AL BUS(%s, %d)...".formatted(BUS_HOSTNAME, BUS_PUERTO));
        } catch (IOException ex) {
            Logger.getLogger(ServicioPartidas.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try (DataOutputStream respuesta = new DataOutputStream(socket.getOutputStream()); DataInputStream mensaje = new DataInputStream(socket.getInputStream())) {

            // Enviar contrato de servicio 
            String contratoServicioJSON = mapper.writeValueAsString(getContrato());
            System.out.println("CONTRATO ENVIADO: " + contratoServicioJSON);
            respuesta.writeUTF(contratoServicioJSON);
            respuesta.flush();

            // Procesar mensajes entrantes
            while (!Thread.currentThread().isInterrupted()) {
                String mensajeJSON = mensaje.readUTF();
                JsonNode jsonNode = mapper.readTree(mensajeJSON);

                // Acceder a los valores directamente
                String nombreEvento = jsonNode.get("nombre_evento").asText();
                System.out.println("Nombre del evento: " + nombreEvento);

                ManejadorEvento manejador = fabricaManejadorEventos.obtenerManejador(
                        nombreEvento,
                        socket,
                        mensajeJSON
                );

                if (manejador != null) {
                    manejador.start();
                }

            }
        } catch (IOException ex) {
            System.out.println("[ERROR SERVICIO PARTIDAS]: Ocurrió un error -> " + ex.getMessage());
        } finally {
        }

    }
}
