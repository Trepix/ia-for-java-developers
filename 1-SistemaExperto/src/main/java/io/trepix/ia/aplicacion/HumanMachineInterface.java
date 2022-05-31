package io.trepix.ia.aplicacion;

import io.trepix.ia.sistemaexperto.Fact;
import io.trepix.ia.sistemaexperto.InferenceEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HumanMachineInterface {
    public static void main(String[] args) {
        HumanMachineInterface humanMachineInterface = new HumanMachineInterface();
        humanMachineInterface.run();
    }

    public void run() {
        System.out.println("** Creating engine **");
        InferenceEngine m = new InferenceEngine(this);

        System.out.println("** Adding rules **");
        m.addRule("R1 : IF (Sides=3(How many sides does it have?)) THEN  Triangle");
        m.addRule("R2 : IF (Triangle AND Right Angle(Does the figure have at least one right angle?)) THEN Right Triangle");
        m.addRule("R3 : IF (Triangle AND Equal Sides=2(How many equal sides does the figure have?)) THEN Isosceles Triangle");
        m.addRule("R4 : IF (Right Triangle AND Isosceles Triangle) THEN Isosceles Right Triangle");
        m.addRule("R5 : IF (Triangle AND Equal Sides=3(How many equal sides does the figure have?)) THEN Equilateral Triangle");
        m.addRule("R6 : IF (Sides=4(How many sides does it have?)) THEN Quadrilateral");
        m.addRule("R7 : IF (Quadrilateral AND Parallel Sides=2(How many sides are parallel to one another? - 0, 2 o 4?)) THEN Trapezium");
        m.addRule("R8 : IF (Quadrilateral AND Parallel Sides=4(How many sides are parallel to one another? - 0, 2 o 4?)) THEN Parallelogram");
        m.addRule("R9 : IF (Parallelogram AND Right Angle(Does the figure have at least one right angle?)) THEN Rectangle");
        m.addRule("R10 : IF (Parallelogram AND Equal Sides=4(How many equal sides does the figure have?)) THEN Diamond");
        m.addRule("R11 : IF (Rectangle AND Diamond THEN Square");

        do {
            System.out.println("\n** Resolution **");
            m.resolve();
        } while (true);
    }

    public int askForIntegerValue(String question) {
        System.out.println(question);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return Integer.decode(br.readLine());
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean askForBooleanValue(String question) {
        try {
            System.out.println(question + " (yes, no)");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String res = br.readLine();
            return (res.equals("yes"));
        } catch (IOException e) {
            return false;
        }
    }

    public void showFacts(List<Fact<?>> facts) {
        StringBuilder res = new StringBuilder("Found solution(s) : \n");
        facts.sort((Fact<?> f1, Fact<?> f2) -> Integer.compare(f2.level(), f1.level()));
        for (Fact<?> fact : facts) {
            if (fact.level() != 0) {
                res.append(fact).append("\n");
            }
        }
        System.out.println(res);
    }

}
