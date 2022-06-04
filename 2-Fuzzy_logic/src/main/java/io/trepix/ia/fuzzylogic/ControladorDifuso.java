package io.trepix.ia.fuzzylogic;

import java.util.ArrayList;

// Clase que gestiona todo el sistema difuso
public class ControladorDifuso {
    protected String nombre;
    protected ArrayList<LinguisticVariable> entradas;
    protected LinguisticVariable salida;
    protected ArrayList<ReglaDifusa> reglas;
    protected ArrayList<NumericalValue> problema;
    
    // Constructor
    public ControladorDifuso(String _nombre ) {
        nombre = _nombre ;
        entradas = new ArrayList();
        reglas = new ArrayList();
        problema = new ArrayList();
    }
    
    // Agregar una variable linguistica como entrada
    public void AgregarVariableEntrada(LinguisticVariable vl) {
        entradas.add(vl);
    }
    
    // Agregar una variable linguistica como salida
    // 1 única posible : sustituye la existante si es necesario
    public void AgregarVariableSalida(LinguisticVariable vl) {
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
    public void AgregarValorNumerico(LinguisticVariable var, double valor) {
        problema.add(new NumericalValue(var,valor));
    }
    
    // Puesta a cero del problema (para pasar al caso siguiente)
    public void EliminarValoresNumericos() {
        problema.clear();
    }
    
    // Encontrar una variable linguistica a partir de su nombre
    public LinguisticVariable VariableLinguisticaParaNombre(String nombre) {
        for (LinguisticVariable var : entradas) {
            if (var.name.equalsIgnoreCase(nombre)) {
                return var;
            }
        }
        if (salida.name.equalsIgnoreCase(nombre)) {
            return salida;
        }
        return null;
    }
    
    public double Resolver() {
        // Iinicialización del conjunto difuso resultado
        ConjuntoDifuso resultado = new ConjuntoDifuso(salida.minimumValue, salida.maximumValue);
        resultado.Agregar(salida.minimumValue, 0);
        resultado.Agregar(salida.maximumValue, 0);
        
        // Aplicación de las reglas y modificación del conjunto difuso resultante
        for(ReglaDifusa regla : reglas) {
            resultado = resultado.O(regla.Aplicar(problema));
        }
        
        // Défuzzification
        return resultado.Baricentro();
    }
}
