package io.trepix.ia.sistemaexperto;

import io.trepix.ia.sistemaexperto.rules.Rule;

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
            Rule copy = Rule.copy(rule);
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
