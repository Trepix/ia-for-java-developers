package io.trepix.ia.bancoPeces;

import io.trepix.ia.MouseClickListener;
import io.trepix.ia.Component;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterface extends JPanel implements MouseClickListener, PropertyChangeListener, Component {

    public static final int INTERVAL_UPDATE_IN_MILLISECONDS = 15;
    protected Ocean ocean;
    protected Timer timer;
    
    public UserInterface(Ocean ocean) {
        this.setBackground(new Color(150, 255, 255));
        this.addMouseListener(this);
        this.ocean = ocean;
        this.ocean.AgregarChangeListener(this);
    }

    @Override
    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ocean.ActualizarOceano();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, INTERVAL_UPDATE_IN_MILLISECONDS);
    }

    protected void paintFish(Graphics g, Fish p) {
        g.drawLine((int) p.posX, (int) p.posY, (int) (p.posX - 10 * p.velocidadX), (int) (p.posY - 10 * p.velocidadY));
    }
    
    protected void paintObstacle(Graphics g, Obstacle o) {
        g.drawOval((int) (o.posX - o.radio), (int) (o.posY - o.radio), (int) o.radio * 2, (int) o.radio * 2);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Fish fish : ocean.fishes()) {
            paintFish(g, fish);
        }
        for (Obstacle obstacle : ocean.obstacles()) {
            paintObstacle(g, obstacle);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ocean.AgregarObstaculo(e.getX(), e.getY(), 10);
    }

}
