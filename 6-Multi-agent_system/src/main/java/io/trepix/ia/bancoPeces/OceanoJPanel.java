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

// La visualizaci�n de nuestra simulaci�n
public class OceanoJPanel extends JPanel implements MouseClickListener, PropertyChangeListener, Component {
    protected Oceano oceano;
    protected Timer timer;
    
    public OceanoJPanel() {
        this.setBackground(new Color(150, 255, 255));
        this.addMouseListener(this);
    }

    @Override
    public void start() {
        oceano = new Oceano(250, this.getWidth(), getHeight());
        oceano.AgregarChangeListener(this);
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                oceano.ActualizarOceano();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(tarea, 0, 15);
    }

    protected void DisenarPez(Pez p, Graphics g) {
        g.drawLine((int) p.posX, (int) p.posY, (int) (p.posX - 10 * p.velocidadX), (int) (p.posY - 10 * p.velocidadY));
    }
    
    protected void DisenarObstaculo(ZonaAEvitar o, Graphics g) {
        g.drawOval((int) (o.posX - o.radio), (int) (o.posY - o.radio), (int) o.radio * 2, (int) o.radio * 2);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Pez p : oceano.peces) {
            DisenarPez(p, g);
        }
        for (ZonaAEvitar o : oceano.obstaculos) {
            DisenarObstaculo(o, g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        oceano.AgregarObstaculo(e.getX(), e.getY(), 10);
    }

}
