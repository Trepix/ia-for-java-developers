package io.trepix.ia.wastesorting;

import io.trepix.ia.Direction;
import io.trepix.ia.Position;
import io.trepix.ia.Size;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Field {
    protected Random generator;
    private final Size size;
    protected ArrayList<Garbage> garbage;
    protected List<ClassifierAgent> cleaners;
    protected int numIteraciones = 0;
    private final PropertyChangeSupport support;
    
    // Métodos
    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
        
    public Field(StartConfig startConfig) {
        garbage = new ArrayList<>();
        cleaners = new ArrayList<>();
        this.generator = startConfig.generator();
        support = new PropertyChangeSupport(this);
        this.size = startConfig.size();
        Initializar(startConfig);
    }

    private void Initializar(StartConfig startConfig) {
        double width = startConfig.size().width();
        double height = startConfig.size().height();
        garbage.clear();
        for (int i = 0; i < startConfig.garbageConfig().amountOfGarbage(); i++) {
            Garbage garbage = new Garbage(new Position(generator.nextDouble() * width, generator.nextDouble() * height), generator.nextInt(startConfig.garbageConfig().types()));
            this.garbage.add(garbage);
        }
        cleaners.clear();
        for (int i = 0; i < startConfig.agentsNumber(); i++) {
            Position position = new Position(generator.nextDouble() * width, generator.nextDouble() * height);
            Direction direction = new Direction(generator.nextDouble() - 0.5, generator.nextDouble() - 0.5);
            ClassifierAgent agent = new ClassifierAgent(position, direction, this);
            cleaners.add(agent);
        }
    }

    public double width() {
        return size.width();
    }
    public double height() {
        return size.height();
    }
    
    public void DepositarResiduo(Garbage d) {
        d.increase();
    }
    
    public Garbage TomarResiduo(Garbage d) {
        if (d.size == 1) {
            garbage.remove(d);
            return d;
        }
        else {
            Garbage carga = d.take();
            return carga;
        }
    }
    
    public void update() {
        for (ClassifierAgent agent : cleaners) {
            agent.updateDirection(garbage);
            agent.updatePosition();
        }
        support.firePropertyChange("changed", numIteraciones, numIteraciones+1);

        numIteraciones++;
        if (numIteraciones % 500 == 0) {
            Collections.reverse(garbage);
        }
    }

    public List<Garbage> garbage() {
        return garbage;
    }

    public List<ClassifierAgent> cleaners() {
        return cleaners;
    }
}
