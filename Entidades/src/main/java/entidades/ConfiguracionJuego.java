
package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Representa la configuracion de una partida
 * @author Equipo 1
 */
public class ConfiguracionJuego implements Serializable {
    @JsonProperty("maximo_jugadores")
    private int maximoJugadores;
    @JsonProperty("fichas_por_jugador")
    private int fichasPorJugador;
    
    /**
     * Usado para permitir la serializacion
     */
    public ConfiguracionJuego() {
        
    }
    
    public ConfiguracionJuego(int maximoJugadores, int fichasPorJugador) {
        this.maximoJugadores = maximoJugadores;
        this.fichasPorJugador = fichasPorJugador;
    }

    /**
     * @return the maximoJugadores
     */
    public int getMaximoJugadores() {
        return maximoJugadores;
    }

    /**
     * @param maximoJugadores the maximoJugadores to set
     */
    public void setMaximoJugadores(int maximoJugadores) {
        this.maximoJugadores = maximoJugadores;
    }

    /**
     * @return the fichasPorJugador
     */
    public int getFichasPorJugador() {
        return fichasPorJugador;
    }

    /**
     * @param fichasPorJugador the fichasPorJugador to set
     */
    public void setFichasPorJugador(int fichasPorJugador) {
        this.fichasPorJugador = fichasPorJugador;
    }     
}
