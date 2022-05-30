package io.trepix.ia.sistemaexperto;

import io.trepix.ia.aplicacion.HumanMachineInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

// Motor de inferencias del sistema experto
public class InferenceEngine {
    private final Facts knownFacts;
    private final Rules rules;
    private final HumanMachineInterface app;
    private int maxRuleLevel;
    
    // Constructor
    public InferenceEngine(HumanMachineInterface app) {
        this.app = app;
        knownFacts = new Facts();
        rules = new Rules();
    }
    
    int askForIntegerValue(String question) {
        return app.askForIntegerValue(question);
    }
    
    boolean askForBooleanValue(String question) {
        return app.askForBooleanValue(question);
    }

    boolean canBeApplied(Rule rule) {
        for (Fact<?> premise : rule.getPremises()) {
            Optional<Fact<?>> fact = knownFacts.search(premise);
            if (fact.isEmpty()) {
                if (premise.isInferred()) {
                    return false;
                }
                fact = Optional.of(FactFactory.createFact(premise, this));
                knownFacts.addFact(fact.get());
            }

            if (premiseDoesNotSatisfyFact(premise, fact.get())) {
                return false;
            }
        }
        return true;
    }

    private boolean premiseDoesNotSatisfyFact(Fact<?> premise, Fact<?> fact){
        return !fact.value().equals(premise.value());
    }

    int getRuleLevel(Rule rule) {
        Optional<Integer> maxLevel = rule.getPremises().stream()
                .map(knownFacts::search)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Fact::level)
                .max(Comparator.naturalOrder());
        return maxLevel.orElseThrow();
    }

    Rule searchRule(Rules rules) {
        for(Rule rule : rules.getRules()) {
            if (canBeApplied(rule)) {
                maxRuleLevel = getRuleLevel(rule);
                return rule;
            }
        }
        return null;
    }
    
    // Algoritmo principal que permtite resolver un caso dado
    public void Resolver() {
        // Se copian todas las reglas
        Rules bdrLocale = new Rules();
        bdrLocale.setRules(rules.getRules());
        
        // Se vacía la base de hechos
        knownFacts.clear();
        
        // mientras existan reglas a aplicar
        Rule r = searchRule(bdrLocale);
        while(r!=null) {
            // Aplicar la regla
            Fact nuevoHecho = r.conclusion;
            nuevoHecho.setLevel(maxRuleLevel + 1);
            knownFacts.addFact(nuevoHecho);
            // Eliminar la regla de las posibles
            bdrLocale.delete(r);
            // Buscar la siguiente regla aplicable
            r = searchRule(bdrLocale);
        }
        
        // Visualización de los resultados
        app.showFacts(knownFacts.getFacts());
    }
    
    // Agregar una regla a la base a partir de su cadena
    // En forma :
    // Nombre : IF premisas THEN conclusion
    public void AgregarRegla(String str) {
        // Separación nombre:regla
        String[] nombreRegla = str.split(":");
        if (nombreRegla.length == 2) {
            String nombre = nombreRegla[0].trim();
            // Separación premisas THEN conclusión
            String regla = nombreRegla[1].trim();
            regla = regla.replaceFirst("^" + "IF", "");
            String[] premisasConclusion = regla.split("THEN");
            if (premisasConclusion.length == 2) {
                // Lectura de las premises
                ArrayList<Fact<?>> premises = new ArrayList<>();
                String[] premisasStr = premisasConclusion[0].split(" AND ");
                for(String cadena : premisasStr) {
                    Fact<?> premisa = FactFactory.createFact(cadena.trim());
                    premises.add(premisa);
                }
                // Lectura de la conclusión
                String conclusionStr = premisasConclusion[1].trim();
                Fact<?> conclusion = FactFactory.createFact(conclusionStr);
                // Creación de la regla y adición a la base
                rules.addRule(new Rule(nombre, premises, conclusion));
            }
        }
    }
}
