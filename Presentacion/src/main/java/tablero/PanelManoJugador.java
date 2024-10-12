package tablero;

import DTOS.FichaDTO;
import interfacesObservador.Observador;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Panel que muestra las fichas en la mano del jugador.
 *
 * @author Juventino López García - 00000248547
 * @author Sebastian Murrieta Verduzco - 00000233463
 */
public class PanelManoJugador extends JPanel {

    private List<FichaDTO> fichasJugador;
    private TableroModelo modelo;
    private List<Observador> observers; // Lista de observadores

    // Constructor que recibe la lista de fichas del jugador
    public PanelManoJugador(TableroModelo modelo) {
        this.modelo = modelo;
        this.fichasJugador = modelo.getJugadores().get(0).getFichas();
        this.observers = new ArrayList<>();
        setBackground(Color.LIGHT_GRAY); // Color de fondo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHints(rh);
        AffineTransform at = new AffineTransform();
        at.scale(.4, .4);
        ((Graphics2D) g).transform(at);
        // Obtener el tamaño del panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Definir un tamaño fijo para las fichas
        int fichaWidth = 30;  // Ancho de la ficha
        int fichaHeight = 60; // Alto de la ficha

        // Posición inicial para dibujar las fichas
        int x = 40;
        int y = (panelHeight - fichaHeight) / 2; // Centrar las fichas verticalmente

        // Dibujar cada ficha del jugador
        for (FichaDTO ficha : fichasJugador) {
            drawDomino(ficha, (Graphics2D) g, x, y, 0);
            x += modelo.getDominoWidth() + 25; // Mover la posición para la siguiente ficha
        }
    }

    // Método para actualizar las fichas en la mano del jugador
    public void actualizarFichas(List<FichaDTO> nuevasFichas) {
        this.fichasJugador = nuevasFichas;
        repaint(); // Redibuja el panel
        notificarObservadores();
    }

    // Registrar un observador
    public void registrarObservador(Observador o) {
        observers.add(o);
    }

    // Notificar a los observadores
    private void notificarObservadores() {
        for (Observador o : observers) {
            o.actualizar(); // Notifica a los observadores cuando se actualizan las fichas
        }
    }

    // Método para obtener la lista de fichas
    public List<FichaDTO> getFichasJugador() {
        return fichasJugador;
    }

    // Método para obtener la lista de observadores
    public List<Observador> getObservers() {
        return observers;
    }

    private void drawDomino(FichaDTO ficha, Graphics2D g, int x, int y, float deg) {

        int width = modelo.getDominoWidth();
        int height = modelo.getDominoHeight();
        int arc = modelo.getDominoEdgeArc();
        int centerCircleSize = 15;

        int pieceCenterX = x + width / 2;
        int pieceCenterY = y + height / 2;

        AffineTransform oldTransform = ((Graphics2D) g).getTransform();
        ((Graphics2D) g).translate(pieceCenterX, pieceCenterY);
        ((Graphics2D) g).rotate(Math.toRadians(deg));
        ((Graphics2D) g).translate(-pieceCenterX, -pieceCenterY);

        g.setColor(modelo.getDominoPieceColor()); // White
        g.fillRoundRect(x, y, width, height, arc, arc);
        g.setStroke(new BasicStroke(4));
        g.setColor(modelo.getStrokeColor());
        g.drawRoundRect(x, y, width, height, arc, arc);
        g.drawLine(x, y + height / 2, x + width, y + height / 2);
        g.setColor(modelo.getCenterCircleColor()); //Center circle yellow thingy
        g.fillOval(x + 42, y + 92, centerCircleSize, centerCircleSize);
        g.setColor(modelo.getStrokeColor()); //Center circle stroke
        g.drawOval(x + 42, y + 92, centerCircleSize, centerCircleSize);

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
