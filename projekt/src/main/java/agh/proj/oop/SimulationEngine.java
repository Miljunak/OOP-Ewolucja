package agh.proj.oop;

import java.util.ArrayList;

public class SimulationEngine {
    public AbstractWorldMap map;
    SimulationEngine(AbstractWorldMap map, int energyConstant, int startingGrass,
                     int growingGrass, int startingEnergy, int animalAmount, int genotypeLength, int grassEnergy,
                     int minMutations, int maxMutations) {
        this.map = map;
        this.map.breedEnergy = energyConstant;
        this.map.growingGrass = growingGrass;
        this.map.grassEnergy = grassEnergy;
        this.map.mutationGenerator.max = maxMutations;
        this.map.mutationGenerator.min = minMutations;
        for (int i = 0; i < startingGrass; i++) this.map.addGrass();
        for (int i = 0; i < animalAmount; i++) {
            ArrayList<Integer> genotypeSet = new ArrayList<Integer>();
            genotypeSet.add(i);
            this.map.giveBirth(new Animal(this.map, 0, startingEnergy, genotypeLength, genotypeSet));
        }
    }
    void run() {
            map.nextMove();
    }
}
