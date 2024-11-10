/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se encarga de manejar el evento de cuando un usuario quiere crear una sala
 * @author Saul Neri
 */
public class AgregarSalaSolicitudManejador extends ManejadorEvento {

    public AgregarSalaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    @Override
    public void run() {

        DataOutputStream respuesta = null;
        
        try {
            //peticion = new DataInputStream(this.clienteSck.getInputStream());
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(AgregarSalaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Acceder a los valores directamente
            String host = jsonNode.get("host").asText();
            int puerto = jsonNode.get("puerto").asInt();

            System.out.println("Host: " + host);
            System.out.println("Puerto: " + puerto);
            
            respuesta.writeUTF("Recibido");
            respuesta.flush();
            
            // cierra la conexion
            this.clienteSck.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
