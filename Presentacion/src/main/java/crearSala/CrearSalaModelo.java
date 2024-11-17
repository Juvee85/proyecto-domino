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

    String nombreSala;
    String contrasena;
    int maxJugadores;
    String nombreJugador;

    private List<ObservadorCrearSala> observadores;

    public CrearSalaModelo() {
        observadores = new ArrayList<>();
    }

    public void anhadirObservador(ObservadorCrearSala observador) {
        observadores.add(observador);
    }

    public void notificar() {
        SalaDTO sala = new SalaDTO();
        sala.setContrasena(contrasena);
        sala.setMaxJugadores(maxJugadores);
        sala.setNombre(nombreSala);
        
        for (ObservadorCrearSala obs : observadores) {
            obs.actualizar(sala, nombreJugador);
        }
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getMaxJugadores() {
        return maxJugadores;
    }

    public void setMaxJugadores(int maxJugadores) {
        this.maxJugadores = maxJugadores;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setObservadores(List<ObservadorCrearSala> observadores) {
        this.observadores = observadores;
    }
    
}
