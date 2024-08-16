package org.northcoders.logiclayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MissionControlTest {

    MissionControl missionControl;
    Map<String, Rover> rovers;
    @BeforeEach
    void setUp() {
        missionControl = new MissionControl(null, null);
        rovers = new HashMap<>();
    }

    @Test
    void addRover_EmptyRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", "", rovers));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", "", rovers));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", "Rover1", rovers));
    }
    @Test
    void addRover_NullRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, null, rovers));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", null, rovers));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, "Rover1", rovers));

    }
    @Test
    void addRover_ValidRoverAddedToMap() {
        missionControl.addRover("R1", "Rover1", rovers);
        assertEquals(1, rovers.size());
        assertEquals("Rover1", rovers.get("R1").getName());

    }


    @Test
    void getRoverPosition() {
    }

    @Test
    void landRover() {
    }


    @Test
    void moveRover() {
    }

    @Test
    void moveRoversSequentially() {
    }
}