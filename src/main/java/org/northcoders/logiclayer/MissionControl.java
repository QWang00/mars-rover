package org.northcoders.logiclayer;

import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;


public class MissionControl {
    private Instruction instruction;

    public RoverPosition getRoverPosition(String roverID){
        return new RoverPosition();
    }

    public void landRover (String roverID, RoverPosition position){

    }

    public void moveRover(String roverID, Instruction instruction){

    }

}
