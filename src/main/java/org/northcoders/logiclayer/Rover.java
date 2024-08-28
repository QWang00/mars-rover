package org.northcoders.logiclayer;

import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

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

    public void move() {
        RoverPosition pos = this.getPosition();
        CompassDirection facing = pos.getFacing();

        int x = pos.getX();
        int y = pos.getY();

        switch (facing) {
            case N:
                if (y < plateau.getMaxY()) {
                    pos.setY(y + 1);
                } else {
                    System.out.println("Warning: Rover is at the top edge and cannot move further, please change direction!");
                }
                break;
            case S:
                if (y > 1) {
                    pos.setY(y - 1);
                } else {
                    System.out.println("Warning: Rover is at the bottom edge and cannot move further, please change direction!");
                }
                break;
            case E:
                if (x < plateau.getMaxX()) {
                    pos.setX(x + 1);
                } else {
                    System.out.println("Warning: Rover is at the right edge and cannot move further, please change direction!");
                }
                break;
            case W:
                if (x > 1) {
                    pos.setX(x - 1);
                } else {
                    System.out.println("Warning: Rover is at the left edge and cannot move further, please change direction!");
                }
                break;
        }
    }

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

    protected void processInstruction(Instruction instruction, Rover rover) {
        CompassDirection currentFacing;
        if(instruction == Instruction.M){
            rover.move();
        }
        currentFacing = rover.getPosition().getFacing();
        CompassDirection newFacingDirection = changeFacingDirection(currentFacing, instruction);
        rover.getPosition().setFacing(newFacingDirection);

    }


    public static class RoverBuilder {
        private String roverID;
        private String name;
        private RoverPosition position;
        private Plateau plateau;

        public RoverBuilder robotID(String roverID) {
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


}
