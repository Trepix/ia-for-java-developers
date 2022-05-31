package io.trepix.ia.sistemaexperto;

import io.trepix.ia.sistemaexperto.Fact;

import java.util.List;

public interface HumanMachineInterface {
    String askForValue(String question);

    void showFacts(List<Fact<?>> facts);
}
