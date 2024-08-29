package org.northcoders.logiclayer;

import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

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
        rovers.add(rover);
    }

    public void landRoverToPlateau(Rover rover, RoverPosition position, Plateau plateau) {
        if (rover == null || position == null || plateau == null)
            throw new IllegalArgumentException();
        if (!rovers.contains(rover)) throw new IllegalArgumentException();

        rover.setPlateau(plateau);
        rover.setPosition(position);
        }


    public RoverPosition getRoverPosition(Rover rover) {
        try {
            return rover.getPosition();
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
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
}
