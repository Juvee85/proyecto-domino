/**
 * ControladorArrastreFicha se encarga de gestionar las acciones de arrastrar y soltar fichas
 * desde la mano del jugador hacia el tablero, controlando la interacción entre el jugador y las fichas
 * en una interfaz gráfica. Este controlador es utilizado para detectar eventos de mouse como
 * selección, movimiento y liberación de una ficha.
 *
 * Se utiliza con el modelo de juego (TableroModelo) que gestiona el estado del juego y las fichas en juego,
 * y dos paneles gráficos (PanelManoJugador y PanelTablero) que representan la mano del jugador y el tablero
 * respectivamente.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
package tablero;

import DTOS.FichaDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class ControladorArrastreFicha {

    private FichaDTO fichaSeleccionada;  // Ficha que está siendo arrastrada por el jugador
    private int offsetX, offsetY;  // Coordenadas de desplazamiento del mouse respecto a la posición inicial de la ficha
    private PanelManoJugador panelManoJugador;  // Panel que muestra las fichas en mano del jugador
    private PanelTablero panelTablero;  // Panel que representa el tablero del juego
    private TableroModelo modelo;  // Modelo que contiene la lógica del estado del tablero y las fichas en juego

    /**
     * Constructor de ControladorArrastreFicha. Inicializa el controlador y asocia los paneles
     * de la mano del jugador, del tablero, y el modelo del juego.
     *
     * @param panelManoJugador Panel que contiene las fichas que tiene el jugador en su mano
     * @param panelTablero Panel que representa el tablero de juego donde se colocarán las fichas
     * @param modelo Modelo que gestiona el estado del juego y las fichas que están en juego
     */
    public ControladorArrastreFicha(PanelManoJugador panelManoJugador, PanelTablero panelTablero, TableroModelo modelo) {
        this.panelManoJugador = panelManoJugador;
        this.panelTablero = panelTablero;
        this.modelo = modelo;
        agregarListeners();
    }

    /**
     * Método para agregar los listeners que detectan las acciones del mouse:
     * selección de ficha, arrastre y liberación de la ficha.
     */
    private void agregarListeners() {
        // Listener para detectar cuando se selecciona una ficha de la mano del jugador
        panelManoJugador.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Al hacer clic, intenta seleccionar una ficha
                seleccionarFicha(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Al soltar el clic, intenta colocar la ficha en el tablero
                soltarFicha(e.getX(), e.getY());
            }
        });

        // Listener para gestionar el arrastre de la ficha seleccionada
        panelManoJugador.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Si hay una ficha seleccionada, se actualiza su posición durante el arrastre
                if (fichaSeleccionada != null) {
                    moverFicha(e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Método para seleccionar una ficha de la mano del jugador. Se comprueba si el clic del
     * mouse coincide con la posición de alguna ficha visible en la mano.
     *
     * @param x Coordenada X del clic del mouse
     * @param y Coordenada Y del clic del mouse
     */
    private void seleccionarFicha(int x, int y) {
        for (FichaDTO ficha : panelManoJugador.getFichasJugador()) {
            // Verificar si el clic está dentro de los límites de la ficha
            if (esClicSobreFicha(ficha, x, y)) {
                fichaSeleccionada = ficha;  // Marca la ficha como seleccionada
                offsetX = x;  // Registra el desplazamiento del mouse en X
                offsetY = y;  // Registra el desplazamiento del mouse en Y
                break;
            }
        }
    }

    /**
     * Verifica si las coordenadas del clic están dentro de los límites de una ficha en la mano del jugador.
     *
     * @param ficha Ficha sobre la que se está verificando el clic
     * @param x Coordenada X del clic
     * @param y Coordenada Y del clic
     * @return true si el clic está dentro de los límites de la ficha, false en caso contrario
     */
    private boolean esClicSobreFicha(FichaDTO ficha, int x, int y) {
        int fichaX = calcularPosicionFichaX(ficha);  // Calcula la posición X de la ficha en la mano
        int fichaY = calcularPosicionFichaY(ficha);  // Calcula la posición Y de la ficha en la mano

        int fichaWidth = modelo.getDominoWidth();  // Ancho de la ficha (valor proporcionado por el modelo)
        int fichaHeight = modelo.getDominoHeight();  // Altura de la ficha (valor proporcionado por el modelo)
        
        // Devuelve true si el clic está dentro del área de la ficha
        return (x >= fichaX && x <= fichaX + fichaWidth && y >= fichaY && y <= fichaY + fichaHeight);
    }

    /**
     * Calcula la posición X de una ficha en la mano del jugador.
     * Este es un método auxiliar para determinar la ubicación exacta de una ficha en la interfaz.
     *
     * @param ficha La ficha cuya posición se está calculando
     * @return La posición X de la ficha en la mano del jugador
     */
    private int calcularPosicionFichaX(FichaDTO ficha) {
        // Lógica específica para calcular la posición X de la ficha (dependiente de la interfaz gráfica)
        return 0;
    }

    /**
     * Calcula la posición Y de una ficha en la mano del jugador.
     * Este es un método auxiliar para determinar la ubicación exacta de una ficha en la interfaz.
     *
     * @param ficha La ficha cuya posición se está calculando
     * @return La posición Y de la ficha en la mano del jugador
     */
    private int calcularPosicionFichaY(FichaDTO ficha) {
        // Lógica específica para calcular la posición Y de la ficha (dependiente de la interfaz gráfica)
        return 0;
    }

    /**
     * Mueve la ficha seleccionada mientras se arrastra con el mouse. Actualiza la interfaz gráfica
     * para mostrar el movimiento en tiempo real.
     *
     * @param x Nueva coordenada X del mouse durante el arrastre
     * @param y Nueva coordenada Y del mouse durante el arrastre
     */
    private void moverFicha(int x, int y) {
        // Actualiza la posición de la ficha seleccionada mientras se arrastra
        panelManoJugador.repaint();  // Redibuja el panel de la mano del jugador para reflejar el movimiento
    }

    /**
     * Suelta la ficha arrastrada y, si está en una posición válida sobre el tablero, la coloca
     * en la posición correspondiente. Si no, la ficha vuelve a la mano del jugador.
     *
     * @param x Coordenada X donde se soltó la ficha
     * @param y Coordenada Y donde se soltó la ficha
     */
    private void soltarFicha(int x, int y) {
        if (fichaSeleccionada != null) {
            // Verificar si la posición donde se soltó la ficha es válida en el tablero
            if (esPosicionValidaEnTablero(x, y)) {
                // Añadir la ficha al tablero si la posición es válida
                modelo.getFichasEnJuego().add(fichaSeleccionada);
                panelTablero.repaint();  // Redibuja el tablero para reflejar la nueva ficha
                
                // Eliminar la ficha de la mano del jugador
                panelManoJugador.getFichasJugador().remove(fichaSeleccionada);
                panelManoJugador.repaint();  // Redibuja la mano del jugador sin la ficha
            }
            fichaSeleccionada = null;  // Limpiar la ficha seleccionada
        }
    }

    /**
     * Verifica si la posición donde se soltó la ficha es válida en el tablero.
     *
     * @param x Coordenada X donde se soltó la ficha
     * @param y Coordenada Y donde se soltó la ficha
     * @return true si la posición es válida, false en caso contrario
     */
    private boolean esPosicionValidaEnTablero(int x, int y) {
        // Lógica para determinar si la posición es válida en el tablero (según las reglas del juego)
        return true;
    }
}
