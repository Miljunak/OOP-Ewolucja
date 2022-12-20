package agh.proj.oop;

public class World {

    public static void main(String[] args) {

        AbstractWorldMap map = new AbstractWorldMap(5,5);
        Animal lopez = new Animal(map, 0, 40, 10);
        Animal java = new Animal(map, 0, 15, 5);
        map.giveBirth(lopez);
        map.giveBirth(java);
        map.addGrass(5);

        SimulationEngine engine = new SimulationEngine(map);
        engine.run(10);

    }
}
