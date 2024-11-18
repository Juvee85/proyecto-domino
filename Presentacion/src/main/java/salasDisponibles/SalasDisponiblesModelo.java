/*
 * SalasDisponiblesModelo.java
 */
package salasDisponibles;

import DTOS.SalaDTO;
import interfacesObservador.ObservadorUnirASala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 12/11/2024
 */
public class SalasDisponiblesModelo {

    private List<SalaDTO> salasDisponibles;

    private String nombreJugador;
    private String contrasenha;
    private SalaDTO salaUnir;

    private List<ObservadorUnirASala> observadores;

    public SalasDisponiblesModelo() {
        observadores = new ArrayList<>();
    }

    public List<SalaDTO> getSalasDisponibles() {
        return salasDisponibles;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public SalaDTO getSalaUnir() {
        return salaUnir;
    }

    public void setSalaUnir(SalaDTO salaUnir) {
        this.salaUnir = salaUnir;
    }

    public void setSalasDisponibles(List<SalaDTO> salasDisponibles) {
        this.salasDisponibles = salasDisponibles;
    }

    public void anhadirObservador(ObservadorUnirASala observador) {
        observadores.add(observador);
    }

    public void notificarObservadores() {
        for (ObservadorUnirASala observador : observadores) {
            observador.actualizar(nombreJugador, contrasenha, salaUnir);
        }
    }
}
