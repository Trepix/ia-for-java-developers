package io.trepix.ia.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterface extends JPanel implements Component, PropertyChangeListener, MouseClickListener {

    private static final int SIZE_RATIO = 3;
    public static final int INTERVAL_UPDATE_IN_MILLISECONDS = 500;
    Timer timer;
    Malla tabla;

    public UserInterface(Malla gameOfLife) {
        tabla = gameOfLife;
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
    }

    public static Size applyRatio(Size size) {
        return size.resize(SIZE_RATIO);
    }

    @Override
    public void start() {
        tabla.AgregarChangeListener(this);
        resume();
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
            if (isRunning()) {
                stop();
            } else {
                resume();
            }
        }
    }

    private static boolean isRightClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;
    }

    private static boolean isLeftClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1;
    }

    private boolean isRunning() {
        return timer != null;
    }

    private void stop() {
        timer.cancel();
        timer = null;
    }

    private void resume() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tabla.Actualizar(true);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, INTERVAL_UPDATE_IN_MILLISECONDS);
    }

}