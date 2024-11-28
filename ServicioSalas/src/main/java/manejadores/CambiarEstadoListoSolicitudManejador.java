/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eventos.CambiarEstadoListoRespuestaEvento;
import eventos.SalaErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;
import repositorio.excepciones.RepositorioSalasException;

/**
 * Maneja el evento de un jugador que quiere cambiar su estado Listo en la 
 * sala de espera.
 * @author Saul Neri
 */
public class CambiarEstadoListoSolicitudManejador extends ManejadorEvento {
    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * 
     * @param clienteSck
     * @param eventoSerializado 
     */
    public CambiarEstadoListoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.setName(String.format("Thread [%s]", this.getClass().getSimpleName()));
        
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Maneja el evento que indica el nuevo estado de un jugador en la sala. 
     * Indica si un jugador esta listo para iniciar la sala.
     * @param nombreSala Nombre de la sala.
     * @param nombreJugador Nombre del jugador.
     * @param listo Estado del jugador, si esta listo o no para empezar la
     * partida.
     * @return Evento de respuesta que sera enviado a los demas jugadores.
     */
    private CambiarEstadoListoRespuestaEvento cambiarEstadoJugador(String nombreSala, String nombreJugador, boolean estadoListo) throws RepositorioSalasException {
        // TODO: Realizar validaciones...
        repositorio.cambiarEstadoListoJugador(nombreSala, nombreJugador, estadoListo);
        return new CambiarEstadoListoRespuestaEvento(nombreSala, nombreJugador, estadoListo);
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
            System.out.println("!!!");
            System.out.println(this.eventoSerializado);
            
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);
            
            String  nombreSala      = (String)  jsonNode.get("nombre_sala").asText();
            String  nombreJugador   = (String)  jsonNode.get("id_jugador").asText();
            boolean estadoListo     = (Boolean) jsonNode.get("listo").asBoolean();
            
            CambiarEstadoListoRespuestaEvento evento = this.cambiarEstadoJugador(nombreSala, nombreJugador, estadoListo);
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            System.out.println("[*] Se notifico el estado listo de un jugador...");
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("No se pudo enviar el mensaje de estado...");
        } catch (RepositorioSalasException ex) {
           this.enviaRespuestaError(ex.getMessage());
        }
    }
}
