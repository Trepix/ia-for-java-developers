package io.trepix.ia.fuzzylogic;

import java.util.*;

// Clase que gestiona todo el sistema difuso
public class ControladorDifuso {
    protected String nombre;
    protected ArrayList<LinguisticVariable> inputs;
    protected LinguisticVariable output;
    protected ArrayList<FuzzyRule> rules;
    protected ArrayList<NumericalValue> problema;
    
    // Constructor
    public ControladorDifuso(String _nombre ) {
        nombre = _nombre ;
        inputs = new ArrayList();
        rules = new ArrayList();
        problema = new ArrayList();
    }
    
    // Agregar una variable linguistica como entrada
    public void AgregarVariableEntrada(LinguisticVariable vl) {
        inputs.add(vl);
    }
    
    // Agregar una variable linguistica como salida
    // 1 única posible : sustituye la existante si es necesario
    public void AgregarVariableSalida(LinguisticVariable vl) {
        output = vl;
    }
    
    // Agregar una regla
    public void AgregarRegla(FuzzyRule regla) {
        rules.add(regla);
    }
    
    // Agregar una regla (formato textual)
    public void AgregarRegla(String rule) {
        Set<LinguisticVariable> linguisticVariables = new HashSet<>(inputs);
        linguisticVariables.add(output);
        rules.add(new FuzzyRule(rule, linguisticVariables));
    }
    
    // Agregar un valor numérico como entrada
    public void AgregarValorNumerico(LinguisticVariable var, double valor) {
        problema.add(new NumericalValue(var,valor));
    }
    
    // Puesta a cero del problema (para pasar al caso siguiente)
    public void EliminarValoresNumericos() {
        problema.clear();
    }

    public double Resolver() {
        LinkedList<Punto2D> points = new LinkedList<>(List.of(
                new Punto2D(output.getMaximumValue(), 0),
                new Punto2D(output.getMaximumValue(), 0)
        ));
        FuzzySet resultado = new FuzzySet(points);
        // Aplicación de las reglas y modificación del conjunto difuso resultante
        for(FuzzyRule regla : rules) {
            resultado = resultado.O(regla.apply(problema));
        }
        
        // Défuzzification
        return resultado.Baricentro();
    }
}
