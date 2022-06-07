package io.trepix.ia.application;

import io.trepix.ia.fuzzylogic.ControladorDifuso;
import io.trepix.ia.fuzzylogic.LinguisticVariable;
import io.trepix.ia.fuzzylogic.LinguisticVariableBuilder;

import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.BoundaryBuilder.startingAt;
import static io.trepix.ia.fuzzylogic.FuzzySetBuilder.*;
import static io.trepix.ia.fuzzylogic.LinguisticValueBuilder.withName;

// Clase principal, y sobrer el ejemplo del zoom de un GPS
public class ZoomGPS {
    public static void main(String[] arg) {
        System.out.println("Lógica difusa : caso del zoom de un GPS");

        // Creación del sistema
        ControladorDifuso controlador = new ControladorDifuso("Gestión del zoom de un GPS");

        System.out.println("Agregar las variables de entrada");
        // Variable linguistica de entrada : distancia (en m, de 0 a 500 000)
        LinguisticVariable distancia = LinguisticVariableBuilder
                .withName("Distancia")
                .withMinimumValue(0)
                .withMaximumValue(500000)
                .andLinguisticValue(withName("Baja").andFuzzySet(OpenLeftTrapezoidal().withBoundary(startingAt(30).endingAt(50))))
                .andLinguisticValue(withName("Media").andFuzzySet(ClosedTrapezoidal().withLeftBoundary(startingAt(40).endingAt(50)).withRightBoundary(startingAt(100).endingAt(150))))
                .andLinguisticValue(withName("Grande").andFuzzySet(OpenRightTrapezoidal().withBoundary(startingAt(100).endingAt(150))))
                .build();

        controlador.AgregarVariableEntrada(distancia);

        // Variable linguistica de entrada : velocidad (en km/h, de 0 a 200)
        LinguisticVariable velocidad = new LinguisticVariable("Velocidad");
        velocidad.addLinguisticValue(withName("Lenta").andFuzzySet(OpenLeftTrapezoidal(0, 200).withBoundary(startingAt(20).endingAt(30))).build());
        velocidad.addLinguisticValue(withName("PocoRapida").andFuzzySet(ClosedTrapezoidal(0, 200).withLeftBoundary(startingAt(20).endingAt(30)).withRightBoundary(startingAt(70).endingAt(80))).build());
        velocidad.addLinguisticValue(withName("Rapida").andFuzzySet(ClosedTrapezoidal(0, 200).withLeftBoundary(startingAt(70).endingAt(80)).withRightBoundary(startingAt(90).endingAt(110))).build());
        velocidad.addLinguisticValue(withName("MuyRapida").andFuzzySet(OpenRightTrapezoidal(0, 200).withBoundary(startingAt(90).endingAt(110))).build());
        controlador.AgregarVariableEntrada(velocidad);

        System.out.println("Agregar la variable de salida");
        // Variable linguistiqe de salida : nivel de zoom (de 1 a 5)
        LinguisticVariable zoom = new LinguisticVariable("Zoom");
        zoom.addLinguisticValue(withName("Pequeña").andFuzzySet(OpenLeftTrapezoidal(0, 5).withBoundary(startingAt(1).endingAt(2))).build());
        zoom.addLinguisticValue(withName("Normal").andFuzzySet(ClosedTrapezoidal(0, 5).withLeftBoundary(startingAt(1).endingAt(2)).withRightBoundary(startingAt(3).endingAt(4))).build());
        zoom.addLinguisticValue(withName("Grande").andFuzzySet(OpenRightTrapezoidal(0, 5).withBoundary(startingAt(3).endingAt(4))).build());
        controlador.AgregarVariableSalida(zoom);

        System.out.println("Agregar las reglas");
        // Agregar las diferentes reglas (9 para cubrir los 12 casos)
        controlador.AgregarRegla("IF Distancia IS Grande THEN Zoom IS Pequeña");
        controlador.AgregarRegla("IF Distancia IS Baja AND Velocidad IS Lenta THEN Zoom IS Normal");
        controlador.AgregarRegla("IF Distancia IS Baja AND Velocidad IS PocoRapida THEN Zoom IS Normal");
        controlador.AgregarRegla("IF Distancia IS Baja AND Velocidad IS Rapida THEN Zoom IS Grande");
        controlador.AgregarRegla("IF Distancia IS Baja AND Velocidad IS MuyRapida THEN Zoom IS Grande");
        controlador.AgregarRegla("IF Distancia IS Media AND Velocidad IS Lenta THEN Zoom IS Pequeña");
        controlador.AgregarRegla("IF Distancia IS Media AND Velocidad IS PocoRapida THEN Zoom IS Normal");
        controlador.AgregarRegla("IF Distancia IS Media AND Velocidad IS Rapida THEN Zoom IS Normal");
        controlador.AgregarRegla("IF Distancia IS Media AND Velocidad IS MuyRapida THEN Zoom IS Grande");

        System.out.println("Resolución de los caso prácticos");
        // Caso práctico 1 : velocidad de 35 km/h, 
        // y siguiente cambio de dirección a 70 m 
        System.out.println("Cas 1 :");
        controlador.AgregarValorNumerico(velocidad, 35);
        controlador.AgregarValorNumerico(distancia, 70);
        System.out.println("Resultado : " + controlador.Resolver() + "\n");

        // Caso práctico 2 : velocidad de 25 km/h, 
        // y siguiente cambio de dirección a 70 m 
        controlador.EliminarValoresNumericos();
        System.out.println("Cas 2 :");
        controlador.AgregarValorNumerico(velocidad, 25);
        controlador.AgregarValorNumerico(distancia, 70);
        System.out.println("Resultado : " + controlador.Resolver() + "\n");

        // Caso práctico 3 : velocidad de 72.5 km/h, 
        // y siguiente cambio de dirección a 40 m 
        controlador.EliminarValoresNumericos();
        System.out.println("Cas 3 :");
        controlador.AgregarValorNumerico(velocidad, 72.5);
        controlador.AgregarValorNumerico(distancia, 40);
        System.out.println("Resultado : " + controlador.Resolver() + "\n");

        // Caso práctico 4 : velocidad de 100 km/h, 
        // y siguiente cambio de dirección a 110 m 
        controlador.EliminarValoresNumericos();
        System.out.println("Caso 4 :");
        controlador.AgregarValorNumerico(velocidad, 100);
        controlador.AgregarValorNumerico(distancia, 110);
        System.out.println("Resultado : " + controlador.Resolver() + "\n");

        // Caso práctico 5 : velocidad de 45 km/h, 
        // y cambio a 160 m 
        controlador.EliminarValoresNumericos();
        System.out.println("Caso 5 :");
        controlador.AgregarValorNumerico(velocidad, 45);
        controlador.AgregarValorNumerico(distancia, 160);
        System.out.println("Resultado : " + controlador.Resolver() + "\n");
    }
}
