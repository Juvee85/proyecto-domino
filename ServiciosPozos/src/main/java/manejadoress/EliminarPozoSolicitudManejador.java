/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import eventos.EliminarPozoRespuestaEvento;
import eventos.PozoErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import manejadores.ManejadorEvento;
import repositorio.excepciones.RepositorioPozoException;
import repositorios.RepositorioPozos;

/**
 *
 * @author diana
 */
public class EliminarPozoSolicitudManejador extends ManejadorEvento{
     private static final RepositorioPozos repositorio = RepositorioPozos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
     public EliminarPozoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
      private EliminarPozoRespuestaEvento eliminarPozo(Sala sala) throws RepositorioPozoException {
        repositorio.eliminarPozo(sala); 
        return new EliminarPozoRespuestaEvento(sala.getNombre(), "Pozo eliminado exitosamente.");
    }
    
    private void enviaRespuestaError(String mensaje) {
        PozoErrorEvento error = new PozoErrorEvento(mensaje);
        try {
            String errorSerializado = objectMapper.writeValueAsString(error);
            respuesta.writeUTF(errorSerializado);
            respuesta.flush();
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        }catch (IOException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        }
    }
    
    @Override
     public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());

            
            Sala sala = objectMapper.readValue(this.eventoSerializado, Sala.class);

            EliminarPozoRespuestaEvento evento = this.eliminarPozo(sala);
            String eventoJSON = objectMapper.writeValueAsString(evento);

            respuesta.writeUTF(eventoJSON);
            respuesta.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("Error al eliminar el pozo. Error en el servidor.");
        } catch (RepositorioPozoException ex) {
            this.enviaRespuestaError(ex.getMessage());
        }
    }
}  

