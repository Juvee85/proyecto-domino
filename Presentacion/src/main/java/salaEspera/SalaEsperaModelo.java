/*
 * SalaEsperaModelo.java
 */
package salaEspera;

import DTOS.JugadorDTO;
import interfacesObservador.Observador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaModelo {

    private List<JugadorDTO> jugadores;

    private List<Observador> observadores;

    public SalaEsperaModelo() {
        observadores = new ArrayList<>();
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    public void anhadirObservador(Observador observador) {
        observadores.add(observador);
    }
    
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }

}
