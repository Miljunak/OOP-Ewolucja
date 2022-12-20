package agh.proj.oop;

public class World {

    public static void main(String[] args) {

        AbstractWorldMap map = new AbstractWorldMap(5,5);
        Animal lopez = new Animal(map, 0, 37, 10);
        Animal java = new Animal(map, 0, 20, 5);
        map.addElement(lopez);
        map.addElement(java);

        SimulationEngine engine = new SimulationEngine(map);
        engine.run(10);

    }
}
