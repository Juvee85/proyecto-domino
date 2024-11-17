/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.Sala;
import eventos.ObtenerSalasRespuestaEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositorio.RepositorioSalas;

/**
 * Se encarga de manejar la solicitud con el nombre ObtenerSalasSolicitud, la cual
 * devolvera una lista con todas las salas abiertas en el sistema
 * @author Saul Neri
 */
public class ObtenerSalasSolicitudManejador extends ManejadorEvento {

    private final static RepositorioSalas repositorio = RepositorioSalas.getInstance();
           
    
    /**
     * Crea un nuevo manejador para regresar las salas activas en el sistema
     * @param clienteSck
     * @param eventoSerializado 
     */
    public ObtenerSalasSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    public List<Sala> obtenerSalas() {
        List<Sala> salasDisponibles = repositorio.getSalas();
        
        return salasDisponibles;
    }
    
    @Override
    public void run() {

        DataOutputStream respuesta = null;

        try {
            //peticion = new DataInputStream(this.clienteSck.getInputStream());
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CrearSalaSolicitudManejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);

            ObtenerSalasRespuestaEvento evento = new ObtenerSalasRespuestaEvento(this.obtenerSalas());
            
            String eventoJSON = objectMapper.writeValueAsString(evento);
            
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();

            // cierra la conexion
            this.clienteSck.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
