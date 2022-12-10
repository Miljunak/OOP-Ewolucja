package agh.proj.oop;

public class World {

    public static void main(String[] args) {
        AbstractWorldMap map = new AbstractWorldMap(2,1);
        Animal furry = new Animal(map, 0, 21, 10);
        Animal lopez = new Animal(map, 0, 37, 10);
        map.addElement(lopez);
        map.addGrass(1);
        System.out.println(map.objectsAt(lopez.getPos()));


    }
}
