package io.trepix.ia.fishschool;

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
                ocean.evolve();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, INTERVAL_UPDATE_IN_MILLISECONDS);
    }

    protected void paintFish(Graphics g, Fish p) {
        Direction direction = p.direction();
        Position position = p.position();
        g.drawLine((int) position.x(), (int) position.y(), (int) (position.x() - 10 * direction.x()), (int) (position.y() - 10 * direction.y()));
    }
    
    protected void paintObstacle(Graphics g, Obstacle o) {
        g.drawOval((int) (o.x() - o.radius()), (int) (o.y() - o.radius()), (int) o.radius() * 2, (int) o.radius() * 2);
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
        ocean.addObstacleAt(new Position(e.getX(), e.getY()));
    }

}
