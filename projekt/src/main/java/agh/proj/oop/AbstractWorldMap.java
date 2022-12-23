package agh.proj.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class AbstractWorldMap implements IObserver {
    final int BREEDENERGY = 25;
    public int width;
    public int height;
    public int day = 0;
    public MapVisualizer visualizer;
    public ArrayList<Animal> animals;
    public HashMap<Vector2d, HashSet<AbstractWorldElement> > elements;
    public AbstractGrassRegion grassRegion;

    public AbstractWorldMap(int width, int height, int grassVariant ) {
        this.width = width;
        this.height = height;
        this.elements = new HashMap<>();
        this.visualizer = new MapVisualizer(this);
        this.animals = new ArrayList<>();
        if(grassVariant==1){
            this.grassRegion=new EquatorGrassRegion(this);
        }
        else{
            this.grassRegion=new ToxicGrassRegion(this);
        }
        grassRegion.setPriority(0);
    }

    public HashSet<AbstractWorldElement> objectsAt(Vector2d position) {
        return elements.getOrDefault(position, null);
    }

    /**
     * Function to create a new animal in map, likely going to be used when breeding is implemented.
     */
    public void giveBirth(Animal animal) {
        animals.add(animal);
        addElement(animal);
    }

    /**
     * Function to kill and remove an animal from the simulation.
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

    public Vector2d getRandom() {
        return new Vector2d(ThreadLocalRandom.current().nextInt(0, width), ThreadLocalRandom.current().nextInt(0, height));
    }

    public void addGrass() {
            Vector2d tmp = grassRegion.getRandomField();
            //Trawia nie rośnie tam, gdzie stoją zwierzęta: feature until proven otherwise
            while (objectsAt(tmp) != null) tmp = grassRegion.getRandomField();
            this.addElement(new Grass(tmp));

    }
    public boolean hasGrass(Vector2d position) {
        return elements.get(position).stream().anyMatch(obj -> obj instanceof Grass);
    }

    public boolean eatGrass(Vector2d position) {
        if (hasGrass(position)) {
            HashSet<AbstractWorldElement> set = elements.get(position);
            set.removeIf(obj -> obj instanceof Grass);
            return true;
        }
        return false;
    }

    public void nextMove() {
        for (int i = 0; i < animals.size(); i++) {
            System.out.println(animals.get(i).genotype.toString() + " " + animals.get(i).direction + " " + animals.get(i).position);
            animals.get(i).move();
        }
        addGrass();
        System.out.println(visualizer.draw(new Vector2d(0,0), new Vector2d(width - 1 , height - 1)));
        day++;

    }

    /**
     * This function is going to be used to determine if animal should be 'teleported' somewhere depending
     * on the style of map ( for example globe loops animal sideways )
     */
    public Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition) {
        if ((newPosition.x >= 0 && newPosition.y >= 0) && (newPosition.x < this.width && newPosition.y < this.height)) {
            return newPosition;
        }
        return oldPosition;
    }
    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {

    }
}
