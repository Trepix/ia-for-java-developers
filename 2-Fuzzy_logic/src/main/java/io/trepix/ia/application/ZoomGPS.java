package io.trepix.ia.application;

import io.trepix.ia.fuzzylogic.FuzzyController;
import io.trepix.ia.fuzzylogic.LinguisticVariable;
import io.trepix.ia.fuzzylogic.LinguisticVariable.LinguisticVariableBuilder;

import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.BoundaryBuilder.startingAt;
import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.*;
import static io.trepix.ia.fuzzylogic.LinguisticValue.LinguisticValueBuilder.withName;

public class ZoomGPS {
    public static void main(String[] arg) {
        System.out.println("Fuzzy Logic : use case of zoom in a GPS");

        FuzzyController controller = new FuzzyController("Management of the zoom of a GPS");

        LinguisticVariable distance = LinguisticVariableBuilder
                .withName("Distance")
                .withMinimumValue(0)
                .withMaximumValue(500000)
                .andLinguisticValue(withName("Short").andFuzzySet(
                        OpenLeftTrapezium()
                                .withBoundary(startingAt(30).endingAt(50)))
                )
                .andLinguisticValue(withName("Middle").andFuzzySet(
                        ClosedTrapezium()
                                .withLeftBoundary(startingAt(40).endingAt(50))
                                .withRightBoundary(startingAt(100).endingAt(150)))
                )
                .andLinguisticValue(withName("Long").andFuzzySet(
                        OpenRightTrapezium()
                                .withBoundary(startingAt(100).endingAt(150)))
                ).build();
        controller.addInputVariable(distance);

        LinguisticVariable speed = LinguisticVariableBuilder
                .withName("Speed")
                .withMinimumValue(0)
                .withMaximumValue(200)
                .andLinguisticValue(withName("Slow").andFuzzySet(
                        OpenLeftTrapezium()
                                .withBoundary(startingAt(20).endingAt(30)))
                )
                .andLinguisticValue(withName("Steady").andFuzzySet(
                        ClosedTrapezium()
                                .withLeftBoundary(startingAt(20).endingAt(30))
                                .withRightBoundary(startingAt(70).endingAt(80)))
                )
                .andLinguisticValue(withName("Fast").andFuzzySet(
                        ClosedTrapezium()
                                .withLeftBoundary(startingAt(70).endingAt(80))
                                .withRightBoundary(startingAt(90).endingAt(110)))
                )
                .andLinguisticValue(withName("VeryFast").andFuzzySet(
                        OpenRightTrapezium()
                                .withBoundary(startingAt(90).endingAt(110)))
                ).build();
        controller.addInputVariable(speed);


        LinguisticVariable zoom = LinguisticVariableBuilder
                .withName("Zoom")
                .withMinimumValue(0)
                .withMaximumValue(5)
                .andLinguisticValue(withName("Small").andFuzzySet(
                        OpenLeftTrapezium()
                                .withBoundary(startingAt(1).endingAt(2)))
                )
                .andLinguisticValue(withName("Normal").andFuzzySet(
                        ClosedTrapezium()
                                .withLeftBoundary(startingAt(1).endingAt(2))
                                .withRightBoundary(startingAt(3).endingAt(4)))
                )
                .andLinguisticValue(withName("Big").andFuzzySet(
                        OpenRightTrapezium()
                                .withBoundary(startingAt(3).endingAt(4)))
                ).build();
        controller.addOutputVariable(zoom);


        controller.addRule("IF Distance IS Long THEN Zoom IS Small");
        controller.addRule("IF Distance IS Short AND Speed IS Slow THEN Zoom IS Normal");
        controller.addRule("IF Distance IS Short AND Speed IS Steady THEN Zoom IS Normal");
        controller.addRule("IF Distance IS Short AND Speed IS Fast THEN Zoom IS Big");
        controller.addRule("IF Distance IS Short AND Speed IS VeryFast THEN Zoom IS Big");
        controller.addRule("IF Distance IS Middle AND Speed IS Slow THEN Zoom IS Small");
        controller.addRule("IF Distance IS Middle AND Speed IS Steady THEN Zoom IS Normal");
        controller.addRule("IF Distance IS Middle AND Speed IS Fast THEN Zoom IS Normal");
        controller.addRule("IF Distance IS Middle AND Speed IS VeryFast THEN Zoom IS Big");

        System.out.println("Case 1 :");
        controller.addNumericalValue(speed, 35);
        controller.addNumericalValue(distance, 70);
        System.out.println("Result : " + controller.resolve() + "\n");

        controller.clearNumericalValues();
        System.out.println("Case 2 :");
        controller.addNumericalValue(speed, 25);
        controller.addNumericalValue(distance, 70);
        System.out.println("Result : " + controller.resolve() + "\n");

        controller.clearNumericalValues();
        System.out.println("Case 3 :");
        controller.addNumericalValue(speed, 72.5);
        controller.addNumericalValue(distance, 40);
        System.out.println("Result : " + controller.resolve() + "\n");

        controller.clearNumericalValues();
        System.out.println("Case 4 :");
        controller.addNumericalValue(speed, 100);
        controller.addNumericalValue(distance, 110);
        System.out.println("Result : " + controller.resolve() + "\n");

        controller.clearNumericalValues();
        System.out.println("Case 5 :");
        controller.addNumericalValue(speed, 45);
        controller.addNumericalValue(distance, 160);
        System.out.println("Result : " + controller.resolve() + "\n");
    }
}
