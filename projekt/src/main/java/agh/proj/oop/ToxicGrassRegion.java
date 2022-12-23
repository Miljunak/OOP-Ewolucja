package agh.proj.oop;

public class ToxicGrassRegion extends AbstractGrassRegion {
    public ToxicGrassRegion(AbstractWorldMap map) {
        super(map);
    }
    @Override
    public void setPriority(int priority){
        for(int i = 0; i < map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                if(priorities[i][j] > priority) {
                    lowPriority.add(new Vector2d(i, j));
                }
                else {
                    highPriority.add(new Vector2d(i, j));
                }

            }
        }
    }
    @Override
    public void updatePriority(Vector2d pos){
        priorities[pos.x][pos.y]++;
        boolean found = false;
        int oldPriority = maxPriority;
        maxPriority--;
        //Spprawdza, czy nie zmieniło się maxPriority
        while (!found) {
            maxPriority++;
            for (int i = 0; i < map.width; i++){
                for (int j = 0; j < map.height; j++){
                    if (priorities[i][j] == maxPriority) {
                        found = true;
                        break;
                    }
                }
            }
        }
        if (maxPriority != oldPriority) setPriority(maxPriority);
        else {
            if(priorities[pos.x][pos.y]-1 <= maxPriority){
                for(int i = 0; i < highPriority.size(); i++){
                    if(pos.equals(highPriority.get(i))) {
                        highPriority.remove(highPriority.get(i));
                    }
                }
            }
        }
    }

}
