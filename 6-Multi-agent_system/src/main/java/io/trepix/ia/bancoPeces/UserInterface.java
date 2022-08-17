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
    protected Oceano oceano;
    protected Timer timer;
    
    public UserInterface(Oceano ocean) {
        this.setBackground(new Color(150, 255, 255));
        this.addMouseListener(this);
        oceano = ocean;
        oceano.AgregarChangeListener(this);
    }

    @Override
    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                oceano.ActualizarOceano();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, INTERVAL_UPDATE_IN_MILLISECONDS);
    }

    protected void paintFish(Graphics g, Pez p) {
        g.drawLine((int) p.posX, (int) p.posY, (int) (p.posX - 10 * p.velocidadX), (int) (p.posY - 10 * p.velocidadY));
    }
    
    protected void paintObstacle(Graphics g, ZonaAEvitar o) {
        g.drawOval((int) (o.posX - o.radio), (int) (o.posY - o.radio), (int) o.radio * 2, (int) o.radio * 2);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Pez p : oceano.fishes()) {
            paintFish(g, p);
        }
        for (ZonaAEvitar o : oceano.obstacles()) {
            paintObstacle(g, o);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        oceano.AgregarObstaculo(e.getX(), e.getY(), 10);
    }

}
