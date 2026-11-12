## Features
Random maze generation with randomized depth-first search(recursive backtracker), implemented iteratively with an explicit stack
and a Fisher-Yates shuffle for the direction order
Backtracking solver
Browser rendering with an animated solving process(explored cells in yellow, solution path in green)
Mazes up to 1000 × 1000 cells (a 2001 × 2001 grid, about 4 million cells)
## Tech stack
Java 25 · JavaScript · HTML Canvas · [Live View Programming](https://github.com/denkspuren/LiveViewProgramming)

## How to run
Requires JDK 25 or newer.

1. Download `lvp-1.0.5.jar` from the
   [Live View Programming releases](https://github.com/denkspuren/LiveViewProgramming/releases)
2. Put it in the same folder as `Maze.java` and `dokumentation.md`
3. Start it:
   ```bash
   java -jar lvp-1.0.5.jar --log Maze.java
   ```
4. Open the address shown in the terminal in your browser

The visualization runs on Live View Programming, developed by
[@handle](https://github.com/RamonDevPrivate)
The tool is not included in this repository.
