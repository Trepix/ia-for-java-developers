package io.trepix.ia.application;

import java.time.Duration;
import java.time.LocalDateTime;
import io.trepix.ia.busquedaCaminos.AStar;
import io.trepix.ia.busquedaCaminos.Algoritmo;
import io.trepix.ia.busquedaCaminos.BellmanFord;
import io.trepix.ia.busquedaCaminos.Dijkstra;
import io.trepix.ia.busquedaCaminos.Grafico;
import io.trepix.ia.busquedaCaminos.IHM;
import io.trepix.ia.busquedaCaminos.BusquedaEnAnchura;
import io.trepix.ia.busquedaCaminos.BusquedaEnProfundidad;
public class Application implements IHM {
    public static void main(String[] args) {
        System.out.println("Pathfinding");
        Application app = new Application();
        app.run();
    }

    // Ejecución de los dos problemas
    private void run() {
        String stringMap = "..  XX   .\n"
                      + "*.  *X  *.\n" 
                      + " .  XX ...\n" 
                      + " .* X *.* \n" 
                      + " ...=...  \n" 
                      + " .* X     \n" 
                      + " .  XXX*  \n" 
                      + " .  * =   \n" 
                      + " .... XX  \n" 
                      + "   *.  X* "; 
        Mapa map = new Mapa(stringMap, 0, 0, 9, 9);
        runAlgorithms(map);

        stringMap = "...*     X .*    *  \n"
                 + " *..*   *X .........\n"  
                 + "   .     =   *.*  *.\n" 
                 + "  *.   * XXXX .    .\n" 
                 + "XXX=XX   X *XX=XXX*.\n" 
                 + "  *.*X   =  X*.  X  \n" 
                 + "   . X * X  X . *X* \n" 
                 + "*  .*XX=XX *X . XXXX\n" 
                 + " ....  .... X . X   \n" 
                 + " . *....* . X*. = * "; 
        map = new Mapa(stringMap, 0, 0, 9, 19);
        runAlgorithms(map);
    }
    private void runAlgorithms(Grafico grafico) {
        runAlgorithm(new BusquedaEnProfundidad(grafico, this));
        runAlgorithm(new BusquedaEnAnchura(grafico, this));
        runAlgorithm(new BellmanFord(grafico, this));
        runAlgorithm(new Dijkstra(grafico, this));
        runAlgorithm(new AStar(grafico, this));
    }
    private void runAlgorithm(Algoritmo algorithm) {
        LocalDateTime start;
        LocalDateTime end;
        Duration duration;

        System.out.println("Algorithm : " + algorithm.name());
        start = LocalDateTime.now();
        algorithm.Resolver();
        end = LocalDateTime.now();
        duration = Duration.between(start, end);
        System.out.println("Execution time (ms) : " + duration.toNanos() / 1000000.0 + "\n");
    }
    @Override
    public void showResults(String path, double distance) {
        System.out.println("Path (distance: " + distance + ") : " + path);
    }
    
}
