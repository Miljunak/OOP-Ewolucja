package agh.proj.oop;

import java.util.HashMap;

public class AbstractWorldMap {
    public int width;
    public int height;
    public HashMap<Vector2d, AbstractWorldElement> map;

    public AbstractWorldMap(int width, int height ) {
        this.width = width;
        this.height = height;
        this.map = new HashMap<>();
    }

    public AbstractWorldElement objectAt(Vector2d position) {
        return map.get(position);
    }

    public boolean place(AbstractWorldElement animal) {
        Vector2d position = animal.getPos();
        if (map.get(position) != null) throw new IllegalArgumentException(position + " is already occupied!");
        map.put(position, animal);
        return true;
    }

    public boolean canMoveTo(Vector2d position) {
        if ((position.x >= 0 && position.y >= 0) && (position.x < this.width && position.y < this.height)){
            return map.get(position) == null;
        }
        return false;
    }

}
