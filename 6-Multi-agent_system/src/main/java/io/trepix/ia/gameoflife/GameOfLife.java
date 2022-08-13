package io.trepix.ia.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLife extends JPanel implements Component, PropertyChangeListener, MouseClickListener {

    private static final int SIZE_RATIO = 3;
    public static final double ALIVE_CELLS_DENSITY = 0.1;
    public static final int INTERVAL_UPDATE_IN_MILLISECONDS = 500;
    Timer timer;
    boolean isRunning = false;
    Malla tabla;

    public GameOfLife() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
    }

    @Override
    public void start() {
        var size = new Size(super.getWidth() / SIZE_RATIO, super.getHeight() / SIZE_RATIO);
        tabla = new Malla(size, ALIVE_CELLS_DENSITY);
        tabla.AgregarChangeListener(this);
        scheduleUpdate();
        isRunning = true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }

    public void paintCell(Graphics g, Cell cell) {
        g.fillRect(SIZE_RATIO * cell.x(), SIZE_RATIO * cell.y(), SIZE_RATIO, SIZE_RATIO);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tabla.aliveCells().forEach(cell -> paintCell(g, cell));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isLeftClick(e)) {
            tabla.CambiarEstado(e.getX() / SIZE_RATIO, e.getY() / SIZE_RATIO);
            tabla.Actualizar(false);
        }

        if (isRightClick(e)) {
            if (isRunning) {
                timer.cancel();
            } else {
                scheduleUpdate();
            }
            isRunning = !isRunning;
        }
    }

    private static boolean isRightClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;
    }

    private static boolean isLeftClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1;
    }

    private void scheduleUpdate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(updateTask(), 0, INTERVAL_UPDATE_IN_MILLISECONDS);
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
