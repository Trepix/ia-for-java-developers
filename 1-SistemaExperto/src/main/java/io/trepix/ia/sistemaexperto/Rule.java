package io.trepix.ia.sistemaexperto;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Rule {
    protected ArrayList<Fact<?>> premises;
    public ArrayList<Fact<?>> getPremises() {
        return premises;
    }
    protected Fact<?> conclusion;
    protected String name;
    
    // Constructor
    public Rule(String name , ArrayList<Fact<?>> premises, Fact<?> conclusion) {
        this.name = name ;
        this.premises = premises;
        this.conclusion = conclusion;
    } 

    @Override
    public String toString() { 
        String rule = name + " : IF (";
        
        StringJoiner sj = new StringJoiner(" AND ");
        for(Fact<?> fact : premises) {
            sj.add(fact.toString());
        }
        
        rule += sj + ") THEN " + conclusion.toString();
        return rule;
   }
}
