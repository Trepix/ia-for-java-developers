package io.trepix.ia.application;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//Should be executed with option -ea in VM Arguments to enable assertions
public class ZoomGPSTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    public static void main(String[] args) {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        ZoomGPS.main(new String[]{});

        String output = outContent.toString();

        System.setOut(originalOut);
        System.setErr(originalErr);

        assert output.contains("2.5") : "1 - Case fail";
        assert output.contains("1.782051282051282") : "2 - Case fail";
        assert output.contains("2.93189964157706") : "3 - Case fail";
        assert output.contains("2.8919625653729") : "4 - Case fail";
        assert output.contains("0.777777777777777") : "5 - Case fail";

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }
}
