package org.northcoders.inputlayer;

public class RoverPosition {
    private int x;
    private int y;
    private CompassDirection facing;

    public RoverPosition(int x, int y, CompassDirection facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CompassDirection getFacing() {
        return facing;
    }

    public void setFacing(CompassDirection facing) {
        this.facing = facing;
    }
}
