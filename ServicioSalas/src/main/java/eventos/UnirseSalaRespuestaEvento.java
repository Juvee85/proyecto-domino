package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import java.util.List;

/**
 * Evento de respuesta para las solicitudes de unirse a una sala
 */
public class UnirseSalaRespuestaEvento {

    private final boolean exitoso;
    private final String mensaje;
    @JsonProperty("nombre_evento")
    private String nombre;
    private List<Jugador> jugadores;
    private final String nombreSala;
    private final String idJugador;

    public UnirseSalaRespuestaEvento(boolean exitoso, String mensaje, String nombreSala, String idJugador) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.nombreSala = nombreSala;
        this.idJugador = idJugador;
        this.nombre = "UnirseSalaRespuesta";
    }

    // Getters necesarios para la serialización JSON
    public boolean isExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

}
