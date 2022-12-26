package agh.proj.oop;

import java.util.concurrent.ThreadLocalRandom;

abstract public class MutationGenerator {
    public void mutate(Animal a){
        int n=a.genotype.size();
        int mutNum = ThreadLocalRandom.current().nextInt(0, n);
        for(int i=0; i<mutNum; i++){
            int mutationPlace=ThreadLocalRandom.current().nextInt(0, n);
            int mutation=ThreadLocalRandom.current().nextInt(0, n);
            a.genotype.set(mutationPlace,mutation);
        }
    }
}
