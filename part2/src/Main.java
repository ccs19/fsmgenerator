import javax.swing.*;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class Main {

    private static final String title = "Fsm Solver";
    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame(title);
                mainFrame.getContentPane().add(new FsmSolverPanel());
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });

    }

}
