package io.trepix.ia.juegoDeLaVida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

// Panel principal que gestiona el juego de la vida (su creación + su lanzamiento + actualización)
public class JuegoDeLaVidaJPanel extends JPanel implements Component, PropertyChangeListener, MouseClickListener {
    Timer timer;
    boolean enCurso = false;
    Malla tabla;
    TimerTask tarea;
    
    public JuegoDeLaVidaJPanel() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
    }

    @Override
    public void start() {
        tabla = new Malla(this.getWidth() / 3, getHeight() / 3, 0.1);
        tabla.AgregarChangeListener(this);
        timer = new Timer();
        tarea = new TimerTask() {
            @Override
            public void run() {
                tabla.Actualizar(true);
            }
        };
        timer.scheduleAtFixedRate(tarea, 0, 500);
        enCurso = true;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }
    
    public void DiseñarCelula(Graphics g, int i, int j) {
        g.fillRect(3*i-1, 3*j-1, 3, 3);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < tabla.ancho; i++) {
            for (int j = 0; j < tabla.alto; j++) {
                if (tabla.contenido[i][j]) {
                    DiseñarCelula(g, i, j);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Clic con el botón izquierdo del ratón : añadir celulas vivas
            tabla.CambiarEstado(e.getX() / 3, e.getY() / 3);
            tabla.Actualizar(false);
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            // Clic con el botón derecho del ratón : pausa el timer
            if (enCurso) {
                timer.cancel();
                timer = null;
            }
            else {
                timer = new Timer();
                tarea = new TimerTask() {
                    @Override
                    public void run() {
                        tabla.Actualizar(true);
                    }
                };
                timer.scheduleAtFixedRate(tarea, 0, 500);
            }
            enCurso = !enCurso;
        }
    }
}
