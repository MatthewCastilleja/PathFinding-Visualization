import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Square implements Comparable<Square> {


    // Instance variables
    private int row;
    private int col;
    private int xPos;
    private int yPos;
    private String state;
    private int distance;
    private ArrayList<Square> neighbors;
    private ArrayList<Square> path;
    final static private int width = 40;
    final static private int height = 30;
    final static private TreeMap<String, Color> colorMap = new TreeMap<String, Color>();
    static {
        colorMap.put("Wall", Color.BLACK);
        colorMap.put("Open", Color.WHITE);
        colorMap.put("End", Color.RED);
        colorMap.put("Start", Color.ORANGE);
        colorMap.put("Visited", Color.BLUE);
        colorMap.put("Path", Color.GREEN);
    }


    /**
     * Creates a new instance of a Square object
     * set the instance variables to the values for 
     * an open unvisited square
     */
    public Square(int r, int c) {
        row = r;
        col = c;
        xPos = c * width;
        yPos = r * height;
        state = "Open";
        distance = Integer.MAX_VALUE;
        neighbors = new ArrayList<Square>();
        path = new ArrayList<Square>();
    }


    /**
     * Return the state that this square is in.
     * @return the state of this Square
     */
    public String getState() {
        return state;
    }


    /**
     * Change this Square's state to the parameter
     * @param s the new state of this Square
     */
    public void setState(String s) {
        state = s;
    }


    /**
     * Return the distance this square is from the start.
     * @return the distance of this Square
     */
    public int getDistance() {
        return distance;
    }


    /**
     * Change this Square's distance to the parameter
     * @param d the new distance of this Square
     */
    public void setDistance(int d) {
        distance = d;
    }


    /**
     * Return the neighbors of this square. Neighbors are squares that
     * are directly above, below, left, or right of this Square.
     * @return the neighbors of this square
     */
    public ArrayList<Square> getNeighbors() {
        return neighbors;
    }
    

    /**
     * Add the parameter Square to this Square's neighbors ArrayList
     * @param n the Square to be added
     */
    public void addNeighbor(Square n) {
        neighbors.add(n);
    }


    /**
     * Return the path of this square. The path is the shortest path from the
     * start Square to this Square.
     * @return the path of this Square
     */
    public ArrayList<Square> getPath() {
        return path;
    }


    /**
     * Change this Square's path to the parameter
     * @param p the new Path of this Square.
     */
    public void setPath(ArrayList<Square> p) {
        path = p;
    }


    /**
     * Add the parameter to this Square's path
     * @param the Square to be added to the path
     */
    public void addPath(Square s) {
        path.add(s);
    }


    /**
     * Check if this Square is equal to the parameter Square
     * @param other the square to be compared to
     * @return whether the square are equal
     */
    public boolean equals(Square other) {
        if(row == other.row && col == other.col) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Paint this square to the parameter.
     * @param g the Graphics object to be drawn on
     */
    public void paintComponent(Graphics g) {
        g.setColor(colorMap.get(state));
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);
    }


    /**
     * Override Object's compareTo method
     * Compare this object to the parameter by using distance
     * @param other the Square this will be compared to
     * @return the difference in distance between these Squares 
     */
    public int compareTo(Square other) {
        return distance - other.distance;
    }


}