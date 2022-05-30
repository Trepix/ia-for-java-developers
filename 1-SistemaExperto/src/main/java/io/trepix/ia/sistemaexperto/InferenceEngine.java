package io.trepix.ia.sistemaexperto;

import io.trepix.ia.aplicacion.HumanMachineInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

// Motor de inferencias del sistema experto
public class InferenceEngine {
    private final Facts facts;
    private final Rules rules;
    private final HumanMachineInterface app;
    private int maxRuleLevel;
    
    // Constructor
    public InferenceEngine(HumanMachineInterface app) {
        this.app = app;
        facts = new Facts();
        rules = new Rules();
    }
    
    int askForIntegerValue(String question) {
        return app.askForIntegerValue(question);
    }
    
    boolean askForBooleanValue(String pregunta) {
        return app.askForBooleanValue(pregunta);
    }
    
    // Indica si una regla pasada como argumento ss aplicable. 
    // Si lo es, devuelve su nivel, si no devuelve -1
    boolean canBeApplied(Rule rule) {
        for (Fact<?> premises : rule.getPremises()) {
            Fact<?> fact = facts.search(premises.name());
            if (fact == null) {
                // Este hecho no existe en base de hechos
                if (premises.question() != null) {
                    fact = FactFactory.createFact(premises, this);
                    facts.addFact(fact);
                } else {
                    return false;
                }
            }

            // El hecho existe en base (antes o creado), ¿pero con el valor correcto?
            if (!fact.value().equals(premises.value())) {
                return false;
            }
        }
        return true;
    }

    int getRuleLevel(Rule rule) {
        Optional<Integer> maxLevel = rule.getPremises().stream()
                .map(premise -> facts.search(premise.name()))
                .map(Fact::level)
                .max(Comparator.naturalOrder());
        return maxLevel.orElseThrow();
    }
    
    // Devuelve la primera regla aplicable de la base que se pasa  como argumento
    // Si hay una, rellena también el atributo de la clase (nivelMaxRegla)
    // si no devuelve null
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
        facts.clear();
        
        // mientras existan reglas a aplicar
        Rule r = searchRule(bdrLocale);
        while(r!=null) {
            // Aplicar la regla
            Fact nuevoHecho = r.conclusion;
            nuevoHecho.setLevel(maxRuleLevel + 1);
            facts.addFact(nuevoHecho);
            // Eliminar la regla de las posibles
            bdrLocale.delete(r);
            // Buscar la siguiente regla aplicable
            r = searchRule(bdrLocale);
        }
        
        // Visualización de los resultados
        app.showFacts(facts.getFacts());
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
