package agh.proj.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

abstract public class AbstractGrassRegion {
    protected AbstractWorldMap map;
    ArrayList<Vector2d> highPriority;
    ArrayList<Vector2d> lowPriority;
    protected int[][] priorities;
    protected int maxPriority;
    public AbstractGrassRegion(AbstractWorldMap map) {
        this.maxPriority = 0;
        this.map = map;
        this.highPriority = new ArrayList<Vector2d>();
        this.lowPriority = new ArrayList<Vector2d>();
        this.priorities = new int[map.width][map.height];
        IntStream.range(0, map.width).forEach(i -> Arrays.fill(priorities[i], 0));
    }
    //Funkcja przechodzi przez wszystkie pola i dodaje je odpowiednio do highPriority/lowPriority
    public void setPriority(int priority){

    }
    public void updatePriority(Vector2d v){

    }
    public Vector2d getRandomField() {
        Random rand = new Random();
        if (rand.nextInt(10) < 8 || lowPriority.size() == 0) {
            return highPriority.get(rand.nextInt(highPriority.size()));
        }
        else return lowPriority.get(rand.nextInt(lowPriority.size()));

    }
}
