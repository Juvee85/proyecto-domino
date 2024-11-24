package salaEspera;

import DTOS.JugadorDTO;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import interfacesObservador.Observable;
import interfacesObservador.Observador;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class SalaEspera extends javax.swing.JFrame implements Observable, Observador {

    private SalaEsperaModelo modelo;
    private List<Observador> observadores;

    /**
     * Creates new form SalaEspera
     *
     * @param modelo
     */
    public SalaEspera(SalaEsperaModelo modelo) {
        this.modelo = modelo;
        this.observadores = new ArrayList<>();

        FlatMacDarkLaf.setup();
        initComponents();
        table.setDefaultRenderer(Object.class, new TableGradientCell());
        jPanel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background");
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
        mostrarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Fondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        salirBtn = new javax.swing.JButton();
        listoBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jugadoresEnSalaLbl = new javax.swing.JLabel();
        jugadoresTotalesLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.BorderLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jugador", "Nombre", "Victorias", "Rol"
            }
        ));
        scroll.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(10);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(15);
            table.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel1.add(scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 141, 790, 350));

        Fondo.setBackground(new java.awt.Color(28, 28, 28));

        jLabel1.setFont(new java.awt.Font("Urban Jungle", 0, 48)); // NOI18N
        jLabel1.setText("Sala    de    espera");

        salirBtn.setBackground(new java.awt.Color(102, 102, 102));
        salirBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        salirBtn.setText("Salir");
        salirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirBtnActionPerformed(evt);
            }
        });

        listoBtn.setBackground(new java.awt.Color(102, 102, 102));
        listoBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        listoBtn.setText("Listo");
        listoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listoBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Jugadores :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("de");

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap(418, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(460, 460, 460))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addComponent(salirBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(listoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addComponent(jugadoresEnSalaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jugadoresTotalesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(203, 203, 203))))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jugadoresEnSalaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jugadoresTotalesLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salirBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void salirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirBtnActionPerformed
        
        
    }//GEN-LAST:event_salirBtnActionPerformed

    private void listoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listoBtnActionPerformed

    }//GEN-LAST:event_listoBtnActionPerformed

    private void mostrarTabla() {
        // TODO: Manejar errores correctamente
        if (modelo == null) {
            System.err.println("El modelo es nulo. No se puede mostrar la tabla.");
            return;
        }

        List<JugadorDTO> jugadores = modelo.getJugadores();
        if (jugadores == null || jugadores.isEmpty()) {
            System.out.println("No hay jugadores para mostrar en la tabla.");
            return;
        }

       
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpia la tabla antes de agregar filas

        for (JugadorDTO jugador : jugadores) {
            model.addRow(new Object[]{1, jugador.getNombre(), jugador.getPartidasGanadas(), "Manager"});
        }

        this.table.repaint();

        /*

        List<JugadorDTO> jugadores = modelo.getJugadores();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (JugadorDTO jugador : jugadores) {
            model.addRow(new Object[]{1, jugador.getNombre(), jugador.getPartidasGanadas(), "Manager"});
        }

        this.table.repaint();
         */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jugadoresEnSalaLbl;
    private javax.swing.JLabel jugadoresTotalesLbl;
    private javax.swing.JButton listoBtn;
    private javax.swing.JButton salirBtn;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    public void anhadirObservadorSalirSala(ActionListener l) {
        salirBtn.addActionListener(l);
    }

    @Override
    public void notificar() {
        this.observadores.forEach(obs -> obs.actualizar());
    }

    @Override
    public void anhadirObservador(Observador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void removerObservador(Observador observador) {
        this.observadores.remove(observador);
    }

    @Override
    public void actualizar() {
        mostrarTabla();
    }
}
