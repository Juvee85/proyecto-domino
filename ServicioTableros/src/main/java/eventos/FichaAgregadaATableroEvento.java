/*
 * FichaAgregadaATableroEvento.java
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import entidades.Pozo.Ficha;
import java.io.Serializable;

/**
 *
 * @author Juventino López García - 00000248547 - 02/12/2024
 */
public class FichaAgregadaATableroEvento implements Serializable {

    @JsonProperty("nombre_evento")
    private String nombreEvento;

    @JsonProperty("sala")
    private String sala;

    @JsonProperty("ficha")
    private Ficha ficha;

    @JsonProperty("jugador")
    private Jugador jugador;

    @JsonProperty("direccion")
    private String direccion;

    public FichaAgregadaATableroEvento(String sala, Ficha ficha, Jugador jugador, String direccion) {
        this.nombreEvento = "FichaAgregadaTableroRespuesta";
        this.sala = sala;
        this.ficha = ficha;
        this.jugador = jugador;
        this.direccion = direccion;
    }

    /**
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }
    
    /**
     * @return the tablero
     */
    public Ficha getFicha() {
        return ficha;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public String getDireccion() {
        return direccion;
    }

}
