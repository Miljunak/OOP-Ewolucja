package agh.proj.oop;

import java.util.ArrayList;
import java.util.Random;

abstract public class AbstractGrassRegion {
    protected AbstractWorldMap map;
    protected int[][] priorities;
    protected int maxPriority;
    public AbstractGrassRegion(AbstractWorldMap map){
        maxPriority=0;
        this.map=map;
        priorities=new int[map.width][map.height];
        for(int i=0; i<map.width; i++){
            for(int j=0; j<map.height; j++){
                priorities[i][j]=0;
            }
        }

    }
    public void setPriority(){

    }
    public Vector2d getRandomField(){
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        boolean found= false;
        Vector2d result= new Vector2d(-1,-1);
        if(randomNum<7){
            maxPriority-=1;
            while(!found){
                maxPriority+=1;
                for(int i=0; i<map.width; i++){
                    for(int j=0; j<map.height; j++){
                        if(priorities[i][j]==maxPriority){
                            found = true;
                            result.x=i;
                            result.y=j;
                        }
                    }
                }
            }
        }
        else{
            for(int i=0; i<map.width; i++){
                for(int j=0; j<map.height; j++){
                    if(priorities[i][j]==maxPriority){
                        found = true;
                        result.x=i;
                        result.y=j;
                    }
                }
        }

    }
        return new Vector2d(1,1);
    }
}
