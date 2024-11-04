/*
 * EventoAgregarFicha.java
 */
package eventos;

import entidades.Jugador;
import entidades.Pozo.Ficha;

/**
 * 
 * @author Juventino López García - 00000248547 - 03/11/2024
 */
public class EventoAgregarFicha extends Evento {

    private Jugador jugador;
    
    private Ficha ficha;

    public EventoAgregarFicha() {
        
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }
    
    
}
