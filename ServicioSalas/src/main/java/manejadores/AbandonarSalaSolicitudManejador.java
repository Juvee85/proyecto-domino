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
import eventos.JugadorAbandonaSalaRespuestaEvento;
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
public class AbandonarSalaSolicitudManejador extends ManejadorEvento {

    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * 
     * @param clienteSck
     * @param eventoSerializado 
     */
    public AbandonarSalaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.setName(String.format("Thread [%s]", this.getClass().getSimpleName()));
        
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Efecuta la operacion de sacar a un jugador de una sala y devuelve un objeto respuesta
     * @param nombreJugador Nombre del jugador a sacar
     * @param nombreSala Nombre de la sala en cuestion
     * @return Respuesta del evento
     * @throws RepositorioSalasException Si ocurre un error en la operacion
     */
    private JugadorAbandonaSalaRespuestaEvento abandonarSala(String nombreJugador, String nombreSala) throws RepositorioSalasException {
        // TODO: Realizar validaciones...
        repositorio.sacarJugadorDeSala(nombreJugador, nombreSala);

        return new JugadorAbandonaSalaRespuestaEvento(nombreJugador, nombreSala);
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
            Logger.getLogger(AbandonarSalaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            System.out.println("!!!");
            System.out.println(this.eventoSerializado);
            
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // Acceder a los valores directamente
            String nombreSala = jsonNode.get("nombre_sala").asText();
            String nombreJugador = jsonNode.get("id_jugador").asText();
            
            JugadorAbandonaSalaRespuestaEvento respuestaEvento = this.abandonarSala(nombreJugador, nombreSala);
            
            String eventoJSON = objectMapper.writeValueAsString(respuestaEvento);
            
            System.out.println("[*] Se saco al jugador de la sala...");
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("No se puedo abandonar la sala debido a un error en el servidor, porfavor intente mas tarde...");
        } catch (RepositorioSalasException ex) {
           this.enviaRespuestaError(ex.getMessage());
        }
    }
}
