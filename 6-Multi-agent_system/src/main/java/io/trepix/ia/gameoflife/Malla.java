package io.trepix.ia.gameoflife;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// Tabla que representa el entorno del juego de la vida + las células
public class Malla {
    private final int ancho;
    private final int alto;
    private boolean[][] contenido;
    private final PropertyChangeSupport support;
    private int numIteraciones;
    
    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
        
    public Malla(Size size, double _densidad) {
        ancho = size.width();
        alto = size.height();
        Random generador = new Random();
        support = new PropertyChangeSupport(this);
        numIteraciones = 0;
        
        contenido = new boolean[ancho][alto];
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                if (generador.nextDouble() < _densidad) {
                    contenido[i][j] = true;
                }
            }
        }
    }

    public List<Cell> aliveCells() {
        List<Cell> aliveCells = new LinkedList<>();
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                if (contenido[i][j]) aliveCells.add(new Cell(i, j));
            }
        }
        return aliveCells;
    }
    
    public void CambiarEstado(int linea, int columna) {
        contenido[linea][columna] = !contenido[linea][columna];
    }
    
    public int NumVecinosVivos(int columna, int linea) {
        int i_min = Math.max(0, columna-1);
        int i_max = Math.min(ancho-1, columna+1);
        int j_min = Math.max(0, linea-1);
        int j_max = Math.min(alto-1, linea+1);
        int nb = 0;
        for (int i = i_min; i <= i_max; i++) {
            for (int j = j_min; j <= j_max; j++) {
                if (contenido[i][j] && !(i==columna && j==linea)) {
                    nb++;
                }
            }
        }
        return nb;
    }
    
    public void Actualizar(boolean conAplicacion) {
        if (conAplicacion) {
            boolean[][] nuevaTabla = new boolean[ancho][alto];
            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    int nb = NumVecinosVivos(i, j);
                    if (nb == 3 || (nb == 2 && contenido[i][j])) {
                        nuevaTabla[i][j] = true;
                    }
                }
            }
            contenido = nuevaTabla;
        }
        support.firePropertyChange("changed", this.numIteraciones, this.numIteraciones+1);
        this.numIteraciones++;
    }
}
