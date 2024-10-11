
package entidades;

import java.io.Serializable;

/**
 *
 * @author
 */
public class ConfiguracionJuego implements Serializable {
    private int maximoJugadores;
    private int fichasPorJugador;
    
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
