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
import eventos.CrearSalaRespuestaEvento;
import eventos.SalaErrorEvento;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;
import repositorio.excepciones.RepositorioSalasException;

/**
 * Se encarga de manejar el evento de cuando un usuario quiere crear una sala
 *
 * @author Saul Neri
 */
public class CrearSalaSolicitudManejador extends ManejadorEvento {

    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * 
     * @param clienteSck
     * @param eventoSerializado 
     */
    public CrearSalaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * 
     * @param sala
     * @return
     * @throws RepositorioSalasException 
     */
    private CrearSalaRespuestaEvento crearSala(Sala sala) throws RepositorioSalasException {
        // TODO: Realizar validaciones...
        repositorio.agregarSala(sala);

        return new CrearSalaRespuestaEvento(sala);
    }

    /**
     * Envia el error al consumidor de los servicios (jugador)
     */
    private void enviaRespuestaError(String mensaje) {
        SalaErrorEvento error = new SalaErrorEvento(mensaje);

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
            Logger.getLogger(CrearSalaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Acceder a los valores directamente
            JsonNode salaSerializada = jsonNode.get("sala");

            Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);

            CrearSalaRespuestaEvento evento = this.crearSala(sala);
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("No se puedo crear la sala debido a un error en el servidor, porfavor intente mas tarde...");
        } catch (RepositorioSalasException ex) {
           this.enviaRespuestaError(ex.getMessage());
        }
    }
}
