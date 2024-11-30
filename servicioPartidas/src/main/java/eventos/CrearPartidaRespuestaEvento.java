package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import java.io.Serializable;
import java.util.List;

/**
 * Evento de respuesta para la creación de una nueva partida
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class CrearPartidaRespuestaEvento implements Serializable {

    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("sala_id")
    private String salaId;

    @JsonProperty("jugadores")
    private List<Jugador> jugadores;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("mensaje")
    private String mensaje;

    public CrearPartidaRespuestaEvento() {
        this.nombre = "CrearPartidaRespuesta";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
