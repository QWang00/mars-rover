package org.northcoders.logiclayer;

import org.northcoders.inputlayer.RoverPosition;

public class Rover {
    private final String roverID;
    private final String name;
    private final RoverPosition position;

    private Rover(RoverBuilder builder){
        this.name = builder.name;
        this.position = builder.position;
        this.roverID = builder.roverID;
    }

    public class RoverBuilder {
        private String roverID;
        private String name;
        private RoverPosition position;

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

        public Rover build() {
            return new Rover(this);
        }


    }


}
