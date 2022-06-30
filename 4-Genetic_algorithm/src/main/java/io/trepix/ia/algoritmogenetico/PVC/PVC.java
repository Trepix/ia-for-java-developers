package io.trepix.ia.algoritmogenetico.PVC;

import java.util.ArrayList;
import java.util.Arrays;

// Problema del Viajante de Comercio
public class PVC {
    // Atributos : ciudades y distancias
    static ArrayList<String> ciudades;
    static int[][] distancias;
    
    // Constructor con iinicialización de las ciudades
    public static void Init() {
        ciudades = new ArrayList(Arrays.asList("París", "Lyon", "Marsella", "Nantes", "Burdeos", "Toulouse", "Lille"));
        
        distancias = new int[ciudades.size()][];
        distancias[0] = new int[] {0, 462, 772, 379, 546, 678, 215}; // París
        distancias[1] = new int[] { 462, 0, 326, 598, 842, 506, 664 }; // Lyon 
        distancias[2] = new int[] { 772, 326, 0, 909, 555, 407, 1005 }; // Marsella 
        distancias[3] = new int[] { 379, 598, 909, 0, 338, 540, 584 }; // Nantes 
        distancias[4] = new int[] { 546, 842, 555, 338, 0, 250, 792 }; // Burdeos 
        distancias[5] = new int[] { 678, 506, 407, 540, 250, 0, 926 }; // Toulouse 
        distancias[6] = new int[] { 215, 664, 1005, 584, 792, 926, 0 }; // Lille 
    }
    
    // Da la distancia entre dos ciudades
    protected static int getDistancia(int ciudad1, int ciudad2) {
        return distancias[ciudad1][ciudad2];
    }

    // Devuelve una lista de index
    protected static ArrayList<Integer> getCiudadesIndex() {
        int numCiudades = ciudades.size();
        ArrayList<Integer> ciudadesIndex = new ArrayList();
        for (int i = 0; i < numCiudades; i++) {
            ciudadesIndex.add(i);
        }
        return ciudadesIndex;
    }
    
    // Devuelve el nombre de una ciudad dada
    protected static String getCiudad(int ciudadIndice) {
        return ciudades.get(ciudadIndice);
    }
}
