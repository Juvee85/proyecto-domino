package com.equipo7.serviciopartidas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import manejadores.AgregarSalaSolicitudManejador;
import repositorio.RepositorioSalas;

/**
 *
 * @author neri
 */
public class ServicioSalas {

    private static final String NOMBRE_SERVICIO = "ServicioSalas";
    private static final String HOSTNAME = "localhost";
    private static final int PUERTO = 15_001;

    public static void main(String[] args) {

        ServerSocket server = null;
        
        try {
            server = new ServerSocket(PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSalas.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        
        while (true) {

            try {
                // acepta el cliente
                Socket client = server.accept();

                System.out.println("CLIENTE CONECTADO POR: " + client.getInetAddress().getHostAddress() + "::" + client.getPort() + "");

                // obtiene el mensaje en bytes del cliente
                DataInputStream peticion = new DataInputStream(client.getInputStream());

                // obtiene el evento serializado
                String eventoSerializado = peticion.readUTF();

                // obtiene el nombre del evento
                JsonNode jsonNode = mapper.readTree(eventoSerializado);
                
                // manejar el evento segun el nombre
                String nombreEvento = jsonNode.get("nombre_evento").asText();
                if (nombreEvento.equals("AgregarSalaSolicitud")) {
                    new AgregarSalaSolicitudManejador(client, eventoSerializado).start();
                }
                
                
            } catch (IOException ex) {
                System.out.println("Error del servidor: " + ex.getMessage());
            }
        }
    }
}
