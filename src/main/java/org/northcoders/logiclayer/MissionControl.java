package org.northcoders.logiclayer;

import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MissionControl {


    private List<Rover> rovers;


    public MissionControl() {
        this.rovers = new ArrayList<>();
    }

    public void addRover (Rover rover){
        if(rover == null) throw new NullPointerException("Rover cannot be null");
        rovers.add(rover);
    }

    public void landRoverToPlateau(Rover rover, RoverPosition position, Plateau plateau) {
        if (rover == null || position == null || plateau == null)
            throw new NullPointerException();
        if (!rovers.contains(rover)) throw new IllegalArgumentException();

        rover.setPlateau(plateau);
        rover.setPosition(position);
        }


    public RoverPosition getRoverPosition(Rover rover) {
        if(!rovers.contains(rover)) throw new NullPointerException("Rover is not in the list");
        else{return rover.getPosition();}

    }

    public CompassDirection moveRoverByInstructions(Rover rover, List<Instruction> instructions) {
        CompassDirection currentFacing = null;

        for (Instruction instruction : instructions){
            rover.processInstruction(instruction, rover);
            currentFacing = rover.getPosition().getFacing();

        }
        return currentFacing;
    }

    public List<Rover> getRovers() {
        return rovers;
    }

    public void setRovers(List<Rover> rovers) {
        this.rovers = rovers;
    }

    public boolean isPositionOccupied(Rover roverToCheck, RoverPosition position){

        return false;
    }
}
