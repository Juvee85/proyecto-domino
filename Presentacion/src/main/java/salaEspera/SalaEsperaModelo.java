/*
 * SalaEsperaModelo.java
 */
package salaEspera;

import DTOS.JugadorDTO;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaModelo {

    private List<JugadorDTO> jugadores;

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }
    
}
