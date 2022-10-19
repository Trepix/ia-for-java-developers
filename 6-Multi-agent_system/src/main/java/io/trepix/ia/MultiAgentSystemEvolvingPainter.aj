package io.trepix.ia;

public aspect MultiAgentSystemEvolvingPainter extends JPanelPainter {

    pointcut whenEvolve(MultiAgentSystem system) :
            target(system) && call(void evolve());
    
    after(MultiAgentSystem system) : whenEvolve(system) {
        updatePanel(thisJoinPoint);
    }
}
