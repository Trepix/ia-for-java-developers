package Logicadifusa;

import java.util.ArrayList;

// Clase que gestiona todo el sistema difuso
public class ControladorDifuso {
    protected String nombre;
    protected ArrayList<VariableLinguistica> entradas;
    protected VariableLinguistica salida;
    protected ArrayList<ReglaDifusa> reglas;
    protected ArrayList<ValorNumerico> problema;
    
    // Constructor
    public ControladorDifuso(String _nombre ) {
        nombre = _nombre ;
        entradas = new ArrayList();
        reglas = new ArrayList();
        problema = new ArrayList();
    }
    
    // Agregar una variable linguistica como entrada
    public void AgregarVariableEntrada(VariableLinguistica vl) {
        entradas.add(vl);
    }
    
    // Agregar una variable linguistica como salida
    // 1 única posible : sustituye la existante si es necesario
    public void AgregarVariableSalida(VariableLinguistica vl) {
        salida = vl;
    }
    
    // Agregar una regla
    public void AgregarRegla(ReglaDifusa regla) {
        reglas.add(regla);
    }
    
    // Agregar una regla (formato textual)
    public void AgregarRegla(String reglaStr) {
        ReglaDifusa regla = new ReglaDifusa(reglaStr, this);
        reglas.add(regla);
    }
    
    // Agregar un valor numérico como entrada
    public void AgregarValorNumerico(VariableLinguistica var, double valor) {
        problema.add(new ValorNumerico(var,valor));
    }
    
    // Puesta a cero del problema (para pasar al caso siguiente)
    public void EliminarValoresNumericos() {
        problema.clear();
    }
    
    // Encontrar una variable linguistica a partir de su nombre
    public VariableLinguistica VariableLinguisticaParaNombre(String nombre) {
        for (VariableLinguistica var : entradas) {
            if (var.nombre.equalsIgnoreCase(nombre)) {
                return var;
            }
        }
        if (salida.nombre.equalsIgnoreCase(nombre)) {
            return salida;
        }
        return null;
    }
    
    public double Resolver() {
        // Iinicialización del conjunto difuso resultado
        ConjuntoDifuso resultado = new ConjuntoDifuso(salida.valorMin, salida.valorMax);
        resultado.Agregar(salida.valorMin, 0);
        resultado.Agregar(salida.valorMax, 0);
        
        // Aplicación de las reglas y modificación del conjunto difuso resultante
        for(ReglaDifusa regla : reglas) {
            resultado = resultado.O(regla.Aplicar(problema));
        }
        
        // Défuzzification
        return resultado.Baricentro();
    }
}
