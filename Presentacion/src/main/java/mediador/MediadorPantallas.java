/*
 * MediadorPantallas.java
 */
package mediador;

import DTOS.FichaDTO;
import DTOS.JugadorDTO;
import DTOS.PartidaDTO;
import interfacesObservador.ObservadorAnhadirFicha;
import tablero.TableroGUI;
import tablero.TableroModelo;

/**
 * Clase que controla la instanciación de las pantallas de juego
 *
 * @author Juventino López García - 00000248547
 */
public class MediadorPantallas {

    private TableroModelo modelo;
    private static MediadorPantallas instance;
    private ObservadorAnhadirFicha observador;

    private MediadorPantallas() {
    }

    public static MediadorPantallas getInstance() {
        if (instance == null) {
            instance = new MediadorPantallas();
        }
        return instance;
    }

    public void mostrarPantallaJuego(PartidaDTO partida) {
        modelo = new TableroModelo();
        FichaDTO fichaIzquierda = partida.getTablero().getFichaExtremoIzquierda();
        modelo.setFichaIzquierda(fichaIzquierda);
        modelo.setFichasEnJuego(partida.getTablero().getFichas());
        modelo.setJugadores(partida.getJugadores());
        modelo.setFichaSeleccionada(modelo.getJugadores().get(0).getFichas().get(0));
        TableroGUI ventana = new TableroGUI(partida, modelo);
        ventana.setVisible(true);
    }

    public void actualizarPantalla(PartidaDTO partida) {
        FichaDTO fichaIzquierda = partida.getTablero().getFichaExtremoIzquierda();
        modelo.setFichaIzquierda(fichaIzquierda);
        modelo.setFichasEnJuego(partida.getTablero().getFichas());
        modelo.setJugadores(partida.getJugadores());
        modelo.setFichaSeleccionada(modelo.getJugadores().get(0).getFichas().get(0));
        modelo.notificar();
    }

    public TableroModelo getModelo() {
        return modelo;
    }

    public void notificarObservadores(JugadorDTO jugador, FichaDTO ficha) {
        observador.actualizar(jugador, ficha);
    }

    public void anhadirObservador(ObservadorAnhadirFicha observador) {
        this.observador = observador;
    }

}
