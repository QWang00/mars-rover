package org.northcoders.ui;

import org.northcoders.logic.MissionControl;
import org.northcoders.logic.Rover;
import org.northcoders.model.Plateau;
import org.northcoders.util.InputParser;

import java.util.Scanner;

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
        return null;
    }

    public void landRoverToPlateau(Rover rover1) {
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
