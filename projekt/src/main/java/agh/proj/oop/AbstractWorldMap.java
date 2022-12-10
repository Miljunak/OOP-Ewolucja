package agh.proj.oop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class AbstractWorldMap implements IObserver {
    public int width;
    public int height;
    public int day = 0;
    public HashMap<Vector2d, HashSet<AbstractWorldElement> > elements;

    public AbstractWorldMap(int width, int height ) {
        this.width = width;
        this.height = height;
        this.elements = new HashMap<>();
    }

    public HashSet<AbstractWorldElement> objectsAt(Vector2d position) {
        return elements.getOrDefault(position, null);
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
    @Override
    public void positionChanged(Vector2d oldPos, Vector2d newPos) {

    }
}
