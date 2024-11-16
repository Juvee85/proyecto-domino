/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioTableros;
import repositorio.excepciones.RepositorioTablerosException;

/**
 *
 * @author Saul Neri
 */
public class CrearTableroPartidaSolicitudManejador extends ManejadorEvento {
private static final RepositorioTableros repositorio = RepositorioTableros.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * Se crea un nuevo manejador para manejar el mensaje de creacion de tablero para
     * una sala activa
     * @param clienteSck
     * @param eventoSerializado 
     */
    public CrearTableroPartidaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
    /**
     * Envia el error al consumidor de los servicios (jugador)
     */
    private void enviaRespuestaError(String mensaje) {
        TablerosErrorEvento error = new TablerosErrorEvento(mensaje);

        String errorSerializado;

        try {
            errorSerializado = objectMapper.writeValueAsString(error);

            respuesta.writeUTF(errorSerializado);
            respuesta.flush();
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        } catch (IOException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        }
    }
    
    @Override
    public void run() {
        try {
            //peticion = new DataInputStream(this.clienteSck.getInputStream());
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CrearTableroPartidaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Acceder a los valores directamente
            JsonNode salaSerializada = jsonNode.get("sala");

            Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);

            //CrearSalaRespuestaEvento evento = this.crearSala(sala);
            
            //String eventoJSON = objectMapper.writeValueAsString(evento);
            
            //respuesta.writeUTF(eventoJSON);
            //respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("No se puedo crear la sala debido a un error en el servidor, porfavor intente mas tarde...");
        } //catch (RepositorioTablerosException ex) {this.enviaRespuestaError(ex.getMessage());}
    }
}
