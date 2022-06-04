package io.trepix.ia.fuzzylogic;

import java.util.ArrayList;
import java.util.List;

public class FuzzyRule {
    private List<FuzzyExpression> premises;
    private FuzzyExpression conclusion;

    public FuzzyRule(String rule, ControladorDifuso controlador) {
        rule = rule.toUpperCase();
        String[] splitRule = rule.split(" THEN ");
        if (hasPremiseAndConclusion(splitRule)) {
            String premise = splitRule[0];
            String conclusionAsString = splitRule[1];

            premise = premise.replaceFirst("IF ", "").trim();
            String[] splitPremiseConditions = premise.split(" AND ");
            premises = new ArrayList<>();
            for(String expression : splitPremiseConditions) {
                String[] splitExpression = expression.trim().split(" IS ");
                if (hasCorrectFormat(splitExpression)) {
                    String linguisticVariable = splitExpression[0];
                    String linguisticValue = splitExpression[1];
                    FuzzyExpression fuzzyExpression = new FuzzyExpression(controlador.VariableLinguisticaParaNombre(linguisticVariable), linguisticValue);
                    premises.add(fuzzyExpression);
                }
            }

            String[] splitConclusion = conclusionAsString.trim().split(" IS ");
            if (hasCorrectFormat(splitConclusion)) {
                String linguisticVariable = splitConclusion[0];
                String linguisticValue = splitConclusion[1];
                conclusion = new FuzzyExpression(controlador.VariableLinguisticaParaNombre(linguisticVariable), linguisticValue);
            }
        }
    }

    private boolean hasCorrectFormat(String[] splitExpression) {
        return splitExpression.length == 2;
    }

    private boolean hasPremiseAndConclusion(String[] splitRule) {
        return splitRule.length == 2;
    }

    ConjuntoDifuso apply(ArrayList<NumericalValue> problema) {
        double degree = 1;
        for (FuzzyExpression fuzzyExpression : premises) {
            double localDegree = 0;
            for (NumericalValue numericalValue : problema) {
                if (numericalValue.belongsTo(fuzzyExpression)) {
                    localDegree = fuzzyExpression.membershipDegree(numericalValue);
                    break;
                }
            }
            degree = Math.min(degree, localDegree);
        }
        return conclusion.fuzzySet().applyMembershipDegree(degree);
    }
}
