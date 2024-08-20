package org.northcoders.logiclayer;

import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MissionControl {


    private Map<String, Rover> rovers;
    List<Plateau> plateaus;

    public MissionControl() {
        this.plateaus = new ArrayList<>();
        this.rovers = new HashMap<>();
    }



    public void addRover (String id, String name){
        if(id==null || id.equals("") || name==null || name.equals("")) throw new IllegalArgumentException();
        Rover rover = new Rover.RoverBuilder()
                .robotID(id)
                .name(name)
                .build();
        rovers.put(id, rover);
    }

    public void landRoverToPlateau(String roverID, RoverPosition position, Plateau plateau) {
        if (roverID == null || roverID.equals("") || position == null || plateau == null)
            throw new IllegalArgumentException();
        if (!rovers.containsKey(roverID)) throw new IllegalArgumentException();

        Rover rover = rovers.get(roverID);
        rover.setPlateau(plateau);
        rover.setPosition(position);
        }


    public List<Plateau> getPlateaus() {
        return plateaus;
    }

    public void setPlateaus(List<Plateau> plateaus) {
        this.plateaus = plateaus;
    }

    public RoverPosition getRoverPosition(String roverID) {
        try {
            Rover rover = rovers.get(roverID);
            return rover.getPosition();
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
// TODO: might need to delete if not used at all in the end
//    public CompassDirection getFacingDirection(String roverID){
//        Rover rover = rovers.get(roverID);
//        return rover.getPosition().getFacing();
//    }

    public CompassDirection changeFacingDirection(CompassDirection originalFacing, Instruction instruction) {
        if (instruction == null || originalFacing == null) throw new NullPointerException();
        switch (originalFacing) {
            case N -> {
                if (instruction.equals(Instruction.R)) {
                    return CompassDirection.E;
                } else if (instruction.equals(Instruction.L)) {
                    return CompassDirection.W;
                }
            }
            case S -> {
                if (instruction.equals(Instruction.R)) {
                    return CompassDirection.W;
                } else if (instruction.equals(Instruction.L)) {
                    return CompassDirection.E;
                }
            }
            case W -> {
                if (instruction.equals(Instruction.R)) {
                    return CompassDirection.N;
                } else if (instruction.equals(Instruction.L)) {
                    return CompassDirection.S;
                }
            }
            case E -> {
                if (instruction.equals(Instruction.R)) {
                    return CompassDirection.S;
                } else if (instruction.equals(Instruction.L)) {
                    return CompassDirection.N;
                }
            }
            default -> {
                return originalFacing;
            }
        }
        return originalFacing;
    }


    public CompassDirection moveRover(String roverID, List<Instruction> instructions) {
        Rover rover = rovers.get(roverID);
        CompassDirection currentFacing = null;

        for (Instruction instruction : instructions){
            if(instruction == Instruction.M){
                rover.move();
            }
            currentFacing = rover.getPosition().getFacing();
            CompassDirection newFacingDirection = changeFacingDirection(currentFacing, instruction);
            rover.getPosition().setFacing(newFacingDirection);

        }
        return currentFacing;
    }

    public void moveRoversSequentially(Map<String, List<Instruction>> roverInstruction){

    }

    public Map<String, Rover> getRovers() {
        return rovers;
    }

    public void setRovers(Map<String, Rover> rovers) {
        this.rovers = rovers;
    }
}
