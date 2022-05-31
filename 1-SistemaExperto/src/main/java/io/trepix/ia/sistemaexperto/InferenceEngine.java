package io.trepix.ia.sistemaexperto;

import io.trepix.ia.sistemaexperto.facts.FactFactory;
import io.trepix.ia.sistemaexperto.rules.Rule;
import io.trepix.ia.sistemaexperto.rules.RuleFactory;

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

    boolean canBeApplied(Rule rule) {
        for (Fact<?> premise : rule.premises()) {
            addToKnownFacts(premise);

            Optional<Fact<?>> fact = knownFacts.search(premise);
            if (fact.isEmpty()) return false;
            if (premiseDoesNotSatisfyFact(premise, fact.get())) return false;
        }
        return true;
    }

    private void addToKnownFacts(Fact<?> premise) {
        if (!knownFacts.exists(premise) && premise.requiresInput()) {
            String value = humanMachineInterface.askForValue(premise.question());
            knownFacts.addFact(FactFactory.createFact(premise, value));
        }
    }

    private boolean premiseDoesNotSatisfyFact(Fact<?> premise, Fact<?> fact) {
        return !fact.value().equals(premise.value());
    }

    Optional<Rule> searchApplicableRule(Rules rules) {
        for (Rule rule : rules.getRules()) {
            if (canBeApplied(rule)) {
                maxRuleLevel = rule.getLevel(knownFacts);
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
