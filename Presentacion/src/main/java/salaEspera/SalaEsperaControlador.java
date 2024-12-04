/*
 * SalaEsperaControlador.java
 */
package salaEspera;

import interfacesObservador.Observador;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class SalaEsperaControlador implements Observador {

    private SalaEspera vista;
    private SalaEsperaModelo modelo;

    public SalaEsperaControlador(SalaEspera vista, SalaEsperaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.anhadirObservadorSalirSala((ActionEvent e) -> {
            int confirmado = JOptionPane.showConfirmDialog(vista, "¿Deseas abandonar la partida?", "Abandonar Sala", JOptionPane.YES_NO_OPTION);
            if (confirmado == JOptionPane.OK_OPTION) {
                this.modelo.notificarObservadoresSalirSala();
                this.vista.dispose();
            }
        });

        this.vista.anhadirObservadorCambiarEstadoListo((ActionEvent e)
                -> this.modelo.notificarObservadorCambiarEstadoListo()
        );

        this.modelo.anhadirObservadorPartidaIniciada(this.vista::dispose);
    }

    @Override
    public void actualizar() {

    }

}
