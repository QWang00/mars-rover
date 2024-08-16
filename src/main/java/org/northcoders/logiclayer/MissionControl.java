package org.northcoders.logiclayer;

import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import java.util.List;
import java.util.Map;


public class MissionControl {

    private Plateau plateau;
    private Map<String, Rover> rovers;

    public MissionControl(Plateau plateau, Map<String, Rover> rovers) {
        this.plateau = plateau;
        this.rovers = rovers;
    }

    public MissionControl() {

    }

    public void addRover (String id, String name, Map<String, Rover> rovers){
        if(id==null || id.equals("") || name==null || name.equals("")) throw new IllegalArgumentException();
        Rover rover = new Rover.RoverBuilder()
                .robotID(id)
                .name(name)
                .build();
        rovers.put(id, rover);
    }

    public RoverPosition getRoverPosition(String roverID) {
        return null;
    }

    public void landRover(String roverID, RoverPosition startingPosition, Plateau plateau) {

    }

    public void moveRover(String roverID, Instruction instruction) {

    }

    public void moveRoversSequentially(Map<String, List<Instruction>> roverInstruction){

    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Map<String, Rover> getRovers() {
        return rovers;
    }

    public void setRovers(Map<String, Rover> rovers) {
        this.rovers = rovers;
    }
}
