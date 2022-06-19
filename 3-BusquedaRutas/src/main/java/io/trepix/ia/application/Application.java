package io.trepix.ia.application;

import io.trepix.ia.busquedaCaminos.*;
import io.trepix.ia.busquedaCaminos.structure.Map;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static io.trepix.ia.busquedaCaminos.structure.MapBuilder.createMap;

public class Application {

    private final static List<Algoritmo> ALGORITHMS = List.of(
            new BusquedaEnProfundidad(),
            new BusquedaEnAnchura(),
            new BellmanFord(),
            new Dijkstra(),
            new AStar()
    );
    public static void main(String[] args) {
        System.out.println("Pathfinding");
        Application app = new Application();
        app.run();
    }
    private void run() {
        Map map = createMap(
                "..  XX   .",
                "*.  *X  *.",
                " .  XX ...",
                " .* X *.* ",
                " ...=...  ",
                " .* X     ",
                " .  XXX*  ",
                " .  * =   ",
                " .... XX  ",
                "   *.  X* ")
                .withStartAt(0,0)
                .withArrivalAt(9,9)
                .build();
        runAlgorithms(map);

        map = createMap("...*     X .*    *  ",
                " *..*   *X .........",
                "   .     =   *.*  *.",
                "  *.   * XXXX .    .",
                "XXX=XX   X *XX=XXX*.",
                "  *.*X   =  X*.  X  ",
                "   . X * X  X . *X* ",
                "*  .*XX=XX *X . XXXX",
                " ....  .... X . X   ",
                " . *....* . X*. = * ")
                .withStartAt(0,0)
                .withArrivalAt(9,19)
                .build();
        runAlgorithms(map);
    }

    private void runAlgorithms(Grafico map) {
        ALGORITHMS.forEach(algorithm -> runAlgorithm(map, algorithm));
    }

    private void runAlgorithm(Grafico map, Algoritmo algorithm) {
        LocalDateTime start;
        LocalDateTime end;

        System.out.println("Algorithm : " + algorithm.name());
        start = LocalDateTime.now();
        Grafico grafico = algorithm.findPath(map);
        this.showResults(grafico.ReconstruirCamino(), grafico.NodoSalida().getDistanceFromBeginning());
        end = LocalDateTime.now();

        System.out.println("Execution time (ms) : " + executionTime(start, end) + "\n");
    }

    private static double executionTime(LocalDateTime start, LocalDateTime end) {
        Duration executionTime = Duration.between(start, end);
        return executionTime.toNanos() / 1000000.0;
    }

    public void showResults(String path, double distance) {
        System.out.println("Path (distance: " + distance + ") : " + path);
    }

}
