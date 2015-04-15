package plproject3;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class FsmSolver {

    private static final String title = "Fsm Solver";
    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame(title);
                FsmSolverPanel fsmSolverPanel = new FsmSolverPanel();
                mainFrame.getContentPane().add(fsmSolverPanel);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
                mainFrame.setJMenuBar(fsmSolverPanel.generateMenu());
                mainFrame.pack();
                mainFrame.setVisible(true);
                mainFrame.setResizable(false);
            }
        });

    }

}
