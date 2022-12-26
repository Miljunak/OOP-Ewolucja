package agh.proj.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class SlightGenotype extends MutationGenerator{
    @Override
    public void mutate(Animal a){
        int n=a.genotype.size();
        int mutNum = ThreadLocalRandom.current().nextInt(0, n);
        ArrayList<Integer> shuffle = new ArrayList<>();
        for(int i=0; i<n; i++){
            shuffle.add(i);
        }
        Collections.shuffle(shuffle);
        for(int i=0; i<mutNum; i++){
            int mutationPlace=shuffle.get(i);
            int mutation=ThreadLocalRandom.current().nextInt(0,2);
            if(mutation==0) mutation = -1;
            a.genotype.set(mutationPlace,((a.genotype.get(mutationPlace)+mutation)%8+8)%8);
        }
    }
}
