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
                int cantidadFicha = modelo.getJugadorLocal().getFichas().size();

                if (modelo.getFichaSeleccionada() == null) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_SPACE ->
                            modelo.setFichaSeleccionada(modelo.getFichaEnCursor());
                        case KeyEvent.VK_LEFT ->
                            modelo.setIndiceFichaEnCursor((indice > 0) ? --indice : cantidadFicha - 1);
                        case KeyEvent.VK_RIGHT ->
                            modelo.setIndiceFichaEnCursor((indice < cantidadFicha - 1) ? ++indice : 0);
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_SPACE ->
                            modelo.notificarAgregarFicha();
                        case KeyEvent.VK_LEFT ->
                            modelo.setDireccionJugada("Izquierda");
                        case KeyEvent.VK_RIGHT ->
                            modelo.setDireccionJugada("Derecha");
                    }
                }

            }
        });
    }
}
