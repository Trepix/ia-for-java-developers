package io.trepix.ia.sistemaexperto;

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

    public List<Fact<?>> getPremises() {
        return premises;
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
