package io.trepix.ia.bancoPeces;

import javax.swing.JFrame;

// Ejecución de la ventana y de la aplicación
public class Aplicacion {
    public static void main(String[] args) {
        // Creación de la ventana
        JFrame ventana = new JFrame();
        ventana.setTitle("Banco de peces");
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        // Creación del contenido
        OceanoJPanel panel = new OceanoJPanel();
        ventana.setContentPane(panel);
        // Visualización
        ventana.setVisible(true);
        panel.Lanzar();
    }
}
