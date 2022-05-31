package io.trepix.ia.sistemaexperto.facts;


import io.trepix.ia.sistemaexperto.Fact;

import java.security.InvalidParameterException;

import static java.lang.Integer.parseInt;

public class FactFactory {

    public static Fact<?> createFact(Fact<?> fact, String value) {
        Class<?> factClass = fact.getClass();
        if (factClass.equals(IntegerFact.class)) {
            Integer integerValue = Integer.decode(value);
            return new IntegerFact(fact.name(), integerValue, null, 0);
        } else if (factClass.equals(BooleanFact.class)) {
            boolean booleanValue = value.equals("yes");
            return new BooleanFact(fact.name(), booleanValue, null, 0);
        }
        throw new InvalidParameterException();
    }

    public static Fact<?> createFact(String stringFact) {
        stringFact = stringFact.trim();
        if (isIntegerFact(stringFact)) {
            return createIntegerFact(stringFact);
        } else {
            return createBooleanFact(stringFact);
        }
    }

    private static boolean isIntegerFact(String stringFact) {
        return stringFact.contains("=");
    }

    private static IntegerFact createIntegerFact(String stringFact) {
        stringFact = stringFact.replaceFirst("^\\(", "");
        String[] premise = stringFact.split("[=()]");
        if (isValidPremise(premise)) {
            String name = premise[0].trim();
            String value = premise[1].trim();
            String question = null;
            if (premise.length == 3) {
                question = premise[2].trim();
            }
            return new IntegerFact(name, parseInt(value), question, 0);
        }
        return null;
    }

    private static boolean isValidPremise(String[] premise) {
        return premise.length >= 2;
    }

    private static BooleanFact createBooleanFact(String stringFact) {
        boolean value = true;
        if (isNegativeFact(stringFact)) {
            value = false;
            stringFact = stringFact.substring(1).trim();
        }
        stringFact = stringFact.replaceFirst("^\\(", "");
        String[] premise = stringFact.split("[()]");
        String name = premise[0].trim();
        String question = null;
        if (premise.length == 2) {
            question = premise[1].trim();
        }
        return new BooleanFact(name, value, question, 0);
    }

    private static boolean isNegativeFact(String stringFact) {
        return stringFact.startsWith("!");
    }


}
