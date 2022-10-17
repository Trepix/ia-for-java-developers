package io.trepix.ia.fishschool;

import io.trepix.ia.Size;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

//Should be executed with option -ea in VM Arguments to enable assertions
public class OceanTest {

    static long seed = 1922;
    static StartConfig config = new StartConfig(10, new Random(seed));
    static Ocean ocean = new Ocean(new Size(50, 50), config);

    static int iterations = 0;

    public static void main(String[] args) {
        addObstaclesAt(new Position(10, 10), new Position(30, 30), new Position(50, 50));

        assertIteration(asList(createFish(28.599881762709120,9.746304701056122,-2.772144510911796), createFish(9.477221616934349,19.864232131711436,1.282431182960900), createFish(36.900400266514540,13.325321398779948,0.070562816725774), createFish(4.482786753352622,41.032302115522550,-3.012461259669642),  createFish(37.718484682939100,40.246894744040425,0.966750330049890), createFish(48.904371393228814,39.938020605096830,-0.305690623237162), createFish(47.374903261562490,34.384888872514370,-1.231112695306985), createFish(9.401949208955442,20.360824325173910,1.460047663079751), createFish(25.646307469625764,19.066000057518730,-1.485355158374263), createFish(44.911849385482140,41.414724600634270,1.635378374012537)) );
        assertIteration(asList(createFish(26.314670714594200,7.802645660593134,-2.436786283225542), createFish(9.999811421590545,22.818364812562010,1.395706490015165),createFish(39.749836491543830,12.386858187199001,-0.318161720963857), createFish(1.533465069423817,40.483210497750650,-2.957524468388836), createFish(39.456432729193445,42.692203842809064,0.952907025402512),createFish(51.629402942363654,38.683348115472640,-0.431489411082322), createFish(47.480438349516920,31.386745722961557,-1.535610704461764), createFish(9.565883816875576,23.356341883848213,1.516124225387493),createFish(25.442188510038626,16.072952204520540,-1.638888586721244), createFish(43.440777678312450,44.029289197725890,2.083295931391061)));
        assertIteration(asList(createFish(25.267420152656246,4.991370814456484,-1.927389248058010),  createFish(10.349379823722236,25.797928863263234,1.454008219254069), createFish(42.653596964681530,11.633084272392232,-0.253979700983985), createFish(-1.364320353431467,39.706783010416885,-2.879803497056977),  createFish(40.364324238062220,45.551527715357250,1.263344956220610), createFish(52.472148980834326,36.983795404739666,-0.602273690774003), createFish(46.712002516634065,28.486830832703287,-1.829828649080397),  createFish(9.642752960412120,26.355356911281714,1.545170474358921), createFish(24.992002447864795,13.106922449203775,-1.721427330327350), createFish(40.893297293319380,45.613695612783450,2.585189323645075)));
        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void addObstaclesAt(Position... positions) {
        Arrays.stream(positions).forEach(ocean::addObstacleAt);
    }

    private static Fish createFish(double x, double y, double radians) {
        return new Fish(new Position(x, y), UnitaryDirection.fromRadians(radians));
    }

    private static void assertIteration(List<Fish> expectedFishes) {
        ocean.evolve();
        var fishes = ocean.fishes();
        assert expectedFishes.containsAll(fishes) : "Fishes are different in iteration " + iterations++;
    }
}
