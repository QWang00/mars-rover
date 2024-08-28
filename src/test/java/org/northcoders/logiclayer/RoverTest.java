package org.northcoders.logiclayer;

import org.junit.jupiter.api.Test;
import org.northcoders.inputlayer.CompassDirection;
import org.northcoders.inputlayer.Instruction;
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

    @Test
    public void testMoveForward_FromNorthOnTopLeftEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(7,6,CompassDirection.N))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(7, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }

    @Test
    public void testMoveForward_FromSouthOnBottomLeftEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(2,1,CompassDirection.S))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(2, rover.getPosition().getX());
        assertEquals(1, rover.getPosition().getY());
    }

    @Test
    public void testMoveForward_FromEastOnBottomRightEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(10,6,CompassDirection.E))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(10, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }

    @Test
    public void testMoveForward_FromWestOnTopRightEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(1,6,CompassDirection.W))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(1, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());

    }

    @Test
    public void testMoveForward_FromNorthNotOnEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(3,5,CompassDirection.N))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(3, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }
    @Test
    public void testMoveForward_FromSouthNotOnEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(7,6,CompassDirection.S))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(7, rover.getPosition().getX());
        assertEquals(5, rover.getPosition().getY());
    }
    @Test
    public void testMoveForward_FromWestNotOnEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(7,6,CompassDirection.W))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(6, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }
    @Test
    public void testMoveForward_FromEastNotOnEdge() {
        Plateau plateau = new Plateau(new int[]{10,6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(7,6,CompassDirection.E))
                .plateau(plateau)
                .build();
        rover.moveForward();
        assertEquals(8, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }

    @Test
    void testTurnRight_FromNorth() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.E, rover.turnRight(CompassDirection.N));
    }

    @Test
    void testTurnLeft_FromNorth() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.W, rover.turnLeft(CompassDirection.N));
    }

    @Test
    void testTurnRight_FromSouth() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.W, rover.turnRight(CompassDirection.S));
    }

    @Test
    void testTurnLeft_FromSouth() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.E, rover.turnLeft(CompassDirection.S));
    }

    @Test
    void testTurnRight_FromWest() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.N, rover.turnRight(CompassDirection.W));
    }

    @Test
    void testTurnLeft_FromWest() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.S, rover.turnLeft(CompassDirection.W));
    }

    @Test
    void testTurnRight_FromEast() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.S, rover.turnRight(CompassDirection.E));
    }

    @Test
    void testTurnLeft_FromEast() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        assertEquals(CompassDirection.N, rover.turnLeft(CompassDirection.E));
    }

    @Test
    public void testChangeFacingDirection_NullInstruction() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = CompassDirection.N;
        Instruction instruction = null;
        assertThrows(NullPointerException.class, () -> rover.changeFacingDirection(originalFacing, instruction));
    }

    @Test
    public void testChangeFacingDirection_NullOriginalFacing() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = null;
        Instruction instruction = Instruction.R;
        assertThrows(NullPointerException.class, () -> rover.changeFacingDirection(originalFacing, instruction));
    }

    @Test
    void testChangeFacingDirection_FromNorthFacingDirection() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = CompassDirection.N;
        assertEquals(CompassDirection.E, rover.changeFacingDirection(originalFacing, Instruction.R));
        assertEquals(CompassDirection.W, rover.changeFacingDirection(originalFacing, Instruction.L));
    }

    @Test
    void testChangeFacingDirection_FromSouthFacingDirection() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = CompassDirection.S;
        assertEquals(CompassDirection.W, rover.changeFacingDirection(originalFacing, Instruction.R));
        assertEquals(CompassDirection.E, rover.changeFacingDirection(originalFacing, Instruction.L));
    }

    @Test
    void testChangeFacingDirection_FromWestFacingDirection() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = CompassDirection.W;
        assertEquals(CompassDirection.N, rover.changeFacingDirection(originalFacing, Instruction.R));
        assertEquals(CompassDirection.S, rover.changeFacingDirection(originalFacing, Instruction.L));
    }

    @Test
    void testChangeFacingDirection_FromEastFacingDirection() {
        Rover rover = new Rover.RoverBuilder()
                .build();
        CompassDirection originalFacing = CompassDirection.E;
        assertEquals(CompassDirection.S, rover.changeFacingDirection(originalFacing, Instruction.R));
        assertEquals(CompassDirection.N, rover.changeFacingDirection(originalFacing, Instruction.L));
    }


    @Test
    void testProcessInstruction_TurnLeft() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(6,5,CompassDirection.S))
                .build();

        rover.processInstruction(Instruction.L, rover);
        assertEquals(CompassDirection.E, rover.getPosition().getFacing());
    }

    @Test
    void testProcessInstruction_TurnRight() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(6,5,CompassDirection.S))
                .build();

        rover.processInstruction(Instruction.R, rover);
        assertEquals(CompassDirection.W, rover.getPosition().getFacing());
    }

    @Test
    void testProcessInstruction_MoveForwardForward() {
        Rover rover = new Rover.RoverBuilder()
                .robotID("R1")
                .name("Rover1")
                .plateau(new Plateau(new int[]{10,6}, "P1"))
                .position(new RoverPosition(6,5,CompassDirection.N))
                .build();

        rover.processInstruction(Instruction.M, rover);
        assertEquals(6, rover.getPosition().getX());
        assertEquals(6, rover.getPosition().getY());
    }

    @Test
    public void testCalculateNewPosition_FromNorth() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 5, CompassDirection.N))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(5, newPosition.getX());
        assertEquals(6, newPosition.getY());
        assertEquals(CompassDirection.N, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_FromSouth() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 5, CompassDirection.S))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(5, newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(CompassDirection.S, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_FromEast() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 5, CompassDirection.E))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(6, newPosition.getX());
        assertEquals(5, newPosition.getY());
        assertEquals(CompassDirection.E, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_FromWest() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 5, CompassDirection.W))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(4, newPosition.getX());
        assertEquals(5, newPosition.getY());
        assertEquals(CompassDirection.W, newPosition.getFacing());
    }

    @Test
    public void testCalculateNewPosition_NorthAtTopEdge() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 6, CompassDirection.N))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(5, newPosition.getX());
        assertEquals(7, newPosition.getY());
        assertEquals(CompassDirection.N, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_SouthAtBottomEdge() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(5, 1, CompassDirection.S))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(5, newPosition.getX());
        assertEquals(0, newPosition.getY());
        assertEquals(CompassDirection.S, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_EastAtRightEdge() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(10, 5, CompassDirection.E))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(11, newPosition.getX());
        assertEquals(5, newPosition.getY());
        assertEquals(CompassDirection.E, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_WestAtLeftEdge() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(1, 5, CompassDirection.W))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(0, newPosition.getX());
        assertEquals(5, newPosition.getY());
        assertEquals(CompassDirection.W, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_NorthAtTopLeftCorner() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(1, 6, CompassDirection.N))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(1, newPosition.getX());
        assertEquals(7, newPosition.getY());
        assertEquals(CompassDirection.N, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_SouthAtBottomRightCorner() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(10, 1, CompassDirection.S))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(10, newPosition.getX());
        assertEquals(0, newPosition.getY());
        assertEquals(CompassDirection.S, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_EastAtBottomRightCorner() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(10, 1, CompassDirection.E))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(11, newPosition.getX());
        assertEquals(1, newPosition.getY());
        assertEquals(CompassDirection.E, newPosition.getFacing());
    }


    @Test
    public void testCalculateNewPosition_WestAtTopLeftCorner() {
        Plateau plateau = new Plateau(new int[]{10, 6}, "P1");
        Rover rover = new Rover.RoverBuilder()
                .position(new RoverPosition(1, 6, CompassDirection.W))
                .plateau(plateau)
                .build();
        RoverPosition newPosition = rover.calculateNewPosition();
        assertEquals(0, newPosition.getX());
        assertEquals(6, newPosition.getY());
        assertEquals(CompassDirection.W, newPosition.getFacing());
    }

}