package io.trepix.ia.juegoDeLaVida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;
public class GameOfLife extends JPanel implements Component, PropertyChangeListener, MouseClickListener {

    private static final int SIZE_RATIO = 3;
    Timer timer;
    boolean enCurso = false;
    Malla tabla;
    TimerTask tarea;
    
    public GameOfLife() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
    }

    @Override
    public void start() {
        tabla = new Malla(this.getWidth() / SIZE_RATIO, getHeight() / SIZE_RATIO, 0.1);
        tabla.AgregarChangeListener(this);
        scheduleUpdate();
        enCurso = true;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }
    
    public void printCell(Graphics g, int i, int j) {
        g.fillRect(SIZE_RATIO*i, SIZE_RATIO*j, SIZE_RATIO, SIZE_RATIO);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < tabla.ancho; i++) {
            for (int j = 0; j < tabla.alto; j++) {
                if (tabla.contenido[i][j]) {
                    printCell(g, i, j);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Clic con el botón izquierdo del ratón : añadir celulas vivas
            tabla.CambiarEstado(e.getX() / SIZE_RATIO, e.getY() / SIZE_RATIO);
            tabla.Actualizar(false);
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            // Clic con el botón derecho del ratón : pausa el timer
            if (enCurso) {
                timer.cancel();
            }
            else {
                scheduleUpdate();
            }
            enCurso = !enCurso;
        }
    }

    private void scheduleUpdate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(updateTask(), 0, 500);
    }

    private TimerTask updateTask() {
        return new TimerTask() {
            @Override
            public void run() {
                tabla.Actualizar(true);
            }
        };
    }
}
