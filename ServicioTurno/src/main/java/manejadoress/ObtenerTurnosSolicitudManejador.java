/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import eventos.ObtenerTurnosRespuestaEvento;
import eventos.TurnoErrorEvento;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import manejadores.ManejadorEvento;
import repositorio.CyclicList;
import repositorio.RepositorioTurnos;
import repositorios.excepciones.RepositorioTurnoException;

/**
 *
 * @author diana
 */
public class ObtenerTurnosSolicitudManejador extends ManejadorEvento {
    private static final RepositorioTurnos repositorio = RepositorioTurnos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
    
    
    public ObtenerTurnosSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
     /**
     * Obtiene el turno actual de la sala especificada.
     *
     * @param sala Sala de la cual se desea obtener el turno actual.
     * @return Evento de respuesta con el turno actual.
     */
    private ObtenerTurnosRespuestaEvento obtenerTurno(Sala sala) throws RepositorioTurnoException {
       CyclicList<String> turnos = repositorio.obtenerTurnos(sala);
    
    // Obtener el jugador actual
    String turnoActual = turnos.current();
    
    // Obtener el siguiente turno usando next(), que avanza el índice
    String turnoSiguiente = turnos.next();
    
    // Restaurar el índice a su posición original
    turnos.add(turnoSiguiente); 
    turnos.next(); 

    return new ObtenerTurnosRespuestaEvento(
        sala.getNombre(),
        turnoActual,
        turnoSiguiente,
        "Turno obtenido exitosamente."
    );

}

/**
     * Envía una respuesta de error al cliente.
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
            System.out.println("ERROR AL MANDAR LA RESPUESTA DE ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ERROR AL ENVIAR LA RESPUESTA DE ERROR: " + ex.getMessage());
        }
    }
    
    @Override
     public void run() {
        try {
            respuesta = new DataOutputStream(this.clienteSck.getOutputStream());

            // Deserializar la sala del evento recibido
            Sala sala = objectMapper.readValue(this.eventoSerializado, Sala.class);

            // Obtener el turno actual y generar el evento de respuesta
            ObtenerTurnosRespuestaEvento evento = this.obtenerTurno(sala);
            String eventoJSON = objectMapper.writeValueAsString(evento);

            // Enviar respuesta al cliente
            respuesta.writeUTF(eventoJSON);
            respuesta.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.enviaRespuestaError("Error al obtener el turno. Error en el servidor.");
        } catch (RepositorioTurnoException ex) {
            this.enviaRespuestaError(ex.getMessage());
        }
    }
}
