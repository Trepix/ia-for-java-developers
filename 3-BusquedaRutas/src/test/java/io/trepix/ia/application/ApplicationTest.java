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


        assert output.contains("[0;0;Road]-[0;1;Road]-[1;1;Road]-[2;1;Road]-[3;1;Road]-[4;1;Road]-[5;1;Road]-[6;1;Road]-[7;1;Road]-[8;1;Road]-[8;2;Road]-[8;3;Road]-[7;3;Grass]-[6;3;Grass]-[5;3;Grass]-[4;3;Road]-[4;4;Bridge]-[4;5;Road]-[5;5;Grass]-[5;6;Grass]-[5;7;Grass]-[4;7;Road]-[3;7;Road]-[2;7;Road]-[1;7;Grass]-[0;7;Grass]-[0;8;Grass]-[0;9;Road]-[1;9;Road]-[2;9;Road]-[3;9;Grass]-[4;9;Grass]-[5;9;Grass]-[6;9;Grass]-[7;9;Grass]-[8;9;Grass]-[9;9;Grass]") : "Case 1 - Depth FAIL";
        assert output.contains("[0;0;Road]-[0;1;Road]-[0;2;Grass]-[0;3;Grass]-[1;3;Grass]-[2;3;Grass]-[3;3;Grass]-[4;3;Road]-[4;4;Bridge]-[4;5;Road]-[4;6;Road]-[4;7;Road]-[4;8;Grass]-[4;9;Grass]-[5;9;Grass]-[6;9;Grass]-[7;9;Grass]-[8;9;Grass]-[9;9;Grass]") : "Case 1 - Width FAIL";
        assert output.contains("[0;0;Road]-[0;1;Road]-[1;1;Road]-[2;1;Road]-[3;1;Road]-[4;1;Road]-[4;2;Road]-[4;3;Road]-[4;4;Bridge]-[4;5;Road]-[4;6;Road]-[4;7;Road]-[4;8;Grass]-[4;9;Grass]-[5;9;Grass]-[6;9;Grass]-[7;9;Grass]-[8;9;Grass]-[9;9;Grass]") : "Case 1 - Bellman-Ford FAIL";
        assert output.contains("[0;0;Road]-[0;1;Road]-[1;1;Road]-[2;1;Road]-[3;1;Road]-[4;1;Road]-[4;2;Road]-[4;3;Road]-[4;4;Bridge]-[4;5;Road]-[4;6;Road]-[4;7;Road]-[4;8;Grass]-[4;9;Grass]-[5;9;Grass]-[6;9;Grass]-[7;9;Grass]-[8;9;Grass]-[9;9;Grass]") : "Case 1 - Dijkstra";
        assert output.contains("[0;0;Road]-[0;1;Road]-[1;1;Road]-[2;1;Road]-[3;1;Road]-[4;1;Road]-[4;2;Road]-[4;3;Road]-[4;4;Bridge]-[4;5;Road]-[4;6;Road]-[4;7;Road]-[4;8;Grass]-[4;9;Grass]-[5;9;Grass]-[6;9;Grass]-[7;9;Grass]-[8;9;Grass]-[9;9;Grass]") : "Case 1 - A*";
        assert output.contains("[0;0;Road]-[1;0;Grass]-[2;0;Grass]-[2;1;Grass]-[2;2;Grass]-[2;3;Road]-[3;3;Road]-[4;3;Bridge]-[5;3;Road]-[6;3;Road]-[7;3;Road]-[8;3;Road]-[8;4;Road]-[9;4;Road]-[9;5;Road]-[9;6;Road]-[8;6;Grass]-[8;7;Road]-[8;8;Road]-[8;9;Road]-[8;10;Road]-[7;10;Grass]-[6;10;Grass]-[5;10;Grass]-[5;9;Bridge]-[5;8;Grass]-[4;8;Grass]-[3;8;Grass]-[2;8;Grass]-[2;9;Bridge]-[2;10;Grass]-[1;10;Grass]-[1;11;Road]-[1;12;Road]-[1;13;Road]-[0;13;Grass]-[0;14;Grass]-[0;15;Grass]-[1;15;Road]-[1;16;Road]-[2;16;Grass]-[3;16;Grass]-[3;15;Grass]-[3;14;Road]-[4;14;Bridge]-[5;14;Road]-[6;14;Road]-[7;14;Road]-[8;14;Road]-[9;14;Road]-[9;15;Grass]-[9;16;Bridge]-[9;17;Grass]-[8;17;Grass]-[8;18;Grass]-[8;19;Grass]-[9;19;Grass]") : "Case 2 - Depth";
        assert output.contains("[0;0;Road]-[0;1;Road]-[0;2;Road]-[1;2;Road]-[1;3;Road]-[2;3;Road]-[2;4;Grass]-[2;5;Grass]-[2;6;Grass]-[2;7;Grass]-[2;8;Grass]-[2;9;Bridge]-[2;10;Grass]-[2;11;Grass]-[2;12;Grass]-[1;12;Road]-[1;13;Road]-[1;14;Road]-[2;14;Road]-[3;14;Road]-[4;14;Bridge]-[5;14;Road]-[5;15;Grass]-[6;15;Grass]-[7;15;Grass]-[8;15;Grass]-[9;15;Grass]-[9;16;Bridge]-[9;17;Grass]-[8;17;Grass]-[8;18;Grass]-[8;19;Grass]-[9;19;Grass]") : "Case 2 - Width";
        assert output.contains("[0;0;Road]-[0;1;Road]-[0;2;Road]-[1;2;Road]-[1;3;Road]-[2;3;Road]-[2;4;Grass]-[2;5;Grass]-[2;6;Grass]-[2;7;Grass]-[2;8;Grass]-[2;9;Bridge]-[2;10;Grass]-[2;11;Grass]-[1;11;Road]-[1;12;Road]-[1;13;Road]-[1;14;Road]-[2;14;Road]-[3;14;Road]-[4;14;Bridge]-[5;14;Road]-[6;14;Road]-[7;14;Road]-[8;14;Road]-[9;14;Road]-[9;15;Grass]-[9;16;Bridge]-[9;17;Grass]-[8;17;Grass]-[8;18;Grass]-[8;19;Grass]-[9;19;Grass]") : "Case 2 - Bellman-Ford";
        assert output.contains("[0;0;Road]-[0;1;Road]-[0;2;Road]-[1;2;Road]-[1;3;Road]-[2;3;Road]-[2;4;Grass]-[2;5;Grass]-[2;6;Grass]-[2;7;Grass]-[2;8;Grass]-[2;9;Bridge]-[2;10;Grass]-[1;10;Grass]-[1;11;Road]-[1;12;Road]-[1;13;Road]-[1;14;Road]-[2;14;Road]-[3;14;Road]-[4;14;Bridge]-[5;14;Road]-[6;14;Road]-[7;14;Road]-[8;14;Road]-[9;14;Road]-[9;15;Grass]-[9;16;Bridge]-[9;17;Grass]-[8;17;Grass]-[8;18;Grass]-[8;19;Grass]-[9;19;Grass]") : "Case 2 - Dijkstra";
        assert output.contains("[0;0;Road]-[0;1;Road]-[0;2;Road]-[1;2;Road]-[1;3;Road]-[2;3;Road]-[2;4;Grass]-[2;5;Grass]-[2;6;Grass]-[2;7;Grass]-[2;8;Grass]-[2;9;Bridge]-[2;10;Grass]-[2;11;Grass]-[1;11;Road]-[1;12;Road]-[1;13;Road]-[1;14;Road]-[2;14;Road]-[3;14;Road]-[4;14;Bridge]-[5;14;Road]-[6;14;Road]-[7;14;Road]-[8;14;Road]-[9;14;Road]-[9;15;Grass]-[9;16;Bridge]-[9;17;Grass]-[8;17;Grass]-[8;18;Grass]-[8;19;Grass]-[9;19;Grass]") : "Case 2 - A*";

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static void Assert() {

    }
}
