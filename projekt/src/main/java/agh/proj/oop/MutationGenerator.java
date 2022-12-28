package agh.proj.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

abstract public class MutationGenerator {
    protected int n;
    protected int mutNum;
    protected int mutation;
    protected int mutationPlace;
    public int min;
    public int max;
    protected ArrayList<Integer> shuffle;

    public void mutate(Animal a) {
        n = a.genotype.size();
        mutNum = ThreadLocalRandom.current().nextInt(min, max);
        shuffle = new ArrayList<>();
        for (int i = 0; i < n; i++) shuffle.add(i);
        Collections.shuffle(shuffle);
    }
}
