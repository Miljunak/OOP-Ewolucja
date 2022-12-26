package agh.proj.oop;

import java.util.concurrent.ThreadLocalRandom;

public class SlightGenotype extends MutationGenerator{
    @Override
    public void mutate(Animal a){
        int n=a.genotype.size();
        int mutNum = ThreadLocalRandom.current().nextInt(0, n);
        for(int i=0; i<mutNum; i++){
            int mutationPlace=ThreadLocalRandom.current().nextInt(0, n);
            int mutation=ThreadLocalRandom.current().nextInt(0,2);
            if(mutation==0) mutation = -1;
            a.genotype.set(mutationPlace,a.genotype.get(mutationPlace)+mutation);
        }
    }
}
