package algoritmogenetico.laberinto;

// Una casilla del laberinto
public class Casilla {
    public int i;
    public int j;
    
    public Casilla(int _i, int _j) {
        i = _i;
        j = _j;
    }
    
    @Override
    public boolean equals(Object o) {
        return (i == ((Casilla)o).i && j == ((Casilla)o).j);
    }
}
