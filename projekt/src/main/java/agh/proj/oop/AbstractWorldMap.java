package agh.proj.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class AbstractWorldMap implements IObserver {
    public int width;
    public int height;
    public int day = 0;
    public MapVisualizer visualizer;
    public ArrayList<Animal> animals;
    public HashMap<Vector2d, HashSet<AbstractWorldElement> > elements;

    public AbstractWorldMap(int width, int height ) {
        this.width = width;
        this.height = height;
        this.elements = new HashMap<>();
        this.visualizer = new MapVisualizer(this);
        this.animals = new ArrayList<>();
    }

    public HashSet<AbstractWorldElement> objectsAt(Vector2d position) {
        return elements.getOrDefault(position, null);
    }

    /**
     * Function to create a new animal in map, likely going to be used when breeding is implemented.
     * @param animal
     */
    public void giveBirth(Animal animal) {
        animals.add(animal);
        addElement(animal);
    }

    /**
     * Function to kill and remove an animal from the simulation.
     * @param animal
     * @return death_date
     */

    public int mementoMori(Animal animal) {
        animals.remove(animal);
        removeElement(animal);
        return day;
    }

    public void addElement(AbstractWorldElement element) {
        Vector2d position = element.getPos();
        if (!elements.containsKey(position)) {
            elements.put(position, new HashSet<>());
        }
        elements.get(position).add(element);
    }

    public void removeElement(AbstractWorldElement element) {
        Vector2d position = element.getPos();
        if (!elements.containsKey(position)) {
            throw new IllegalArgumentException("no elements at position " + position.toString());
        }
        if (!elements.get(position).contains(element)) {
            throw new IllegalArgumentException("element " + element + " is not on map");
        }
        elements.get(position).remove(element);
        if (elements.get(position).isEmpty()) elements.remove(position);
    }

    public boolean canMoveTo(Vector2d position) {
        return (position.x >= 0 && position.y >= 0) && (position.x < this.width && position.y < this.height);
    }

    public Vector2d getRandom() {
        return new Vector2d(ThreadLocalRandom.current().nextInt(0, width), ThreadLocalRandom.current().nextInt(0, height));
    }

    public void addGrass(int n) {
        for (int i = 0; i < n; i++ ) this.addElement(new Grass(getRandom()));
    }

    public void nextMove() {
        for (int i = 0; i < animals.size(); i++) {
            System.out.println(animals.get(i).genotype.toString() + " " + animals.get(i).direction + " " + animals.get(i).position);
            animals.get(i).move();
        }
        System.out.println(visualizer.draw(new Vector2d(0,0), new Vector2d(width - 1 , height - 1)));
        day++;
    }
    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {

    }
}
