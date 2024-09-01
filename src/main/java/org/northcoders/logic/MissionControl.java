package org.northcoders.logic;

import org.northcoders.model.CompassDirection;
import org.northcoders.model.Instruction;
import org.northcoders.model.Plateau;
import org.northcoders.model.RoverPosition;

import java.util.ArrayList;
import java.util.List;


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
            throw new NullPointerException("Arguments cannot be null");
        if (!rovers.contains(rover)) throw new IllegalArgumentException("Rover is not in control");

        if (isPositionOutOfRange(position, plateau)) {
            throw new IllegalArgumentException("Position is out of range of the plateau");
        }

        if (isPositionOccupied(rover, position)) {
            throw new IllegalStateException("Position is occupied by another rover");
        }

        rover.setPlateau(plateau);
        rover.setPosition(position);
    }

    public boolean isPositionOutOfRange(RoverPosition position, Plateau plateau) {
        return position.getX() < 1 || position.getX() > plateau.getMaxX() ||
                position.getY() < 1 || position.getY() > plateau.getMaxY();
    }


    public RoverPosition getRoverPosition(Rover rover) {
        if(!rovers.contains(rover)) throw new NullPointerException("Rover is not in control");
        else{return rover.getPosition();}

    }

    public CompassDirection moveRoverByInstructions(Rover rover, List<Instruction> instructions) {
        CompassDirection currentFacing = null;

        for (Instruction instruction : instructions){
            rover.processInstruction(instruction, this);
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
        if(!rovers.contains(roverToCheck)) throw new IllegalArgumentException("This Rover is not in control of Mission Control");
        for (Rover rover : this.getRovers()) {
            int roverX = rover.getPosition().getX();
            int roverY = rover.getPosition().getY();
            if (!roverToCheck.equals(rover) &&
                    roverX == position.getX() &&
                    roverY == position.getY()) {
                return true;
            }
        }
        return false;
    }
}
