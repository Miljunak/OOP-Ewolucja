package agh.proj.oop;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenotype extends MutationGenerator {
    @Override
    public void mutate(Animal a) {
        super.mutate(a);
        for (int i = 0; i < mutNum; i++) {
            mutationPlace = shuffle.get(i);
            mutation = ThreadLocalRandom.current().nextInt(0, n);
            a.genotype.set(mutationPlace, mutation);
        }
    }
}
