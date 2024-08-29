package org.northcoders.logiclayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
import org.northcoders.inputlayer.RoverPosition;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MissionControlTest {

    MissionControl missionControl;
    Rover rover;


    @BeforeEach
    void setUp() {
        missionControl = new MissionControl();
        rover = new Rover.RoverBuilder().build();


    }


    @Nested
    class testAddRover {
        @Test
        public void testAddRover_NullRover() {
            assertThrows(NullPointerException.class, () -> missionControl.addRover(null));
        }

        @Test
        void testAddRover_ValidRoverAddedToMap() {
            Plateau plateau = new Plateau(new int[] {14, 14}, "p1");
            rover.setPlateau(plateau);
            missionControl.addRover(rover);
            assertEquals(1, missionControl.getRovers().size());
            assertEquals(rover, missionControl.getRovers().get(0));
            assertEquals(plateau, missionControl.getRovers().get(0).getPlateau());

        }

    }

    @Nested
    class testLandRoverToPlateau {

        @Test
        void testLandRoverToPlateau_NullFields() {
            RoverPosition position = new RoverPosition(10, 3, CompassDirection.S);
            Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
            missionControl.addRover(rover);
            assertThrows(NullPointerException.class, () -> missionControl.landRoverToPlateau(null, null, null));
            assertThrows(NullPointerException.class, () -> missionControl.landRoverToPlateau(null, position, plateau));
            assertThrows(NullPointerException.class, () -> missionControl.landRoverToPlateau(rover, null, plateau));
            assertThrows(NullPointerException.class, () -> missionControl.landRoverToPlateau(rover, position, null));
        }

        @Test
        void testLandRoverToPlateau_RoverNotInList() {

            RoverPosition position = new RoverPosition(10, 3, CompassDirection.S);
            Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
            assertThrows(IllegalArgumentException.class, () -> missionControl.landRoverToPlateau(rover, position, plateau));

        }

        @Test
        void testLandRoverToPlateau_Valid() {
            RoverPosition position = new RoverPosition(10, 3, CompassDirection.S);
            Plateau plateau = new Plateau(new int[]{15, 7}, "P1");
            rover.setPlateau(plateau);
            rover.setPosition(position);


            missionControl.addRover(rover);
            assertEquals(1, missionControl.getRovers().size());
            assertTrue(missionControl.getRovers().contains(rover));
            missionControl.landRoverToPlateau(rover, position, plateau);

            Rover rover = missionControl.getRovers().get(0);
            assertNotNull(rover);
            assertEquals(position, rover.getPosition());
            assertEquals(plateau, rover.getPlateau());

        }

    }

    @Nested
    class testGetRoverPosition {
        @Test
        void testGetRoverPosition_RoverNotInList() {
            assertThrows(NullPointerException.class, () -> missionControl.getRoverPosition(rover));
        }

        @Test
        void testGetRoverPosition_ValidRoverID() {
            missionControl.addRover(rover);
            RoverPosition position = new RoverPosition(3, 2, CompassDirection.S);
            rover.setPlateau(new Plateau(new int[]{15, 7}, "P1"));
            rover.setPosition(position);
            assertEquals(position, missionControl.getRoverPosition(rover));

        }
    }

    @Nested
    class testMoveRoverByInstructions {
        @Test
        void testMoveRoverByInstructions_RoverIDNotInList() {

            List<Instruction> instructions = new ArrayList<>() {};
            instructions.add(Instruction.R);
            assertThrows(NullPointerException.class, () -> missionControl.moveRoverByInstructions(rover, instructions));
        }

        @Test
        void testMoveRoverByInstructions_EmptyInstructions() {
            rover.setPlateau(new Plateau(new int[]{14, 14}, "P1"));
            rover.setPosition(new RoverPosition(6, 5, CompassDirection.S));
            missionControl.getRovers().add(rover);

            List<Instruction> instructions = new ArrayList<>() {};
            missionControl.moveRoverByInstructions(rover, instructions);
            assertEquals(6, rover.getPosition().getX());
            assertEquals(5, rover.getPosition().getY());
        }

        @Test
        void testMoveRoverByInstructions_SingleInstruction() {
            rover.setPlateau(new Plateau(new int[]{14, 14}, "P1"));
            rover.setPosition(new RoverPosition(6, 5, CompassDirection.S));
            missionControl.getRovers().add(rover);

            List<Instruction> instructions = new ArrayList<>() {};
            instructions.add(Instruction.M);

            missionControl.moveRoverByInstructions(rover, instructions);
            assertEquals(6, rover.getPosition().getX());
            assertEquals(4, rover.getPosition().getY());
            assertEquals(CompassDirection.S, rover.getPosition().getFacing());

        }

        @Test
        void testMoveRoverByInstructions_MultipleInstructions() {
            rover.setPlateau(new Plateau(new int[]{14, 14}, "P1"));
            rover.setPosition(new RoverPosition(3, 4, CompassDirection.S));
            missionControl.getRovers().add(rover);
            List<Instruction> instructions = new ArrayList<>() {};
            instructions.add(Instruction.R);
            instructions.add(Instruction.M);
            instructions.add(Instruction.L);
            instructions.add(Instruction.L);
            instructions.add(Instruction.R);
            instructions.add(Instruction.M);
            missionControl.moveRoverByInstructions(rover, instructions);
            assertEquals(2, rover.getPosition().getX());
            assertEquals(3, rover.getPosition().getY());
            assertEquals(CompassDirection.S, rover.getPosition().getFacing());

        }
    }

}