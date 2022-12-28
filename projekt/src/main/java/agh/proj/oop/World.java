package agh.proj.oop;

public class World {

    public static void main(String[] args) {

        AbstractWorldMap map = new GlobeMap(12,12, false, false);
        AbstractGrassRegion grassRegion = new EquatorGrassRegion(map);
        Animal lopez = new Animal(map, 0, 40, 10);
        Animal java = new Animal(map, 0, 40, 10);
        map.giveBirth(lopez);
        map.giveBirth(java);
        //map.addGrass(5);

        SimulationEngine engine = new SimulationEngine(map, grassRegion);
        engine.run(50);

    }
}
