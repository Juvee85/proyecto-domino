/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Pozo;
import entidades.Pozo.Ficha;
import entidades.Sala;
import eventos.CrearPozoRespuestaEvento;
import eventos.PozoErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadores.ManejadorEvento;
import repositorio.excepciones.RepositorioPozoException;
import repositorios.RepositorioPozos;

/**
 *
 * @author diana
 */
public class CrearPozoSolicitudManejador extends ManejadorEvento {
    private static final RepositorioPozos repositorio = RepositorioPozos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
    
//    
    public CrearPozoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
//    
     private CrearPozoRespuestaEvento crearPozo(Sala sala) throws RepositorioPozoException {
         return null;
//
//    int fichasTotales = repositorio.obtenerJuegoFicha(sala).size();
//
//    int jugadoresEnSala = sala.getJugadoresEnSala(); 
//    int fichasPorJugador = 4; 
//    int fichasRepartidas = jugadoresEnSala * fichasPorJugador;
//
//    int fichasRestantes = fichasTotales - fichasRepartidas;
//
//    if (fichasRestantes < 0) {
//        throw new RepositorioPozoException("No hay suficientes fichas para crear el pozo.");
//    }
//
//    // Crear el pozo en el repositorio
//    repositorio.crearPozo(sala);
//
//    // Retornar el evento con la respuesta
//    return new CrearPozoRespuestaEvento("Pozo creado exitosamente.",
//        fichasRestantes
//    );
    }

      
     private void enviaRespuestaError(String mensaje) {
        PozoErrorEvento error = new PozoErrorEvento(mensaje);

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
                    respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
                    
                    JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

                    JsonNode salaSerializada = jsonNode.get("pozo");
                    
                    Sala sala = objectMapper.treeToValue(salaSerializada, Sala.class);

                    CrearPozoRespuestaEvento evento = this.crearPozo(sala);
                    
                    String eventoJSON = objectMapper.writeValueAsString(evento);

                    respuesta.writeUTF(eventoJSON);
                    
                    respuesta.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                
                    this.enviaRespuestaError("No se pudo crear el pozo debido a un error en el servidor, por favor intente más tarde...");
                
                } catch (RepositorioPozoException ex) {
                    this.enviaRespuestaError(ex.getMessage());
                }
            }

}
    
    

