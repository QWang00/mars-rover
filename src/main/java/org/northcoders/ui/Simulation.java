package org.northcoders.ui;

import org.northcoders.logic.MissionControl;
import org.northcoders.logic.Rover;
import org.northcoders.model.CompassDirection;
import org.northcoders.model.Plateau;
import org.northcoders.model.RoverPosition;
import org.northcoders.util.InputParser;

import java.util.Scanner;

import static org.northcoders.model.CompassDirection.N;

public class Simulation {
    private Plateau plateau;
    private MissionControl missionControl;
    private InputParser inputParser;
    private Scanner scanner;

    public Simulation() {
        this.missionControl = new MissionControl();
        this.inputParser = new InputParser();
        this.scanner = new Scanner(System.in);
    }

    public void createPlateau() {
        while (true) {
            try {
                System.out.println("Enter the width of the plateau:");
                String width = scanner.nextLine();
                System.out.println("Enter the height of the plateau:");
                String height = scanner.nextLine();
                System.out.println("Enter the name of the plateau:");
                String name = scanner.nextLine();

                int[] size = inputParser.parsePlateauSize(width, height);
                plateau = new Plateau(size, name);
                System.out.println("Plateau created with width: "
                        + size[0] + " , height "
                        + size[1] + " with name of "
                        + name);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input for plateau size. Please enter valid numbers greater than zero.");
            } catch (Exception e) {
                System.out.println("Unexpected error. Please try again.");
            }
        }
    }

    public Rover createRover() {
        RoverPosition defaultPosition = new RoverPosition(1,1,N);
        while (true) {
            System.out.println("Enter Rover ID:");
            String id = scanner.nextLine();
            System.out.println("Enter Rover Name:");
            String name = scanner.nextLine();

            if (!missionControl.getRovers().stream().anyMatch(r -> r.getRoverID().equals(id) || r.getName().equals(name))) {
                Rover rover = new Rover.RoverBuilder()
                        .roverId(id)
                        .name(name)
                        .plateau(plateau)
                        .position(defaultPosition)
                        .build();
                missionControl.addRover(rover);
                return rover;
            } else {
                System.out.println("ID or Name already exists. Please enter a different one.");
            }
        }
    }


    public void landRoverToPlateau(Rover rover1) {
        while (true) {
            try {
                System.out.println("Enter landing position coordinate x:");
                String x = scanner.next();
                System.out.println("Enter landing position coordinate x:");
                String y = scanner.next();
                System.out.println("Enter landing facing direction (N, S, W, or E: ");
                String direction = scanner.next();
                scanner.nextLine();

                int[] pos = inputParser.parseRoverCoordinates(x, y);
                CompassDirection compassDirection = inputParser.parseFacingDirection(direction);
                RoverPosition position = new RoverPosition(pos[0], pos[1], compassDirection);

                if (missionControl.isPositionOutOfRange(position, plateau)) {
                    System.out.println("Position is out of range of the plateau. Please enter a valid position.");
                } else if (missionControl.isPositionOccupied(rover1, position)) {
                    System.out.println("Position is occupied by another rover. Please choose a different position.");
                } else {
                    rover1.setPosition(position);
                    missionControl.landRoverToPlateau(rover1, position, plateau);
                    System.out.println("Rover landed at: " + position.getX() + ", " + position.getY() + ", facing " + compassDirection);
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please provide valid numbers and direction (N, S, E, W).");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    public void moveRoverByInstructions(Rover rover1) {
    }

    public void simulateEdgeCollision(Rover rover1) {
    }

    public void simulateRoverCollision(Rover rover1, Rover rover2) {
    }

    public void endSimulation() {
    }
}
