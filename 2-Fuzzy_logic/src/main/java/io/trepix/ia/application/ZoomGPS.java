package io.trepix.ia.application;

import io.trepix.ia.fuzzylogic.ControladorDifuso;
import io.trepix.ia.fuzzylogic.LinguisticVariable;
import io.trepix.ia.fuzzylogic.LinguisticVariableBuilder;

import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.BoundaryBuilder.startingAt;
import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.*;
import static io.trepix.ia.fuzzylogic.LinguisticValueBuilder.withName;

public class ZoomGPS {
    public static void main(String[] arg) {
        System.out.println("Fuzzy Logic : use case of zoom in a GPS");

        ControladorDifuso controller = new ControladorDifuso("Management of the zoom of a GPS");

        LinguisticVariable distance = LinguisticVariableBuilder
                .withName("Distance")
                .withMinimumValue(0)
                .withMaximumValue(500000)
                .andLinguisticValue(withName("Short").andFuzzySet(
                        OpenLeftTrapezoidal()
                                .withBoundary(startingAt(30).endingAt(50)))
                )
                .andLinguisticValue(withName("Middle").andFuzzySet(
                        ClosedTrapezoidal()
                                .withLeftBoundary(startingAt(40).endingAt(50))
                                .withRightBoundary(startingAt(100).endingAt(150)))
                )
                .andLinguisticValue(withName("Long").andFuzzySet(
                        OpenRightTrapezoidal()
                                .withBoundary(startingAt(100).endingAt(150)))
                ).build();
        controller.AgregarVariableEntrada(distance);

        LinguisticVariable speed = LinguisticVariableBuilder
                .withName("Speed")
                .withMinimumValue(0)
                .withMaximumValue(200)
                .andLinguisticValue(withName("Slow").andFuzzySet(
                        OpenLeftTrapezoidal()
                                .withBoundary(startingAt(20).endingAt(30)))
                )
                .andLinguisticValue(withName("Steady").andFuzzySet(
                        ClosedTrapezoidal()
                                .withLeftBoundary(startingAt(20).endingAt(30))
                                .withRightBoundary(startingAt(70).endingAt(80)))
                )
                .andLinguisticValue(withName("Fast").andFuzzySet(
                        ClosedTrapezoidal()
                                .withLeftBoundary(startingAt(70).endingAt(80))
                                .withRightBoundary(startingAt(90).endingAt(110)))
                )
                .andLinguisticValue(withName("VeryFast").andFuzzySet(
                        OpenRightTrapezoidal()
                                .withBoundary(startingAt(90).endingAt(110)))
                ).build();
        controller.AgregarVariableEntrada(speed);


        LinguisticVariable zoom = LinguisticVariableBuilder
                .withName("Zoom")
                .withMinimumValue(0)
                .withMaximumValue(5)
                .andLinguisticValue(withName("Small").andFuzzySet(
                        OpenLeftTrapezoidal()
                                .withBoundary(startingAt(1).endingAt(2)))
                )
                .andLinguisticValue(withName("Normal").andFuzzySet(
                        ClosedTrapezoidal()
                                .withLeftBoundary(startingAt(1).endingAt(2))
                                .withRightBoundary(startingAt(3).endingAt(4)))
                )
                .andLinguisticValue(withName("Big").andFuzzySet(
                        OpenRightTrapezoidal()
                                .withBoundary(startingAt(3).endingAt(4)))
                ).build();
        controller.AgregarVariableSalida(zoom);


        controller.AgregarRegla("IF Distance IS Long THEN Zoom IS Small");
        controller.AgregarRegla("IF Distance IS Short AND Speed IS Slow THEN Zoom IS Normal");
        controller.AgregarRegla("IF Distance IS Short AND Speed IS Steady THEN Zoom IS Normal");
        controller.AgregarRegla("IF Distance IS Short AND Speed IS Fast THEN Zoom IS Big");
        controller.AgregarRegla("IF Distance IS Short AND Speed IS VeryFast THEN Zoom IS Big");
        controller.AgregarRegla("IF Distance IS Middle AND Speed IS Slow THEN Zoom IS Small");
        controller.AgregarRegla("IF Distance IS Middle AND Speed IS Steady THEN Zoom IS Normal");
        controller.AgregarRegla("IF Distance IS Middle AND Speed IS Fast THEN Zoom IS Normal");
        controller.AgregarRegla("IF Distance IS Middle AND Speed IS VeryFast THEN Zoom IS Big");

        System.out.println("Case 1 :");
        controller.AgregarValorNumerico(speed, 35);
        controller.AgregarValorNumerico(distance, 70);
        System.out.println("Result : " + controller.Resolver() + "\n");

        controller.EliminarValoresNumericos();
        System.out.println("Case 2 :");
        controller.AgregarValorNumerico(speed, 25);
        controller.AgregarValorNumerico(distance, 70);
        System.out.println("Result : " + controller.Resolver() + "\n");

        controller.EliminarValoresNumericos();
        System.out.println("Case 3 :");
        controller.AgregarValorNumerico(speed, 72.5);
        controller.AgregarValorNumerico(distance, 40);
        System.out.println("Result : " + controller.Resolver() + "\n");

        controller.EliminarValoresNumericos();
        System.out.println("Case 4 :");
        controller.AgregarValorNumerico(speed, 100);
        controller.AgregarValorNumerico(distance, 110);
        System.out.println("Result : " + controller.Resolver() + "\n");

        controller.EliminarValoresNumericos();
        System.out.println("Case 5 :");
        controller.AgregarValorNumerico(speed, 45);
        controller.AgregarValorNumerico(distance, 160);
        System.out.println("Result : " + controller.Resolver() + "\n");
    }
}
