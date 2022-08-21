package io.trepix.ia.fishschool;

import io.trepix.ia.Size;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

//Should be executed with option -ea in VM Arguments to enable assertions
public class OceanTest {

    static long seed = 1922;
    static StartConfig config = new StartConfig(10, new Random(seed));
    static Ocean ocean = new Ocean(new Size(10, 10), config);

    static int iterations = 0;

    public static void main(String[] args) {
        assertIteration(asList(createFish(3.525834484731247, 3.356454124283129, 2.733516467498341), createFish(3.7586834620912146, 5.602925714907033, 0.8258167962670885), createFish(8.875830919705384, 4.770803722259975, 0.7980755733368752), createFish(-1.2925950171574965, 7.1663742625928535, -2.7599451428761106), createFish(9.338564524985443, 9.662441653062817, 0.7786182353091629), createFish(12.069608684243738, 7.26531967522804, -0.3056906232371617), createFish(10.274633814470397, 4.614113478914012, -1.231112695306985), createFish(3.239433934851223, 6.115605047777187, 1.0756968567359468), createFish(6.5900810500239615, 1.819913110009927, -1.0425641032506452), createFish(8.827480685882632, 10.677941650488954, 1.6353783740125374)));
        assertIteration(asList(createFish(1.136408774390929, 5.170467568214752, 2.49223973508495), createFish(6.156758594699342, 7.405489349946448, 0.6445697184855484), createFish(10.333600131598836, 7.392808475279226, 1.0633773258430574), createFish(-2.5803864700571664, 5.636143670444756, -2.6063185022664084), createFish(11.950367978354006, 11.138410054137871, 0.5143734460192256), createFish(12.725031549134842, 6.010647185603848, -0.4314894110823215), createFish(10.105535087954438, 1.6159703293612013, -1.5356107044617644), createFish(5.222453943328798, 8.366745124711006, 0.8486368110552024), createFish(8.589682599165464, -0.4165111888812527, -0.8412468490119923), createFish(8.551245191312447, 12.987255253830774, 1.6630054377495858)));
        assertIteration(asList( createFish(-0.7673082148533119,7.489058007439162,2.258251362956413), createFish(8.964479437224783,8.462231357462107,0.3599712604099325), createFish(10.624211435282605,10.32714998302205,1.3611944787346169), createFish(-2.2181068988094435,3.616242240806678,-2.4029289776693643), createFish(12.929610179039896,11.784462837481328,0.21705117514373853), createFish(12.472148980834328,4.3110944748708775,-0.6022736907740035), createFish(9.23156416711714,-1.2839445608970674,-1.8298286490803972), createFish(7.7016706335444,10.055971161378803,0.5981024540776124), createFish(11.08389939419729,-1.6669980741983963,-0.5891638346968721), createFish(8.15764546882681,12.974067796547219,1.702375579883093)));
        System.out.println("Everything OK, refactor didn't broke anything, ''anything'' exercised by tests :)");
    }

    private static Fish createFish(double x, double y, double radians) {
        return new Fish(new Position(x, y), Direction.fromRadians(radians));
    }

    private static void assertIteration(List<Fish> expectedFishes) {
        ocean.evolve();
        var fishes = ocean.fishes();
        assert expectedFishes.containsAll(fishes) : "Fishes are different in iteration " + iterations++;
    }
}
