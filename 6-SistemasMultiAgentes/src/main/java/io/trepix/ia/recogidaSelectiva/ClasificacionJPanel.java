package recogidaSelectiva;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

// Panel que contiene la simulación de recogida selectiva
public class RecogidaPanel extends JPanel implements PropertyChangeListener, MouseListener {
    Timer timer;
    boolean enCurso = false;
    TimerTask tarea;
    Entorno env;
    
    public RecogidaPanel() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
    }
    
    public void Lanzar() {
        env = Entorno.getInstance();
        env.Initializar(50, 30, getWidth(), getHeight(), 3);
        env.AgregarChangeListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (enCurso) {
            // Detenemos  el timer
            timer.cancel();
            timer = null;
            enCurso = false;
        }
        else {
            // Se lanza el timer
            timer = new Timer();
            tarea = new TimerTask() {
                @Override
                public void run() {
                    env.Actualizar();
                }
            };
            timer.scheduleAtFixedRate(tarea, 0, 10);
            enCurso = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }    
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
        int agentesCargados = 0;
        for (AgenteClasificacion a : env.agentes) {
            if (a.estaCargado()) {
                agentesCargados++;
            }
        }
        System.out.println(env.residuos.size() + " - " + agentesCargados);
    }
    
    public void DiseñarAgente(AgenteClasificacion agent, Graphics g) {
        if (agent.estaCargado()) {
            g.setColor(Color.GRAY);
        }
        else {
            g.setColor(Color.BLACK);
        }
        g.fillRect((int) agent.posX - 1, (int) agent.posY - 1, 3, 3);
    }
    
    public void DiseñarResiduo(Residuo d, Graphics g) {
        // Elección de la color
        Color color;
        switch(d.type) {
            casilla 1 :    
                color = Color.RED;
                break;
            casilla 2 :
                color = Color.GREEN;
                break;
            default : 
                color = Color.BLUE;
        }
        g.setColor(color);
        // Abajoe : cuadrado
        g.fillRect((int) d.posX - 1, (int) d.posY - 1, 3, 3);
        // Zona de influencia (ronda)
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
        g.setColor(color);
        int zona = d.ZonaInfluencia();
        g.fillOval((int) d.posX - zone, (int) d.posY - zone, zona * 2, zona * 2);
    }
    
    @Override
    public void pintarComponente(Graphics g) {
        super.pintarComponente(g);
        for (AgenteClasificacion agent : env.agentes) {
            DiseñarAgente(agent, g);
        }
        for (Residuo residuo : env.residuos) {
            DiseñarResiduo(residuo, g);
        }
    }
}
