package io.trepix.ia.gameoflife;

import org.aspectj.lang.JoinPoint;

import javax.swing.JPanel;

public aspect JPanelPainterOnSystemUpdate {

    pointcut whenUserUpdate(Malla mesh) :
            target(mesh) && call(void CambiarEstado(..));

    pointcut whenUpdate(MultiAgentSystem system) :
            target(system) && call(void update());

    after(Malla mesh) : whenUserUpdate(mesh) {
        updatePanel(thisJoinPoint);
    }

    after(MultiAgentSystem system) : whenUpdate(system) {
        updatePanel(thisJoinPoint);
    }

    private void updatePanel(JoinPoint joinPoint) {
        Object caller = joinPoint.getThis();
        if (caller instanceof JPanel panel) {
            panel.repaint();
        }
        else {
            System.out.println("Something is wrong. Probably UI is not being updated");
        }
    }
}
