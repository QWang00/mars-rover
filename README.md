# Mars Rover Simulation 

Welcome to the Mars Rover Simulation project! This project lets you explore the Martian surface with rovers, land them on a plateau, and move them around based on your instructions. Perfect for a quick space adventure!

## Project Overview

In this simulation, you’ll:

- Create a Martian plateau with defined dimensions.
- Land rovers on the plateau.
- Move the rovers using simple commands.
- Handle edge cases where rovers hit the plateau's boundaries or collide with each other.

##  Features

- **Plateau Creation**: Define the size and name of the Martian landscape.
- **Rover Management**: Land and control rovers on the plateau.
- **Movement Instructions**: Navigate rovers with commands to turn and move.
- **Collision and Boundary Handling**: Manage rovers' interactions with the plateau's edges and each other.

## 🛠Getting Started

Here’s how you can get the project running on your machine:

1. **Clone the Repository:**

   ```bash
   git https://github.com/QWang00/mars-rover.git
   ```

2. **Navigate to the Project Directory:**

   ```bash
   cd mars-rover
   ```

3. **Build and Run the Project:**

   Make sure you have Java installed. Then compile and run the project

   The `Main` class is the entry point for the application and handles the overall simulation flow.

4. **Follow the On-Screen Instructions:**

   The application will guide you through creating a plateau, landing rovers, and giving movement commands.

##  How to Use

1. **Create a Plateau:**

   Enter the width, height, and name of the plateau.

2. **Create and Land Rovers:**

   Provide the rover ID, name, landing coordinates, and direction.

3. **Give Movement Instructions:**

   Use `L` for left turn, `R` for right turn, and `M` for moving forward.

##  API Documentation

- **`Rover`**: Represents a rover with methods to set its position and move it based on instructions.
- **`Plateau`**: Defines the boundaries of the Martian landscape.
- **`MissionControl`**: Manages rovers, checks for collisions, and handles movement.
- **`Simulation`**: Controls the simulation logic and interacts with the `Main` class.
- **`Main`**: The entry point for the application that starts the simulation process.

