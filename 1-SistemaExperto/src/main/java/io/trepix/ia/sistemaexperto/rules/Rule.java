package io.trepix.ia.sistemaexperto.rules;

import io.trepix.ia.sistemaexperto.Fact;
import io.trepix.ia.sistemaexperto.Facts;
import io.trepix.ia.sistemaexperto.HumanMachineInterface;
import io.trepix.ia.sistemaexperto.facts.FactFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class Rule {
    protected List<Fact<?>> premises;
    protected Fact<?> conclusion;
    protected String name;
    // Constructor
    public Rule(String name, List<Fact<?>> premises, Fact<?> conclusion) {
        this.name = name;
        this.premises = premises;
        this.conclusion = conclusion;
    }

    public static Rule copy(Rule rule) {
        return new Rule(rule.name, rule.premises, rule.conclusion);
    }

    public Fact<?> conclusion() {
        return this.conclusion;
    }


    public int getLevel(Facts knownFacts) {
        return premises.stream()
                .map(knownFacts::search)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Fact::level)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    public boolean canBeApplied(Facts knownFacts, HumanMachineInterface humanMachineInterface) {
        for (Fact<?> premise : premises) {
            if (!knownFacts.exists(premise) && premise.requiresInput()) {
                String value = humanMachineInterface.askForValue(premise.question());
                knownFacts.addFact(FactFactory.createFact(premise, value));
            }

            Optional<Fact<?>> fact = knownFacts.search(premise);
            if (fact.isEmpty()) return false;
            if (premiseDoesNotSatisfyFact(premise, fact.get())) return false;
        }
        return true;
    }

    private boolean premiseDoesNotSatisfyFact(Fact<?> premise, Fact<?> fact) {
        return !fact.value().equals(premise.value());
    }

    @Override
    public String toString() {
        String rule = name + " : IF (";

        StringJoiner sj = new StringJoiner(" AND ");
        for (Fact<?> fact : premises) {
            sj.add(fact.toString());
        }

        rule += sj + ") THEN " + conclusion.toString();
        return rule;
    }

}
