package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import entidades.Pozo.Ficha;

/**
 *
 * @author Saul Neri
 */
public class PrimeraFichaAgregadaRespuestaEvento {

    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("ficha")
    private Ficha ficha;

    @JsonProperty("sala")
    private String nombreSala;

    @JsonProperty("jugador")
    private Jugador jugador;

    @JsonProperty("turno_actual")
    private String turnoActual;

    public PrimeraFichaAgregadaRespuestaEvento(String nombreSala, Ficha ficha, Jugador jugador, String turnoActual) {
        this.nombre = "PrimeraFichaAgregada";
        this.ficha = ficha;
        this.nombreSala = nombreSala;
        this.jugador = jugador;
        this.turnoActual = turnoActual;
    }

    /**
     * @return the ficha
     */
    public Ficha getFicha() {
        return ficha;
    }

    /**
     * @param ficha the ficha to set
     */
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
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

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * @return the turnoActual
     */
    public String getTurnoActual() {
        return turnoActual;
    }

    /**
     * @param turnoActual the turnoActual to set
     */
    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
