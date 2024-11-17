/*
 * CrearSalaModelo.java
 */
package crearSala;

import DTOS.SalaDTO;
import interfacesObservador.salas.ObservadorCrearSala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class CrearSalaModelo {

    private SalaDTO sala;

    private List<ObservadorCrearSala> observadores;

    public CrearSalaModelo() {
        observadores = new ArrayList<>();
    }

    public void anhadirObservador(ObservadorCrearSala observador) {
        observadores.add(observador);
    }

    public void notificar() {
        for (ObservadorCrearSala obs : observadores) {
            obs.actualizar(getSala());
        }
    }

    /**
     * @return the sala
     */
    public SalaDTO getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
}
