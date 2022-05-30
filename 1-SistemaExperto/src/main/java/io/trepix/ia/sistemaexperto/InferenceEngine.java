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

    Optional<Rule> searchApplicableRule(Rules rules) {
        for(Rule rule : rules.getRules()) {
            if (canBeApplied(rule)) {
                maxRuleLevel = getRuleLevel(rule);
                return Optional.of(rule);
            }
        }
        return Optional.empty();
    }

    public void resolve() {
        Rules rules = new Rules(this.rules.getRules());

        knownFacts.clear();

        Optional<Rule> rule = searchApplicableRule(rules);
        while(rule.isPresent()) {
            Fact<?> newFact = rule.get().conclusion;
            newFact.setLevel(maxRuleLevel + 1);
            knownFacts.addFact(newFact);

            rules.delete(rule.get());
            rule = searchApplicableRule(rules);
        }

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
