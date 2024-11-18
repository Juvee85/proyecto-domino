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

        vista.anhadirObservador(() -> {
            obtenerDatos();
        });

        vista.anhadirObservadorUnir((ActionEvent e) -> {
            modelo.setSalaUnir(vista.obtenerSalaSeleccionada());
            vista.desplegarDialogoUnirSala();
        });
    }

    public void obtenerDatos() {
        modelo.setNombreJugador(vista.getNombre());
        modelo.setContrasenha(vista.getContrasenha());
        modelo.notificarObservadores();
    }
}
