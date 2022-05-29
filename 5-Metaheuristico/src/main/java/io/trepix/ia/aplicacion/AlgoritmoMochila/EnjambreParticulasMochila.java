package application.AlgoritmosMochila;

import Metaheuristico.Algoritmos.EnjambreParticular;
import Metaheuristico.ISolucion;
import application.Caja;
import application.ProblemaMochila;
import application.SolucionMochila;
import java.util.ArrayList;

// Enjambre particular para el problema de la mochila
public class EnjambreParticularMochila extends EnjambreParticular {
    protected int numIteraciones = 0;
    private final static int NUM_MAX_ITERACIONES = 200;
    
    @Override
    protected void ActualizarSoluciones() {
        for (ISolucion sol : soluciones) {
            SolucionMochila solucion = (SolucionMochila) sol;
            if (!solucion.equals(mejorSolucion)) {
                // Añadir un elemento de los mejores
                solucion = AgregarElemento(solucion, mejorSolucion);
                solucion = AgregarElemento(solucion, mejorActual);
                // Disminución del peso si es necesario
                int index;
                while (solucion.getPeso() > ((ProblemaMochila)problema).pesosMax) {
                    index = ProblemaMochila.generador.nextInt(solucion.contenido.size());
                    solucion.contenido.remove(index);
                }
                // Para terminar, se completa
                solucion = Completar(solucion);
            }
        }
    }
    
    protected SolucionMochila AgregarElemento(SolucionMochila solucion, ISolucion solucionSource) {
        int index = ProblemaMochila.generador.nextInt(((SolucionMochila)solucionSource).contenido.size());
        Caja b = ((SolucionMochila)solucionSource).contenido.get(index);
        if (!solucion.contenido.contains(b)) {
            solucion.contenido.add(b);
        }
        return solucion;
    }
    
    protected SolucionMochila Completar(SolucionMochila solucion) {
        double espacioDispo = ((ProblemaMochila)problema).pesosMax - solucion.getPeso();
        ArrayList<Caja> cajasPosibles = ((ProblemaMochila)problema).Cajas();
        cajasPosibles.removeAll(solucion.contenido);
        ((ProblemaMochila)problema).EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        Caja b;
        int index;
        while (!cajasPosibles.isEmpty()) {
            index = ProblemaMochila.generador.nextInt(cajasPosibles.size());
            b = cajasPosibles.get(index);
            solucion.contenido.add(b);
            cajasPosibles.remove(b);
            espacioDispo = ((ProblemaMochila)problema).pesosMax - solucion.getPeso();
            ((ProblemaMochila)problema).EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        }
        return solucion;
    }
    
    @Override
    protected void ActualizarVariables() {
        mejorActual = soluciones.get(0);
        for (ISolucion sol : soluciones) {
            if (sol.getValor() > mejorActual.getValor()) {
                mejorActual = sol;
            }
        }
        if (mejorActual.getValor() > mejorSolucion.getValor()) {
            mejorSolucion = mejorActual;
        }
    }

    @Override
    protected boolean CriterioParada() {
        return numIteraciones > NUM_MAX_ITERACIONES;
    }

    @Override
    protected void Incrementar() {
        numIteraciones++;
    }

    @Override
    protected void EnviarResultado() {
        ihm.MostrarMensaje(mejorSolucion.toString());
    }
}
