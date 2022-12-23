package agh.proj.oop;

public class EquatorGrassRegion extends AbstractGrassRegion {
    public EquatorGrassRegion(AbstractWorldMap map) {
        super(map);
    }
    @Override
    public void setPriority(int priority) {
        //Na ten moment bardzo niewielkie rozmiary mapy mogą powodować błędy)
        int midpoint = map.height/2;
        for (int i = 0; i < map.width; i++) {
            for (int j = 0; j < map.height; j++) {
                //Przypadek 1: map.height nieparzyste
                if (midpoint*2 < map.height) {
                    if (j <= (midpoint)+(map.height/10)+1&& j >=(midpoint)-(map.height/10)-1) {
                        highPriority.add(new Vector2d(i, j));
                    }
                    else lowPriority.add(new Vector2d(i, j));
                }
                //Przypadek 2: map.height parzyste
                else {
                    if (j <= (midpoint)+(map.height/10)+1 && j >= (midpoint)-(map.height/10)-2) {
                        highPriority.add(new Vector2d(i, j));
                    }
                    else lowPriority.add(new Vector2d(i,j));
                }
            }
        }
    }

}
