package io.trepix.ia.kp.algorithms;

import io.trepix.ia.metaheuristics.algorithms.AlgorithmVoraz;
import io.trepix.ia.kp.Caja;
import io.trepix.ia.kp.ProblemaMochila;
import io.trepix.ia.kp.SolucionMochila;
import java.util.ArrayList;
import java.util.Collections;

// Algoritmo voraz para el problema de la mochila
public class AlgorithmVorazMochila extends AlgorithmVoraz {
    SolucionMochila solucion;
    
    @Override
    protected void ConstruirSolucion() {
        solucion = new SolucionMochila();
        ProblemaMochila pb = (ProblemaMochila) problem;
        ArrayList<Caja> cajasPosibles = pb.Cajas();
        Collections.sort(cajasPosibles, (Caja b1, Caja b2) -> (int) (((b2.valor/b2.pesos) >= (b1.valor/b1.pesos)) ? 1 : -1));
        double espacioDispo = pb.pesosMax;
        for (Caja b : cajasPosibles) {
            if (b.pesos <= espacioDispo) {
                solucion.contenido.add(b);
                espacioDispo -= b.pesos;
            }
        }
    }

    @Override
    protected void sendResult() {
        ihm.MostrarMensaje(solucion.toString());
    }

}
