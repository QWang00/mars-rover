package org.northcoders.ui;


import org.northcoders.logic.MissionControl;
import org.northcoders.logic.Rover;
import org.northcoders.util.InputParser;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();

        System.out.println("Welcome to Mars Rover Simulation!");

        System.out.println("Let's create a plateau on Mars.");
        simulation.createPlateau();

        System.out.println("Great! Now let's create the first Rover.");
        Rover rover1 = simulation.createRover();
        System.out.println("Now let's land Rover 1 on the plateau. Ensure the position is within the range of the plateau.");
        simulation.landRoverToPlateau(rover1);

        System.out.println("Cool! Let's create the second Rover.");
        Rover rover2 = simulation.createRover();
        System.out.println("Now let's land Rover 2 on Mars. Make sure the position is within the range of your plateau!");
        simulation.landRoverToPlateau(rover2);

        System.out.println("Time to give Rover 1 a ride! Let's move it.");
        System.out.println("What are your instructions?");
        simulation.moveRoverByInstructions(rover1);

        System.out.println("Now for Rover 2! Let's get it moving.");
        System.out.println("What are your instructions?");
        simulation.moveRoverByInstructions(rover2);

        System.out.println("Let's simulate the case when Rover 1 hits the edge of the plateau.");
        simulation.simulateEdgeCollision(rover1);

        System.out.println("Now, let's see what happens when rovers collide.");
        simulation.simulateRoverCollision(rover1, rover2);

        System.out.println("That's the end of the simulation. Thank you for participating!");
        simulation.endSimulation();
    }
}