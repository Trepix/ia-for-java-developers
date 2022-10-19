package io.trepix.ia.gameoflife;

import io.trepix.ia.JPanelPainter;

public aspect UserUpdatePainter extends JPanelPainter {

    pointcut whenUserUpdate(GameOfLife gameOfLife) :
            target(gameOfLife) && call(void changeState(..));

    after(GameOfLife gameOfLife) : whenUserUpdate(gameOfLife) {
        updatePanel(thisJoinPoint);
    }

}
