package org.northcoders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.northcoders.input.CompassDirection;
import org.northcoders.input.Instruction;
import org.northcoders.input.RoverPosition;

import static org.junit.jupiter.api.Assertions.*;

class RoverTest {
    @Nested
    class testRoverBuilder {
        @Test
        public void testRoverBuilder_SetsFinalFieldsCorrectly() {
            Rover rover = new Rover.RoverBuilder()
                    .roverId("ZR1")
                    .name("Zhu Rong")
                    .build();
            assertEquals("ZR1", rover.getRoverID());
            assertEquals("Zhu Rong", rover.getName());
        }

        @Test
        public void testRoverBuilder_SetsMutableFieldsCorrectly() {
            Plateau plateau = new Plateau(new int[]{5, 6}, "p1");
            Rover rover = new Rover.RoverBuilder()
                    .name("Wulong")
                    .position(new RoverPosition(2, 4, CompassDirection.N))
                    .plateau(plateau)
                    .build();
            rover.setName("Wu Long");
            rover.setPosition(new RoverPosition(3, 5, CompassDirection.S));
            assertEquals("Wu Long", rover.getName());
            assertEquals(CompassDirection.S, rover.getPosition().getFacing());
            assertEquals(3, rover.getPosition().getX());
            assertEquals(5, rover.getPosition().getY());
        }
    }

    @Nested
    class testGetPosition {
        @Test
        public void getPosition_PositionExceedsPlateauDimension() {
            Plateau plateau = new Plateau(new int[]{4, 5}, "P1");
            Rover rover = new Rover.RoverBuilder()
                    .name("Wulong")
                    .plateau(plateau)
                    .build();
            assertThrows(InvalidPositionException.class, () -> rover.setPosition(new RoverPosition(5, 7, CompassDirection.N)));
            assertThrows(InvalidPositionException.class, () -> rover.setPosition(new RoverPosition(5, 1, CompassDirection.N)));
            assertThrows(InvalidPositionException.class, () -> rover.setPosition(new RoverPosition(1, 7, CompassDirection.N)));
        }

        @Test
        public void getPosition_PositionWithinPlateauDimension() {
            Plateau plateau = new Plateau(new int[]{4, 5}, "P1");
            Rover rover = new Rover.RoverBuilder()
                    .name("Wulong")
                    .plateau(plateau)
                    .build();

            rover.setPosition(new RoverPosition(4, 5, CompassDirection.N));
            assertEquals(4, rover.getPosition().getX());
            assertEquals(5, rover.getPosition().getY());

            rover.setPosition(new RoverPosition(1, 1, CompassDirection.N));
            assertEquals(1, rover.getPosition().getX());
            assertEquals(1, rover.getPosition().getY());

        }
    }

    @Nested
    class testCalculateNewPosition {
        Rover rover;

        @BeforeEach
        void setUp() {
            rover = new Rover.RoverBuilder().plateau(new Plateau(new int[]{14, 14}, "P1")).build();
        }

