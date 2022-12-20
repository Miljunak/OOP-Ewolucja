package agh.proj.oop;

public class World {

    public static void main(String[] args) {

        AbstractWorldMap map = new AbstractWorldMap(10,10);
        Animal lopez = new Animal(map, 0, 37, 10);
        map.addElement(lopez);

        SimulationEngine engine = new SimulationEngine(map);
        engine.run(1);

    }
}
