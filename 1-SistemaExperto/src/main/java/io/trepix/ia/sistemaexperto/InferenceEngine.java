package io.trepix.ia.sistemaexperto;

import io.trepix.ia.aplicacion.HumanMachineInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    private boolean premiseDoesNotSatisfyFact(Fact<?> premise, Fact<?> fact) {
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
        for (Rule rule : rules.getRules()) {
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
        while (rule.isPresent()) {
            Fact<?> newFact = rule.get().conclusion;
            newFact.setLevel(maxRuleLevel + 1);
            knownFacts.addFact(newFact);

            rules.delete(rule.get());
            rule = searchApplicableRule(rules);
        }

        app.showFacts(knownFacts.getFacts());
    }

    public void addRule(String input) {
        String[] splitInput = input.split(":");
        if (splitInput.length == 2) {
            String name = splitInput[0].trim();
            String rule = splitInput[1].trim();

            rule = rule.replaceFirst("^IF", "");
            String[] splitRule = rule.split("THEN");
            if (splitRule.length == 2) {
                String premisesAsString = splitRule[0];
                String conclusionsAsString = splitRule[1];

                List<Fact<?>> premises = createPremises(premisesAsString);
                Fact<?> conclusion = FactFactory.createFact(conclusionsAsString);

                rules.addRule(new Rule(name, premises, conclusion));
            }
        }
    }

    private List<Fact<?>> createPremises(String premisesAsString) {
        List<Fact<?>> premises = new ArrayList<>();
        for (String premiseAsString : premisesAsString.split(" AND ")) {
            Fact<?> premise = FactFactory.createFact(premiseAsString.trim());
            premises.add(premise);
        }
        return premises;
    }

}
