package bancoPeces;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

// La visualización de nuestra simulación
public class OceanoJPanel extends JPanel implements MouseListener, PropertyChangeListener {
    protected Oceano oceano;
    protected Timer timer;
    
    public OceanoJPanel() {
        this.setBackground(new Color(150, 255, 255));
        this.addMouseListener(this);
    }
    
    public void Lanzar() {
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

    protected void DiseñarPez(Pez p, Graphics g) {
        g.drawLine((int) p.posX, (int) p.posY, (int) (p.posX - 10 * p.velocidadX), (int) (p.posY - 10 * p.velocidadY));
    }
    
    protected void DiseñarObstaculo(ZonaAEvitar o, Graphics g) {
        g.drawOval((int) (o.posX - o.radio), (int) (o.posY - o.radio), (int) o.radio * 2, (int) o.radio * 2);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }
    
    @Override
    public void pintarComponente(Graphics g) {
        super.pintarComponente(g);
        for (Pez p : oceano.peces) {
            DiseñarPez(p, g);
        }
        for (ZonaAEvitar o : oceano.obstaculos) {
            DiseñarObstaculo(o, g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        oceano.AgregarObstaculo(e.getX(), e.getY(), 10);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
