package agh.proj.oop;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Class which creates and operates on Object simulating 2D Vector
 */

public class Vector2d {
    public int x;
    public int y;

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public Vector2d opposite() {
        if (x == 0 && y != 0) return new Vector2d(0,-y);
        if (x != 0 && y == 0) return new Vector2d(-x,0);
        if (x == 0) return this;
        return new Vector2d(-x,-y);
    }

    public boolean equals(Object other){
        if (!(other instanceof Vector2d temp)) return false;
        return x == temp.x && y == temp.y;
    }
}
