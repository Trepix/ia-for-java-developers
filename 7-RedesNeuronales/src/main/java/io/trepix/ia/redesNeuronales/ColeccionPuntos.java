package redNeuronal;

import java.util.ArrayList;
import java.util.Random;

// Conjunto de las puntos usados en el algoritmo
public class ColleccionPuntos {
    protected PuntoND[] ptsAprendizaje;
    protected PuntoND[] ptsGeneralizacion;
    
    PuntoND[] getPtsAprendizaje() {
        return ptsAprendizaje;
    }
    
    PuntoND[] getPtsGeneralizacion() {
        return ptsGeneralizacion;
    }
    
    public ColleccionPuntos(String[] _contenido, int _numSalidas, double _ratioAprendizaje) {
        // Lectura del archivo total
        int numLineas = _contenido.length;
        ArrayList<PuntoND> puntos = new ArrayList();
        for (int i = 0; i < numLineas; i++) {
            puntos.add(new PuntoND(_contenido[i], _numSalidas));
        }
        
        // Creación de las puntos de aprendizaje
        int numPtsAprendizaje = (int) (numLineas * _ratioAprendizaje);
        ptsAprendizaje = new PuntoND[numPtsAprendizaje];
        Random generador = new Random();
        for (int i = 0; i < numPtsAprendizaje; i++) {
            int index = generador.nextInt(puntos.size());
            ptsAprendizaje[i] = puntos.get(index);
            puntos.remove(index);
        }
        
        // Creación de las puntos de generalización
        ptsGeneralizacion = (PuntoND[]) puntos.toArray(new PuntoND[puntos.size()]);
    }
}
