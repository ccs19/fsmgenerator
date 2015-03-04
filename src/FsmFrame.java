import javax.swing.*;

/**
 * Created by Chris on 3/1/2015.
 */
public class FsmFrame extends JFrame {

    String title = "Finite State Machine Maker 0.1.6555b.gamma";

    FsmFrame()
    {
        super();
        this.setTitle(title);

        this.getContentPane().add(new FsmPanel());


        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
