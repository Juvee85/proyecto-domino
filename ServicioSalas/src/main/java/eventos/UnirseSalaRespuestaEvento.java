package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;

/**
 * Evento de respuesta para las solicitudes de unirse a una sala
 */
public class UnirseSalaRespuestaEvento {

    private final boolean exitoso;
    private final String mensaje;
    @JsonProperty("nombre_evento")
    private String nombre;
    @JsonProperty("sala")
    private Sala sala;
    @JsonProperty("nombre_sala")
    private final String nombreSala;
    @JsonProperty("id_jugador")
    private final String idJugador;

    public UnirseSalaRespuestaEvento(boolean exitoso, String mensaje, String nombreSala, String idJugador, Sala sala) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.nombreSala = nombreSala;
        this.idJugador = idJugador;
        this.nombre = "UnirseSalaRespuesta";
        this.sala = sala;
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

    public Sala getSala() {
        return sala;
    }

}
