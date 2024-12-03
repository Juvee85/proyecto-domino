/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Sala;
import eventos.AsignarTurnoRespuestaEvento;
import java.io.DataOutputStream;
import java.net.Socket;
import manejadores.ManejadorEvento;
import repositorio.RepositorioTurno;
import repositorios.excepciones.RepositorioTurnoException;

/**
 *
 * @author diana
 */
public class AsignarTurnoSolicitudManejador extends ManejadorEvento {
    private static final RepositorioTurno repositorio = RepositorioTurno.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;
    
    
    
    public AsignarTurnoSolicitudManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }
    
     /**
     * Asigna un turno a la sala especificada.
     *
     * @param sala Sala a la que se asignará un turno.
     * @return Evento de respuesta con el turno asignado.
     * @throws RepositorioTurnoException Si no existe una cola para la sala.
     */
    private AsignarTurnoRespuestaEvento asignarTurno(Sala sala) throws RepositorioTurnoException {
        String turno = repositorio.asignarTurno(sala);
        return new AsignarTurnoRespuestaEvento(sala.getNombre(), turno, "Turno asignado exitosamente.");
    }

}
