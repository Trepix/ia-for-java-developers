package io.trepix.ia.sistemaexperto;

import java.util.List;

public interface HumanMachineInterface {
    String askForValue(String question);

    void showFacts(List<Fact<?>> facts);
}
