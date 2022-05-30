package io.trepix.ia.sistemaexperto;

import java.util.ArrayList;
import java.util.Optional;

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
    public Optional<Fact<?>> search(Fact<?> factToSearch) {
        return facts.stream()
                .filter(fact -> fact.name().equals(factToSearch.name()))
                .findFirst();
    }
}
