/*
 * CrearSalaControlador.java
 */
package crearSala;

import interfacesObservador.Observador;

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
        String nombreSala = this.vista.obtenerNombre();
        String contrasena = this.vista.obtenerContrasenha();
        int maxJugadores = this.vista.obtenerNumeroJugadores();
        String nombreJugador = this.vista.obtenerNombreJugador();

        modelo.setNombreSala(nombreSala);
        modelo.setContrasena(contrasena);
        modelo.setMaxJugadores(maxJugadores);
        modelo.setNombreJugador(nombreJugador);

        modelo.notificar();
    }
}
