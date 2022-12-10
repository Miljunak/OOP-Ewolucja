package agh.proj.oop;

import java.util.HashMap;
import java.util.HashSet;

public class AbstractWorldMap {
    public int width;
    public int height;
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

}
