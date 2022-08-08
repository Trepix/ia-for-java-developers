package io.trepix.ia.recogidaSelectiva;

import javax.swing.JFrame;

// Clase que contiene el main y crea la ventana + lanza la simulation
public class Aplicacion {
    public static void main(String[] args) {
        // Creación de la ventana
        JFrame ventana = new JFrame();
        ventana.setTitle("Recogida selectiva");
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        // Creación del contenido
        ClasificacionJPanel panel = new ClasificacionJPanel();
        ventana.setContentPane(panel);
        // Visualización
        ventana.setVisible(true);
        panel.Lanzar();
    }
}
