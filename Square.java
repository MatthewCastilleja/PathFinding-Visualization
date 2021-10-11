import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Square implements Comparable<Square> {

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
        colorMap.put("Start", Color.GREEN);
        colorMap.put("Visited", Color.BLUE);
        colorMap.put("Path", Color.GREEN);
    }

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

    public String getState() {
        return state;
    }

    public void setState(String s) {
        state = s;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int d) {
        distance = d;
    }

    public ArrayList<Square> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(Square n) {
        neighbors.add(n);
    }

    public ArrayList<Square> getPath() {
        return path;
    }

    public void setPath(ArrayList<Square> p) {
        path = p;
    }

    public void addPath(Square s) {
        path.add(s);
    }



    public boolean equals(Square other) {
        if(row == other.row && col == other.col) {
            return true;
        } else {
            return false;
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(colorMap.get(state));
        g.fillRect(xPos, yPos, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);
    }

    public int compareTo(Square other) {
        return distance - other.distance;
    }
}