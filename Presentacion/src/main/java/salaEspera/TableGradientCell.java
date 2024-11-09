package salaEspera;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer personalizado para celdas de una tabla {@link JTable} con un fondo de gradiente.
 * Esta clase personaliza la forma en que se renderizan las celdas de la tabla, aplicando un gradiente
 * como fondo. Además, centra el texto dentro de las celdas.
 * 
 * <p>Las celdas tienen un fondo gradiente que cambia de color dependiendo de si están seleccionadas o no.
 * Si la celda está seleccionada, se usa un gradiente definido por los colores {@code color1} y {@code color2}.
 * Si la celda no está seleccionada, las filas pares tienen un fondo de gradiente entre dos tonos de gris oscuro.</p>
 * 
 * <p>El texto dentro de las celdas se centra tanto horizontal como verticalmente.</p>
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class TableGradientCell extends DefaultTableCellRenderer {

    /**
     * Crea un renderer con un gradiente predeterminado de colores.
     * El texto en las celdas se centra por defecto.
     */
    public TableGradientCell() {
        this(Color.decode("#787878"), Color.decode("#121212"));
        setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
    }

    /**
     * Crea un renderer con un gradiente personalizado definido por los colores proporcionados.
     * El texto en las celdas se centra por defecto.
     * 
     * @param color1 El primer color del gradiente.
     * @param color2 El segundo color del gradiente.
     */
    public TableGradientCell(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
    }

    private Color color1;  // Primer color del gradiente
    private Color color2;  // Segundo color del gradiente
    private int x;         // Coordenada X para el gradiente
    private int width;     // Ancho del gradiente
    private boolean isSelected;  // Estado de selección de la celda
    private int row;       // Fila de la celda

    /**
     * Obtiene el componente de la celda de la tabla y configura las coordenadas para el gradiente.
     * 
     * @param table La tabla donde se encuentra la celda.
     * @param value El valor a mostrar en la celda.
     * @param isSelected Indica si la celda está seleccionada.
     * @param hasFocus Indica si la celda tiene el foco.
     * @param row El número de la fila de la celda.
     * @param column El número de la columna de la celda.
     * @return El componente de la celda renderizado.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Rectangle cellRec = table.getCellRect(row, column, true);
        x = -cellRec.x;
        width = table.getWidth() - cellRec.x;
        this.isSelected = isSelected;
        this.row = row;
        return com;
    }

    /**
     * Dibuja el fondo de la celda con un gradiente y pinta el componente.
     * Si la celda está seleccionada, usa el gradiente personalizado definido por {@code color1} y {@code color2}.
     * Si no está seleccionada y la fila es par, usa un gradiente en tonos oscuros.
     * 
     * @param g El objeto gráfico utilizado para pintar la celda.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        if (isSelected) {
            g2.setPaint(new GradientPaint(x, 0, color1, width, 0, color2));
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        } else if (row % 2 == 0) {
            g2.setPaint(new GradientPaint(x, 0, Color.decode("#000000"), width, 0, Color.decode("#434343")));
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
