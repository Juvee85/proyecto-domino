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
    
    
    
    public CrearPozoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
     private CrearPozoRespuestaEvento crearPozo(List<Ficha> fichas) throws RepositorioPozoException {

        return null;
//        repositorio.agregarFicha(ficha);
//
//        return new CrearPozoRespuestaEvento();
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
        } catch (IOException ex) {
            Logger.getLogger(CrearPozoSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

           
            JsonNode salaSerializada = jsonNode.get("pozo");

            Pozo pozo = objectMapper.treeToValue(salaSerializada, Pozo.class);

            CrearPozoRespuestaEvento evento = this.crearPozo((List<Ficha>) pozo);
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("No se puedo crear el pozo debido a un error en el servidor, porfavor intente mas tarde...");
        } catch (RepositorioPozoException ex) {
           this.enviaRespuestaError(ex.getMessage());
        }
    }
}
    
    

