/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejadoress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entidades.Jugador;
import entidades.Pozo.Ficha;
import entidades.Sala;
import eventos.ObtenerTurnoFichaJugadaRespuestaEvento;
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
public class ObtenerTurnoFichaJugadaManejador extends ManejadorEvento {

    private static final RepositorioTurnos repositorio = RepositorioTurnos.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private DataOutputStream respuesta = null;

    public ObtenerTurnoFichaJugadaManejador(Socket clienteSck, String eventoSerializado) {
        this.eventoSerializado = eventoSerializado;
        this.clienteSck = clienteSck;
    }

    /**
     * Crea el evento con toda la informacion necesaria para que los demas jugadores
     * actualicen su estado del tablero y turno.
     * @param nombreSala Nombre de la sala
     * @param ficha Ficha agregada al tablero
     * @param jugador Jugador quien agrego la ficha
     * @param direccion Direccion en que se puso la ficha en el tablero (izquierda, derecha)
     * @return Evento
     * @throws RepositorioTurnoException Si ocurre un error, 
     * por ejemplo si no se encuentra la sala.
     */
    private ObtenerTurnoFichaJugadaRespuestaEvento obtenerTurnoFichaJugada(String nombreSala, Ficha ficha, Jugador jugador, String direccion) throws RepositorioTurnoException {
        String turnoActual = repositorio.obtenerJugadorSiguienteTurno(nombreSala);

        return new ObtenerTurnoFichaJugadaRespuestaEvento(
                nombreSala,
                ficha,
                jugador,
                direccion,
                turnoActual
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

            JsonNode jsonNode = objectMapper.readTree(this.eventoSerializado);
            
            // Deserializar la sala del evento recibido
            String nombreSala = jsonNode.get("sala").asText();
            // direccion de la ficha puesta en el tablero
            String direccion = jsonNode.get("direccion").asText();
            
            // ficha agregada al tablero
            JsonNode fichaSerializada = jsonNode.get("ficha");
            Ficha ficha = objectMapper.treeToValue(fichaSerializada, Ficha.class);
           
            // jugador quien puso la ficha
            JsonNode jugadorSerializado = jsonNode.get("jugador");
            Jugador jugador = objectMapper.treeToValue(jugadorSerializado, Jugador.class);
            
            // Obtener el turno actual y generar el evento de respuesta
            ObtenerTurnoFichaJugadaRespuestaEvento evento = this.obtenerTurnoFichaJugada(nombreSala, ficha, jugador, direccion);
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
