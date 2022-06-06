package io.trepix.ia.application;

import io.trepix.ia.fuzzylogic.*;

// Clase principal, y sobrer el ejemplo del zoom de un GPS
public class ZoomGPS {
    public static void main(String[] arg) {
        System.out.println("Lógica difusa : caso del zoom de un GPS");
        
        // Creación del sistema
        ControladorDifuso controlador = new ControladorDifuso("Gestión del zoom de un GPS");
        
        System.out.println("Agregar las variables de entrada");
        // Variable linguistica de entrada : distancia (en m, de 0 a 500 000)
        LinguisticVariable distancia = new LinguisticVariable("Distancia", 0, 500000);
        distancia.addLinguisticValue(new LinguisticValue("Baja", FuzzySetBuilder.leftTrapezoidal(0, 50000).withStartBoundaryAt(30).withEndBoundaryAt(50).build()));
        distancia.addLinguisticValue(new LinguisticValue("Media", new ConjuntoDifusoTrapecio(0, 500000, 40, 50, 100, 150)));
        distancia.addLinguisticValue(new LinguisticValue("Grande", FuzzySetBuilder.rightTrapezoidal(0, 50000).withStartBoundaryAt(100).withEndBoundaryAt(150).build()));
        controlador.AgregarVariableEntrada(distancia);

        // Variable linguistica de entrada : velocidad (en km/h, de 0 a 200)
        LinguisticVariable velocidad = new LinguisticVariable("Velocidad", 0, 200);
        velocidad.addLinguisticValue(new LinguisticValue("Lenta", FuzzySetBuilder.leftTrapezoidal(0, 200).withStartBoundaryAt(20).withEndBoundaryAt(30).build()));
        velocidad.addLinguisticValue(new LinguisticValue("PocoRapida", new ConjuntoDifusoTrapecio(0, 200, 20, 30, 70, 80)));
        velocidad.addLinguisticValue(new LinguisticValue("Rapida", new ConjuntoDifusoTrapecio(0, 200, 70, 80, 90, 110)));
        velocidad.addLinguisticValue(new LinguisticValue("MuyRapida", FuzzySetBuilder.rightTrapezoidal(0, 200).withStartBoundaryAt(90).withEndBoundaryAt(110).build()));
        controlador.AgregarVariableEntrada(velocidad);
        
        System.out.println("Agregar la variable de salida");
        // Variable linguistiqe de salida : nivel de zoom (de 1 a 5)
        LinguisticVariable zoom = new LinguisticVariable("Zoom", 0, 5);
        zoom.addLinguisticValue(new LinguisticValue("Pequeña", FuzzySetBuilder.leftTrapezoidal(0, 5).withStartBoundaryAt(1).withEndBoundaryAt(2).build()));
        zoom.addLinguisticValue(new LinguisticValue("Normal", new ConjuntoDifusoTrapecio(0, 5, 1, 2, 3, 4)));
        zoom.addLinguisticValue(new LinguisticValue("Grande", FuzzySetBuilder.rightTrapezoidal(0, 5).withStartBoundaryAt(3).withEndBoundaryAt(4).build()));
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
