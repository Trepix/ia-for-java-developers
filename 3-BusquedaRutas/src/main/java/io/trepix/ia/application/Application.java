package io.trepix.ia.application;

import io.trepix.ia.pathfinding.Map;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.algorithms.*;
import io.trepix.ia.pathfinding.structure.Tile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.trepix.ia.pathfinding.MapBuilder.createMap;
import static io.trepix.ia.pathfinding.PathFindingAlgorithm.Path;

public class Application {

    private final static List<PathFindingAlgorithm<Tile>> ALGORITHMS = List.of(
            new BusquedaEnProfundidad<>(),
            new BusquedaEnAnchura<>(),
            new BellmanFord<>(),
            new Dijkstra<>(),
            new AStar<>()
    );

    public static void main(String[] args) {
        System.out.println("Pathfinding");
        Application app = new Application();
        app.run();
    }

    private static double executionTime(LocalDateTime start, LocalDateTime end) {
        Duration executionTime = Duration.between(start, end);
        return executionTime.toNanos() / 1000000.0;
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
                .withStartAt(0, 0)
                .withArrivalAt(9, 9)
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
                .withStartAt(0, 0)
                .withArrivalAt(9, 19)
                .build();
        runAlgorithms(map);
    }

    private void runAlgorithms(Map map) {
        ALGORITHMS.forEach(algorithm -> runAlgorithm(map, algorithm));
    }

    private void runAlgorithm(Map map, PathFindingAlgorithm<Tile> algorithm) {
        LocalDateTime start;
        LocalDateTime end;

        System.out.println("Algorithm : " + algorithm.name());
        start = LocalDateTime.now();
        Path path = algorithm.findPath(map);
        this.showResults(path);
        end = LocalDateTime.now();

        System.out.println("Execution time (ms) : " + executionTime(start, end) + "\n");
    }

    public void showResults(Path path) {
        String steps = path.steps().stream().map(Tile::toString).collect(Collectors.joining("-"));
        System.out.println("Path (distance: " + path.distance() + ") : " + steps);
    }

}
