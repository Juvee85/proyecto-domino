package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class Partida {
    private List<Jugador> jugadores;
    private Tablero tablero;
    private ConfiguracionJuego configuracion;
    private EstadoPartida estado;
    private Pozo pozo;
    
    public Partida(Jugador anfitrion) {
        this.jugadores = new ArrayList<>();
        this.jugadores.add(anfitrion);
        this.estado = EstadoPartida.EN_ESPERA;
        this.pozo = new Pozo();
    }
    
    /**
     * @return the jugadores
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @return the configuracion
     */
    public ConfiguracionJuego getConfiguracion() {
        return configuracion;
    }

    /**
     * @param configuracion the configuracion to set
     */
    public void setConfiguracion(ConfiguracionJuego configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return the estado
     */
    public EstadoPartida getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }
            
}
