

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import java.io.Serializable;

/**
 * Este evento es usado para mandarse cuando el evento CrearSalaSolicitud es procesado
 * correctamente por el servicio
 * @author Saul Neri
 */
public class JugadorAbandonaSalaRespuestaEvento implements Serializable {
    
    @JsonProperty("nombre_evento")
    private String nombre;
    
    @JsonProperty("id_jugador")
    private String nombreJugador;
    
    @JsonProperty("nombre_sala")
    private String nombreSala;
    //@JsonProperty("sala")
    //private Sala sala;
    
    public JugadorAbandonaSalaRespuestaEvento(String nombreJugador, String nombreSala) {
        //this.sala = sala;
        this.nombreJugador = nombreJugador;
        this.nombreSala = nombreSala;
        this.nombre = "JugadorAbandonaSala";
    } 


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombreJugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * @param nombreJugador the nombreJugador to set
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    /**
     * @return the nombreSala
     */
    public String getNombreSala() {
        return nombreSala;
    }

    /**
     * @param nombreSala the nombreSala to set
     */
    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }
    
    
}
