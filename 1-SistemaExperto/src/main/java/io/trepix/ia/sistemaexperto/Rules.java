package io.trepix.ia.sistemaexperto;

import java.util.ArrayList;

class Rules {
    protected ArrayList<Rule> rules;
    public ArrayList<Rule> getRules() {
       return rules;
    }

    public Rules() {
        rules = new ArrayList<>();
    }

    public Rules(ArrayList<Rule> rules) {
        this.rules = new ArrayList<>();
        for (Rule rule : rules) {
            Rule copy = new Rule(rule.name, rule.premises, rule.conclusion);
            this.rules.add(copy);
        }
    }
    public void addRule(Rule r) {
        rules.add(r);
    }
    public void delete(Rule r) {
        rules.remove(r);
    }
}
