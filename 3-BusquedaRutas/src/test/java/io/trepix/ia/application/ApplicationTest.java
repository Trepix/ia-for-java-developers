package io.trepix.ia.application;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//Should be executed with option -ea in VM Arguments to enable assertions
public class ApplicationTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    public static void main(String[] args) {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Application.main(new String[]{});

        String output = outContent.toString();

        System.setOut(originalOut);
        System.setErr(originalErr);


        assert output.contains("[0;0;Camino]-[0;1;Camino]-[1;1;Camino]-[2;1;Camino]-[3;1;Camino]-[4;1;Camino]-[5;1;Camino]-[6;1;Camino]-[7;1;Camino]-[8;1;Camino]-[8;2;Camino]-[8;3;Camino]-[7;3;Hierba]-[6;3;Hierba]-[5;3;Hierba]-[4;3;Camino]-[4;4;Puente]-[4;5;Camino]-[5;5;Hierba]-[5;6;Hierba]-[5;7;Hierba]-[4;7;Camino]-[3;7;Camino]-[2;7;Camino]-[1;7;Hierba]-[0;7;Hierba]-[0;8;Hierba]-[0;9;Camino]-[1;9;Camino]-[2;9;Camino]-[3;9;Hierba]-[4;9;Hierba]-[5;9;Hierba]-[6;9;Hierba]-[7;9;Hierba]-[8;9;Hierba]-[9;9;Hierba]") : "Case 1 - Depth FAIL";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[0;2;Hierba]-[0;3;Hierba]-[1;3;Hierba]-[2;3;Hierba]-[3;3;Hierba]-[4;3;Camino]-[4;4;Puente]-[4;5;Camino]-[4;6;Camino]-[4;7;Camino]-[4;8;Hierba]-[4;9;Hierba]-[5;9;Hierba]-[6;9;Hierba]-[7;9;Hierba]-[8;9;Hierba]-[9;9;Hierba]") : "Case 1 - Width FAIL";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[1;1;Camino]-[2;1;Camino]-[3;1;Camino]-[4;1;Camino]-[4;2;Camino]-[4;3;Camino]-[4;4;Puente]-[4;5;Camino]-[4;6;Camino]-[4;7;Camino]-[4;8;Hierba]-[4;9;Hierba]-[5;9;Hierba]-[6;9;Hierba]-[7;9;Hierba]-[8;9;Hierba]-[9;9;Hierba]") : "Case 1 - Bellman-Ford FAIL";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[1;1;Camino]-[2;1;Camino]-[3;1;Camino]-[4;1;Camino]-[4;2;Camino]-[4;3;Camino]-[4;4;Puente]-[4;5;Camino]-[4;6;Camino]-[4;7;Camino]-[4;8;Hierba]-[4;9;Hierba]-[5;9;Hierba]-[6;9;Hierba]-[7;9;Hierba]-[8;9;Hierba]-[9;9;Hierba]") : "Case 1 - Dijkstra";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[1;1;Camino]-[2;1;Camino]-[3;1;Camino]-[4;1;Camino]-[4;2;Camino]-[4;3;Camino]-[4;4;Puente]-[4;5;Camino]-[4;6;Camino]-[4;7;Camino]-[4;8;Hierba]-[4;9;Hierba]-[5;9;Hierba]-[6;9;Hierba]-[7;9;Hierba]-[8;9;Hierba]-[9;9;Hierba]") : "Case 1 - A*";
        assert output.contains("[0;0;Camino]-[1;0;Hierba]-[2;0;Hierba]-[2;1;Hierba]-[2;2;Hierba]-[2;3;Camino]-[3;3;Camino]-[4;3;Puente]-[5;3;Camino]-[6;3;Camino]-[7;3;Camino]-[8;3;Camino]-[8;4;Camino]-[9;4;Camino]-[9;5;Camino]-[9;6;Camino]-[8;6;Hierba]-[8;7;Camino]-[8;8;Camino]-[8;9;Camino]-[8;10;Camino]-[7;10;Hierba]-[6;10;Hierba]-[5;10;Hierba]-[5;9;Puente]-[5;8;Hierba]-[4;8;Hierba]-[3;8;Hierba]-[2;8;Hierba]-[2;9;Puente]-[2;10;Hierba]-[1;10;Hierba]-[1;11;Camino]-[1;12;Camino]-[1;13;Camino]-[0;13;Hierba]-[0;14;Hierba]-[0;15;Hierba]-[1;15;Camino]-[1;16;Camino]-[2;16;Hierba]-[3;16;Hierba]-[3;15;Hierba]-[3;14;Camino]-[4;14;Puente]-[5;14;Camino]-[6;14;Camino]-[7;14;Camino]-[8;14;Camino]-[9;14;Camino]-[9;15;Hierba]-[9;16;Puente]-[9;17;Hierba]-[8;17;Hierba]-[8;18;Hierba]-[8;19;Hierba]-[9;19;Hierba]") : "Case 2 - Depth";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[0;2;Camino]-[1;2;Camino]-[1;3;Camino]-[2;3;Camino]-[2;4;Hierba]-[2;5;Hierba]-[2;6;Hierba]-[2;7;Hierba]-[2;8;Hierba]-[2;9;Puente]-[2;10;Hierba]-[2;11;Hierba]-[2;12;Hierba]-[1;12;Camino]-[1;13;Camino]-[1;14;Camino]-[2;14;Camino]-[3;14;Camino]-[4;14;Puente]-[5;14;Camino]-[5;15;Hierba]-[6;15;Hierba]-[7;15;Hierba]-[8;15;Hierba]-[9;15;Hierba]-[9;16;Puente]-[9;17;Hierba]-[8;17;Hierba]-[8;18;Hierba]-[8;19;Hierba]-[9;19;Hierba]") : "Case 2 - Width";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[0;2;Camino]-[1;2;Camino]-[1;3;Camino]-[2;3;Camino]-[2;4;Hierba]-[2;5;Hierba]-[2;6;Hierba]-[2;7;Hierba]-[2;8;Hierba]-[2;9;Puente]-[2;10;Hierba]-[2;11;Hierba]-[1;11;Camino]-[1;12;Camino]-[1;13;Camino]-[1;14;Camino]-[2;14;Camino]-[3;14;Camino]-[4;14;Puente]-[5;14;Camino]-[6;14;Camino]-[7;14;Camino]-[8;14;Camino]-[9;14;Camino]-[9;15;Hierba]-[9;16;Puente]-[9;17;Hierba]-[8;17;Hierba]-[8;18;Hierba]-[8;19;Hierba]-[9;19;Hierba]") : "Case 2 - Bellman-Ford";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[0;2;Camino]-[1;2;Camino]-[1;3;Camino]-[2;3;Camino]-[2;4;Hierba]-[2;5;Hierba]-[2;6;Hierba]-[2;7;Hierba]-[2;8;Hierba]-[2;9;Puente]-[2;10;Hierba]-[1;10;Hierba]-[1;11;Camino]-[1;12;Camino]-[1;13;Camino]-[1;14;Camino]-[2;14;Camino]-[3;14;Camino]-[4;14;Puente]-[5;14;Camino]-[6;14;Camino]-[7;14;Camino]-[8;14;Camino]-[9;14;Camino]-[9;15;Hierba]-[9;16;Puente]-[9;17;Hierba]-[8;17;Hierba]-[8;18;Hierba]-[8;19;Hierba]-[9;19;Hierba]") : "Case 2 - Dijkstra";
        assert output.contains("[0;0;Camino]-[0;1;Camino]-[0;2;Camino]-[1;2;Camino]-[1;3;Camino]-[2;3;Camino]-[2;4;Hierba]-[2;5;Hierba]-[2;6;Hierba]-[2;7;Hierba]-[2;8;Hierba]-[2;9;Puente]-[2;10;Hierba]-[2;11;Hierba]-[1;11;Camino]-[1;12;Camino]-[1;13;Camino]-[1;14;Camino]-[2;14;Camino]-[3;14;Camino]-[4;14;Puente]-[5;14;Camino]-[6;14;Camino]-[7;14;Camino]-[8;14;Camino]-[9;14;Camino]-[9;15;Hierba]-[9;16;Puente]-[9;17;Hierba]-[8;17;Hierba]-[8;18;Hierba]-[8;19;Hierba]-[9;19;Hierba]") : "Case 2 - A*";

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void Assert() {

    }
}
