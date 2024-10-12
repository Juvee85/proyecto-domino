package tablero;

import DTOS.FichaDTO;
import DTOS.JugadorDTO;
import DTOS.PartidaDTO;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TableroGUI extends javax.swing.JFrame {

    private PanelManoJugador panelManoJugador;
    private PartidaDTO partida;
    private TableroModelo modelo;
    private PanelTablero panelTablero;
    private PanelManoJugador mano;

    /**
     * Creates new form TableroGUI
     *
     * @param partida
     */
    public TableroGUI(PartidaDTO partida, TableroModelo modelo) {
        initComponents();
        this.modelo = modelo;
        this.partida = partida;

        // Inicializar el panel de la mano del jugador
        panelManoJugador = new PanelManoJugador(modelo);
        manoJugador.add(panelManoJugador);  // Agregar el panel al contenedor manoJugador
        panelManoJugador.setSize(manoJugador.getSize());
        panelManoJugador.setOpaque(false);

        // Inicializar el panel del tablero
        panelTablero = new PanelTablero(modelo);
        tableroDomino.add(panelTablero);
        panelTablero.setSize(tableroDomino.getSize());
        panelTablero.setOpaque(false);

        this.mostrarJugadores();
    }

    private void mostrarJugadores() {
        List<JugadorDTO> jugadores = this.partida.getJugadores();

        for (JugadorDTO j : jugadores) {
            /*
            if (j.esAnfitrion())
                this.player1.setEnabled(true);
            else if (j.getNumero() == 2)
                this.player2.setEnabled(true);
            else if (j.getNumero() == 3)
                this.player3.setEnabled(true);
            else if (j.getNumero() == 4)
                this.player4.setEnabled(true);
             */
            if (j.esAnfitrion()) {
                this.player1.setVisible(true);
            } else if (j.getNumero() == 2) {
                this.player2.setVisible(true);
            } else if (j.getNumero() == 3) {
                this.player3.setVisible(true);
            } else if (j.getNumero() == 4) {
                this.player4.setVisible(true);
            }
        }

    }

    public void actualizarManoJugador(List<FichaDTO> fichasJugador) {
        panelManoJugador.actualizarFichas(fichasJugador);  // Actualizar fichas en el panel
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        player2 = new tablero.PanelRound();
        player3 = new tablero.PanelRound();
        player4 = new tablero.PanelRound();
        player1 = new tablero.PanelRound();
        tableroDomino = new tablero.PanelRound();
        Fondo = new javax.swing.JPanel();
        manoJugador = new tablero.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1163, 731));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        player2.setBackground(new java.awt.Color(255, 255, 102));
        player2.setRoundBottomLeft(100);
        player2.setRoundBottomRight(100);
        player2.setRoundTopLeft(100);
        player2.setRoundTopRight(100);

        javax.swing.GroupLayout player2Layout = new javax.swing.GroupLayout(player2);
        player2.setLayout(player2Layout);
        player2Layout.setHorizontalGroup(
            player2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        player2Layout.setVerticalGroup(
            player2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(player2, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 36, -1, -1));

        player3.setBackground(new java.awt.Color(0, 153, 255));
        player3.setRoundBottomLeft(100);
        player3.setRoundBottomRight(100);
        player3.setRoundTopLeft(100);
        player3.setRoundTopRight(100);

        javax.swing.GroupLayout player3Layout = new javax.swing.GroupLayout(player3);
        player3.setLayout(player3Layout);
        player3Layout.setHorizontalGroup(
            player3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        player3Layout.setVerticalGroup(
            player3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(player3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1025, 36, -1, -1));

        player4.setBackground(new java.awt.Color(204, 0, 0));
        player4.setRoundBottomLeft(100);
        player4.setRoundBottomRight(100);
        player4.setRoundTopLeft(100);
        player4.setRoundTopRight(100);

        javax.swing.GroupLayout player4Layout = new javax.swing.GroupLayout(player4);
        player4.setLayout(player4Layout);
        player4Layout.setHorizontalGroup(
            player4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        player4Layout.setVerticalGroup(
            player4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(player4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1025, 581, -1, -1));

        player1.setBackground(new java.awt.Color(51, 255, 51));
        player1.setRoundBottomLeft(100);
        player1.setRoundBottomRight(100);
        player1.setRoundTopLeft(100);
        player1.setRoundTopRight(100);

        javax.swing.GroupLayout player1Layout = new javax.swing.GroupLayout(player1);
        player1.setLayout(player1Layout);
        player1Layout.setHorizontalGroup(
            player1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        player1Layout.setVerticalGroup(
            player1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(player1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 581, -1, -1));

        tableroDomino.setBackground(new java.awt.Color(51, 51, 51));
        tableroDomino.setRoundBottomLeft(50);
        tableroDomino.setRoundBottomRight(50);
        tableroDomino.setRoundTopLeft(50);
        tableroDomino.setRoundTopRight(50);

        javax.swing.GroupLayout tableroDominoLayout = new javax.swing.GroupLayout(tableroDomino);
        tableroDomino.setLayout(tableroDominoLayout);
        tableroDominoLayout.setHorizontalGroup(
            tableroDominoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 863, Short.MAX_VALUE)
        );
        tableroDominoLayout.setVerticalGroup(
            tableroDominoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        getContentPane().add(tableroDomino, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 107, -1, 500));

        Fondo.setBackground(new java.awt.Color(28, 28, 28));

        manoJugador.setBackground(new java.awt.Color(102, 102, 102));
        manoJugador.setRoundBottomLeft(50);
        manoJugador.setRoundBottomRight(50);
        manoJugador.setRoundTopLeft(50);
        manoJugador.setRoundTopRight(50);

        javax.swing.GroupLayout manoJugadorLayout = new javax.swing.GroupLayout(manoJugador);
        manoJugador.setLayout(manoJugadorLayout);
        manoJugadorLayout.setHorizontalGroup(
            manoJugadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 309, Short.MAX_VALUE)
        );
        manoJugadorLayout.setVerticalGroup(
            manoJugadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(manoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(700, Short.MAX_VALUE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addContainerGap(619, Short.MAX_VALUE)
                .addComponent(manoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private tablero.PanelRound manoJugador;
    private tablero.PanelRound player1;
    private tablero.PanelRound player2;
    private tablero.PanelRound player3;
    private tablero.PanelRound player4;
    private tablero.PanelRound tableroDomino;
    // End of variables declaration//GEN-END:variables
}
