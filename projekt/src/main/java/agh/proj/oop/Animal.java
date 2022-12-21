package agh.proj.oop;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Animal extends AbstractWorldElement{
    public AbstractWorldMap map;

    public int direction;
    public int energy;
    public int birth;
    public int death;
    private int gIndex;
    public ArrayList<Integer> genotype = new ArrayList<>();
    public Animal(AbstractWorldMap map, int birth, int energy, int gLength) {
        this.gIndex = 0;
        this.birth = birth;
        this.energy = energy;
        this.map = map;
        this.position = map.getRandom();
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        for (int i = 0; i < gLength; i++) genotype.add(ThreadLocalRandom.current().nextInt(0, 8));
        //observers.add(map);
    }
    /**
     * Function moves animal, also kills the animal if it no longer has sufficient energy to move.
     */

    public void move() {
        int dir = (direction + genotype.get(gIndex))%8;
        int x = (dir == 0 || dir == 4) ? 0 : (dir < 4) ? 1 : -1;
        int y = (dir == 6 || dir == 2) ? 0 : (abs(dir - 4) < 2) ? -1 : 1;
        Vector2d newPos = map.canMoveTo(this.position, new Vector2d(position.x + x, position.y + y));
        direction = dir;
        gIndex = (gIndex + 1)%genotype.size();
        if (!newPos.equals(this.position)) {
            map.removeElement(this);
            this.position = newPos;
            map.addElement(this);
        }
        else if (map instanceof PortalMap) {
            energy -= map.BREEDENERGY;
            map.removeElement(this);
            this.position = map.getRandom();
            map.addElement(this);
        }
        energy--;
        if (map.eatGrass(this.position)) energy += 10;
        if (energy <= 0) this.death = map.mementoMori(this);
    }



    @Override
    public String toString() {
        return "";
    }

}
