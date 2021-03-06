package io.trepix.ia.expertsystem;

import io.trepix.ia.expertsystem.rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Rules {
    final private List<Rule> rules;
    private int maxRuleLevel;

    public Rules() {
        rules = new ArrayList<>();
    }

    public Rules(Rules rules) {
        this.rules = new ArrayList<>();
        for (Rule rule : rules.rules) {
            this.rules.add(Rule.copy(rule));
        }
        this.maxRuleLevel = rules.maxRuleLevel;
    }

    public int maxRuleLevel() {
        return maxRuleLevel;
    }

    public void addRule(Rule r) {
        rules.add(r);
    }

    public void delete(Rule r) {
        rules.remove(r);
    }

    public Optional<Rule> searchApplicableRule(Facts knownFacts, HumanMachineInterface humanMachineInterface) {
        for (Rule rule : rules) {
            rule.addNonDeductedPremisesToKnownFacts(knownFacts, humanMachineInterface);
            if (rule.canBeApplied(knownFacts)) {
                maxRuleLevel = rule.getLevel(knownFacts);
                return Optional.of(rule);
            }
        }
        return Optional.empty();
    }
}
