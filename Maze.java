import static java.lang.IO.println;




  class Maze {
    final int[][] grid;
    final int width;
    final int height;
    
    Maze(int width, int height) {
        if(width  <= 0|| height <= 0){
            width = 1;
            height = 1;
            
        }
        if(width > 1000 || height > 1000){
            width = 1000;
            height = 1000;
        }
          
        this.grid = new int[height*2+1][width*2+1];
        this.width = grid[0].length;
        this.height = grid.length;
        
    }
    
}
class MazeGenerator {
    static void generate(Maze maze) {
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        int srow = 1;
        int scol = 1;
        
        maze.grid[srow][scol] = 1;
        stack.push(new int[]{srow, scol});

        int[] drow = {-2, 2, 0, 0};
        int[] dcol = {0, 0, -2, 2};

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int row = current[0];
            int col = current[1];

             // labsetup
            for (int i = 3; i > 0; i--) {
                int j = (int)(Math.random() * (i + 1));
                int tmprow = drow[i]; drow[i] = drow[j]; drow[j] = tmprow;
                int tmpcol = dcol[i]; dcol[i] = dcol[j]; dcol[j] = tmpcol;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++) {
                int newrow = row + drow[i];
                int newcol = col + dcol[i];
                
                if (newrow > 0 && newrow < maze.height - 1 &&
                    newcol > 0 && newcol < maze.width - 1 &&
                    maze.grid[newrow][newcol] == 0) {
                    
               
                    maze.grid[row + drow[i]/2][col + dcol[i]/2] = 1;
                    maze.grid[newrow][newcol] = 1;
                    stack.push(new int[]{newrow, newcol});
                    found = true;
                    break;
                }
            }

            if (!found) stack.pop();
             // labsetup
        }
        maze.grid[1][0] = 1;
        maze.grid[maze.height-2][maze.width-1] = 1;
    }
    
}
    

class MazeRenderer {
    static void render(Maze maze) {
        int cellSize = Math.max(1, 600 / maze.width);
        int canvasSize = maze.width * cellSize;


         println("Html:\n<canvas id=\"mazeCanvas\" width=\"" + canvasSize + "\" height=\"" + canvasSize + "\"></canvas>\n~~~");

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height; row++) {
            for (int col = 0; col < maze.width; col++) {
                sb.append(maze.grid[row][col]);
            }
        }

  
        String js = "JavaScriptCall:\n" +
    "(function() {\n" +
    "const canvas = document.getElementById('mazeCanvas');\n" +
    "const ctx = canvas.getContext('2d');\n" +
    "const grid = \"" + sb.toString() + "\";\n" +
    "const cols = " + maze.width + ";\n" +
    "const cellSize = " + cellSize + ";\n" +
    "for (let i = 0; i < grid.length; i++) {\n" +
    "ctx.fillStyle = grid[i] === '1' ? '#ffffff' : '#000000';\n" +
    "const row = Math.floor(i / cols);\n" +
    "const col = i % cols;\n" +
    "ctx.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);\n" +
    "}\n" +
    "})();\n" +
    "~~~";

println(js);
}
static void animate(Maze maze) {
    int cellSize = Math.max(1, 600 / maze.width);
    
    
    StringBuilder exp = new StringBuilder();
    for (int i = 0; i < MazeSolver.exploredcount; i++) {
        exp.append(MazeSolver.explored[i][0]).append(",")
           .append(MazeSolver.explored[i][1]).append(";");
    }

    
    StringBuilder sol = new StringBuilder();
    for (int i = 0; i < MazeSolver.solutioncount; i++) {
        sol.append(MazeSolver.solution[i][0]).append(",")
           .append(MazeSolver.solution[i][1]).append(";");
    }

    String js = "JavaScriptCall:\n" +
        "setTimeout(function() {\n" +
        "const canvas = document.getElementById('mazeCanvas');\n" +
        "const ctx = canvas.getContext('2d');\n" +
        "const cellSize = " + cellSize + ";\n" +
        "const explored = \"" + exp.toString() + "\".split(';').filter(s => s).map(s => s.split(',').map(Number));\n" +
        "const solution = \"" + sol.toString() + "\".split(';').filter(s => s).map(s => s.split(',').map(Number));\n" +
        "const batchSize = Math.max(1, Math.floor(explored.length / 300));\n" +
        "let i = 0;\n" +
        "function drawExplored() {\n" +
        "    for (let b = 0; b < batchSize && i < explored.length; b++, i++) {\n" +
        "        ctx.fillStyle = '#ffff00';\n" +
        "        ctx.fillRect(explored[i][1] * cellSize, explored[i][0] * cellSize, cellSize, cellSize);\n" +
        "    }\n" +
        "    if (i < explored.length) requestAnimationFrame(drawExplored);\n" +
        "    else drawSolution();\n" +
        "}\n" +
        "function drawSolution() {\n" +
        "    let j = 0;\n" +
        "    const solBatch = Math.max(1, Math.floor(solution.length / 1000));\n" +
        "    function step() {\n" +
        "        for (let b = 0; b < solBatch && j < solution.length; b++, j++) {\n" +
        "            ctx.fillStyle = '#00ff00';\n" +
        "            ctx.fillRect(solution[j][1] * cellSize, solution[j][0] * cellSize, cellSize, cellSize);\n" +
        "        }\n" +
        "        if (j < solution.length) requestAnimationFrame(step);\n" +
        "    }\n" +
        "    step();\n" +
        "}\n" +
        "drawExplored();\n" +
        "}, 200);\n" +
        "~~~";

    println(js);
}}

