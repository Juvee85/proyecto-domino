package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import java.io.Serializable;

/**
 * Evento que se envia cuando se crean los turnos de la partida. El paso final
 * en el flujo de creacion de partida.
 *
 * @author diana
 */
public class CrearTurnosRespuestaEvento implements Serializable {

    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("sala")
    private Sala sala;

    @JsonProperty("fichas_restantes")
    private int fichasRestantes;

    @JsonProperty("turno_actual")
    private String turnoActual;

    public CrearTurnosRespuestaEvento() {

    }

    public CrearTurnosRespuestaEvento(Sala sala, String turnoActual, int fichasRestantes) {
        this.nombre = "PartidaPreparadaRespuesta";
        this.sala = sala;
        this.turnoActual = turnoActual;
        this.fichasRestantes = fichasRestantes;
    }

    /**
     * Devuelve el nombre del evento.
     *
     * @return nombre del evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del evento.
     *
     * @param nombre el nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el jugador que tiene el turno actual.
     *
     * @return el nombre del jugador en el turno actual.
     */
    public String getTurnoActual() {
        return turnoActual;
    }

    /**
     * Establece el jugador que tiene el turno actual.
     *
     * @param turnoActual el nombre del jugador a asignar como turno actual.
     */
    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getFichasRestantes() {
        return fichasRestantes;
    }

    public void setFichasRestantes(int fichasRestantes) {
        this.fichasRestantes = fichasRestantes;
    }

}
