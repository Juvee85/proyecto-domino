/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eventos.FinalizarPartidaRespuestaEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorios.RepositorioPartidas;
import repositorios.excepciones.RepositorioPartidasException;

/**
 * Maneja el evento de finalización de una partida
 * 
 * @author Sebastian Murrieta Verduzco
 */
public class FinalizarPartidaSolicitudManejador extends ManejadorEvento {
    private static final RepositorioPartidas repositorio = RepositorioPartidas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * Constructor del manejador de finalización de partida
     * 
     * @param clienteSck Socket de comunicación
     * @param eventoSerializado Evento serializado en formato JSON
     */
    public FinalizarPartidaSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.setName(String.format("Thread [%s]", this.getClass().getSimpleName()));
        
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Finaliza la partida en el repositorio
     * 
     * @param salaId Identificador de la sala
     * @param ganador Nombre del jugador ganador
     * @return Evento de respuesta de finalización de partida
     * @throws RepositorioPartidasException Si ocurre un error al finalizar la partida
     */
    private FinalizarPartidaRespuestaEvento finalizarPartida(String salaId, String ganador) throws RepositorioPartidasException {
        // Finalizar partida en el repositorio
        repositorio.finalizarPartida(salaId, ganador);
        return new FinalizarPartidaRespuestaEvento(salaId, ganador);
    }

    /**
     * Envía una respuesta de error al cliente
     * 
     * @param mensaje Mensaje de error
     */
//    private void enviaRespuestaError(String mensaje) {
//        SalaErrorEvento error = new SalaErrorEvento(mensaje);
//
//        try {
//            String errorSerializado = objectMapper.writeValueAsString(error);
//            respuesta.writeUTF(errorSerializado);
//            respuesta.flush();
//        } catch (JsonProcessingException ex) {
//            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
//        } catch (IOException ex) {
//            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
//        }
//    }
 
    @Override
    public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(FinalizarPartidaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);
            
            String salaId = jsonNode.get("sala_id").asText();
            String ganador = jsonNode.get("ganador").asText();
            
            FinalizarPartidaRespuestaEvento evento = this.finalizarPartida(salaId, ganador);
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            System.out.println("[*] Se finalizó la partida...");
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
//            this.enviaRespuestaError("No se pudo enviar el mensaje de finalización de partida...");
        } catch (RepositorioPartidasException ex) {
//           this.enviaRespuestaError(ex.getMessage());
        }
    }
}