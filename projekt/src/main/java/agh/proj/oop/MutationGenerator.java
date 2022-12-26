package agh.proj.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

abstract public class MutationGenerator {
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
            int mutation=ThreadLocalRandom.current().nextInt(0, n);
            a.genotype.set(mutationPlace,mutation);
        }
    }
}
