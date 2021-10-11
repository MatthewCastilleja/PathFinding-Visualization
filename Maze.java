import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Maze extends JPanel implements KeyListener {


    // Instance variables
    private Square[][] squares;
    private HashSet<Integer> specialSquares;
    private Square start;
    private Square end;
    final private int width = 800;
    final private int height = 600;


    /**
     * Creates a new instance of a Maze object
     * Instanciates a 20 x 20 grid of Square objects
     */
    public Maze() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addKeyListener(this); 
        resetMaze();        
    }


    /**
     * Calls the method associated with the key that was pressed
     * Spacebar - reset
     * A - A* algorithm
     * D - Dijkstra's algorithm
     * @param evt a KeyEvent that the user created
     */
    public void keyPressed(KeyEvent evt) {

        // Do the function associated with the key pressed
        int keyCode = evt.getKeyCode();
        switch(keyCode) {
            case 32: resetMaze(); break;
            case 65: startAStar(); break;
            case 68: startDijkstras(); break;
            default: System.out.println("Invalid key code.");
        }
    }
    public void keyReleased(KeyEvent evt) {}
    public void keyTyped(KeyEvent evt) {}
    public boolean isFocusTraversable () {
        return true ;
    }


    /**
     * Override the getPreferredSize method
     * @return the dimensions of the JPanel instance
     */
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    

    /**
     * Paints the components of the Maze onto the Graphics object
     * pre: g != null
     * @param g the Graphics objects that will be painted on.
     */
    public void paintComponent(Graphics g) {

        // Check preconditions
        if (g == null) {            
            throw new IllegalArgumentException("Violation of precondition: paintComponent. " +
                    "Graphics g == null. ");
        }

        // Paint all of the Squares in squares
        super.paintComponent(g);
        for (Square[] row : squares) {
            for (Square s : row) {
                s.paintComponent(g);
            }
        }
    }


    /**
     * Clear the visualization and setup a new maze to be solved.
     */
    public void resetMaze() {
        clearSquares();
        setupSquares();
        setupNeighbors();
        repaint();
    }


    /**
     * Clear all of the instance variables
     * Create a new HashSet of the special Square indices
     * Create a new 2d array of base Square objects
     */
    public void clearSquares() {

        // Clear all of the squares
        specialSquares = new HashSet<Integer>();
        squares = new Square[20][20];
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[r].length; c++) {
                squares[r][c] = new Square(r, c);
            }
        }
        start = null;
        end = null;
    }


    /**
     * Setup the special Square objects
     * Sets the state of 100 randomly chosen Squares to a wall
     * Sets the state of a single Square to the start
     * Sets the state of a single Square to the end
     */
    public void setupSquares() {

        // Setup the walls
        int numWalls = 100;
        while (specialSquares.size() != numWalls) {
            int i = (int) (Math.random() * squares.length * squares[0].length);
            if (specialSquares.add(i)) {
                squares[i / 20][i % 20].setState("Wall");
            }
        }

        // Set the start
        int i = (int) (Math.random() * squares.length * squares[0].length);
        while (!specialSquares.add(i)) {
            i = (int) (Math.random() * squares.length * squares[0].length);
        }
        start = squares[i / 20][i % 20];
        start.setState("Start");

        // Set the end
        i = (int) (Math.random() * squares.length * squares[0].length);
        while (!specialSquares.add(i)) {
            i = (int) (Math.random() * squares.length * squares[0].length);
        }
        end = squares[i / 20][i % 20];
        end.setState("End");
    }


    /**
     * Update the neighbors ArrayList for all of the Square objects
     * in the squares 2d array. Neighbors are defined as Square objects that are 
     * directly above, below, right or left of the current Square.
     */
    public void setupNeighbors() {

        // Loop through all of the squares
        for (int r = 0;  r < squares.length; r++) {
            for (int c = 0; c < squares[r].length; c++) {
                Square current = squares[r][c];

                // Check if the surrounding squares are valid neighbors
                int[][] deltas = new int[][] { {1, 0}, {0, 1}, {0, -1}, {-1, 0}};
                for (int i = 0; i < deltas.length; i++) {
                    int newRow = r + deltas[i][0];
                    int newCol = c + deltas[i][1];
                    if (newRow >= 0 && newRow < squares.length 
                            && newCol >= 0 && newCol < squares[newRow].length 
                            && !squares[newRow][newCol].getState().equals("Wall")) {
                        current.addNeighbor(squares[newRow][newCol]);
                    }
                }
            }
        }
    }


    /**
     * Update the state variables of all of the Square
     * objects in the parameter path to "Path".
     * @param path the path of Square objects from the start to the end Square.
     */
    public void showPath(ArrayList<Square> path) {
        System.out.println("Show Path");

        // Update Squares in path
        for (int i = 1; i < path.size() - 1; i++) {
            path.get(i).setState("Path");
        }
        repaint();
    }


    /**
     * Create a deep copy of the parametr's path instance variable.
     * @param s the Square that's path will be copied.
     * @return and ArrayList of Square objects that begin with the 
     * start square and end with this square.
     */
    public ArrayList<Square> deepCopyPath(Square s) {

        // Copy the path of the parameter
        ArrayList<Square> deepCopy = new ArrayList<Square>();
        for (Square o : s.getPath()) {
            deepCopy.add(o);
        }

        return deepCopy;
    }


    /*
     */
    public void startAStar() {}


    /**
     * Finds the shortest path from the start square to the end square.
     * Changes the state of the squares that were visited and
     * the squares that are a part of the solution path.
     */
    public void startDijkstras() {

        // Create the visited and toVisit data structures
        Set<Square> visited = new HashSet<Square>();
        ArrayList<Square> toVisit = new ArrayList<Square>();
        toVisit.add(start);
        start.addPath(start);
        start.setDistance(0);
        
        // Loop while there is a neighbor to visit
        while (!toVisit.isEmpty()) {
            Square current = toVisit.remove(0);
            visited.add(current);

            // Check if reached end
            if (current.equals(end)) {
                showPath(current.getPath());
                return;
            }

            // Update the current square
            if (!current.getState().equals("Start")) {
                current.setState("Visited");
            }

            // Handle the neighbors of the current square
            for (Square neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {

                    // Update the neighbor's distance
                    if (current.getDistance() + 1 < neighbor.getDistance()) {
                        neighbor.setPath(deepCopyPath(current));
                        neighbor.addPath(neighbor);
                        neighbor.setDistance(current.getDistance() + 1);
                    }
                    
                    // Add the neighbor if not already in toVisit
                    if (!toVisit.contains(neighbor)) {
                        toVisit.add(neighbor);
                    }
                }
            }

            // Sort the squares by their distance
            Collections.sort(toVisit);
        }
    }


}