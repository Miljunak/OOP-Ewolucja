package agh.proj.oop;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Animal extends AbstractWorldElement{
    public AbstractWorldMap map;
    public ArrayList<IObserver> observers = new ArrayList<>();
    public int direction;
    public int energy;
    public int birth;
    public int death;
    private int gIndex;
    public ArrayList<Integer> genotype = new ArrayList<>();
    public Animal(AbstractWorldMap map, int birth, int energy, int n) {
        this.gIndex = 0;
        this.birth = birth;
        this.energy = energy;
        this.map = map;
        this.position = map.getRandom();
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        for (int i = 0; i < n; i++) genotype.add(ThreadLocalRandom.current().nextInt(0, 8));
        observers.add(map);
    }

    public boolean move() {
        int dir = (direction + genotype.get(gIndex))%8;
        int x = (dir == 0 || dir == 4) ? 0 : (dir < 4) ? 1 : -1;
        int y = (dir == 6 || dir == 2) ? 0 : (abs(dir - 4) < 2) ? -1 : 1;
        Vector2d newPos = new Vector2d(position.x + x, position.y + y);
        if (map.canMoveTo(newPos)) {
            map.removeElement(this);
            direction = dir;
            position = newPos;
            gIndex = gIndex%genotype.size();
            map.addElement(this);
            return true;
        }
        return false;
    }



    @Override
    public String toString() {
        return genotype.toString() + getPos();
    }

}
