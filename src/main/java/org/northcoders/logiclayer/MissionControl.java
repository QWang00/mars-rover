package org.northcoders.logiclayer;

import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;


public class MissionControl {
    //private Instruction instruction;

    public RoverPosition getRoverPosition(Rover rover){
        return rover.getPosition();
    }

    public void landRover (String roverID, RoverPosition position){

    }

    public void moveRover(String roverID, Instruction instruction){

    }

//    public MissionControl(Instruction instruction) {
//        this.instruction = instruction;
//    }
}
