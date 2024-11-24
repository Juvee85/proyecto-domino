/*
 * SalaEsperaModelo.java
 */
package salaEspera;

import DTOS.JugadorDTO;
import interfacesObservador.Observador;
import interfacesObservador.ObservadorSalirSala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaModelo {

    private List<JugadorDTO> jugadores;

    private List<Observador> observadores;
    
    private List<ObservadorSalirSala> observadoresSalirSala;

    public SalaEsperaModelo() {
        observadores = new ArrayList<>();
        observadoresSalirSala = new ArrayList<>();
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
    
    public void anhadirObservadorSalirSala(ObservadorSalirSala observador) {
        observadoresSalirSala.add(observador);
    }

    public void notificarObservadoresSalirSala() {
        for (ObservadorSalirSala observadorSalirSala : observadoresSalirSala) {
            observadorSalirSala.actualizar();
        }
    }
}
