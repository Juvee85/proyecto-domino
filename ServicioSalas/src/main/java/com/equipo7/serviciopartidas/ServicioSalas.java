package com.equipo7.serviciopartidas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import manejadores.AgregarSalaSolicitudManejador;
import repositorio.RepositorioSalas;
import servicio.ContratoServicio;

/**
 *
 * @author neri
 */
public class ServicioSalas extends Thread {

    private static final String BUS_HOSTNAME = "localhost";
    private static final int BUS_PUERTO = 15_001;

    private static final Scanner in = new Scanner(System.in);
    
    private Socket socket = null;

    public ServicioSalas() {

    }

    public static ContratoServicio getContrato() {
        ContratoServicio contrato = new ContratoServicio();

        contrato.setHost("localhost");
        contrato.setNombreServicio("Servicio Salas");
        contrato.setEventosEscuchables(Arrays.asList(
                "CrearSalaSolicitud",
                "CrearSalaRespuesta",
                "ConsultarSalasSolicitud",
                "ConsultarSalasRespuesta"
        ));

        return contrato;
    }

    @Override
    public void run() {

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

            while (true) {
                
                System.out.println("MSG > ");
                String msg = in.nextLine();
                
                respuesta.writeUTF(msg);
                respuesta.flush();
                
                System.out.println("[=!=] ESCUCHANDO MENSAJES DEL BUS...");
                
                // recibe el mensaje del ESB
                String response = mensaje.readUTF();
                System.out.println("[MSG] Respuesta del servidor: " + response);

                //
            }

        } catch (IOException ex) {
            System.out.println("[ERROR SERVICIO SALAS]: Ocurrio un error -> " + ex.getMessage());
        } finally {

        }
    }
}
