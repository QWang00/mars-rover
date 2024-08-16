package org.northcoders.logiclayer;

import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import java.util.List;
import java.util.Map;


public class MissionControl {


    private Map<String, Rover> rovers;
    List<Plateau> plateaus;

    public MissionControl(Plateau plateau, Map<String, Rover> rovers) {

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

    public void landRoverToPlateau(String roverID, RoverPosition position, Plateau plateau) {

    }

    public List<Plateau> getPlateaus() {
        return plateaus;
    }

    public void setPlateaus(List<Plateau> plateaus) {
        this.plateaus = plateaus;
    }

    public RoverPosition getRoverPosition(String roverID) {
        return null;
    }

    public void moveRover(String roverID, Instruction instruction) {

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
