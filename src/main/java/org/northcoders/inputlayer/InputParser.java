package org.northcoders.inputlayer;

import java.util.ArrayList;
import java.util.List;

public class InputParser {
    private String input;

    public CompassDirection parseCompassDirection(String input) {return CompassDirection.valueOf(input);}

    public List<Instruction> parseInstruction(String input){
        if(input.equals("")) throw new IllegalArgumentException();
        List<Instruction> parsedInstruction = new ArrayList<>();
        for (char letter : input.toCharArray()){
            parsedInstruction.add(Instruction.valueOf(String.valueOf(letter)));
        }


        return parsedInstruction;
    }
    /*
    public Instruction parseInstruction(String input){
        return Instruction.valueOf(input);
    }

     */

    public int[] parsePlateauSize(String maxX, String maxY){
        if(maxX.equals(null) || maxY.equals(null)) throw new NullPointerException();
        if(maxX.equals("0") || maxY.equals("0")) throw new IllegalArgumentException();

        int[] sizeXY = new int[2];
        int intMaxX = Integer.parseInt(maxX);
        int intMaxY = Integer.parseInt(maxY);
        sizeXY[0] = intMaxX;
        sizeXY[1] = intMaxY;
        return sizeXY;
    }

    public int[] parseRoverPositionXY(String x, String y) {
        return parsePlateauSize(x, y);
    }

    public CompassDirection parsePositionFacing(String facing){
        return parseCompassDirection(facing);
    }

}
