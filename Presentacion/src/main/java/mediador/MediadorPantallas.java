/*
 * MediadorPantallas.java
 */
package mediador;

import DTOS.FichaDTO;
import DTOS.PartidaDTO;
import tablero.TableroGUI;
import tablero.TableroModelo;

/**
 * Clase que controla la instanciación de las pantallas de juego 
 * 
 * @author Juventino López García - 00000248547
 */
public class MediadorPantallas {

    public static void mostrarPantallaJuego(PartidaDTO partida) {
        TableroModelo modelo = new TableroModelo();
        FichaDTO fichaIzquierda = partida.getTablero().getFichaExtremoIzquierda();
        modelo.setFichaIzquierda(fichaIzquierda);
        modelo.setFichasEnJuego(partida.getTablero().getFichas());
        modelo.setJugadores(partida.getJugadores());
        TableroGUI ventana = new TableroGUI(partida, modelo);
        ventana.setVisible(true);
    }
}
