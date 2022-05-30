package io.trepix.ia.aplicacion;

import io.trepix.ia.sistemaexperto.Fact;
import io.trepix.ia.sistemaexperto.InferenceEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HumanMachineInterface {
    public static void main(String[] args) {
        HumanMachineInterface humanMachineInterface = new HumanMachineInterface();
        humanMachineInterface.run();
    }

    public void run() {
        // Creación del motor
        System.out.println("** Creación del motor **");
        InferenceEngine m = new InferenceEngine(this);
        
        // Agregar las reglas
        System.out.println("** Agregar las reglas **");
        m.AgregarRegla("R1 : IF (Orden=3(¿Cuál es el orden?)) THEN  Triángulo");
        m.AgregarRegla("R2 : IF (Triángulo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Triángulo Rectángulo");
        m.AgregarRegla("R3 : IF (Triángulo AND Lados Iguales=2(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Isósceles");
        m.AgregarRegla("R4 : IF (Triángulo rectángulo AND Triángulo Isósceles) THEN Triángulo Rectángulo Isósceles");
        m.AgregarRegla("R5 : IF (Triángulo AND Lados Iguales=3(¿Cuántos lados iguales tiene la figura?)) THEN Triángulo Equilátero");
        m.AgregarRegla("R6 : IF (Orden=4(¿Cuál es el orden?)) THEN Cuadrilátero");
        m.AgregarRegla("R7 : IF (Cuadrilátero AND Lados Paralelos=2(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Trapecio");
        m.AgregarRegla("R8 : IF (Cuadrilátero AND Lados Paralelos=4(¿Cuántos lados paralelos entre sí - 0, 2 o 4?)) THEN Paralelogramo");
        m.AgregarRegla("R9 : IF (Paralelogramo AND Ángulo Recto(¿La figura tiene al menos un ángulo recto?)) THEN Rectángulo");
        m.AgregarRegla("R10 : IF (Paralelogramo AND Lados Iguales=4(¿Cuántos lados iguales tiene la figura?)) THEN Rombo");
        m.AgregarRegla("R11 : IF (Rectángulo AND Rombo THEN Cuadrado");
         
        // Resolución
        do {
            System.out.println("\n** Resolución **");
            m.resolve();
        } while (true);
    }

    public int askForIntegerValue(String question) {
        System.out.println(question);
        try { 
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return Integer.decode(br.readLine()); 
        } 
        catch (Exception e) { 
            return 0; 
        } 
    }

    public boolean askForBooleanValue(String question) {
        try {
            System.out.println(question + " (si, no)");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String res = br.readLine();
            return (res.equals("si"));
        } 
        catch (IOException e) {
            return false;
        }
    }

    public void showFacts(ArrayList<Fact<?>> facts) {
        StringBuilder res = new StringBuilder("Solución(s) encontrada(s) : \n");
        facts.sort((Fact<?> f1, Fact<?> f2) -> Integer.compare(f2.level(), f1.level()));
        for(Fact<?> f : facts) {
            if (f.level() != 0) {
                res.append(f).append("\n");
            }
        }
        System.out.println(res);
    }

}
