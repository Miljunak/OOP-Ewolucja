package agh.proj.oop;

public class ToxicGrassRegion extends AbstractGrassRegion2 {
    public ToxicGrassRegion(AbstractWorldMap map){
        super(map);
    }
    @Override
    public void setPriority(int priority){
        for(int i=0; i<map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                if(priorities[i][j]>priority){
                    lowPriority.add(new Vector2d(i,j));
                }
                else{
                    highPriority.add(new Vector2d(i,j));
                }

            }
        }
    }
    @Override
    public void updatePriority(Vector2d v){
        priorities[v.x][v.y]+=1;
        boolean found= false;
        int oldPriority=maxPriority;
        maxPriority-=1;
        //Spprawdza, czy nie zmieniło się maxPriority
        while(!found){
            maxPriority+=1;
            for(int i=0; i<map.width; i++){
                for(int j=0; j<map.height; j++){
                    if(priorities[i][j]==maxPriority){
                        found = true;
                    }
                }
            }
        }
        if(maxPriority!=oldPriority){
            setPriority(maxPriority);
        }
        else{
            if(priorities[v.x][v.y]-1<=maxPriority){
                for(int i=0; i<highPriority.size(); i++){
                    if(v.equals(highPriority.get(i))){
                        highPriority.remove(highPriority.get(i));
                    }
                }
            }
        }

    }
}
