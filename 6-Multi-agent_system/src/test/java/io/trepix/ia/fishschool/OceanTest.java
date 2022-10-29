package io.trepix.ia.fishschool;

import io.trepix.ia.Direction;
import io.trepix.ia.Position;
import io.trepix.ia.Size;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

//Should be executed with option -ea in VM Arguments to enable assertions
public class OceanTest {

    static long seed = 1907;
    static StartConfig config = new StartConfig(10, new Random(seed));
    static Ocean ocean = new Ocean(new Size(50, 50), config);

    static int iterations = 0;

    public static void main(String[] args) {
        addObstaclesAt(new Position(10, 10), new Position(30, 30));

        assertIteration(asList(
                createFish(28.352570537001004, 30.434748468071618, -2.350294259690315),
                createFish(35.694735089255430, 33.698320529715280, -0.725618781623142),
                createFish(28.628345727365090, 46.013189433954764, 1.829608227147544),
                createFish(14.525014789963004, 21.558179527252978, 1.365100166951837),
                createFish(28.122170709265088, 40.443202401896194, 1.159688885458263),
                createFish(21.030660900993666, 47.210667651146440, 0.937727981932875),
                createFish(8.364681937055586, 36.769620130006910, 0.523405286342268),
                createFish(30.358958030985697, 24.100242887679013, 2.197111588119139),
                createFish(7.145078118899701, 41.902267662388766, 2.359602691012083),
                createFish(37.334594806346935, 16.464218959287140, -1.676933481147592)));

        assertIteration(asList(
                createFish(25.660722903066820, 29.110378309965157, -2.684371148333178),
                createFish(38.539222113801195, 32.744961433051910, -0.323393913407352),
                createFish(27.553197676059263, 48.813913745230920, 1.937331252251047),
                createFish(15.300483028340410, 24.456221799420145, 1.309338183880177),
                createFish(28.769144025572920, 43.372609428580205, 1.353430923880705),
                createFish(23.310120327543544, 49.161068820832530, 0.707760416820686),
                createFish(10.419583841668706, 38.955341558372700, 0.816237508988949),
                createFish(27.741176620417870, 25.565582604770420, 2.631284030926745),
                createFish(4.995846475207054, 43.995304533002900, 2.369440112542338),
                createFish(37.610147611315334, 13.476900654908773, -1.478815747497627)));

        assertIteration(asList(
                createFish(22.866379689694224,28.018752913650783,-2.769167791220171),
                createFish(41.519343126698736,32.400173456917990,-0.115183853315478),
                createFish(26.076166380910700,51.425116256173300,2.085576777541323),
                createFish(16.162484225443840,27.329713392620352,1.279354308657359),
                createFish(29.112439804232924,46.352902704141220,1.456113176071730),
                createFish(26.034751600943714,50.416610307207940,0.431808328700584),
                createFish(11.931249519207988,41.546647576455030,1.042701579470907),
                createFish(24.743459331734908,25.682591395599427,3.102579827809319),
                createFish(3.458327603073695,46.571354943579870,2.108897342986596),
                createFish(38.219978044548430,10.539536727387580,-1.366092876126271)));

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void addObstaclesAt(Position... positions) {
        Arrays.stream(positions).forEach(ocean::addObstacleAt);
    }

    private static Fish createFish(double x, double y, double radians) {
        return new Fish(new Position(x, y), Direction.fromRadians(radians));
    }

    private static void assertIteration(List<Fish> expectedFishes) {
        ocean.evolve();
        var fishes = ocean.fishes();
        assert expectedFishes.containsAll(fishes) : "Fishes are different in iteration " + iterations++;
    }
}
