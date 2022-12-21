package agh.proj.oop;

import java.util.ArrayList;
import java.util.Random;

abstract public class AbstractGrassRegion2 {
    protected AbstractWorldMap map;
    ArrayList<Vector2d> highPriority;
    ArrayList<Vector2d> lowPriority;
    protected int[][] priorities;
    protected int maxPriority;
    public AbstractGrassRegion2(AbstractWorldMap map){
        maxPriority=0;
        this.map=map;
        highPriority= new ArrayList<Vector2d>();
        lowPriority= new ArrayList<Vector2d>();
        priorities=new int[map.width][map.height];
        for(int i=0; i<map.width; i++){
            for(int j=0; j<map.height; j++){
                priorities[i][j]=0;
            }
        }
    }
    //Funkcja przechodzi przez wszystkie pola i dodaje je odpowiednio do highPriority/lowPriority
    public void setPriority(int priority){

    }
    public void updatePriority(Vector2d v){

    }
    public Vector2d getRandomField(){
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        boolean found= false;
        Vector2d result= new Vector2d(-1,-1);
        int rng=0;
        if(randomNum<8){
            rng=rand.nextInt(highPriority.size());
            return highPriority.get(rng);
        }
        else{
            rng=rand.nextInt(lowPriority.size());
            return lowPriority.get(rng);
        }

    }
}
