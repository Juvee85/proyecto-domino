/*
 * ControladorSeleccionarFicha.java
 */
package tablero;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class ControladorSeleccionarFicha {

    private TableroGUI vista;
    private TableroModelo modelo;

    public ControladorSeleccionarFicha(TableroGUI vista, TableroModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        agregarObservers();
    }

    private void agregarObservers() {
        vista.anhadirListenerPanelManoJugador(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int indice = modelo.getIndiceFichaSeleccionada();
                int size = modelo.getJugadores().get(0).getFichas().size();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE -> modelo.agregarFicha();
                    case KeyEvent.VK_LEFT -> modelo.setIndiceFichaSeleccionada((indice > 0) ? --indice : size-1);
                    case KeyEvent.VK_RIGHT -> modelo.setIndiceFichaSeleccionada((indice < size-1) ? ++indice : 0);

                }
            }

        });
    }
}
