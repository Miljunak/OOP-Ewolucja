package agh.proj.oop;

public class SimulationEngine {
    public AbstractWorldMap map;
    SimulationEngine(AbstractWorldMap map) {
        this.map = map;
    }
    void run(int n){
        for (int i = 0; i < n; i++) {
            map.nextMove();
        }
    }
}
