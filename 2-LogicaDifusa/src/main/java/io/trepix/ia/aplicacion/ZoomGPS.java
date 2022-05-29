package application;

import Logicadifusa.ControladorDifuso;
import Logicadifusa.ConjuntoDifusoTrapecio;
import Logicadifusa.ConjuntoDifusoTrapecioDerecha;
import Logicadifusa.ConjuntoDifusoTrapecioIzquierda;
import Logicadifusa.ValorLinguistico;
import Logicadifusa.VariableLinguistica;

// Clase principal, y sobrer el ejemplo del zoom de un GPS
public class ZoomGPS {
    public static void main(String[] arg) {
        System.out.println("Lógica difusa : caso del zoom de un GPS");
        
        // Creación del sistema
        ControladorDifuso controlador = new ControladorDifuso("Gestión del zoom de un GPS");
        
        System.out.println("Agregar las variables de entrada");
        // Variable linguistica de entrada : distancia (en m, de 0 a 500 000)
        VariableLinguistica distancia = new VariableLinguistica("Distancia", 0, 500000); 
        distancia.AgregarValorLinguistico(new ValorLinguistico("Baja", new ConjuntoDifusoTrapecioIzquierda(0, 500000, 30, 50))); 
        distancia.AgregarValorLinguistico(new ValorLinguistico("Media", new ConjuntoDifusoTrapecio(0, 500000, 40, 50, 100, 150))); 
        distancia.AgregarValorLinguistico(new ValorLinguistico("Grande", new ConjuntoDifusoTrapecioDerecha(0, 500000, 100, 150))); 
        controlador.AgregarVariableEntrada(distancia);

        // Variable linguistica de entrada : velocidad (en km/h, de 0 a 200)
        VariableLinguistica velocidad = new VariableLinguistica("Velocidad", 0, 200);
        velocidad.AgregarValorLinguistico(new ValorLinguistico("Lenta", new ConjuntoDifusoTrapecioIzquierda(0, 200, 20, 30)));
        velocidad.AgregarValorLinguistico(new ValorLinguistico("PocoRapida", new ConjuntoDifusoTrapecio(0, 200, 20, 30, 70, 80)));
        velocidad.AgregarValorLinguistico(new ValorLinguistico("Rapida", new ConjuntoDifusoTrapecio(0, 200, 70, 80, 90, 110)));
        velocidad.AgregarValorLinguistico(new ValorLinguistico("MuyRapida", new ConjuntoDifusoTrapecioDerecha(0, 200, 90, 110)));
        controlador.AgregarVariableEntrada(velocidad);
        
        System.out.println("Agregar la variable de salida");
        // Variable linguistiqe de salida : nivel de zoom (de 1 a 5)
        VariableLinguistica zoom = new VariableLinguistica("Zoom", 0, 5); 
        zoom.AgregarValorLinguistico(new ValorLinguistico("Pequeña", new ConjuntoDifusoTrapecioIzquierda(0, 5, 1, 2))); 
        zoom.AgregarValorLinguistico(new ValorLinguistico("Normal", new ConjuntoDifusoTrapecio(0, 5, 1, 2, 3, 4))); 
        zoom.AgregarValorLinguistico(new ValorLinguistico("Groanda, new ConjuntoDifusoTrapecioDerecha(0, 5, 3, 4))); 
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
