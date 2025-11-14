/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

/**
 *
 * @author santi
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.Bloque;
import modelo.DiscoSimulado;

public class PanelDiscoVisual extends JPanel {

    public PanelDiscoVisual() {
    }

    public void actualizarVista(DiscoSimulado disco) {
        this.removeAll();

        int totalBloques = disco.bloques.length;
        int columnas = (int) Math.ceil(Math.sqrt(totalBloques));
        int filas = (int) Math.ceil((double) totalBloques / columnas);

        this.setLayout(new GridLayout(filas, columnas, 2, 2)); 
        for (Bloque bloque : disco.bloques) {
            JLabel labelBloque = new JLabel();
            labelBloque.setOpaque(true); 
            labelBloque.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            labelBloque.setHorizontalAlignment(SwingConstants.CENTER);
            labelBloque.setText(String.valueOf(bloque.id)); 
            if (bloque.estaOcupado) {
                labelBloque.setBackground(new Color(239, 83, 80)); 
                labelBloque.setForeground(Color.WHITE);
            } else {
                labelBloque.setBackground(new Color(102, 187, 106));
                labelBloque.setForeground(Color.BLACK);
            }

            this.add(labelBloque);
        }
        
        this.revalidate();
        this.repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
