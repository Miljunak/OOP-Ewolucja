package agh.proj.oop;

public class Grass extends AbstractWorldElement {
    public Grass(Vector2d pos) {
        this.position = pos;
    }

    @Override
    public String toString() {
        return getPos().toString();
    }
}
