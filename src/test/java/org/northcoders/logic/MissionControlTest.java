package org.northcoders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.northcoders.model.CompassDirection;
import org.northcoders.model.Instruction;
import org.northcoders.model.Plateau;
import org.northcoders.model.RoverPosition;

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
        void testLandRoverToPlateau_RoverNotInControl() {

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
        void testGetRoverPosition_RoverNotInControl() {
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
        void testMoveRoverByInstructions_RoverIDNotInControl() {

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

    @Nested
    class testIsPositionOccupied {
        RoverPosition occupiedPosition;
        RoverPosition currentPosition;
        Rover roverOccupying;
        Plateau plateau;

        @BeforeEach
        void setUp(){
            plateau = new Plateau(new int[] {14, 14}, "P1");

            occupiedPosition = new RoverPosition(1, 2, CompassDirection.N);
            roverOccupying = new Rover.RoverBuilder().plateau(plateau).position(occupiedPosition).build();

           rover.setPlateau(plateau);
           currentPosition = new RoverPosition(5, 6, CompassDirection.S);
           rover.setPosition(currentPosition);
        }
        @Test
        public void testIsPositionOccupied_RoverNotInControl() {

            assertThrows(IllegalArgumentException.class, () -> {
                missionControl.isPositionOccupied(rover, occupiedPosition);
            });
        }

        @Test
        public void testIsPositionOccupied_PositionOccupiedByAnotherRover() {
            missionControl.addRover(rover);
            missionControl.addRover(roverOccupying);
            assertTrue(missionControl.isPositionOccupied(rover, occupiedPosition));
        }

        @Test
        public void testIsPositionOccupied_PositionNotOccupied() {
            missionControl.addRover(rover);
            assertFalse(missionControl.isPositionOccupied(rover, occupiedPosition));
        }

        @Test
        public void testIsPositionOccupied_SameRoverPosition() {
            missionControl.addRover(rover);
            missionControl.addRover(roverOccupying);
            assertFalse(missionControl.isPositionOccupied(rover, currentPosition));
        }



    }

}