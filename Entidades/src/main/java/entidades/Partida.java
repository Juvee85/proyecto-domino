package entidades;

import entidades.Pozo.Ficha;
import java.util.ArrayList;
import java.util.Collections;
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
        anfitrion.esAnfitrion(true);
        this.estado = EstadoPartida.EN_ESPERA;
        this.pozo = new Pozo();
    }
   
    /**
     * Agrega un nuevo jugador a la partida
     * @param jugador 
     * @return  
     */
    public boolean agregarJugador(Jugador jugador) {
        if (!this.jugadores.isEmpty() && this.jugadores.size() < this.configuracion.getMaximoJugadores()) {
            jugador.setNumero(this.jugadores.size()+1);
            return this.jugadores.add(jugador);
        }
       
        return false;
    }
    
    /**
     * @return the jugadores
     */
    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(jugadores);
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

    /**
     * @return the pozo
     */
    public Pozo getPozo() {
        return pozo;
    }
    
    public void iniciar() {
        
        if (this.jugadores.size() < 2) {
            this.estado = EstadoPartida.EN_ESPERA;
            return;
        }
        
        int fichasPorJugador = this.configuracion.getFichasPorJugador();
        
        for (Jugador j: this.jugadores) {
            List<Ficha> fichasPozo = this.getPozo().obtenerJuegoDeFichas(fichasPorJugador);
            System.out.println(fichasPozo.size());
            j.asignarFichas(fichasPozo);
        }
        
        this.estado = EstadoPartida.EN_CURSO;
    }       
}
