package agh.proj.oop;

import java.util.ArrayList;
import java.util.Random;
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
    public Animal(AbstractWorldMap map, int birth, int energy, int gLength) {
        this.gIndex = 0;
        this.birth = birth;
        this.energy = energy;
        this.map = map;
        this.position = map.getRandom();
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        for (int i = 0; i < gLength; i++) genotype.add(ThreadLocalRandom.current().nextInt(0, 8));
        this.breedStatus = true;
        //observers.add(map);
    }
    public Animal(Animal a, Animal b){
        this.gIndex = 0;
        this.birth = a.map.day;//na co wskazuje birth? dzień symulacji???
        this.energy = 2*a.map.energyConstant;
        this.map = a.map;
        this.position = a.position;
        this.direction = ThreadLocalRandom.current().nextInt(0, 8);
        Random rand = new Random();
        Animal stronger;
        Animal weaker;
        if(a.energy>b.energy){
            stronger=a;
            weaker=b;
        }
        else{
            stronger=b;
            weaker=a;
        }
        int side=rand.nextInt(2);
        float ratio = stronger.energy/(weaker.energy+stronger.energy);
        if(side==0){
            int i=0;
            while(i<a.genotype.size()*ratio){
                genotype.add(stronger.genotype.get(i));
                i++;
            }
            while(i<a.genotype.size()){
                genotype.add(weaker.genotype.get(i));
                i++;
            }
        }
        else{
            int i=a.genotype.size()-1;
            while(i>a.genotype.size()-a.genotype.size()*ratio){
                genotype.add(stronger.genotype.get(i));
                i--;
            }
            while(i>0){
                genotype.add(weaker.genotype.get(i));
                i--;
            }
            }
            //genotype.add(ThreadLocalRandom.current().nextInt(0, 8));

        this.breedStatus = true;
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
    public boolean isHealthy() {
        //STALA 15 POZNIEJ BEDZIE JEDNYM Z POTRZEBNYCH INPUTOW
        return this.energy > 15 && this.breedStatus;
    }

    @Override
    public String toString() {
        //NASTEPUJACA CZESC KODU MUSI ZOSTAC USUNIETA, JEST WYKORZYSTYWANA DO TESTOW
        if (this.genotype.size() == 10) return "L";
        else if (this.genotype.size() == 5) return "J";
        //KONIEC KODU TESTOWEGO
        return "X";
    }

}
