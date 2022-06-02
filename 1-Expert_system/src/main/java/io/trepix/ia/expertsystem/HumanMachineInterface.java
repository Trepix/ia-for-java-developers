package io.trepix.ia.expertsystem;

import java.util.List;

public interface HumanMachineInterface {
    String askForValue(String question);

    void showFacts(List<Fact<?>> facts);
}
