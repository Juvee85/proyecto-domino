/*
 * SalaEsperaModelo.java
 */
package salaEspera;

import DTOS.JugadorDTO;
import interfacesObservador.Observador;
import interfacesObservador.ObservadorCambiarEstadoListo;
import interfacesObservador.ObservadorSalirSala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaModelo {

    private List<JugadorDTO> jugadores;
    private int jugadoresMaximo;
    
    private List<Observador> observadores;

    private List<ObservadorSalirSala> observadoresSalirSala;
    private ObservadorCambiarEstadoListo observadorCambiarEstado;

    public SalaEsperaModelo() {
        observadores = new ArrayList<>();
        observadoresSalirSala = new ArrayList<>();
        this.jugadoresMaximo = 0;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }
    
    public void setJugadoresMaximo(int numero) {
        this.jugadoresMaximo = numero;
    }
    
    public int getJugadoresMaximo() {
        return this.jugadoresMaximo;
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
    
    public void anhadirObservadorCambiarEstado(ObservadorCambiarEstadoListo observador) {
        this.observadorCambiarEstado = observador;
    }

    public void notificarObservadoresSalirSala() {
        for (ObservadorSalirSala observadorSalirSala : observadoresSalirSala) {
            observadorSalirSala.actualizar();
        }
    }
    
    public void notificarObservadorCambiarEstadoListo() {
        if (this.observadorCambiarEstado != null) {
            this.observadorCambiarEstado.actualizarEstadoJugador();
        }
    }
}
