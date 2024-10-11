package tablero;

import DTOS.FichaDTO;
import interfacesObservador.Observador;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que muestra las fichas en la mano del jugador.
 * 
 * @author Juventino López García - 00000248547
 * @author Sebastian Murrieta Verduzco - 00000233463
 */

public class PanelManoJugador extends JPanel {

    private List<FichaDTO> fichasJugador;
    private List<Observador> observers; // Lista de observadores

    // Constructor que recibe la lista de fichas del jugador
    public PanelManoJugador() {
        this.fichasJugador = new ArrayList<>();
        this.observers = new ArrayList<>();
        setBackground(Color.LIGHT_GRAY); // Color de fondo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
            dibujarFicha(g, x, y, fichaWidth, fichaHeight, ficha);
            x += fichaWidth + 5; // Mover la posición para la siguiente ficha
        }
    }

    // Método para dibujar una ficha
    private void dibujarFicha(Graphics g, int x, int y, int width, int height, FichaDTO ficha) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, width, height, 15, 15); // Dibujar el rectángulo redondeado de la ficha
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 15, 15); // Dibujar el borde de la ficha

        // Ajustar la fuente y dibujar los puntos de la ficha
        g.setFont(new Font("Arial", Font.BOLD, 12)); // Fuente ajustada
        g.drawString(String.valueOf(ficha.getPuntosCabeza()), x + width / 4, y + height / 2);
        g.drawString(String.valueOf(ficha.getPuntosCola()), x + (3 * width / 4), y + height / 2);
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
}