        @Test
        public void testCalculateNewPosition_FacingNorth() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.N));

            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(3, newPosition.getX());
            assertEquals(4, newPosition.getY());
            assertEquals(CompassDirection.N, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingSouth() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.S));

            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(3, newPosition.getX());
            assertEquals(2, newPosition.getY());
            assertEquals(CompassDirection.S, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingEast() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.E));
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(4, newPosition.getX());
            assertEquals(3, newPosition.getY());
            assertEquals(CompassDirection.E, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingWest() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.W));
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(2, newPosition.getX());
            assertEquals(3, newPosition.getY());
            assertEquals(CompassDirection.W, newPosition.getFacing());
        }


        @Test
        public void testCalculateNewPosition_FacingNorthAtTopEdge() {
            Rover rover = new Rover.RoverBuilder()
                    .position(new RoverPosition(1, 14, CompassDirection.N))
                    .build();
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(1, newPosition.getX());
            assertEquals(15, newPosition.getY());
            assertEquals(CompassDirection.N, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingSouthAtBottomEdge() {
            Rover rover = new Rover.RoverBuilder()
                    .position(new RoverPosition(1, 1, CompassDirection.S))
                    .build();
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(1, newPosition.getX());
            assertEquals(0, newPosition.getY());
            assertEquals(CompassDirection.S, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingWestAtBottomEdge() {
            Rover rover = new Rover.RoverBuilder()
                    .position(new RoverPosition(1, 1, CompassDirection.W))
                    .build();
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(0, newPosition.getX());
            assertEquals(1, newPosition.getY());
            assertEquals(CompassDirection.W, newPosition.getFacing());
        }

        @Test
        public void testCalculateNewPosition_FacingEastAtBottomEdge() {
            Rover rover = new Rover.RoverBuilder()
                    .position(new RoverPosition(14, 1, CompassDirection.E))
                    .build();
            RoverPosition newPosition = rover.calculateNewPosition();
            assertEquals(15, newPosition.getX());
            assertEquals(1, newPosition.getY());
            assertEquals(CompassDirection.E, newPosition.getFacing());
        }
    }


    @Nested
    class testMoveForward {
        MissionControl missionControl;
        Rover rover;
        RoverPosition currentPosition;
        Plateau plateau;

        @BeforeEach
        void setUp(){
            missionControl = new MissionControl();
            plateau = new Plateau(new int[]{14, 14}, "P1");
            rover = new Rover.RoverBuilder().plateau(plateau).build();
        }
        @Test
        void testMoveForward_HitPlateauTopLeftEdge() {

            currentPosition = new RoverPosition(10, 14, CompassDirection.N);
            rover.setPosition(currentPosition);
            rover.moveForward(missionControl);
            assertEquals(10, rover.getPosition().getX());
            assertEquals(14, rover.getPosition().getY());
            assertEquals(CompassDirection.N, rover.getPosition().getFacing());

        }

        @Test
        void testMoveForward_HitPlateauTopRightEdge() {
            currentPosition = new RoverPosition(14, 10, CompassDirection.E);
            rover.setPosition(currentPosition);
            rover.moveForward(missionControl);
            assertEquals(14, rover.getPosition().getX());
            assertEquals(10, rover.getPosition().getY());
            assertEquals(CompassDirection.E, rover.getPosition().getFacing());
        }

        @Test
        void testMoveForward_HitPlateauBottomLeftEdge() {
            currentPosition = new RoverPosition(1, 10, CompassDirection.W);
            rover.setPosition(currentPosition);
            rover.moveForward(missionControl);
            assertEquals(1, rover.getPosition().getX());
            assertEquals(10, rover.getPosition().getY());
            assertEquals(CompassDirection.W, rover.getPosition().getFacing());
        }

        @Test
        void testMoveForward_HitPlateauBottomRightEdge() {
            currentPosition = new RoverPosition(10, 1, CompassDirection.S);
            rover.setPosition(currentPosition);
            rover.moveForward(missionControl);
            assertEquals(10, rover.getPosition().getX());
            assertEquals(1, rover.getPosition().getY());
            assertEquals(CompassDirection.S, rover.getPosition().getFacing());
        }

        @Test
        void testMoveForward_PositionOccupiedByAnotherRover() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.N));

            Rover rover2 = new Rover.RoverBuilder()
                    .plateau(plateau)
                    .position(new RoverPosition(3, 4, CompassDirection.N))
                    .build();

            missionControl.addRover(rover);
            missionControl.addRover(rover2);

            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                rover.moveForward(missionControl);
            });

            assertEquals("Cannot move forward; position is occupied by another rover.", exception.getMessage());
        }

        @Test
        void testMoveForward_SingleRoverInPlateau() {
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.E));

            missionControl.addRover(rover);
            rover.moveForward(missionControl);

            assertEquals(4, rover.getPosition().getX());
            assertEquals(3, rover.getPosition().getY());
            assertEquals(CompassDirection.E, rover.getPosition().getFacing());
        }


        @Test
        void testMoveForward_NoCollisionWithOtherRoversMoving() {
            // Setup two rovers far away from each other
            rover.setPosition(new RoverPosition(3, 3, CompassDirection.W));

            Rover rover2 = new Rover.RoverBuilder()
                    .plateau(plateau)
                    .position(new RoverPosition(5, 5, CompassDirection.S))
                    .build();

            missionControl.addRover(rover);
            missionControl.addRover(rover2);

            rover.moveForward(missionControl);
            rover2.moveForward(missionControl);

            assertEquals(2, rover.getPosition().getX());
            assertEquals(3, rover.getPosition().getY());
            assertEquals(CompassDirection.W, rover.getPosition().getFacing());

            assertEquals(5, rover2.getPosition().getX());
            assertEquals(4, rover2.getPosition().getY());
            assertEquals(CompassDirection.S, rover2.getPosition().getFacing());
        }
    }

    @Nested
    class testTurnRight {
        @Test
        void testTurnRight_FromNorth() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.E, rover.turnRight(CompassDirection.N));
        }

        @Test
        void testTurnRight_FromWest() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.N, rover.turnRight(CompassDirection.W));
        }

        @Test
        void testTurnRight_FromSouth() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.W, rover.turnRight(CompassDirection.S));
        }

        @Test
        void testTurnRight_FromEast() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.S, rover.turnRight(CompassDirection.E));
        }

    }

    @Nested
    class testTurnLeft {
        @Test
        void testTurnLeft_FromNorth() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.W, rover.turnLeft(CompassDirection.N));
        }

        @Test
        void testTurnLeft_FromSouth() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.E, rover.turnLeft(CompassDirection.S));
        }

        @Test
        void testTurnLeft_FromWest() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.S, rover.turnLeft(CompassDirection.W));
        }

        @Test
        void testTurnLeft_FromEast() {
            Rover rover = new Rover.RoverBuilder()
                    .build();
            assertEquals(CompassDirection.N, rover.turnLeft(CompassDirection.E));
        }
    }

    @Nested
    class testChangeFacingDirection {
        @Test
        public void testChangeFacingDirection_NullInstruction() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.N;
            Instruction instruction = null;
            assertThrows(NullPointerException.class, () -> rover.changeFacingDirection(originalFacing, instruction));
        }

        @Test
        public void testChangeFacingDirection_NullOriginalFacing() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = null;
            Instruction instruction = Instruction.R;
            assertThrows(NullPointerException.class, () -> rover.changeFacingDirection(originalFacing, instruction));
        }

        @Test
        void testChangeFacingDirection_FromNorthFacingDirection() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.N;

            assertEquals(CompassDirection.E, rover.changeFacingDirection(originalFacing, Instruction.R));

            assertEquals(CompassDirection.W, rover.changeFacingDirection(originalFacing, Instruction.L));
        }


        @Test
        void testChangeFacingDirection_FromSouthFacingDirection() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.S;

            assertEquals(CompassDirection.W, rover.changeFacingDirection(originalFacing, Instruction.R));
            assertEquals(CompassDirection.E, rover.changeFacingDirection(originalFacing, Instruction.L));
        }

        @Test
        void testChangeFacingDirection_FromWestFacingDirection() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.W;

            assertEquals(CompassDirection.N, rover.changeFacingDirection(originalFacing, Instruction.R));

            assertEquals(CompassDirection.S, rover.changeFacingDirection(originalFacing, Instruction.L));
        }


        @Test
        void testChangeFacingDirection_FromEastFacingDirection() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.E;

            assertEquals(CompassDirection.S, rover.changeFacingDirection(originalFacing, Instruction.R));

            assertEquals(CompassDirection.N, rover.changeFacingDirection(originalFacing, Instruction.L));
        }


        @Test
        void testChangeFacingDirection_WestEdgeCase() {
            Rover rover = new Rover.RoverBuilder().build();
            CompassDirection originalFacing = CompassDirection.W;


            assertEquals(CompassDirection.N, rover.changeFacingDirection(originalFacing, Instruction.R));
        }
    }
}



