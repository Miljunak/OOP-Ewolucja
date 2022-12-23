package agh.proj.oop;

public class SimulationEngine {
    public AbstractWorldMap map;
    public AbstractGrassRegion grassRegion;
    SimulationEngine(AbstractWorldMap map, AbstractGrassRegion grassRegion) {

        this.map = map;
        this.grassRegion=grassRegion;
    }
    void run(int n){
        for (int i = 0; i < n; i++) {
            map.nextMove();
        }
    }
}
