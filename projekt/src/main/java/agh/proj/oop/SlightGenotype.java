package agh.proj.oop;

import java.util.concurrent.ThreadLocalRandom;

public class SlightGenotype extends MutationGenerator{
    @Override
    public void mutate(Animal a) {
        super.mutate(a);
        for (int i = 0; i < mutNum; i++) {
            mutationPlace = shuffle.get(i);
            mutation = (ThreadLocalRandom.current().nextInt(0,2) == 0) ? -1 : 1;
            a.genotype.set(mutationPlace,((a.genotype.get(mutationPlace)+mutation)%8+8)%8);
        }
    }
}
