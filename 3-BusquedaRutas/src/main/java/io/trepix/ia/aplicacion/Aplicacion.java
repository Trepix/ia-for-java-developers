package application;

import java.time.Duration;
import java.time.LocalDateTime;
import busquedaCaminos.AStar;
import busquedaCaminos.Algoritmo;
import busquedaCaminos.BellmanFord;
import busquedaCaminos.Dijkstra;
import busquedaCaminos.Grafico;
import busquedaCaminos.IHM;
import busquedaCaminos.BusquedaEnAncho;
import busquedaCaminos.BúsquedaEnProfundidad;

// Utilización de los algoritmos sobre los ejemplos de mapa
public class Aplicacion implements IHM {

    // Programa main
    public static void main(String[] args) {
        System.out.println("Búsqueda de caminos");
        Aplicacion app = new Aplicacion();
        app.Lanzar();
    }

    // Ejecución de los dos problemas
    private void Lanzar() {
        // Caso primer mapa
        String mapaStr = "..  XX   .\n" 
                      + "*.  *X  *.\n" 
                      + " .  XX ...\n" 
                      + " .* X *.* \n" 
                      + " ...=...  \n" 
                      + " .* X     \n" 
                      + " .  XXX*  \n" 
                      + " .  * =   \n" 
                      + " .... XX  \n" 
                      + "   *.  X* "; 
        Mapa mapa1 = new Mapa(mapaStr, 0, 0, 9, 9);
        LanzarAlgoritmos(mapa1);
        
        // Caso segundo mapa
        mapaStr = "...*     X .*    *  \n" 
                 + " *..*   *X .........\n"  
                 + "   .     =   *.*  *.\n" 
                 + "  *.   * XXXX .    .\n" 
                 + "XXX=XX   X *XX=XXX*.\n" 
                 + "  *.*X   =  X*.  X  \n" 
                 + "   . X * X  X . *X* \n" 
                 + "*  .*XX=XX *X . XXXX\n" 
                 + " ....  .... X . X   \n" 
                 + " . *....* . X*. = * "; 
        Mapa mapa2 = new Mapa(mapaStr, 0, 0, 9, 19);
        LanzarAlgoritmos(mapa2);
    }
    
    // Ejecución de todos los algoritmos seguidos
    private void LanzarAlgoritmos(Grafico grafico) {
        LanzarAlgoritmo("Profundidad", grafico);
        LanzarAlgoritmo("Ancho", grafico);
        LanzarAlgoritmo("Bellman-Ford", grafico);
        LanzarAlgoritmo("Dijkstra", grafico);
        LanzarAlgoritmo("A*", grafico);
    }
    
    // Ejecución de un algoritmo junto a su nombre y visualización del tiempo
    private void LanzarAlgoritmo(String nomnre, Grafico grafico) {
        // Iinicialización
        LocalDateTime inicio;
        LocalDateTime fin;
        Duration duracion;
        Algoritmo algo = null;
        
        // Creación del algoritmo
        switch(nombre) {
            casilla "Profundidad" :
                algo = new BúsquedaEnProfundidad(grafico, this);
                break;
            casilla "Ancho" : 
                algo = new BusquedaEnAncho(grafico, this);
                break;
            casilla "Bellman-Ford" :
                algo = new BellmanFord(grafico, this);
                break;
            casilla "Dijkstra" :
                algo = new Dijkstra(grafico, this);
                break;
            casilla "A*" :
                algo = new AStar(grafico, this);
                break;
        }
        
        // Resolución
        System.out.println("Algoritmo : " + nombre);
        inicio = LocalDateTime.now();
        algo.Resolver();
        fin = LocalDateTime.now();
        duracion = Duration.between(inicio, fin);
        System.out.println("Duración (ms) : " + duracion.toMillis() + "\n");
    }
    
    // Métodos que vienen de la interface, para la visualización del resultado
    @Override
    public void MostrarResultado(String camino, double distancia) {
        System.out.println("Camino (tamaño : " + distancia + ") : " + camino);
    }
    
}
