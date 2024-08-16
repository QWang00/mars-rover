package org.northcoders.logiclayer;

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
        this.position = position;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
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
