/*
 * CrearSalaModelo.java
 */
package crearSala;

import interfacesObservador.ObservadorCrearSala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class CrearSalaModelo {

    private String nombreSala;

    private String contrasenhaSala;

    private int numeroJugadores;

    private List<ObservadorCrearSala> observadores;

    public CrearSalaModelo() {
        observadores = new ArrayList<>();
    }

    
    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getContrasenhaSala() {
        return contrasenhaSala;
    }

    public void setContrasenhaSala(String contrasenhaSala) {
        this.contrasenhaSala = contrasenhaSala;
    }

    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    public void setNumeroJugadores(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public void anhadirObservador(ObservadorCrearSala observador) {
        observadores.add(observador);
    }

    public void notificar() {
        for (ObservadorCrearSala observadore : observadores) {
            observadore.actualizar(nombreSala, contrasenhaSala, numeroJugadores);
        }
    }
}
