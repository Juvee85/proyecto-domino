/*
 * JugadorUnidoASalaEvento.java
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;

/**
 *
 * @author Juventino López García - 00000248547 - 21/11/2024
 */
public class JugadorUnidoASalaEvento {

    @JsonProperty("nombre_evento")
    private String nombre;
    private String nombreSala;
    private Jugador jugador;

    public JugadorUnidoASalaEvento() {
        this.nombre = "JugadorUnidoASala";
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
