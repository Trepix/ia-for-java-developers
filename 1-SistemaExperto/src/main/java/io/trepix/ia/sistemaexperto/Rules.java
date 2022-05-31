package io.trepix.ia.sistemaexperto;

import java.util.ArrayList;
import java.util.List;

class Rules {
    protected List<Rule> rules;

    public Rules() {
        rules = new ArrayList<>();
    }

    public Rules(Rules rules) {
        this.rules = new ArrayList<>();
        for (Rule rule : rules.rules) {
            Rule copy = new Rule(rule.name, rule.premises, rule.conclusion);
            this.rules.add(copy);
        }
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void addRule(Rule r) {
        rules.add(r);
    }

    public void delete(Rule r) {
        rules.remove(r);
    }
}
