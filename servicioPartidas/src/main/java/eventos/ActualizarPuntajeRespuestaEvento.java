package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Evento de respuesta para la actualización de puntaje de un jugador
 *
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */

public class ActualizarPuntajeRespuestaEvento implements Serializable {

    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("sala_id")
    private String salaId;

    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty("puntaje_actualizado")
    private int puntajeActualizado;

    public ActualizarPuntajeRespuestaEvento() {
        this.nombre = "ActualizarPuntajeRespuesta";
    }

    // Getters and setters for all properties
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPuntajeActualizado() {
        return puntajeActualizado;
    }

    public void setPuntajeActualizado(int puntajeActualizado) {
        this.puntajeActualizado = puntajeActualizado;
    }
}
