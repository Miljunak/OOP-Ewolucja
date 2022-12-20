package agh.proj.oop;

public class World {

    public static void main(String[] args) {

        GlobeMap map = new GlobeMap(5,5);
        Animal lopez = new Animal(map, 0, 37, 10);
        Animal java = new Animal(map, 0, 8, 5);
        map.giveBirth(lopez);
        map.giveBirth(java);

        SimulationEngine engine = new SimulationEngine(map);
        engine.run(10);

    }
}
