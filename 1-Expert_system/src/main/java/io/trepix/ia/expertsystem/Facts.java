package io.trepix.ia.expertsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Facts {
    protected List<Fact<?>> facts;

    public Facts() {
        facts = new ArrayList<>();
    }

    public List<Fact<?>> getFacts() {
        return facts;
    }

    public void clear() {
        facts.clear();
    }

    public void addFact(Fact<?> fact) {
        facts.add(fact);
    }

    public Optional<Fact<?>> search(Fact<?> factToSearch) {
        return facts.stream()
                .filter(fact -> fact.isTheSame(factToSearch))
                .findFirst();
    }

    public boolean exists(Fact<?> factToSearch) {
        return facts.stream()
                .anyMatch(fact -> fact.isTheSame(factToSearch));
    }
}
