package agh.proj.oop;

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
            this.map.giveBirth(new Animal(this.map, 0, startingEnergy, genotypeLength));
        }
    }
    void run(int n){
        for (int i = 0; i < n; i++) {
            map.nextMove();
        }
    }
}
