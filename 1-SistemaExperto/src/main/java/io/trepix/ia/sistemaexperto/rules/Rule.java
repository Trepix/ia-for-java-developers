package io.trepix.ia.sistemaexperto.rules;

import io.trepix.ia.sistemaexperto.Fact;

import java.util.List;
import java.util.StringJoiner;

public class Rule {
    protected List<Fact<?>> premises;
    protected Fact<?> conclusion;
    protected String name;
    // Constructor
    public Rule(String name, List<Fact<?>> premises, Fact<?> conclusion) {
        this.name = name;
        this.premises = premises;
        this.conclusion = conclusion;
    }

    public static Rule copy(Rule rule) {
        return new Rule(rule.name, rule.premises, rule.conclusion);
    }

    public List<Fact<?>> premises() {
        return premises;
    }

    public Fact<?> conclusion() {
        return this.conclusion;
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
