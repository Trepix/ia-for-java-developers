package io.trepix.ia.wastesorting;

import io.trepix.ia.Direction;
import io.trepix.ia.Position;
import io.trepix.ia.Size;
import io.trepix.ia.wastesorting.StartConfig.GarbageConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Finish tests
public class FieldTest {

    static long seed = 1907;

    static GarbageConfig garbageConfig = new GarbageConfig(10, 3);
    static StartConfig config = new StartConfig(new Random(seed), new Size(15, 15), garbageConfig, 3);

    static Field field = new Field(config);

    static int currentIteration = 0;

    @Test
    public void run_test() {

        assertIteration(1,
                asList(
                        createCleaner(12.170373599092743,12.159686095948079,1.531800319784192),
                        createCleaner(7.557082107207076,9.107618831913720,2.023544138026795),
                        createCleaner(6.496268125389757,0.000000000000000,1.398701520451393)),
                asList(
                        createGarbage(9.138401324948218,9.770564428094294,1),
                        createGarbage(4.730536559528570,9.548456731046445,0),
                        createGarbage(12.707336638986312,13.831461919848858,2),
                        createGarbage(5.776739111291425,13.437605887985452,0),
                        createGarbage(12.858101229815105,10.615615033820818,0),
                        createGarbage(0.491557990983423,9.635234464774090,0),
                        createGarbage(1.335987147730857,10.778362643451760,1)));

        assertIteration(100,
                asList(
                        createCleaner(0.000000000000000,0.000000000000000,2.718129228476944),
                        createCleaner(15.000000000000000,0.000000000000000,0.298388389077814),
                        createCleaner(0.000000000000000,15.000000000000000,2.158039753546782)),
                asList(
                        createGarbage(9.138401324948218,9.770564428094294,1),
                        createGarbage(4.730536559528570,9.548456731046445,0),
                        createGarbage(12.707336638986312,13.831461919848858,2),
                        createGarbage(5.776739111291425,13.437605887985452,0),
                        createGarbage(12.858101229815105,10.615615033820818,0),
                        createGarbage(0.491557990983423,9.635234464774090,0),
                        createGarbage(1.335987147730857,10.778362643451760,1)));



        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static AgenteClasificacion createCleaner(double x, double y, double radians) {
        return new AgenteClasificacion(new Position(x, y), Direction.fromRadians(radians), field);
    }

    private static Residuo createGarbage(double x, double y, int type) {
        return new Residuo(x, y, type);
    }

    private static void assertIteration(int iterationToTest, List<AgenteClasificacion> expectedCleaners, List<Residuo> expectedGarbage) {
        while (currentIteration <= iterationToTest) {
            field.Actualizar();
            ++currentIteration;
        }

        var cleaners = field.cleaners();
        var garbage = field.garbage();

//        printExpectedOutput();

        assertTrue(expectedCleaners.containsAll(cleaners), "Cleaners are different in iteration " + (currentIteration - 1));
        assertTrue(expectedGarbage.containsAll(garbage), "Garbage are different in iteration " + (currentIteration - 1));
    }

    private static void printExpectedOutput() {
        var cleaners = field.cleaners();
        var garbage = field.garbage();

        System.out.println("assertIteration(");

        System.out.println("asList(");
        for (int i = 0; i < cleaners.size(); i++) {
            AgenteClasificacion cleaner = cleaners.get(i);
            double radians = Math.acos(cleaner.velocidadX);
            String templated = String.format("createCleaner(%.15f/%.15f/%.15f)", cleaner.posX, cleaner.posY, radians);
            String result = templated.replace(",", ".").replace("/", ",");
            System.out.print(result);
            if (i != cleaners.size() - 1) System.out.println(",");
        }
        System.out.println("), ");

        System.out.println("asList(");
        for (int i = 0; i < garbage.size(); i++) {
            Residuo trash = garbage.get(i);
            String templated = String.format("createGarbage(%.15f/%.15f/%d)", trash.posX, trash.posY, trash.type);
            String result = templated.replace(",", ".").replace("/", ",");
            System.out.print(result);
            if (i != garbage.size() - 1) System.out.println(",");
        }
        System.out.println("));");
    }
}
