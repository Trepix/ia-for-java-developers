package io.trepix.ia.sistemaexperto;

import java.util.ArrayList;
class Facts {
    protected ArrayList<Fact<?>> facts;
    public ArrayList<Fact<?>> getFacts() {
        return facts;
    }

    public Facts() {
        facts = new ArrayList<>();
    } 

    public void clear() {
        facts.clear();
    }
    public void addFact(Fact<?> fact) {
        facts.add(fact);
    }
    public Fact<?> search(String name) {
        for(Fact<?> fact : facts) {
            if (fact.name().equals(name)) {
                return fact;
            }
        }
        return null;
    }
}
