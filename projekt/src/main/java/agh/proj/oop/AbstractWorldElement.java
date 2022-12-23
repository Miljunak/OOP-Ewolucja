package agh.proj.oop;

public class AbstractWorldElement {
    protected Vector2d position;
    public Vector2d getPos() { return position;}
    public boolean isHealthy() {
        //TRAWA NIE MOZE BYC HEALTHY = GOTOWA DO ROZMNAZANIA
        return false;
    }
}
