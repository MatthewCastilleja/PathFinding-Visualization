import java.util.*;
import java.awt.*;
import javax.swing.*;

public class PathFindingRunner {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+ SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("PathFinding Solver Visual");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add(new Maze());
        f.pack();
        f.setVisible(true);
    } 
}