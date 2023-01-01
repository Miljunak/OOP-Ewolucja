package agh.proj.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
    public boolean breedStatus;
    public ArrayList<Integer> genotypeSet;
    public Animal(AbstractWorldMap map, int birth, int energy, int gLength, ArrayList<Integer> genotypeSet) {
        this.gIndex = 0;
        this.birth = birth;
        this.energy = energy;
        this.map = map;
        this.position = map.getRandom();
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        for (int i = 0; i < gLength; i++) genotype.add(ThreadLocalRandom.current().nextInt(0, 8));
        this.breedStatus = true;
        this.genotypeSet = genotypeSet;
        //observers.add(map);
    }
    public Animal(Animal a, Animal b) {
        //System.out.println("testetestes");
        this.gIndex = 0;
        this.birth = a.map.day; //na co wskazuje birth? dzień symulacji??? // tak, to jest dzien symulacji
        this.energy = 2*a.map.breedEnergy;
        this.map = a.map;
        this.position = a.position;
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        int side = ThreadLocalRandom.current().nextInt(0, 2);
        Animal stronger = (a.energy > b.energy) ? a : b;
        Animal weaker = (a.energy > b.energy) ? b : a;
        double ratio = ((double)stronger.energy)/((double)(weaker.energy+stronger.energy));
        double ratioInv = (double)(weaker.energy)/(double)((weaker.energy+stronger.energy));
        int i = 0;
        if (side == 0) {
            //System.out.println("sssstestetestes");
            while (i < a.genotype.size()*ratio) {
                genotype.add(stronger.genotype.get(i));
                i++;
            }
            while (i < a.genotype.size()) {
                genotype.add(weaker.genotype.get(i));
                i++;
            }
        }
        else {
            while (i < a.genotype.size()*ratioInv){
                genotype.add(weaker.genotype.get(i));
                i++;
            }
            while (i < a.genotype.size()) {
                genotype.add(stronger.genotype.get(i));
                i++;
            }
        }
        this.breedStatus = false;
        map.mutationGenerator.mutate(this);
        this.genotypeSet = mergeArrayLists(a.genotypeSet, b.genotypeSet);
    }

    private static ArrayList<Integer> mergeArrayLists(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        Set<Integer> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }

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
            energy -= map.breedEnergy;
            map.removeElement(this);
            this.position = map.getRandom();
            map.addElement(this);
        }
        energy--;
        if (map.eatGrass(this.position)) energy = Math.min(energy + map.grassEnergy, 300);
        if (energy <= 0) {
            //System.out.println(this);
            this.death = map.mementoMori(this);
        }
    }

    @Override
    public boolean isHealthy() {
        return this.energy > map.breedEnergy && this.breedStatus;
    }

    @Override
    public String toString() {
        return "X";
    }

}
