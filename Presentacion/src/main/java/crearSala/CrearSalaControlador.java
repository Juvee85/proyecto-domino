/*
 * CrearSalaControlador.java
 */
package crearSala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class CrearSalaControlador {

    private CrearSala vista;

    private CrearSalaModelo modelo;

    public CrearSalaControlador(CrearSala vista, CrearSalaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        vista.anhadirCrearSalaObservador(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setNombreSala(vista.obtenerNombre());
                modelo.setContrasenhaSala(vista.obtenerContrasenha());
                modelo.setNumeroJugadores(vista.obtenerNumeroJugadores());
            }
        });
    }

}
