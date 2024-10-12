/*
 * TableroModelo.java
 */
package tablero;

import DTOS.FichaDTO;
import interfacesObservador.Observador;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juventino López García - 00000248547
 */
public class TableroModelo {

    //General drawing info
    private final int windowWidth = 1200;
    private final int windowHeight = 720;
    private double zoomFactor = .6;
    private double prevZoomFactor = 1;
    private double xOffset = 0;
    private double yOffset = 0;

    //Domino drawing info
    private final int dominoWidth = 100;
    private final int dominoHeight = 200;
    private final int dominoEdgeArc = 40;
    private final int dominoStartingX = 650;
    private final int dominoStartingY = 350;

    //Pip Drawing info
    private final int pipSpacing = 30;
    private final int pipSize = 18;
    private final int padding = 12;
    private final int pipLayout[][] = {
        {0, 2}, // 2 pips
        {0, 2, 2, 0}, // 4 pips}
        {0, 2, 2, 0, 0, 2} // 6 pips
    };

    //Colors
    private final Color strokeColor = Color.BLACK;
    private final Color pipColor = Color.BLACK;
    private final Color dominoPieceColor = new Color(252, 247, 236);
    private final Color centerCircle = new Color(197, 185, 76);

    private List<FichaDTO> fichasEnJuego;
    private FichaDTO fichaIzquierda;

    //Observers
    private List<Observador> observers;

    public TableroModelo() {
        this.observers = new ArrayList<>();
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getDominoWidth() {
        return dominoWidth;
    }

    public int getDominoHeight() {
        return dominoHeight;
    }

    public int getDominoEdgeArc() {
        return dominoEdgeArc;
    }

    public int getPipSpacing() {
        return pipSpacing;
    }

    public int getPipSize() {
        return pipSize;
    }

    public int getPadding() {
        return padding;
    }

    public int[][] getPipLayout() {
        return pipLayout;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getPipColor() {
        return pipColor;
    }

    public Color getDominoPieceColor() {
        return dominoPieceColor;
    }

    public Color getCenterCircleColor() {
        return centerCircle;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public double getPrevZoomFactor() {
        return prevZoomFactor;
    }

    public void setPrevZoomFactor(double prevZoomFactor) {
        this.prevZoomFactor = prevZoomFactor;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public int getDominoStartingX() {
        return dominoStartingX;
    }

    public int getDominoStartingY() {
        return dominoStartingY;
    }

    public List<FichaDTO> getFichasEnJuego() {
        return fichasEnJuego;
    }

    public FichaDTO getFichaIzquierda() {
        return fichaIzquierda;
    }

    public void setFichaIzquierda(FichaDTO fichaIzquierda) {
        this.fichaIzquierda = fichaIzquierda;
    }

    public List<Observador> getObservers() {
        return observers;
    }

    public void setObservers(List<Observador> observers) {
        this.observers = observers;
    }
    
    public void setFichasEnJuego(List<FichaDTO> fichasEnJuego) {
        this.fichasEnJuego = fichasEnJuego;
    }

}
