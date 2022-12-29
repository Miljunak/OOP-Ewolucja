package agh.proj.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AbstractWorldMap {
    public int breedEnergy;
    public int growingGrass;
    public int grassEnergy;
    public int width;
    public int height;
    public int day = 0;
    public MapVisualizer visualizer;
    public ArrayList<Animal> animals;
    public HashMap<Vector2d, ArrayList<AbstractWorldElement> > elements;
    public AbstractGrassRegion grassRegion;
    public MutationGenerator mutationGenerator;
    public ArrayList<Animal> waitingChildren;
    public ArrayList<Animal> deadAnimals;
    public int grassCount;

    public AbstractWorldMap(int width, int height, int isToxic, int isRandom) {
        this.width = width;
        this.height = height;
        this.elements = new HashMap<>();
        this.visualizer = new MapVisualizer(this);
        this.animals = new ArrayList<>();
        this.waitingChildren = new ArrayList<>();
        this.deadAnimals = new ArrayList<>();
        this.mutationGenerator = (isRandom == 1) ? new RandomGenotype() : new SlightGenotype();
        this.grassRegion = (isToxic == 1) ? new ToxicGrassRegion(this) : new EquatorGrassRegion(this);
        grassRegion.setPriority(0);
        this.grassCount = 0;
    }

    public ArrayList<AbstractWorldElement> objectsAt(Vector2d position) {
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
        deadAnimals.add(animal);
        return day;
    }

    public void addElement(AbstractWorldElement element) {
        Vector2d position = element.getPos();
        if (!elements.containsKey(position)) {
            elements.put(position, new ArrayList<>());
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
        if (grassCount + animals.size() >= width * height) return;
        Vector2d tmp = grassRegion.getRandomField();
        //Trawia nie rośnie tam, gdzie stoją zwierzęta: feature until proven otherwise
        while (objectsAt(tmp) != null) tmp = grassRegion.getRandomField();
        this.addElement(new Grass(tmp));
        grassCount++;

    }
    public boolean hasGrass(Vector2d position) {
        return elements.get(position).stream().anyMatch(obj -> obj instanceof Grass);
    }

    public boolean eatGrass(Vector2d position) {
        if (hasGrass(position)) {
            ArrayList<AbstractWorldElement> set = elements.get(position);
            set.removeIf(obj -> obj instanceof Grass);
            grassCount--;
            return true;
        }
        return false;
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
    public void canBreed(Animal father) {
        ArrayList<AbstractWorldElement> currentTile = objectsAt(father.position);
        //Odnajdywanie partnera.
        for (AbstractWorldElement element : currentTile) {
            if (element.isHealthy() && element != father && father.isHealthy()) {
                Animal mother = (Animal) element;
                mother.breedStatus = false;
                father.breedStatus = false;
                this.waitingChildren.add(new Animal(mother, father));
                mother.energy -= breedEnergy;
                father.energy -= breedEnergy;
                break;
            }
        }
    }
    public int findStrongest(Vector2d pos){
        if (objectsAt(pos) == null) return -1;
        int result = 0;
        for(int i = 0; i < objectsAt(pos).size(); i++){
            AbstractWorldElement object = objectsAt(pos).get(i);
            if (object instanceof Animal) {
                result = Math.max(result, ((Animal) object).energy);
            }
        }
        return result;
    }
    public void nextMove() {
        for (Animal value : animals) {
            System.out.println(value.genotype.toString() + " " + value.direction + " " + value.position);
            value.move();
        }
        for(int i = 0; i < growingGrass; i++) addGrass();
        System.out.println(visualizer.draw(new Vector2d(0,0), new Vector2d(width - 1 , height - 1)));
        day++;
        for (Animal animal : animals) this.canBreed(animal);
        while (deadAnimals.size() > 0) {
            Animal currAnimal = deadAnimals.remove(0);
            this.removeElement(currAnimal);
            animals.remove(currAnimal);
        }
        while (waitingChildren.size() > 0) this.giveBirth(waitingChildren.remove(0));
        for (Animal animal : animals) animal.breedStatus = true;
    }
}
