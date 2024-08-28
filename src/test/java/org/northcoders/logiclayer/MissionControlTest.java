package org.northcoders.logiclayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
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
    void testAddRover_EmptyRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", ""));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", ""));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("", "Rover1"));
    }
    @Test
    void testAddRover_NullRover() {
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, null));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover("R1", null));
        assertThrows(IllegalArgumentException.class,()-> missionControl.addRover(null, "Rover1"));

    }
    @Test
    void testAddRover_ValidRoverAddedToMap() {
        missionControl.addRover("R1", "Rover1");
        assertEquals(1, missionControl.getRovers().size());
        assertEquals("Rover1", missionControl.getRovers().get("R1").getName());

    }


    @Test
    void testLandRoverToPlateau_EmptyFields() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
        missionControl.addRover("R1", "Rover1");
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("", position, plateau));

    }

    @Test
    void testLandRoverToPlateau_NullFields() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
        missionControl.addRover("R1", "Rover1");
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau(null,null, null));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau(null, position, plateau));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", null, plateau));
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", position, null));
    }

    @Test
    void testLandRoverToPlateau_RoverNotInMap() {
        RoverPosition position = new RoverPosition(10,3, CompassDirection.S);
        Plateau plateau = new Plateau(new int[]{15, 7}, "P1");

        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .build();
        assertThrows(IllegalArgumentException.class,()->missionControl.landRoverToPlateau("R1", position, plateau));

    }

    @Test
    void testLandRoverToPlateau_Valid(){
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
    void testGetRoverPosition_RoverIDNotInRovers() {
        assertThrows(NullPointerException.class,()->missionControl.getRoverPosition("R2"));
    }

    @Test
    void testGetRoverPosition_ValidRoverID() {
        missionControl.addRover("R1", "Rover1");
        RoverPosition position = new RoverPosition(3,2,CompassDirection.S);
        missionControl.landRoverToPlateau("R1", position, new Plateau(new int[]{7,7}, "P1"));
        assertEquals(position, missionControl.getRoverPosition("R1"));

    }


    @Test
    void testMoveRoverByInstructions_RoverIDNotInMap() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .build();
        List<Instruction> instructions = new ArrayList<>(){};
        instructions.add(Instruction.R);
        assertThrows(NullPointerException.class,()->missionControl.moveRoverByInstructions("R1", instructions));
    }

    @Test
    void testMoveRoverByInstructions_EmptyInstructions() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(6,5,CompassDirection.S))
                .build();
        missionControl.getRovers().put("R1", rover);

        List<Instruction> instructions = new ArrayList<>(){};
        missionControl.moveRoverByInstructions("R1", instructions);
        assertEquals(6,rover.getPosition().getX());
        assertEquals(5,rover.getPosition().getY());
    }

    @Test
    void testMoveRoverByInstructions_SingleInstruction() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(6,5,CompassDirection.S))
                .build();
        missionControl.getRovers().put("R1", rover);

        List<Instruction> instructions = new ArrayList<>(){};
        instructions.add(Instruction.R);
        missionControl.moveRoverByInstructions("R1", instructions);
        assertEquals(6,rover.getPosition().getX());
        assertEquals(5,rover.getPosition().getY());
        assertEquals(CompassDirection.W, rover.getPosition().getFacing());

    }

    @Test
    void testMoveRoverByInstructions_MultipleInstructions() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(3,4,CompassDirection.S))
                .build();
        missionControl.getRovers().put("R1", rover);
        List<Instruction> instructions = new ArrayList<>(){};
        instructions.add(Instruction.R);
        instructions.add(Instruction.M);
        instructions.add(Instruction.L);
        instructions.add(Instruction.L);
        instructions.add(Instruction.R);
        instructions.add(Instruction.M);
        missionControl.moveRoverByInstructions("R1", instructions);
        assertEquals(2,rover.getPosition().getX());
        assertEquals(3,rover.getPosition().getY());
        assertEquals(CompassDirection.S, rover.getPosition().getFacing());

    }


}