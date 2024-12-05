/*
 * Clase PanelTablero.java
 * Esta clase es responsable de la visualización del tablero de juego en un componente gráfico (JPanel).
 * Implementa el método de dibujo (renderizado) de las fichas en el tablero, incluyendo la disposición, el zoom,
 * y el manejo de las orientaciones de las fichas.
 */
package tablero;

import DTOS.FichaDTO;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JPanel;

/**
 * PanelTablero es un JPanel especializado que se encarga de dibujar gráficamente el estado del tablero de dominó.
 * Utiliza el modelo TableroModelo para obtener la configuración gráfica y los datos de las fichas en juego.
 * 
 * @autor Juventino López García - 00000248547 - 10/10/2024
 */
public class PanelTablero extends JPanel {

    private TableroModelo modelo;  // El modelo que contiene la lógica y configuración del tablero

    /**
     * Constructor de PanelTablero.
     * 
     * @param modelo El modelo TableroModelo que contiene la lógica del tablero.
     */
    public PanelTablero(TableroModelo modelo) {
        this.modelo = modelo;
    }

    /**
     * Sobrescribe el método paintComponent de JPanel para incluir el dibujo personalizado del tablero.
     * 
     * @param g El objeto Graphics que permite el dibujo en el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Llama al método paintComponent de la clase padre
        draw((Graphics2D) g);     // Convierte Graphics a Graphics2D para mayor funcionalidad
    }

    /**
     * Método que se encarga de dibujar el tablero de dominó y sus fichas.
     * 
     * @param g El objeto Graphics2D que permite realizar el dibujo en 2D.
     */
    public void draw(Graphics2D g) {
        setZoom(g);  // Aplica zoom al tablero según el modelo

        // Mejora la calidad de renderizado de las formas, suavizando las líneas
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);

        // Obtiene las fichas en juego del modelo
        List<FichaDTO> fichas = modelo.getFichasEnJuego();
        int xLeftOffset = modelo.getDominoStartingX();  // Posición inicial X para las fichas del lado izquierdo
        int xRightOffset = modelo.getDominoStartingX() - 200;  // Posición inicial X para las fichas del lado derecho

        boolean enLadoIzq = true;  // Indica si las fichas deben colocarse en el lado izquierdo
        if (fichas != null)
            for (FichaDTO ficha : fichas) {
            int deg = 0;  // Grados de rotación de la ficha
            if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                deg = 90;  // Rotar 90 grados si la ficha está en horizontal
            }
            if (enLadoIzq) {
                // Calcula la nueva posición X según la orientación de la ficha
                if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                    xLeftOffset = xLeftOffset - modelo.getDominoHeight();
                } else if (ficha.getOrientacion() == Orientacion.VERTICAL) {
                    xLeftOffset = xLeftOffset - (modelo.getDominoWidth() * 3) / 2;
                }
                
