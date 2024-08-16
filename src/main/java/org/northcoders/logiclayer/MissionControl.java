package org.northcoders.logiclayer;

import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import java.util.Map;


public class MissionControl {

    private Plateau plateau;
    private Map<String, Rover> rovers;

    public MissionControl(Plateau plateau, Map<String, Rover> rovers) {
        this.plateau = plateau;
        this.rovers = rovers;
    }

    public void addRover (String id, String name){

    }

    public RoverPosition getRoverPosition(String roverID) {
        return null;
    }

    public void landRover(String roverID, RoverPosition startingPosition) {

    }

    public void moveRover(String roverID, Instruction instruction) {

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
