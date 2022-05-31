package io.trepix.ia.sistemaexperto;

import io.trepix.ia.aplicacion.HumanMachineInterface;
import io.trepix.ia.sistemaexperto.facts.FactFactory;
import io.trepix.ia.sistemaexperto.rules.Rule;
import io.trepix.ia.sistemaexperto.rules.RuleFactory;

import java.util.Comparator;
import java.util.Optional;

public class InferenceEngine {
    private final Facts knownFacts;
    private final Rules rules;
    private final HumanMachineInterface humanMachineInterface;
    private int maxRuleLevel;

    // Constructor
    public InferenceEngine(HumanMachineInterface humanMachineInterface) {
        this.humanMachineInterface = humanMachineInterface;
        knownFacts = new Facts();
        rules = new Rules();
    }

    public int askForIntegerValue(String question) {
        return humanMachineInterface.askForIntegerValue(question);
    }

    public boolean askForBooleanValue(String question) {
        return humanMachineInterface.askForBooleanValue(question);
    }

    boolean canBeApplied(Rule rule) {
        for (Fact<?> premise : rule.premises()) {
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

    private boolean premiseDoesNotSatisfyFact(Fact<?> premise, Fact<?> fact) {
        return !fact.value().equals(premise.value());
    }

    int getRuleLevel(Rule rule) {
        Optional<Integer> maxLevel = rule.premises().stream()
                .map(knownFacts::search)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Fact::level)
                .max(Comparator.naturalOrder());
        return maxLevel.orElseThrow();
    }

    Optional<Rule> searchApplicableRule(Rules rules) {
        for (Rule rule : rules.getRules()) {
            if (canBeApplied(rule)) {
                maxRuleLevel = getRuleLevel(rule);
                return Optional.of(rule);
            }
        }
        return Optional.empty();
    }

    public void resolve() {
        Rules rules = new Rules(this.rules);

        knownFacts.clear();

        Optional<Rule> rule = searchApplicableRule(rules);
        while (rule.isPresent()) {
            Fact<?> newFact = rule.get().conclusion();
            newFact.setLevel(maxRuleLevel + 1);
            knownFacts.addFact(newFact);

            rules.delete(rule.get());
            rule = searchApplicableRule(rules);
        }

        humanMachineInterface.showFacts(knownFacts.getFacts());
    }

    public void addRule(String input) {
        Rule rule = RuleFactory.createRule(input);
        rules.addRule(rule);
    }

}
