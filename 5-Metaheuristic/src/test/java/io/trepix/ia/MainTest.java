package io.trepix.ia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//Should be executed with option -ea in VM Arguments to enable assertions
public class MainTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;



    public static void main(String[] args) {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        String seed = "1952";
        Application.main(new String[]{seed});

        String output = outContent.toString().replace("\r", "");

        System.setOut(originalOut);
        System.setErr(originalErr);

        assert output.contains("Greedy Algorithm\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - K (4.0, 10.0) - I (5.0, 12.0) - L (3.0, 7.0)") : "Case 1 - Greedy Algorithm FAIL";
        assert output.contains("Gradient Descent\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - L (3.0, 7.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0)") : "Case 1 - Gradient Descent FAIL";
        assert output.contains("Tabu Search\nValor : 54.0 - Peso : 19.0 - D (3.0, 10.0) - A (4.0, 15.0) - L (3.0, 7.0) - I (5.0, 12.0) - K (4.0, 10.0)") : "Case 1 - Tabu Search FAIL";
        assert output.contains("Simulated Annealing\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - L (3.0, 7.0) - K (4.0, 10.0) - I (5.0, 12.0)") : "Case 1 - Simulated Annealing FAIL";
        assert output.contains("Particle Swarm\nValor : 54.0 - Peso : 19.0 - D (3.0, 10.0) - A (4.0, 15.0) - I (5.0, 12.0) - L (3.0, 7.0) - K (4.0, 10.0)") : "Case 1 - Particle Swarm FAIL";

        assert output.contains("Greedy Algorithm\nValor : 171.22212161608886 - Peso : 29.340658436925168 - 013 (0.4553244627397157, 14.952025706133174) - 068 (0.3682514780896451, 10.747748135109177) - 086 (0.6902877143015473, 16.445958532700644) - 083 (0.7081698516775581, 14.01911730071255) - 050 (1.0447384449692643, 13.957309525540653) - 055 (2.696006245348305, 17.389399492894295) - 037 (2.460779076942771, 12.375745544550824) - 021 (4.681438169744675, 17.581572253953222) - 084 (5.0408199054967575, 17.72239935141759) - 080 (2.834848250515126, 9.757096555217277) - 064 (3.8864244547437012, 13.05255226787681) - 087 (4.4735703823561, 13.221196949982621)") : "Case 2 - Greedy Algorithm FAIL";
        assert output.contains("Gradient Descent\nValor : 36.95740910307911 - Peso : 29.797741317207713 - 070 (18.32234587924412, 8.338354045344726) - 004 (10.430656992994326, 14.661745532193727) - 050 (1.0447384449692643, 13.957309525540653)") : "Case 2 - Gradient Descent FAIL";
        assert output.contains("Tabu Search\nValor : 47.5812622559845 - Peso : 29.82457508822259 - 034 (1.946398079375039, 0.0789596649796076) - 083 (0.7081698516775581, 14.01911730071255) - 007 (22.01322183487892, 3.7180402528472256) - 037 (2.460779076942771, 12.375745544550824) - 055 (2.696006245348305, 17.389399492894295)") : "Case 2 - Tabu Search FAIL";
        assert output.contains("Simulated Annealing\nValor : 76.50791316003114 - Peso : 29.925293432474152 - 100 (22.081012407106112, 2.3803847756466445) - 073 (2.9262412732112706, 0.5732792168346479) - 068 (0.3682514780896451, 10.747748135109177) - 013 (0.4553244627397157, 14.952025706133174) - 055 (2.696006245348305, 17.389399492894295) - 083 (0.7081698516775581, 14.01911730071255) - 086 (0.6902877143015473, 16.445958532700644)") : "Case 2 - Simulated Annealing FAIL";
        assert output.contains("Particle Swarm\nValor : 100.25764466859985 - Peso : 29.84642483533817 - 012 (4.386973504764658, 1.462899856686699) - 055 (2.696006245348305, 17.389399492894295) - 084 (5.0408199054967575, 17.72239935141759) - 085 (14.911177690690435, 8.512812473538256) - 050 (1.0447384449692643, 13.957309525540653) - 083 (0.7081698516775581, 14.01911730071255) - 086 (0.6902877143015473, 16.445958532700644) - 068 (0.3682514780896451, 10.747748135109177)") : "Case 2 - Particle Swarm FAIL";

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }


}
