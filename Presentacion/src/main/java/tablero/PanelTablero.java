/*
 * PanelTablero.java
 */
package tablero;

import DTOS.FichaDTO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Juventino López García - 00000248547 - 10/10/2024
 */
public class PanelTablero extends JPanel {

    private TableroModelo modelo;

    public PanelTablero(TableroModelo modelo) {
        this.modelo = modelo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        draw((Graphics2D) g);
    }

    
    public void draw(Graphics2D g) {
        setZoom(g);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);

        List<FichaDTO> fichas = modelo.getFichasEnJuego();
        int xLeftOffset = modelo.getDominoStartingX();
        int xRightOffset = modelo.getDominoStartingX() + 150;

        boolean enLadoIzq = true;
        for (FichaDTO ficha : fichas) {
            int deg = 0;
            if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                deg = 90;
            }
            if (enLadoIzq) {
                drawDomino(ficha, g, xLeftOffset, modelo.getDominoStartingY(), deg);

                if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                    xLeftOffset = xLeftOffset - (modelo.getDominoHeight());
                } else {
                    xLeftOffset = xLeftOffset - (modelo.getDominoWidth() * 3) / 2;
                }
            } else {
                drawDomino(ficha, g, xRightOffset, modelo.getDominoStartingY(), deg);
                if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                    xRightOffset = xRightOffset + (modelo.getDominoHeight());
                } else {
                    xRightOffset = xRightOffset + (modelo.getDominoWidth() * 3) / 2;
                }
            }

            if (ficha.equals(modelo.getFichaIzquierda())) {
                enLadoIzq = false;
            }
        }
    }

    private void setZoom(Graphics2D g) {
        AffineTransform at = new AffineTransform();

        double xOffset = modelo.getxOffset();
        double yOffset = modelo.getyOffset();
        double zoomFactor = modelo.getZoomFactor();

        double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
        double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

        double zoomDiv = zoomFactor / modelo.getPrevZoomFactor();

        xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
        yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

        modelo.setxOffset(xOffset);
        modelo.setyOffset(yOffset);

        at.translate(xOffset, yOffset);
        at.scale(zoomFactor, zoomFactor);
        modelo.setPrevZoomFactor(zoomFactor);

        g.transform(at);
    }

    private void drawDomino(FichaDTO ficha, Graphics2D g, int x, int y, float deg) {

        int width = modelo.getDominoWidth();
        int height = modelo.getDominoHeight();

        int pieceCenterX = x + width / 2;
        int pieceCenterY = y + height / 2;

        AffineTransform oldTransform = ((Graphics2D) g).getTransform();
        ((Graphics2D) g).translate(pieceCenterX, pieceCenterY);
        ((Graphics2D) g).rotate(Math.toRadians(deg));
        ((Graphics2D) g).translate(-pieceCenterX, -pieceCenterY);

        g.setColor(new Color(252, 247, 236)); // White
        g.fillRoundRect(x, y, width, height, 40, 40);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 40, 40);
        g.drawLine(x, y + height / 2, x + width, y + height / 2);
        g.setColor(new Color(197, 185, 76)); //Center circle yellow thingy
        g.fillOval(x + 42, y + 92, 15, 15);
        g.setColor(Color.BLACK); //Center circle stroke
        g.drawOval(x + 42, y + 92, 15, 15);

        drawPips(ficha, g, x, y);
        ((Graphics2D) g).setTransform(oldTransform);
    }

    private void drawPips(FichaDTO ficha, Graphics2D g, int x, int y) {
        int pipSpacing = modelo.getPipSpacing();
        int pipSize = modelo.getPipSize();
        int padding = modelo.getPadding();

        int pipLayout[][] = modelo.getPipLayout();

        for (int i = 0; i < 2; i++) {
            int pipsToDraw = (i == 0) ? ficha.getPuntosCabeza() : ficha.getPuntosCola();
            int pipYOffset = (i == 0) ? 0 : modelo.getDominoHeight() / 2;

            if (pipsToDraw > 0 && pipsToDraw <= 6) {
                if (pipsToDraw % 2 == 1) {
                    g.fillOval(x + 50 - pipSize / 2, y + 50 - pipSize / 2 + pipYOffset, pipSize, pipSize);
                    pipsToDraw--;
                }

                if (pipsToDraw > 0) {
                    int[] pipPositions = pipLayout[pipsToDraw / 2 - 1];
                    for (int j = 0; j < pipsToDraw; j++) {
                        int pipX = x + pipPositions[j] * pipSpacing + padding;
                        int pipY;
                        pipY = switch (j) {
                            case 0, 2 ->
                                y + 0 * pipSpacing + padding + ((i == 0) ? 0 : 100);
                            case 1, 3 ->
                                y + 2 * pipSpacing + padding + ((i == 0) ? 0 : 100);
                            default ->
                                y + 1 * pipSpacing + padding + ((i == 0) ? 0 : 100);
                        };
                        g.fillOval(pipX, pipY, pipSize, pipSize);
                    }
                }
            }
        }
    }
}
