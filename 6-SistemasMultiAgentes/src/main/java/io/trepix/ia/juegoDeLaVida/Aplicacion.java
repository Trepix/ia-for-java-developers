package JuegoDeLaVida;

import javax.swing.JFrame;

// Ventana principal de la aplicación (y lanzamiento)
public class Aplicacion {
    public static void main(String[] args) {
        // Creación de la ventana
        JFrame ventana = new JFrame();
        ventana.setTitle("Juego de la vida");
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        // Creación del contenido
        juegoDeLaVidaJPanel panel = new juegoDeLaVidaJPanel();
        ventana.setContentPane(panel);
        // Visualización
        ventana.setVisible(true);
        panel.Lanzar();
    }
}
