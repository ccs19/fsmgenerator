package plproject3;

import javax.swing.*;
import java.awt.*;


public class FsmCreator {


    private static final String title = "Fsm Creator";


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run(){
                JFrame mainFrame = new JFrame(title);
                mainFrame.getContentPane().add(new FsmCreatorPanel());
                mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                mainFrame.pack();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
                mainFrame.setVisible(true);
            }
        });

    }
}
