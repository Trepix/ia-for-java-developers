package io.trepix.ia;

import io.trepix.ia.gameoflife.GameOfLife;
import org.aspectj.lang.JoinPoint;

import javax.swing.JPanel;

public aspect JPanelPainterOnSystemChanges {

    pointcut whenUserUpdate(GameOfLife gameOfLife) :
            target(gameOfLife) && call(void changeState(..));

    pointcut whenEvolve(MultiAgentSystem system) :
            target(system) && call(void evolve());

    after(GameOfLife gameOfLife) : whenUserUpdate(gameOfLife) {
        updatePanel(thisJoinPoint);
    }

    after(MultiAgentSystem system) : whenEvolve(system) {
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
