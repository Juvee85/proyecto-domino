/*
 * Clase TableroModelo.java
 * Esta clase se encarga de gestionar el modelo del tablero de juego, es decir, la lógica relacionada con
 * el estado del tablero y las fichas que están en juego. Además, contiene información sobre la configuración
 * visual de las fichas y el tablero, como el tamaño, colores y la disposición de los elementos gráficos.
 */
package tablero;

import DTOS.FichaDTO;
import DTOS.JugadorDTO;
import interfacesObservador.Observable;
import interfacesObservador.Observador;
import interfacesObservador.ObservadorAnhadirFicha;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * TableroModelo es responsable de gestionar el estado del juego y los elementos
 * gráficos necesarios para representar visualmente un tablero de dominó. Define
 * las dimensiones del tablero, las fichas y otros detalles de presentación,
 * además de proporcionar los métodos necesarios para modificar y acceder a
 * estos datos. También maneja la lista de fichas en juego y los jugadores que
 * participan.
 *
 * @autor Juventino López García - 00000248547
 */
public class TableroModelo implements Observable {

    // Información general para el dibujo del tablero
    private final int windowWidth = 1200;  // Ancho de la ventana del juego
    private final int windowHeight = 720;  // Altura de la ventana del juego
    private double zoomFactor = .6;  // Factor de zoom actual
    private double prevZoomFactor = 1;  // Factor de zoom previo
    private double xOffset = 0;  // Desplazamiento horizontal para el ajuste de la vista
    private double yOffset = 0;  // Desplazamiento vertical para el ajuste de la vista

    // Información para dibujar las fichas de dominó
    private final int dominoWidth = 100;  // Ancho de las fichas de dominó
    private final int dominoHeight = 200;  // Altura de las fichas de dominó
    private final int dominoEdgeArc = 40;  // Radio de los bordes redondeados de las fichas
    private final int dominoStartingX = (480 * 100) / 60;  // Posición inicial X para colocar las fichas
    private final int dominoStartingY = 320;  // Posición inicial Y para colocar las fichas

    // Información para dibujar los puntos (pips) en las fichas
    private final int pipSpacing = 30;  // Espacio entre los puntos
    private final int pipSize = 18;  // Tamaño de los puntos
    private final int padding = 12;  // Espaciado alrededor de los puntos
    private final int pipLayout[][] = { // Disposición de los puntos según la cantidad en cada cara
        {0, 2}, // 2 puntos (una fila)
        {0, 2, 2, 0}, // 4 puntos (dos filas)
        {0, 2, 2, 0, 0, 2} // 6 puntos (tres filas)
    };

    // Colores utilizados en el tablero y las fichas
    private final Color strokeColor = Color.BLACK;  // Color del borde de las fichas
    private final Color pipColor = Color.BLACK;  // Color de los puntos (pips)
    private final Color dominoPieceColor = new Color(252, 247, 236);  // Color de fondo de las fichas
    private final Color centerCircle = new Color(197, 185, 76);  // Color del círculo central en las fichas de dominó

    // Lista de fichas que están en juego actualmente
    private List<FichaDTO> fichasEnJuego;
    private FichaDTO fichaEnCursor;
    private FichaDTO fichaSeleccionada;
    private int indiceFichaSeleccionada;
    private List<JugadorDTO> jugadores;
    private JugadorDTO jugadorLocal;
    private String direccionJugada;
    // Referencia a la ficha más a la izquierda en el tablero
    private FichaDTO fichaIzquierda;

    // Lista de observadores para el patrón de diseño "Observer" (observar cambios en el modelo)
    private List<Observador> observadores;
    private List<ObservadorAnhadirFicha> observadoresAnhadir;
    private int cantidadFichasRestantes;

    /**
     * Constructor de TableroModelo. Inicializa la lista de observadores y deja
     * las demás propiedades con sus valores predeterminados.
     */
    public TableroModelo() {
        this.observadores = new ArrayList<>();
        this.observadoresAnhadir = new ArrayList<>();
    }

    // Métodos para acceder a los atributos del modelo
    /**
     * Obtiene la altura de la ventana de juego.
     *
     * @return Altura de la ventana en píxeles.
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Obtiene el ancho de la ventana de juego.
     *
     * @return Ancho de la ventana en píxeles.
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Obtiene el ancho de las fichas de dominó.
     *
     * @return Ancho de una ficha en píxeles.
     */
    public int getDominoWidth() {
        return dominoWidth;
    }

    /**
     * Obtiene la altura de las fichas de dominó.
     *
     * @return Altura de una ficha en píxeles.
     */
    public int getDominoHeight() {
        return dominoHeight;
    }

    /**
     * Obtiene el valor del radio de los bordes redondeados de las fichas de
     * dominó.
     *
     * @return Radio de los bordes en píxeles.
     */
    public int getDominoEdgeArc() {
        return dominoEdgeArc;
    }

    /**
     * Obtiene el espacio entre los puntos (pips) en una ficha.
     *
     * @return Espaciado entre puntos en píxeles.
     */
    public int getPipSpacing() {
        return pipSpacing;
    }

    /**
     * Obtiene el tamaño de los puntos (pips) en una ficha.
     *
     * @return Tamaño de los puntos en píxeles.
     */
    public int getPipSize() {
        return pipSize;
    }

    /**
     * Obtiene el espacio de relleno (padding) alrededor de los puntos en las
     * fichas.
     *
     * @return Padding en píxeles.
     */
    public int getPadding() {
        return padding;
    }

    /**
     * Obtiene la disposición de los puntos (pips) para diferentes cantidades.
     *
     * @return Arreglo bidimensional que define la disposición de los puntos en
     * las fichas.
     */
    public int[][] getPipLayout() {
        return pipLayout;
    }

