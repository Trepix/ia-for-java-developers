package io.trepix.ia.application.AlgoritmosMochila;

import io.trepix.ia.Metaheuristico.Algoritmos.AlgoritmoVoraz;
import io.trepix.ia.application.Caja;
import io.trepix.ia.application.ProblemaMochila;
import io.trepix.ia.application.SolucionMochila;
import java.util.ArrayList;
import java.util.Collections;

// Algoritmo voraz para el problema de la mochila
public class AlgoritmoVorazMochila extends AlgoritmoVoraz {
    SolucionMochila solucion;
    
    @Override
    protected void ConstruirSolucion() {
        solucion = new SolucionMochila();
        ProblemaMochila pb = (ProblemaMochila) problema;
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
    protected void EnviarResultado() {
        ihm.MostrarMensaje(solucion.toString());
    }

}
