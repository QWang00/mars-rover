package org.northcoders.logiclayer;

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
