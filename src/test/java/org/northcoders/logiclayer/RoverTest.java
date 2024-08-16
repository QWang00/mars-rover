package org.northcoders.logiclayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.RoverPosition;

import static org.junit.jupiter.api.Assertions.*;

class RoverTest {
    @Test
    public void testRoverBuilder_SetsFinalFieldsCorrectly(){
        Rover rover = new Rover.RoverBuilder()
                .robotID("ZR1")
                .name("Zhu Rong")
                .build();
        assertEquals("ZR1", rover.getRoverID());
        assertEquals("Zhu Rong", rover.getName());
    }

    @Test
    public void testRoverBuilder_SetsMutableFieldsCorrectly(){
        Plateau plateau = new Plateau(new int[]{5,6}, "p1");
        Rover rover = new Rover.RoverBuilder()
                .name("Wulong")
                .position(new RoverPosition(2,4, CompassDirection.N))
                .plateau(plateau)
                .build();
        rover.setName("Wu Long");
        rover.setPosition(new RoverPosition(3,5,CompassDirection.S));
        assertEquals("Wu Long", rover.getName());
        assertEquals(CompassDirection.S, rover.getPosition().getFacing());
        assertEquals(3, rover.getPosition().getX());
        assertEquals(5, rover.getPosition().getY());
    }

    @Test
    public void getPosition_PositionExceedsPlateauDimension(){
        Plateau plateau = new Plateau(new int[]{4,5}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .name("Wulong")
                .plateau(plateau)
                .build();
        assertThrows(InvalidPositionException.class,()->rover.setPosition(new RoverPosition(5,7,CompassDirection.N)));
        assertThrows(InvalidPositionException.class,()->rover.setPosition(new RoverPosition(5,1,CompassDirection.N)));
        assertThrows(InvalidPositionException.class,()->rover.setPosition(new RoverPosition(1,7,CompassDirection.N)));
    }

    @Test
    public void getPosition_PositionWithinPlateauDimension(){
        Plateau plateau = new Plateau(new int[]{4,5}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .name("Wulong")
                .plateau(plateau)
                .build();

        rover.setPosition(new RoverPosition(4,5,CompassDirection.N));
        assertEquals(4, rover.getPosition().getX());
        assertEquals(5, rover.getPosition().getY());

        rover.setPosition(new RoverPosition(1,1,CompassDirection.N));
        assertEquals(1, rover.getPosition().getX());
        assertEquals(1, rover.getPosition().getY());

    }
}