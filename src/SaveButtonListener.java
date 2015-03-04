import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static java.lang.Integer.*;

/**
 * Created by Chris on 3/3/2015.
 */
public class SaveButtonListener implements ActionListener {

    FsmPanel listenPanel;
    String saveString = "";
    String numStates = "";
    String alphabet = "";
    String stateTransitions = "";
    String startState = "";
    String acceptStates = "";

    //Safe verification parameters
    ArrayList<String> unsafeSaveReasons;

    SaveButtonListener(FsmPanel jPanel)
    {
        listenPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        unsafeSaveReasons = new ArrayList<String>();
        checkStrings();
        if(unsafeSaveReasons.size() > 0) {  //If any invalid input in text fields...

            String dialogMessage = "Cannot save\n";

            for(int i = 0; i < unsafeSaveReasons.size(); i++)
            {
                dialogMessage += unsafeSaveReasons.get(i) + "\n";
            }
            //Show dialog with input errors found
            JOptionPane.showMessageDialog(null, dialogMessage, "Cannot save", JOptionPane.OK_OPTION);
        }
        else {
            saveAutomaton();
        }
    }

    private void checkStrings() {
        checkNumStates();
        checkAlphabet();
        checkAccepStates();
        checkStateTransitions();
        checkStartState();
    }

    private void checkNumStates()
    {
        numStates = listenPanel.getNumStates();
        try
        {
            Integer.parseInt(numStates);
        }
        catch(NumberFormatException e )
        {
            unsafeSaveReasons.add("Invalid entry in Number of States");
        }
    }

    private void checkAlphabet()
    {
        alphabet = listenPanel.getAlphabet();
    }

    private void checkAccepStates()
    {
        acceptStates = listenPanel.getAcceptStates();
    }

    private void checkStateTransitions()
    {
        stateTransitions = listenPanel.getTransitions();
    }

    private void checkStartState()
    {
        startState = listenPanel.getStartState();
        try
        {
            Integer.parseInt(startState);
        }
        catch (NumberFormatException e)
        {
            unsafeSaveReasons.add("Invalid entry in Start State");
        }
    }

    private void saveAutomaton()
    {
        JFileChooser jFileChooser = new JFileChooser();
        int returnVal = jFileChooser.showSaveDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();

            try {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                fileWriter.write(numStates + "\n" +
                                 alphabet + "\n" +
                                 stateTransitions + "\n" +
                                 startState + "\n" +
                                 acceptStates);
                fileWriter.close();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.OK_OPTION);
            }
        }
    }
}
