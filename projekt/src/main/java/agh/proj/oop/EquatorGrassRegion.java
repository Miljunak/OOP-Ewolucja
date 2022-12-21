package agh.proj.oop;

public class EquatorGrassRegion extends AbstractGrassRegion2{
    public EquatorGrassRegion(AbstractWorldMap map){
        super(map);
    }
    @Override
    public void setPriority(int priority){
        //Na ten moment bardzo niewielkie rozmiary mapy mogą powodować błędy)
        int midpoint=(map.height/2);
        for(int i=0; i<map.width; i++){
            for(int j=0; j<map.height; j++){
                //Przypadek 1: map.height nieparzyste
                if(midpoint*2<map.height){
                    if(j<=(midpoint+1) && j>=(midpoint-1)){
                        highPriority.add(new Vector2d(i,j));
                    }
                    else{
                        lowPriority.add(new Vector2d(i,j));
                    }
                }
                //Przypadek 2: map.height parzyste
                else{
                    if(j<=(midpoint+2) && j>=(midpoint-1)){
                        highPriority.add(new Vector2d(i,j));
                    }
                    else{
                        lowPriority.add(new Vector2d(i,j));
                    }
                }
            }
        }
    }

}
