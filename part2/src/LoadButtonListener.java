import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class LoadButtonListener implements ActionListener {

    FsmSolverPanel listenPanel;
    String numStatesString = "";
    int numStates = 0;
    int startState = 0;
    int alphabetLength = 0;
    String alphabet = "";
    String parsedAlphabet[];
    String stateTransitions = "";
    String startStateString = "";

    String acceptStates = "";


    //Safe verification parameters
    ArrayList<String> unsafeLoadReasons;

    LoadButtonListener(FsmSolverPanel jPanel){
        listenPanel = jPanel;
        unsafeLoadReasons = new ArrayList<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        loadAutomaton();
    }

    private void loadAutomaton(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(".")); //Select project directory
        int returnVal = jFileChooser.showOpenDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();
            try {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                fileWriter.close();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(listenPanel, "Failed to load file. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