                // Dibuja la ficha en la posición calculada
                drawDomino(ficha, g, xLeftOffset, modelo.getDominoStartingY(), deg);
                xLeftOffset += (ficha.esMula()) ? modelo.getDominoWidth() / 2 : 0;  // Ajuste si la ficha es una "mula"
            } else {
                // Lógica similar para las fichas del lado derecho
                if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                    xRightOffset = xRightOffset + modelo.getDominoHeight();
                } else if (ficha.getOrientacion() == Orientacion.VERTICAL) {
                    xRightOffset = xRightOffset + (modelo.getDominoWidth() * 3) / 2;
                }

                drawDomino(ficha, g, xRightOffset, modelo.getDominoStartingY(), deg);
                xRightOffset -= (ficha.esMula()) ? modelo.getDominoWidth() / 2 : 0;
            }

            // Cuando se alcanza la ficha más a la izquierda, cambia al lado derecho
            if (ficha.equals(modelo.getFichaIzquierda())) {
                enLadoIzq = false;
            }
        }
    }

    /**
     * Aplica el zoom y el desplazamiento en la vista del tablero.
     * 
     * @param g El objeto Graphics2D utilizado para realizar las transformaciones de la vista.
     */
    private void setZoom(Graphics2D g) {
        AffineTransform at = new AffineTransform();

        // Obtiene los valores de desplazamiento y zoom desde el modelo
        double xOffset = modelo.getxOffset();
        double yOffset = modelo.getyOffset();
        double zoomFactor = modelo.getZoomFactor();

        // Calcula la posición del mouse relativa a la pantalla
        double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
        double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

        // Calcula la relación del zoom actual y el previo
        double zoomDiv = zoomFactor / modelo.getPrevZoomFactor();

        // Ajusta los desplazamientos en base al zoom
        xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
        yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

        modelo.setxOffset(xOffset);
        modelo.setyOffset(yOffset);

        // Aplica la transformación de zoom al gráfico
        at.scale(zoomFactor, zoomFactor);
        modelo.setPrevZoomFactor(zoomFactor);

        g.transform(at);  // Aplica la transformación de zoom
    }

    /**
     * Dibuja una ficha de dominó en una posición específica con un ángulo de rotación.
     * 
     * @param ficha La ficha que se va a dibujar.
     * @param g El objeto Graphics2D para realizar el dibujo.
     * @param x La coordenada X de la ficha.
     * @param y La coordenada Y de la ficha.
     * @param deg El ángulo de rotación en grados.
     */
    private void drawDomino(FichaDTO ficha, Graphics2D g, int x, int y, float deg) {
        int width = modelo.getDominoWidth();
        int height = modelo.getDominoHeight();
        int arc = modelo.getDominoEdgeArc();  // Radio de los bordes redondeados
        int centerCircleSize = 15;  // Tamaño del círculo central

        // Calcula el centro de la ficha para aplicar la rotación
        int pieceCenterX = x + width / 2;
        int pieceCenterY = y + height / 2;

        // Guarda la transformación original antes de rotar
        AffineTransform oldTransform = g.getTransform();
        g.translate(pieceCenterX, pieceCenterY);
        g.rotate(Math.toRadians(deg));
        g.translate(-pieceCenterX, -pieceCenterY);

        // Dibuja la ficha (rectángulo con bordes redondeados)
        g.setColor(modelo.getDominoPieceColor());  // Color de la ficha
        g.fillRoundRect(x, y, width, height, arc, arc);
        g.setStroke(new BasicStroke(4));  // Ancho del borde de la ficha
        g.setColor(modelo.getStrokeColor());
        g.drawRoundRect(x, y, width, height, arc, arc);  // Dibuja el borde
        g.drawLine(x, y + height / 2, x + width, y + height / 2);  // Dibuja la línea divisoria entre las dos mitades

        // Dibuja el círculo central
        g.setColor(modelo.getCenterCircleColor());
        g.fillOval(x + 42, y + 92, centerCircleSize, centerCircleSize);
        g.setColor(modelo.getStrokeColor());
        g.drawOval(x + 42, y + 92, centerCircleSize, centerCircleSize);

        drawPips(ficha, g, x, y);  // Dibuja los puntos (pips) de la ficha

        // Restaura la transformación original
        g.setTransform(oldTransform);
    }

    /**
     * Dibuja los puntos (pips) de una ficha de dominó en ambas mitades.
     * 
     * @param ficha La ficha a la que se le van a dibujar los pips.
     * @param g El objeto Graphics2D para dibujar los pips.
     * @param x La coordenada X donde se dibuja la ficha.
     * @param y La coordenada Y donde se dibuja la ficha.
     */
    private void drawPips(FichaDTO ficha, Graphics2D g, int x, int y) {
        int pipSpacing = modelo.getPipSpacing();
        int pipSize = modelo.getPipSize();
        int padding = modelo.getPadding();

        // Distribución de los puntos en una ficha de dominó (layout)
        int pipLayout[][] = modelo.getPipLayout();

        for (int i = 0; i < 2; i++) {
            int pipsToDraw = (i == 0) ? ficha.getPuntosCabeza() : ficha.getPuntosCola();  // Pips en cada mitad
            int pipYOffset = (i == 0) ? 0 : modelo.getDominoHeight() / 2;  // Y-offset para la segunda mitad

            // Dibuja los pips si hay al menos uno
            if (pipsToDraw > 0 && pipsToDraw <= 6) {
                // Si el número de pips es impar, dibuja uno en el centro
                if (pipsToDraw % 2 == 1) {
                    g.fillOval(x + 50 - pipSize / 2, y + 50 - pipSize / 2 + pipYOffset, pipSize, pipSize);
                    pipsToDraw--;
                }

                // Dibuja el resto de los pips de acuerdo con el layout
                if (pipsToDraw > 0) {
                    int[] pipPositions = pipLayout[pipsToDraw / 2 - 1];  // Obtiene la disposición de los pips
                    for (int j = 0; j < pipsToDraw; j++) {
                        int pipX = x + pipPositions[j] * pipSpacing + padding;
                        int pipY = switch (j) {
                            case 0, 2 -> y + 0 * pipSpacing + padding + ((i == 0) ? 0 : 100);  // Pip en la fila superior
                            case 1, 3 -> y + 2 * pipSpacing + padding + ((i == 0) ? 0 : 100);  // Pip en la fila inferior
                            default -> y + 1 * pipSpacing + padding + ((i == 0) ? 0 : 100);  // Pip en la fila media
                        };
                        g.fillOval(pipX, pipY, pipSize, pipSize);  // Dibuja el pip
                    }
                }
            }
        }
    }
}
