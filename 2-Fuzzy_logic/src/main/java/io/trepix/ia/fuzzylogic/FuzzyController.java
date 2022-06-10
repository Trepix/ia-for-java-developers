package io.trepix.ia.fuzzylogic;

import java.util.*;

public class FuzzyController {
    protected String name;
    protected ArrayList<LinguisticVariable> inputs;
    protected LinguisticVariable output;
    protected ArrayList<FuzzyRule> rules;
    protected ArrayList<NumericalValue> problems;
    
    public FuzzyController(String name) {
        this.name = name;
        inputs = new ArrayList<>();
        rules = new ArrayList<>();
        problems = new ArrayList<>();
    }

    public void addInputVariable(LinguisticVariable vl) {
        inputs.add(vl);
    }

    public void addOutputVariable(LinguisticVariable vl) {
        output = vl;
    }

    public void addRule(String rule) {
        Set<LinguisticVariable> linguisticVariables = new HashSet<>(inputs);
        linguisticVariables.add(output);
        rules.add(new FuzzyRule(rule, linguisticVariables));
    }

    public void addNumericalValue(LinguisticVariable var, double valor) {
        problems.add(new NumericalValue(var, valor));
    }

    public void clearNumericalValues() {
        problems.clear();
    }

    public double resolve() {
        LinkedList<Point> points = new LinkedList<>(List.of(
                new Point(output.getMinimumValue(), 0),
                new Point(output.getMaximumValue(), 0)
        ));
        FuzzySet result = new FuzzySet(points);
        for (FuzzyRule rule : rules) {
            result = result.union(rule.apply(problems));
        }

        return result.Baricentro();
    }
}
