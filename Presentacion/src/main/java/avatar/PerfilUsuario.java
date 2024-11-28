/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package avatar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author diana
 */
public class PerfilUsuario extends javax.swing.JFrame {
private Color avatarColor = Color.WHITE;
    private JTextField nombreField;
    private JPanel avatarPanel;
    
    public PerfilUsuario() {
        setTitle("PERFIL DE USUARIO");
        setSize(800, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(51, 51, 51));
        
        // Panel principal con margen
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(51, 51, 51));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("PERFIL DE USUARIO");
        titulo.setFont(new Font("Arial", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Avatar
        avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dibujar círculo de fondo
                g2d.setColor(avatarColor);
                Ellipse2D circle = new Ellipse2D.Double(10, 10, 130, 130);
                g2d.fill(circle);
                
                
                
              
            }
        };
        avatarPanel.setPreferredSize(new Dimension(150, 150));
        avatarPanel.setMaximumSize(new Dimension(150, 150));
        avatarPanel.setBackground(new Color(51, 51, 51));
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(avatarPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Panel de colores
        JPanel coloresPanel = new JPanel();
        coloresPanel.setLayout(new GridLayout(2, 3, 5, 5));
        coloresPanel.setBackground(new Color(51, 51, 51));
        coloresPanel.setMaximumSize(new Dimension(150, 100));
        coloresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Color[] colores = {
            Color.RED, Color.CYAN, Color.GREEN,
            Color.YELLOW, Color.ORANGE, new Color(128, 0, 128) 
        };
        
        for (Color color : colores) {
            JButton colorBtn = new JButton();
            colorBtn.setBackground(color);
            colorBtn.setPreferredSize(new Dimension(40, 40));
            colorBtn.addActionListener(e -> {
                avatarColor = color;
                avatarPanel.repaint();
            });
            coloresPanel.add(colorBtn);
        }
        
        mainPanel.add(coloresPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Campo de nombre
        JPanel nombrePanel = new JPanel();
        nombrePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        nombrePanel.setBackground(new Color(51, 51, 51));
        nombrePanel.setMaximumSize(new Dimension(400, 40));
        
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setForeground(Color.WHITE);
        nombreField = new JTextField(20);
        nombrePanel.add(nombreLabel);
        nombrePanel.add(nombreField);
        
        mainPanel.add(nombrePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 140));
        botonesPanel.setBackground(new Color(51, 51, 51));
        
        JButton volverBtn = new JButton("Volver");
        JButton unirseBtn = new JButton("Unirse");
        
        // Estilo de botones
        for (JButton btn : new JButton[]{volverBtn, unirseBtn}) {
            btn.setPreferredSize(new Dimension(100, 35));
            btn.setBackground(new Color(69, 69, 69));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            btn.setFocusPainted(false);
        }
        
        volverBtn.addActionListener(e -> System.exit(0));
        unirseBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "¡Bienvenido " + nombreField.getText() + "!"));
        
        botonesPanel.add(volverBtn);
        botonesPanel.add(unirseBtn);
        
        mainPanel.add(botonesPanel);
        
        add(mainPanel);
        setLocationRelativeTo(null);
    }
    /**
     * Creates new form PerfilUsuario
     */
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerfilUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            PerfilUsuario ventana = new PerfilUsuario();
            ventana.setVisible(true);
        });
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