class MazeSolver {
    static int[][] explored;
    static int[][] solution;
    static int exploredcount = 0;
    static int solutioncount = 0;

    static void solve(Maze maze) {
        boolean[][] visited = new boolean[maze.height][maze.width];
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        
       
        explored = new int[maze.height * maze.width][2];
        solution = new int[maze.height * maze.width][2];

        int[] start = {1, 1};
        int[] end = {maze.height - 2, maze.width - 2};

        stack.push(start);
        visited[1][1] = true;

        int[] drow = {-1, 1, 0, 0};
        int[] dcol = {0, 0, -1, 1};
        // solver
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int row = current[0];
            int col = current[1];

            explored[exploredcount++] = new int[]{row, col};

            
            if (row == end[0] && col == end[1]) {
                
                int[][] stackArr = stack.toArray(new int[0][]);
                solutioncount = stackArr.length;
                for (int i = 0; i < solutioncount; i++) {
                    solution[i] = stackArr[solutioncount - 1 - i];
                }
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++) {
                int newrow = row + drow[i];
                int newcol = col + dcol[i];

                if (newrow >= 0 && newrow < maze.height &&
                    newcol >= 0 && newcol < maze.width &&
                    maze.grid[newrow][newcol] == 1 &&
                    !visited[newrow][newcol]) {

                    visited[newrow][newcol] = true;
                    stack.push(new int[]{newrow, newcol});
                    found = true;
                    break;
                }
            }

            if (!found) stack.pop();
        // solver
        }
    }
}





void main() {

    println("Clear:");
    println("Html:\n<div></div>\n~~~");

  println("""
        Cutout: ./dokumentation.md;// section1
        | Markdown
        Html: <img src="dfs_maze_backtracking_flowchart.png" width="500">
        """);  
    println("""
        Markdown: # Codegrobbeschreibung  
        Markdown: ## Class: Maze
        Markdown: Klasse zur erzeugung des Objects Maze das die anderen Klassen benötigen width und height x2 weil Wände und Gänge eigen Zellen haben in diesem Labyrinth.
        Markdown: ## Class: Maze Generator
        Markdown: Iterative Umsetzung mit dem ArrayDeque in diesem Fall wird es als Stack benutzt mit dem First in Last Out prinzip. Um eine Zufällige Zelle um die Gerade besuchte Zelle zu wählen wird der Fishe-Yates-Shuffle Algorithmus benutzt dieser Soriert das array so das jede Variante gleich wahrscheinlich ist. Solange es benachbarte Zellen gibt die noch nicht besucht worden sind wird das wierderholt wenn es keine mehr gibt wird das oberste Stack element gepopt und dann für das Vorherige nochmal geschaut, dies passiert so lange bis der Stack leer ist.
        
        Text[t2]:
        
        ```java
        ${0}
        ```
        ~~~

        Codeblock:./Maze.java;// labsetup
        | Text[t2] | Markdown
        Markdown: ## Class: Maze Renderer
        Markdown: Dieser Klasse baut ein String aus dem Array das eben in Mazer generator erstellt wurde und schickt dieses an den Browser zusammen mit JavaScript das für 0 Schwarz Einfügt und für 1 Weiß
        Markdown: ## Class: Maze Solver
        Markdown: Dies ist ein iteratives Stack bases verfahren. Außerdem wird für später gespeichert in explored alle Wege die gegangen wurden und in Solution der richtige. Das Backtracking funktioniert so, dass so lange in eine Richutng gegangen wird bis eine Sackgasse erreiccht wird und dann die Stack objecte solange gepoppt werden bis es wieder ein möglichen Weg gibt.
        Text[t2]:
        
        ```java
        ${0}
        ```
        ~~~

        Codeblock:./Maze.java;// solver
        | Text[t2] | Markdown
        
        Markdown: ## Class: Maze Renderer animations abschnitt
        Markdown: Erst werden zwei Strings gebaut aus dem explored un solution Array und dann werden prozentuale Teile des Strings, damit die Göße für die Länge der Animation keine Rolle spielt, nach und nach an ihren Entsprechenden position gefärbt für explored Gelb und solution Grün.
        """);
    int size = 10; // Generate Maze
        println("""
        Input:
        path: ./Maze.java
        label: "// Generate Maze"
        template: int size = $;
        placeholder: Enter maze size
        type: number
        ~~~
        | Html
        """);
    if(size <= 0)
        println("Markdown: Zu klein");
    if(size>1000)
        println("Markdown: Zu groß");
   
    Maze maze = new Maze(size, size);
    MazeGenerator.generate(maze);
    MazeSolver.solve(maze);
    MazeRenderer.render(maze);
    MazeRenderer.animate(maze);
     println("""
        Cutout: ./dokumentation.md;// section2
        | Markdown
        """); 
}

    
    
