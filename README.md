# Skiing Centre Simulation

An object-oriented, event-driven simulation of a skiing centre, written in Java. This project simulates the movement, routing, and decisions of different types of skiers across ski routes and lifts throughout a single simulated day (up until 15:00). 

## Overview
The simulation is built on a **priority event queue** (using Java's standard PriorityQueue). The `event` package manages the lifecycle of simulation events (e.g., placing new events on the timeline as consequences of earlier ones). The `infrastructure` package manages physical locations (lifts, routes, knots), and the `engine` package contains the core mechanics to run the simulation, including pathfinding and time tracking.

## Features & Mechanics
*   **Polymorphic Athletes:** Various types of skiers with different decision-making logic:
    *   **Local Athlete (`L`):** Dynamically chooses the most attractive adjacent route step-by-step based on route difficulty, wear and tear, and boredom.
    *   **Greedy Athlete (`Z`):** Finds the most attractive route in the entire resort and uses pathfinding to get there.
    *   **Collectioner Athlete (`K`):** Aims to visit as many different routes as possible, pathfinding to unvisited or least-visited routes.
*   **Boredom Mechanics:** Athletes experience exponentially smoothed boredom (`β`) for repeatedly using the same route.
*   **Pathfinding (BFS):** Uses Breadth-First Search (`BFS.java`) to calculate the shortest path (in terms of edges) for Greedy and Collectioner athletes to reach their specific destinations.
*   **Event-Driven Architecture:** Models the flow of time and state changes using events like `LiftCycle` (moving passengers in capacity-constrained groups), `Decision`, `RideStart`, and `RideEnd`.
*   **Statistics & Visualization:** Calculates final statistics for lifts (max/avg queue length, utilization percentage) and routes (total rides, final snow condition). Generates spatial LaTeX maps (using the provided `kadra` module) visualizing these statistics and individual skier tracking paths.
*   **Unit Testing:** Includes JUnit tests verifying core mechanics like ski lift capacity logic and the BFS pathfinding algorithm.

## How to Run
Run the included bash script from the root directory. This script compiles the Java files into the `bin` directory, runs the simulation using input data, and triggers the generation of LaTeX maps in the `maps` directory.

    ./run.sh

*(Input data should be provided via standard input according to the format specified below).*

## Input Data Format
The simulation reads configuration data from standard input. The data is divided into four sections separated by empty lines. Each section starts with a line containing the number of items.

**1. Knots / Nodes**
*   `[number of knots]`
*   Subsequent lines: `<height> <x> <y> [s]` 
    *(where `s` is an optional flag indicating a starting/communicated knot)*

**2. Ski Lifts**
*   `[number of lifts]`
*   Subsequent lines: `<start_knot> <end_knot> <interval_sec> <capacity> <ride_time_sec>`

**3. Routes**
*   `[number of routes]`
*   Subsequent lines: `<start_knot> <end_knot> <difficulty(0-10)> <ride_time_sec> <base_attractiveness> <wear_resistance>`

**4. Athlete Groups**
*   `[number of athlete groups]`
*   Every group consists of exactly 3 lines:
    1. `<count> <skill(0-10)> <spontaneity> <boredom_factor> <type: L|Z|K> [s]` *(where `s` is an optional flag to track the athletes)*
    2. `<weight_difficulty> <weight_surface> <weight_boredom>` *(must sum to 1)*
    3. `<start_knot> <start_time (HH:MM:SS)> [spawn_interval_sec]`

## Running Tests
To run the included JUnit tests (e.g., Lift Cycle and BFS logic), execute:

    ./runTests.sh

## Acknowledgments & Code Structure
Please note that the `src/kadra/` package (containing Polish class names and files) was provided by the university lecturers as a utility module for generating LaTeX maps. 

The entire core simulation, event system, pathfinding, and skier logic (`skiingCentre`, `engine`, `event`, `infrastructure`, and `test` packages) were designed and developed independently by me.
