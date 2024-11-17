/*
 * CrearSalaControlador.java
 */
package crearSala;

import interfacesObservador.Observador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class CrearSalaControlador implements Observador {

    private CrearSala vista;

    private CrearSalaModelo modelo;

    public CrearSalaControlador(CrearSala vista, CrearSalaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.anhadirObservador(this);

    }

    @Override
    public void actualizar() {
        String nombreSala = this.vista.nombreSalaTxt.getText();
        String contrasena = this.vista.contraSalaTxt.getText();
        int maxJugadores = (Integer) this.vista.noJugadoresSalaTxt.getValue();
        
        //modelo.set
        modelo.setContrasenhaSala(contrasena);
        modelo.setNumeroJugadores(maxJugadores);
        
        modelo.notificar();
    }
}