    /**
     * Obtiene el color del borde de las fichas.
     *
     * @return Color del borde.
     */
    public Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * Obtiene el color de los puntos (pips) en las fichas.
     *
     * @return Color de los puntos.
     */
    public Color getPipColor() {
        return pipColor;
    }

    /**
     * Obtiene el color de fondo de las fichas de dominó.
     *
     * @return Color de las fichas.
     */
    public Color getDominoPieceColor() {
        return dominoPieceColor;
    }

    /**
     * Obtiene el color del círculo central en las fichas de dominó.
     *
     * @return Color del círculo central.
     */
    public Color getCenterCircleColor() {
        return centerCircle;
    }

    // Métodos relacionados con el zoom y el desplazamiento de la vista
    /**
     * Obtiene el factor de zoom actual.
     *
     * @return Valor del factor de zoom.
     */
    public double getZoomFactor() {
        return zoomFactor;
    }

    /**
     * Establece un nuevo factor de zoom.
     *
     * @param zoomFactor Nuevo valor del factor de zoom.
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Obtiene el factor de zoom anterior.
     *
     * @return Valor del factor de zoom previo.
     */
    public double getPrevZoomFactor() {
        return prevZoomFactor;
    }

    /**
     * Establece un nuevo factor de zoom previo.
     *
     * @param prevZoomFactor Nuevo valor del factor de zoom previo.
     */
    public void setPrevZoomFactor(double prevZoomFactor) {
        this.prevZoomFactor = prevZoomFactor;
    }

    /**
     * Obtiene el desplazamiento horizontal de la vista.
     *
     * @return Valor del desplazamiento horizontal.
     */
    public double getxOffset() {
        return xOffset;
    }

    /**
     * Establece un nuevo desplazamiento horizontal.
     *
     * @param xOffset Nuevo valor del desplazamiento horizontal.
     */
    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * Obtiene el desplazamiento vertical de la vista.
     *
     * @return Valor del desplazamiento vertical.
     */
    public double getyOffset() {
        return yOffset;
    }

    /**
     * Establece un nuevo desplazamiento vertical.
     *
     * @param yOffset Nuevo valor del desplazamiento vertical.
     */
    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    // Métodos relacionados con las posiciones iniciales de las fichas en el tablero
    /**
     * Obtiene la posición inicial X donde se colocan las fichas.
     *
     * @return Valor de la coordenada X inicial.
     */
    public int getDominoStartingX() {
        return dominoStartingX;
    }

    /**
     * Obtiene la posición inicial Y donde se colocan las fichas.
     *
     * @return Valor de la coordenada Y inicial.
     */
    public int getDominoStartingY() {
        return dominoStartingY;
    }

    public JugadorDTO getJugadorLocal() {
        return jugadorLocal;
    }

    public void setJugadorLocal(JugadorDTO jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    // Métodos relacionados con las fichas en juego y los jugadores
    /**
     * Obtiene la lista de fichas actualmente en juego.
     *
     * @return Lista de FichaDTO representando las fichas en el tablero.
     */
    public List<FichaDTO> getFichasEnJuego() {
        return fichasEnJuego;
    }

    public FichaDTO getFichaIzquierda() {
        return fichaIzquierda;
    }

    public void setFichaIzquierda(FichaDTO fichaIzquierda) {
        this.fichaIzquierda = fichaIzquierda;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
        if (jugadorLocal != null) {
            jugadorLocal = jugadores.stream()
                    .filter(jugador -> jugador.getNombre().equals(jugadorLocal.getNombre()))
                    .findFirst()
                    .orElse(jugadorLocal);
        }
    }

    public List<Observador> getObservers() {
        return observadores;
    }

    public void setObservers(List<Observador> observers) {
        this.observadores = observers;
    }

    public void setFichasEnJuego(List<FichaDTO> fichasEnJuego) {
        this.fichasEnJuego = fichasEnJuego;
    }

    public FichaDTO getFichaEnCursor() {
        return fichaEnCursor;
    }

    public void setFichaEnCursor(FichaDTO fichaEnCursor) {
        this.fichaEnCursor = fichaEnCursor;
    }

    public FichaDTO getFichaSeleccionada() {
        return fichaSeleccionada;
    }

    public String getDireccionJugada() {
        return direccionJugada;
    }

    public void setDireccionJugada(String direccionJugada) {
        this.direccionJugada = direccionJugada;
    }

    public void setFichaSeleccionada(FichaDTO fichaSeleccionada) {
        this.fichaSeleccionada = fichaSeleccionada;
    }

    public int getIndiceFichaSeleccionada() {
        return indiceFichaSeleccionada;
    }

    public void setIndiceFichaEnCursor(int indiceFichaSeleccionada) {
        this.indiceFichaSeleccionada = indiceFichaSeleccionada;
        fichaEnCursor = jugadorLocal.getFichas().get(indiceFichaSeleccionada);
        notificar();
    }

    @Override
    public void notificar() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }

    @Override
    public void anhadirObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void removerObservador(Observador observador) {
        observadores.remove(observador);
    }

    public void notificarAgregarFicha() {
        for (ObservadorAnhadirFicha observador : observadoresAnhadir) {
            observador.actualizar(jugadorLocal, fichaSeleccionada, direccionJugada);
        }
    }

    public void anhadirObservadorAnhadirFicha(ObservadorAnhadirFicha observador) {
        observadoresAnhadir.add(observador);
    }

    public void setCantidadFichasRestantes(int cantidadFichasRestantes) {
        this.cantidadFichasRestantes = cantidadFichasRestantes;
    }

    public int obtenerCantidadFichasRestantes() {
        return cantidadFichasRestantes;
    }
}
