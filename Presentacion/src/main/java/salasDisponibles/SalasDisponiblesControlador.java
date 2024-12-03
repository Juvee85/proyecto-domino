/*
 * SalasDisponiblesControlador.java
 */
package salasDisponibles;

import java.awt.event.ActionEvent;

/**
 *
 * @author Juventino López García - 00000248547 - 12/11/2024
 */
public class SalasDisponiblesControlador {

    private SalasDisponiblesModelo modelo;
    private SalasDisponibles vista;

    public SalasDisponiblesControlador(SalasDisponiblesModelo modelo, SalasDisponibles vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.anhadirObservador(this::obtenerDatos);

        this.vista.anhadirObservadorUnir((ActionEvent e) -> {
            modelo.setSalaUnir(vista.obtenerSalaSeleccionada());
            vista.desplegarDialogoUnirSala();
        });

        this.vista.anhadirObservadorRegresar(e -> {
            modelo.notificarObservadoresRegresarPantalla();
            vista.dispose();
        });
    }

    public void obtenerDatos() {
        modelo.setNombreJugador(vista.getNombre());
        modelo.setContrasenha(vista.getContrasenha());
        modelo.notificarObservadores();

        vista.dispose();
    }
}
