/*
 * InicioControlador.java
 */
package inicio;

import java.awt.event.ActionEvent;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public class InicioControlador {

    private Inicio vista;
    private InicioModelo modelo;

    public InicioControlador(Inicio vista, InicioModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        vista.anhadirCrearSalaObservador((ActionEvent e) -> {
            modelo.notificarCrearSala();
            vista.dispose();
        });

        vista.anhadirUnirSalaObservador((ActionEvent e) -> {
            modelo.notificarSalasDisponibles();
            vista.dispose();
        });

        vista.anhadirSalirObservador(e -> System.exit(0));
    }

}
