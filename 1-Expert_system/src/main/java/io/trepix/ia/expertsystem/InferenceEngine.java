package io.trepix.ia.expertsystem;

import io.trepix.ia.expertsystem.rules.Rule;
import io.trepix.ia.expertsystem.rules.RuleFactory;

import java.util.Optional;

public class InferenceEngine {
    private final Facts knownFacts;
    private final Rules rules;
    private final HumanMachineInterface humanMachineInterface;

    // Constructor
    public InferenceEngine(HumanMachineInterface humanMachineInterface) {
        this.humanMachineInterface = humanMachineInterface;
        knownFacts = new Facts();
        rules = new Rules();
    }

    public void resolve() {
        Rules rules = new Rules(this.rules);

        knownFacts.clear();

        Optional<Rule> rule = rules.searchApplicableRule(knownFacts, humanMachineInterface);
        while (rule.isPresent()) {
            Fact<?> newFact = rule.get().conclusion();
            newFact.setLevel(rules.maxRuleLevel() + 1);
            knownFacts.addFact(newFact);

            rules.delete(rule.get());
            rule = rules.searchApplicableRule(knownFacts, humanMachineInterface);
        }

        humanMachineInterface.showFacts(knownFacts.getFacts());
    }

    public void addRule(String input) {
        Rule rule = RuleFactory.createRule(input);
        rules.addRule(rule);
    }

}
