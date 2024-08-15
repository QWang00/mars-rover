package org.northcoders.logiclayer;

import org.northcoders.inputlayer.PlateauSize;

public class Plateau{
    private int maxX;
    private int maxY;

    public Plateau(int[] dimensions) {
            this.maxX = dimensions[0];
            this.maxY = dimensions[1];
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
