import javax.swing.*;


public class Part1 {


    private static final String title = "Fsm Creator";


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run(){
                JFrame mainFrame = new JFrame(title);
                mainFrame.getContentPane().add(new FsmPanel());
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });

    }
}
