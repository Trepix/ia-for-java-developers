package io.trepix.ia.fuzzylogic;

import java.util.ArrayList;

// Clase que representa una regla difusa, con varias premisas y una conclusión
public class ReglaDifusa {
    protected ArrayList<FuzzyExpression> premisas;
    protected FuzzyExpression conclusion;
    
    // Constructor
    public ReglaDifusa(ArrayList<FuzzyExpression> _premisas, FuzzyExpression _conclusion) {
        premisas = _premisas;
        conclusion = _conclusion;
    }

    // Constructor a partir de una cadena de caracteres
    public ReglaDifusa(String reglaStr, ControladorDifuso controlador) {
        // Paso de la regla a mayúsculas
        reglaStr = reglaStr.toUpperCase();
        // Separación premisas / conclusión (palabra clave THEN)
        String[] regla = reglaStr.split(" THEN ");
        if (regla.length == 2) {
            // Se tratan las premisas enlevando el IF después justo a AND
            regla[0] = regla[0].replaceFirst("IF ", "").trim();
            String[] premisasStr = regla[0].split(" AND ");
            premisas = new ArrayList();
            for(String exp : premisasStr) {
                // Cortamos la palabra clave "IS", y se crea una expresión difusa
                String[] partes = exp.trim().split(" IS ");
                if (partes.length == 2) {
                    FuzzyExpression expDifusa = new FuzzyExpression(controlador.VariableLinguisticaParaNombre(partes[0]), partes[1]);
                    premisas.add(expDifusa);
                }
            }
            // Se trata la conclusión
            String[] concluStr = regla[1].trim().split(" IS ");
            if (concluStr.length == 2) {
                conclusion = new FuzzyExpression(controlador.VariableLinguisticaParaNombre(concluStr[0]), concluStr[1]);
            }
        }
    }
    
    // Aplicar la regla a un problema numérico dado
    // Esto produce un conjunto difuso
    ConjuntoDifuso Aplicar(ArrayList<NumericalValue> problema) {
        double degre = 1;
        for (FuzzyExpression fuzzyExpression : premisas) {
            double degreLocal = 0;
            for (NumericalValue numericalValue : problema) {
                if (numericalValue.belongsTo(fuzzyExpression)) {
                    LinguisticValue linguisticValue = fuzzyExpression.linguisticValue();
                    degreLocal = linguisticValue.membershipDegree(numericalValue.value());
                    break;
                }
            }
            degre = Math.min(degre, degreLocal);
        }
        return conclusion.linguisticValue().fuzzySet().MultiplicarPor(degre);
    }
}
