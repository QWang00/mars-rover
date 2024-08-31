package org.northcoders.logic;

import org.northcoders.model.CompassDirection;
import org.northcoders.model.Instruction;
import org.northcoders.model.Plateau;
import org.northcoders.model.RoverPosition;

public class Rover {
    private final String roverID;
    private String name;
    private RoverPosition position;
    private Plateau plateau;

    private Rover(RoverBuilder builder){
        this.name = builder.name;
        this.position = builder.position;
        this.roverID = builder.roverID;
        this.plateau = builder.plateau;
    }

    public static class RoverBuilder {
        private String roverID;
        private String name;
        private RoverPosition position;
        private Plateau plateau;

        public RoverBuilder roverId(String roverID) {
            this.roverID = roverID;
            return this;
        }

        public RoverBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoverBuilder position(RoverPosition position) {
            this.position = position;
            return this;
        }

        public RoverBuilder plateau(Plateau plateau){
            this.plateau = plateau;
            return this;
        }

        public Rover build() {
            return new Rover(this);
        }


    }

    public String getRoverID() {
        return roverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoverPosition getPosition() {
        return position;
    }

    public void setPosition(RoverPosition position) {
        if(position.getX()>plateau.getMaxX() || position.getY() > plateau.getMaxY()) throw new InvalidPositionException();
        else {
            this.position = position;
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void moveForward(MissionControl missionControl) {
        RoverPosition newPosition = calculateNewPosition();

        if (newPosition.getX() < 1 || newPosition.getX() > plateau.getMaxX() ||
                newPosition.getY() < 1 || newPosition.getY() > plateau.getMaxY()) {
            System.out.println("Warning: Rover is at the edge and cannot move further, please change direction!");
            return;
        }

        if (missionControl.isPositionOccupied(this, newPosition)) {
            throw new IllegalStateException("Cannot move forward; position is occupied by another rover.");
        }

        setPosition(newPosition);
    }

    protected RoverPosition calculateNewPosition() {
        RoverPosition pos = this.getPosition();
        CompassDirection facing = pos.getFacing();
        int x = pos.getX();
        int y = pos.getY();

        switch (facing) {
            case N -> y++;
            case S -> y--;
            case E -> x++;
            case W -> x--;
        }
        return new RoverPosition(x, y, facing);
    }

    protected CompassDirection changeFacingDirection(CompassDirection originalFacing, Instruction instruction) {
        if (instruction == null || originalFacing == null) throw new NullPointerException("Arguments cannot be null");

        switch (instruction) {
            case R:
                return turnRight(originalFacing);
            case L:
                return turnLeft(originalFacing);
            default:
                return originalFacing;
        }
    }

    protected CompassDirection turnRight(CompassDirection currentFacing) {
        switch (currentFacing) {
            case N:
                return CompassDirection.E;
            case S:
                return CompassDirection.W;
            case W:
                return CompassDirection.N;
            case E:
                return CompassDirection.S;
            default:
                return currentFacing;
        }
    }

    protected CompassDirection turnLeft(CompassDirection currentFacing) {
        switch (currentFacing) {
            case N:
                return CompassDirection.W;
            case S:
                return CompassDirection.E;
            case W:
                return CompassDirection.S;
            case E:
                return CompassDirection.N;
            default:
                return currentFacing;
        }
    }

    protected void processInstruction(Instruction instruction, MissionControl missionControl) {
        CompassDirection currentFacing = getPosition().getFacing();
        if(instruction == Instruction.M){
            moveForward(missionControl);
        }

        CompassDirection newFacingDirection = changeFacingDirection(currentFacing, instruction);
        getPosition().setFacing(newFacingDirection);

    }






}
