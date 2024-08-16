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
        Rover rover = new Rover.RoverBuilder()
                .name("Wulong")
                .position(new RoverPosition(2,4, CompassDirection.N))
                .build();
        rover.setName("Wu Long");
        rover.setPosition(new RoverPosition(3,5,CompassDirection.S));
        assertEquals("Wu Long", rover.getName());
        assertEquals(CompassDirection.S, rover.getPosition().getFacing());
        assertEquals(3, rover.getPosition().getX());
        assertEquals(5, rover.getPosition().getY());
    }
}