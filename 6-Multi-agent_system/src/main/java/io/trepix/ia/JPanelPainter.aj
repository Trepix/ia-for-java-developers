package io.trepix.ia;

import org.aspectj.lang.JoinPoint;

import javax.swing.*;

public abstract aspect JPanelPainter {

    protected void updatePanel(JoinPoint joinPoint) {
        Object caller = joinPoint.getThis();
        if (caller instanceof JPanel panel) {
            panel.repaint();
        }
        else {
            System.out.println("Something is wrong. Probably UI is not being updated");
        }
    }
}
