package io.trepix.ia.fuzzylogic;

import java.util.ArrayList;
import java.util.Optional;

// Clase que representa una regla difusa, con varias premisas y una conclusión
public class ReglaDifusa {
    protected ArrayList<ExpresionDifusa> premisas;
    protected ExpresionDifusa conclusion;
    
    // Constructor
    public ReglaDifusa(ArrayList<ExpresionDifusa> _premisas, ExpresionDifusa _conclusion) {
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
                    ExpresionDifusa expDifusa = new ExpresionDifusa(controlador.VariableLinguisticaParaNombre(partes[0]), partes[1]);
                    premisas.add(expDifusa);
                }
            }
            // Se trata la conclusión
            String[] concluStr = regla[1].trim().split(" IS ");
            if (concluStr.length == 2) {
                conclusion = new ExpresionDifusa(controlador.VariableLinguisticaParaNombre(concluStr[0]), concluStr[1]);
            }
        }
    }
    
    // Aplicar la regla a un problema numérico dado
    // Esto produce un conjunto difuso
    ConjuntoDifuso Aplicar(ArrayList<ValorNumerico> problema) {
        double degre = 1;
        for (ExpresionDifusa premisa : premisas) {
            double degreLocal = 0;
            Optional<LinguisticValue> vl = Optional.empty();
            for (ValorNumerico pb : problema) {
                if (premisa.varL.equals(pb.vl)) {
                    vl = premisa.varL.searchLinguisticValue(premisa.nombreValorLinguistico);
                    if (vl.isPresent()) {
                        degreLocal = vl.get().membershipDegree(pb.valor);
                        break;
                    }
                }
            }
            if (vl.isEmpty()) {
                return null;
            }
            degre = Math.min(degre, degreLocal);
        }
        return conclusion.varL.searchLinguisticValue(conclusion.nombreValorLinguistico).orElseThrow().fuzzySet.MultiplicarPor(degre);
    }
}
