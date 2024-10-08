package org.northcoders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.northcoders.model.CompassDirection;
import org.northcoders.model.Instruction;
import org.northcoders.model.Plateau;
import org.northcoders.model.RoverPosition;

import javax.swing.text.Position;
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
        public void testLandRoverToPlateau_PositionOccupied() {
            Plateau plateau = new Plateau(new int[]{15,15}, "P1");
            RoverPosition position1 = new RoverPosition(3, 3, CompassDirection.N);
            rover.setPlateau(plateau);
            rover.setPosition(position1);
            missionControl.addRover(rover);

            RoverPosition position2 = new RoverPosition(5, 3, CompassDirection.N);
            Rover rover2 = new Rover.RoverBuilder().plateau(plateau).position(position2).build();
            missionControl.addRover(rover2);

            RoverPosition position3 = new RoverPosition(3, 3, CompassDirection.S);

            IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
                missionControl.landRoverToPlateau(rover2, position3, plateau);
            });

            assertEquals("Position is occupied by another rover", thrown.getMessage());
        }

        @Test
        public void testLandRoverToPlateau_PositionOutOfRange() {
            Plateau plateau = new Plateau(new int[]{15,15}, "P1");
            RoverPosition position1 = new RoverPosition(3, 3, CompassDirection.N);
            rover.setPlateau(plateau);
            rover.setPosition(position1);
            missionControl.addRover(rover);

            RoverPosition positionOutOfRange = new RoverPosition(17, 3, CompassDirection.N);

            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                missionControl.landRoverToPlateau(rover, positionOutOfRange, plateau);
            });

            assertEquals("Position is out of range of the plateau", thrown.getMessage());
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
            missionControl.addRover(rover);

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

    @Nested
    class testIsPositionOutOfRange{
        private Plateau plateau;
        private Rover rover1;
        private Rover rover2;
        @BeforeEach
        public void setUp() {
            missionControl = new MissionControl();
            plateau = new Plateau(new int[]{5,5}, "P1"); // Example plateau size
            missionControl.setRovers(new ArrayList<>());

            rover1 = new Rover.RoverBuilder()
                    .roverId("R1")
                    .name("Rover1")
                    .position(new RoverPosition(1, 1, CompassDirection.N))
                    .plateau(plateau)
                    .build();

            rover2 = new Rover.RoverBuilder()
                    .roverId("R2")
                    .name("Rover2")
                    .position(new RoverPosition(2, 2, CompassDirection.E))
                    .plateau(plateau)
                    .build();

            missionControl.addRover(rover1);
            missionControl.addRover(rover2);
        }

        @Test
        public void testIsPositionOutOfRange_PositionWithinRange() {
            RoverPosition position = new RoverPosition(3, 3, CompassDirection.N);
            assertFalse(missionControl.isPositionOccupied(rover1, position));
        }

        @Test
        public void testIsPositionOutOfRange_CoordinatesLargerThenOne() {
            RoverPosition position = new RoverPosition(6, 6, CompassDirection.N);
            assertThrows(IllegalArgumentException.class, () -> {
                missionControl.landRoverToPlateau(rover1, position, plateau);
            });
        }
        @Test
        public void testIsPositionOutOfRange_CoordinatesEqualToZero() {
            RoverPosition position = new RoverPosition(0, 1, CompassDirection.N);
            assertThrows(IllegalArgumentException.class, () -> {
                missionControl.landRoverToPlateau(rover1, position, plateau);
            });
        }


    }

}