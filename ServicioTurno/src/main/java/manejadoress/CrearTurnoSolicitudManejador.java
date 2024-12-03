/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import eventos.CrearTurnoRespuestaEvento;
import eventos.TurnoErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import manejadores.ManejadorEvento;
import repositorio.RepositorioTurno;
import repositorios.excepciones.RepositorioTurnoException;

/**
 *
 * @author diana
 */
public class CrearTurnoSolicitudManejador extends ManejadorEvento{
    private static final RepositorioTurno repositorio = RepositorioTurno.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
    
    
    public CrearTurnoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
    /**
     * Crea una nueva cola de turnos para la sala especificada.
     *
     * @param sala Sala para la que se crea la cola de turnos.
     * @return Evento de respuesta con éxito.
     * @throws RepositorioTurnoException Si la cola ya existe.
     */
    private CrearTurnoRespuestaEvento crearTurno(Sala sala) throws RepositorioTurnoException {
        repositorio.crearTurno(sala);
        return new CrearTurnoRespuestaEvento(sala.getNombre(), "Turno creado exitosamente.");
    }
    
    /**
     * Envía una respuesta de error 
     *
     * @param mensaje Mensaje de error.
     */
    private void enviaRespuestaError(String mensaje) {
        TurnoErrorEvento error = new TurnoErrorEvento(mensaje);
        try {
            String errorSerializado = objectMapper.writeValueAsString(error);
            respuesta.writeUTF(errorSerializado);
            respuesta.flush();
        } catch (JsonProcessingException ex) {
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: %s".formatted(ex.getMessage()));
        } catch (IOException ex) {
            System.out.println("ERROR AL ENVIAR LA RESPUESTA DE ERROR: " + ex.getMessage());
        }
    }
    
    @Override
    public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());

            Sala sala = objectMapper.readValue(this.eventoSerializado, Sala.class);

            CrearTurnoRespuestaEvento evento = this.crearTurno(sala);
            String eventoJSON = objectMapper.writeValueAsString(evento);

            respuesta.writeUTF(eventoJSON);
            respuesta.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("Error al eliminar el pozo. Error en el servidor.");
        } catch (RepositorioTurnoException ex) {
            this.enviaRespuestaError(ex.getMessage());
        }
    }
    
}
