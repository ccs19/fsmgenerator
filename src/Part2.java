import javax.swing.*;
import java.awt.*;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class Part2 {

    private static final String title = "Fsm Solver";
    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame(title);
                mainFrame.getContentPane().add(new FsmPanelPart2());
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
                mainFrame.setVisible(true);
            }
        });

    }

}
