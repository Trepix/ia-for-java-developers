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

        String seed = "1618033988749";
        Application.main(new String[]{seed});

        String output = outContent.toString().replace("\r", "");

        System.setOut(originalOut);
        System.setErr(originalErr);


        assert output.contains("Greedy Algorithm\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0) - L (3.0, 7.0)") : "Case 1 - Greedy Algorithm FAIL";
        assert output.contains("Gradient Descent\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0) - L (3.0, 7.0)") : "Case 1 - Gradient Descent FAIL";
        assert output.contains("Tabu Search\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0) - L (3.0, 7.0)") : "Case 1 - Tabu Search FAIL";
        assert output.contains("Particle Swarm\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0) - L (3.0, 7.0)") : "Case 1 - Particle Swarm FAIL";
        assert output.contains("Simulated Annealing\nValor : 54.0 - Peso : 19.0 - A (4.0, 15.0) - D (3.0, 10.0) - I (5.0, 12.0) - K (4.0, 10.0) - L (3.0, 7.0)") : "Case 1 - Simulated Annealing FAIL";
        
        assert output.contains("Greedy Algorithm\nValor : 142.7608524837154 - Peso : 28.514171647473677 - 006 (5.966474243101088, 19.84310522212164) - 010 (1.6710228299469354, 2.6500343124979175) - 012 (0.559159659094357, 18.680697821346605) - 025 (3.4870100795680625, 16.346479803769583) - 028 (4.710793735357784, 16.847791140714538) - 034 (4.921552169044931, 18.170300461057195) - 036 (3.0385616559772854, 9.984900304398476) - 054 (0.3903794306963593, 4.030079026281104) - 064 (0.8724444237210982, 4.9302989896989935) - 088 (0.6764022541245684, 18.768976144012964) - 089 (2.220371166841204, 12.50818925781638)") : "Case 2 - Greedy Algorithm FAIL";
        assert output.contains("Gradient Descent\nValor : 136.64731978704472 - Peso : 29.1852938557224 - 006 (5.966474243101088, 19.84310522212164) - 010 (1.6710228299469354, 2.6500343124979175) - 012 (0.559159659094357, 18.680697821346605) - 022 (3.3129146008258257, 8.379890287160897) - 025 (3.4870100795680625, 16.346479803769583) - 034 (4.921552169044931, 18.170300461057195) - 036 (3.0385616559772854, 9.984900304398476) - 054 (0.3903794306963593, 4.030079026281104) - 059 (2.0690013427806853, 2.3543681568829378) - 064 (0.8724444237210982, 4.9302989896989935) - 088 (0.6764022541245684, 18.768976144012964) - 089 (2.220371166841204, 12.50818925781638)") : "Case 2 - Gradient Descent FAIL";
        assert output.contains("Tabu Search\nValor : 136.6473197870447 - Peso : 29.1852938557224 - 006 (5.966474243101088, 19.84310522212164) - 010 (1.6710228299469354, 2.6500343124979175) - 012 (0.559159659094357, 18.680697821346605) - 022 (3.3129146008258257, 8.379890287160897) - 025 (3.4870100795680625, 16.346479803769583) - 034 (4.921552169044931, 18.170300461057195) - 036 (3.0385616559772854, 9.984900304398476) - 054 (0.3903794306963593, 4.030079026281104) - 059 (2.0690013427806853, 2.3543681568829378) - 064 (0.8724444237210982, 4.9302989896989935) - 088 (0.6764022541245684, 18.768976144012964) - 089 (2.220371166841204, 12.50818925781638)") : "Case 2 - Tabu Search FAIL";
        assert output.contains("Particle Swarm\nValor : 147.7075568032654 - Peso : 29.59502592893767 - 006 (5.966474243101088, 19.84310522212164) - 012 (0.559159659094357, 18.680697821346605) - 025 (3.4870100795680625, 16.346479803769583) - 028 (4.710793735357784, 16.847791140714538) - 034 (4.921552169044931, 18.170300461057195) - 054 (0.3903794306963593, 4.030079026281104) - 064 (0.8724444237210982, 4.9302989896989935) - 076 (5.790438767388217, 17.58163893644636) - 088 (0.6764022541245684, 18.768976144012964) - 089 (2.220371166841204, 12.50818925781638)") : "Case 2 - Particle Swarm FAIL";
        assert output.contains("Simulated Annealing\nValor : 143.49470027944724 - Peso : 29.593816679504105 - 006 (5.966474243101088, 19.84310522212164) - 010 (1.6710228299469354, 2.6500343124979175) - 012 (0.559159659094357, 18.680697821346605) - 025 (3.4870100795680625, 16.346479803769583) - 034 (4.921552169044931, 18.170300461057195) - 036 (3.0385616559772854, 9.984900304398476) - 054 (0.3903794306963593, 4.030079026281104) - 064 (0.8724444237210982, 4.9302989896989935) - 076 (5.790438767388217, 17.58163893644636) - 088 (0.6764022541245684, 18.768976144012964) - 089 (2.220371166841204, 12.50818925781638)") : "Case 2 - Simulated Annealing FAIL";

        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }


}
