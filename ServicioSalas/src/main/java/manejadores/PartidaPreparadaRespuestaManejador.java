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
import entidades.Tablero;
import eventos.IniciarPartidaRespuestaEvento;
import eventos.SalaErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;
import repositorio.excepciones.RepositorioSalasException;

/**
 *
 * @author Saul Neri
 */
public class PartidaPreparadaRespuestaManejador extends ManejadorEvento {
    private static final RepositorioSalas repositorio = RepositorioSalas.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    /**
     * 
     * @param clienteSck
     * @param eventoSerializado 
     */
    public PartidaPreparadaRespuestaManejador(Socket clienteSck, String eventoSerializado) {
        //this.nombreEvento = nombreEvento;
        this.setName(String.format("Thread [%s]", this.getClass().getSimpleName()));
        
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
    /**
     * Manda el evento de iniciar la partida a los jugadores de la sala especifica.
     * @param sala Objeto sala.
     * @param tablero Objeto tablero de la partida.
     * @param fichasRestantes Fichas restantes en el pozo.
     * @return Evento de respuesta del manejador.
     * @throws RepositorioSalasException Si ocurre un error en la actualizacion de la sala.
     */
    public IniciarPartidaRespuestaEvento iniciarPartida(Sala sala, int fichasRestantes, String turnoActual) throws RepositorioSalasException {
        
        Sala salaActualizada = repositorio.actualizarSala(sala);
        if (salaActualizada == null) {
            throw new RepositorioSalasException("No se encontro la sala");
        }
        
        return new IniciarPartidaRespuestaEvento(sala, fichasRestantes, turnoActual);
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
            
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            // arboles JSON
            JsonNode salaSerializada = jsonNode.get("sala");
            
            // obtencion de los datos
            int fichasRestantesPozo = jsonNode.get("fichas_restantes").asInt();
            Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);
            
            String turnoActual = jsonNode.get("turno_actual").asText();
                    
            // creacion del evento y serilizacion del mismo.
            IniciarPartidaRespuestaEvento respuestaEvento = this.iniciarPartida(sala, fichasRestantesPozo, turnoActual);
            String eventoJSON = objectMapper.writeValueAsString(respuestaEvento);
            System.out.println("[*] Se saco al jugador de la sala...");
            
            // envio del evento.
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
