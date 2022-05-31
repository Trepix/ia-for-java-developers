package io.trepix.ia.sistemaexperto.rules;

import io.trepix.ia.sistemaexperto.Fact;
import io.trepix.ia.sistemaexperto.facts.FactFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class RuleFactory {

    public static Rule createRule(String stringRule) {
        String[] splitInput = stringRule.split(":");
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

                return new Rule(name, premises, conclusion);
            }
        }
        throw new InvalidParameterException();
    }

    private static List<Fact<?>> createPremises(String premisesAsString) {
        List<Fact<?>> premises = new ArrayList<>();
        for (String premiseAsString : premisesAsString.split(" AND ")) {
            Fact<?> premise = FactFactory.createFact(premiseAsString.trim());
            premises.add(premise);
        }
        return premises;
    }
}
