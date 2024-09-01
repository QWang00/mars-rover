package org.northcoders.ui;

l;
import org.northcoders.logic.Rover;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();

        System.out.println("======================================");
        System.out.println("Welcome to the Mars Rover Simulation!");
        System.out.println("======================================\n");


        System.out.println(">>> STEP 1: Create the Plateau <<<");
        System.out.println("Enter the dimensions and name of the plateau where the rovers will operate.");
        simulation.createPlateau();
        System.out.println("Plateau created successfully!");
        System.out.println("--------------------------------------\n");


        System.out.println(">>> STEP 2: Create and Land the First Rover <<<");
        Rover rover1 = simulation.createRover();
        System.out.println("Position the first Rover on the plateau.");
        simulation.landRoverToPlateau(rover1);
        System.out.println("Rover " + rover1.getName() + " landed successfully!");
        System.out.println("--------------------------------------\n");


        System.out.println(">>> STEP 3: Create and Land the Second Rover <<<");
        Rover rover2 = simulation.createRover();
        System.out.println("Position the second Rover on the plateau.");
        simulation.landRoverToPlateau(rover2);
        System.out.println("Rover " + rover2.getName() + " landed successfully!");
        System.out.println("--------------------------------------\n");


        System.out.println(">>> STEP 4: Move the First Rover <<<");
        System.out.println("Enter the movement instructions for Rover " + rover1.getName() + ":");
        simulation.moveRoverByInstructions(rover1);
        System.out.println("Rover " + rover1.getName() + " moved successfully!");
        System.out.println("--------------------------------------\n");


        System.out.println(">>> STEP 5: Move the Second Rover <<<");
        System.out.println("Enter the movement instructions for Rover " + rover2.getName() + ":");
        simulation.moveRoverByInstructions(rover2);
        System.out.println("Rover " + rover2.getName() + " moved successfully!");
        System.out.println("--------------------------------------\n");

        System.out.println("======================================");
        System.out.println("Simulation complete! Thank you for participating in the Mars Rover Simulation.");
        System.out.println("======================================");
    }
}
