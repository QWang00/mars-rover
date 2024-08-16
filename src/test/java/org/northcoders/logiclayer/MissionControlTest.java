package org.northcoders.logiclayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.RoverPosition;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MissionControlTest {

    MissionControl missionControl;


    @BeforeEach
    void setUp() {
        missionControl = new MissionControl();

    }

    @Test
    void addRover_EmptyRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", ""));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", ""));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", "Rover1"));
    }
    @Test
    void addRover_NullRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, null));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", null));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, "Rover1"));

    }
    @Test
    void addRover_ValidRoverAddedToMap() {
        missionControl.addRover("R1", "Rover1");
        assertEquals(1, missionControl.getRovers().size());
        assertEquals("Rover1", missionControl.getRovers().get("R1").getName());

    }


    @Test
    void landRoverToPlateau_EmptyFields() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
        missionControl.addRover("R1", "Rover1");
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("", position, plateau));

    }

    @Test
    void landRoverToPlateau_NullFields() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
        missionControl.addRover("R1", "Rover1");
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau(null,null, null));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau(null, position, plateau));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", null, plateau));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", position, null));
    }

    @Test
    void landRoverToPlateau_RoverNotInMap() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");

        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .build();
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", position, plateau));

    }

    @Test
    void landRoverToPlateau_Valid(){
        RoverPosition position = new RoverPosition(10, 3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");

        missionControl.addRover("R1", "Rover1");
        assertEquals(1, missionControl.getRovers().size());
        assertTrue(missionControl.getRovers().containsKey("R1"));
        missionControl.landRoverToPlateau("R1", position, plateau);

        Rover rover = missionControl.getRovers().get("R1");
        assertNotNull(rover);
        assertEquals(position, rover.getPosition());
        assertEquals(plateau, rover.getPlateau());

    }


    @Test
    void getRoverPosition() {
    }

    @Test
    void moveRover() {
    }

    @Test
    void moveRoversSequentially() {
    }
}