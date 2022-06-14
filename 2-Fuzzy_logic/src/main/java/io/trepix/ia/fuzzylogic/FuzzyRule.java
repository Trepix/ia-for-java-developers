package io.trepix.ia.fuzzylogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FuzzyRule {
    private List<FuzzyExpression> premises;
    private FuzzyExpression conclusion;

    public FuzzyRule(String rule, Set<LinguisticVariable> linguisticVariables) {
        rule = rule.toUpperCase();
        String[] splitRule = rule.split(" THEN ");
        if (hasPremiseAndConclusion(splitRule)) {
            String premise = splitRule[0];
            String conclusionAsString = splitRule[1];

            premise = premise.replaceFirst("IF ", "").trim();
            String[] splitPremiseConditions = premise.split(" AND ");
            premises = new ArrayList<>();
            for (String expression : splitPremiseConditions) {
                String[] splitExpression = expression.trim().split(" IS ");
                if (hasCorrectFormat(splitExpression)) {
                    FuzzyExpression fuzzyExpression = createFuzzyExpression(splitExpression, linguisticVariables);
                    premises.add(fuzzyExpression);
                }
            }

            String[] splitConclusion = conclusionAsString.trim().split(" IS ");
            if (hasCorrectFormat(splitConclusion)) {
                conclusion = createFuzzyExpression(splitConclusion, linguisticVariables);
            }
        }
    }

    private static LinguisticVariable find(Set<LinguisticVariable> linguisticVariables, String linguisticVariableName) {
        return linguisticVariables.stream()
                .filter(x -> x.name.equalsIgnoreCase(linguisticVariableName))
                .findFirst()
                .orElseThrow();
    }

    private boolean hasCorrectFormat(String[] splitExpression) {
        return splitExpression.length == 2;
    }

    private boolean hasPremiseAndConclusion(String[] splitRule) {
        return splitRule.length == 2;
    }

    private FuzzyExpression createFuzzyExpression(String[] splitConclusion, Set<LinguisticVariable> linguisticVariables) {
        String linguisticVariableName = splitConclusion[0];
        String linguisticValueName = splitConclusion[1];
        return new FuzzyExpression(find(linguisticVariables, linguisticVariableName), linguisticValueName);
    }

    FuzzySet apply(ArrayList<NumericalValue> values) {
        double degree = 1;
        for (FuzzyExpression fuzzyExpression : premises) {
            double localDegree = values.stream()
                    .filter(value -> value.belongsTo(fuzzyExpression))
                    .findFirst()
                    .map(fuzzyExpression::membershipDegree)
                    .orElse(0.0);
            degree = Math.min(degree, localDegree);
        }
        /* Larsen implication
            μ     (x,y) = μ (x)·μ (y)
             (A→B)         A     B
        */
        return conclusion.fuzzySet().applyDegree(degree);
    }
}
