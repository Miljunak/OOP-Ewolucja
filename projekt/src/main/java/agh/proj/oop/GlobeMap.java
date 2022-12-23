package agh.proj.oop;

public class GlobeMap extends AbstractWorldMap {
    public GlobeMap(int width, int height, int grassVariant) {
        super(width, height, grassVariant);
    }

    @Override
    public Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition) {
        if (newPosition.y >= 0 && newPosition.y < height) {
            if (newPosition.x == -1) return new Vector2d(width - 1, newPosition.y);
            if (newPosition.x == width) return new Vector2d(0, newPosition.y);
            return newPosition;
        }
        return oldPosition;
    }
}
