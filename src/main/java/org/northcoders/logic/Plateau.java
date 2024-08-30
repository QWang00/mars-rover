package org.northcoders.logic;

public class Plateau{
    private int maxX;
    private int maxY;
    private String name;

    public Plateau(int[] dimensions, String name) {
            this.maxX = dimensions[0];
            this.maxY = dimensions[1];
            this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plateau() {
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}
