package io.trepix.ia.expertsystem.rules;

import io.trepix.ia.expertsystem.Fact;
import io.trepix.ia.expertsystem.Facts;
import io.trepix.ia.expertsystem.HumanMachineInterface;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static io.trepix.ia.expertsystem.facts.FactFactory.createFact;

public class Rule {
    protected List<Fact<?>> premises;
    protected Fact<?> conclusion;
    protected String name;

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

    public void addNonDeductedPremisesToKnownFacts(Facts knownFacts, HumanMachineInterface humanMachineInterface) {
        for (Fact<?> premise : premises) {
            Optional<Fact<?>> knownFact = knownFacts.search(premise);
            if (knownFact.isPresent() && factDoesNotSatisfyPremise(knownFact.get(), premise)) break;
            if (knownFact.isEmpty() && premise.isDeducted()) break;

            if (knownFact.isEmpty() && !premise.isDeducted()) {
                String value = humanMachineInterface.askForValue(premise.question());
                knownFacts.addFact(createFact(premise, value));
            }
        }
    }

    public boolean canBeApplied(Facts knownFacts) {
        for (Fact<?> premise : premises) {
            Optional<Fact<?>> fact = knownFacts.search(premise);
            if (fact.isEmpty()) return false;
            if (factDoesNotSatisfyPremise(fact.get(), premise)) return false;
        }
        return true;
    }

    private boolean factDoesNotSatisfyPremise(Fact<?> fact, Fact<?> premise) {
        return !fact.hasSameValue(premise);
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
