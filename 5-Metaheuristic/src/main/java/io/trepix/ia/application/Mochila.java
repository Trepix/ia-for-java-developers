package io.trepix.ia.application;

import io.trepix.ia.Metaheuristico.Algoritmo;
import io.trepix.ia.Metaheuristico.IHM;
import io.trepix.ia.Metaheuristico.IProblema;
import io.trepix.ia.application.AlgoritmosMochila.*;

// Clase principal
public class Mochila implements IHM {
    public static void main(String[] args) {
        Mochila app = new Mochila();
        app.Lanzar();
    }

    @Override
    public void MostrarMensaje(String msg) {
        System.out.println(msg);
    }

    private void Lanzar() {
        System.out.println("Metaheurísticos de optimización");
        ProblemaMochila pb = new ProblemaMochila();
        LanzarAlgoritmos(pb);
        System.out.println("*****************************************\n");
        pb = new ProblemaMochila(100, 30, 20);
        LanzarAlgoritmos(pb);
    }
    
    private void LanzarAlgoritmos(IProblema pb) {
        Algoritmo algo;
        
        System.out.println("Algoritmo voraz");
        algo = new AlgoritmoVorazMochila();
        algo.Resolver(pb, this);
        System.out.println();
        
        System.out.println("Descenso de gradiente");
        algo = new DescensoGradienteMochila();
        algo.Resolver(pb, this);
        System.out.println();
        
        System.out.println("Búsqueda tabú");
        algo = new BusquedaTabuMochila();
        algo.Resolver(pb, this);
        System.out.println();
        
        System.out.println("Recorrido simulado");
        algo = new RecocidoSimuladoMochila();
        algo.Resolver(pb, this);
        System.out.println();
        
        System.out.println("Enjambre particular");
        algo = new EnjambreParticulasMochila();
        algo.Resolver(pb, this);
        System.out.println();
    }
}
