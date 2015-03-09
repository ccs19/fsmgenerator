import com.sun.corba.se.spi.orbutil.fsm.FSM;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Listener for save button
 */
public class SaveButtonListener implements ActionListener {

    FsmPanel listenPanel;
    String numStatesString = "";
    int numStates = 0;
    int startState = 0;
    int alphabetLength = 0;
    String alphabet = "";
    String stateTransitions = "";
    String startStateString = "";

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

            String dialogMessage = "\n";

            for (String unsafeSaveReason : unsafeSaveReasons) {
                dialogMessage += unsafeSaveReason + "\n";
            }
            //Show dialog with input errors found
            JOptionPane.showMessageDialog(null, dialogMessage, "Cannot save", JOptionPane.OK_OPTION);
        }
        else {
            saveAutomaton();
        }
    }

    /**
     * Check all the user input
     */
    private void checkStrings() {
        checkNumStates();
        checkAlphabet();
        checkAccepStates();
        checkStateTransitions();
        checkStartState();
    }

    private void checkNumStates()
    {
        numStatesString = listenPanel.getNumStates();
        try
        {
            numStates = FsmChecker.checkNumStates(numStatesString, unsafeSaveReasons);
        }
        catch(NumberFormatException e )
        {
            unsafeSaveReasons.add("Invalid entry in Number of States: Not a number");
        }
    }

    private void checkAlphabet()
    {
        alphabet = listenPanel.getAlphabet();
        if(alphabet.length() == 0)
            unsafeSaveReasons.add("Invalid entry in Alphabet");
        String[] parsedAlphabet = FsmChecker.checkAlphabet(alphabet, unsafeSaveReasons);
        if(parsedAlphabet != null) {
            alphabetLength = parsedAlphabet.length;
        }
        else{
            alphabetLength = 0;
        }
    }

    private void checkAccepStates()
    {
        acceptStates = listenPanel.getAcceptStates();
        if(acceptStates.length() == 0)
            unsafeSaveReasons.add("Invalid entry in Accept States: No entry");
        else
        {
            try{
                FsmChecker.checkAcceptStates(acceptStates, unsafeSaveReasons, numStates);
            }
            catch(NumberFormatException e)
            {
                unsafeSaveReasons.add("Invalid entry in Accept States: Not a number");
            }
        }

    }

    private void checkStateTransitions()
    {
        stateTransitions = listenPanel.getTransitions();
        if(stateTransitions.length() == 0)
        {
            unsafeSaveReasons.add("Invalid entry in State Transitions");
        }
        FsmChecker.checkStateTransitions(stateTransitions, unsafeSaveReasons, numStates, alphabetLength);
    }

    private void checkStartState()
    {
        startStateString = listenPanel.getStartState();
        try
        {
            startState = FsmChecker.checkStartState(numStates, startStateString, unsafeSaveReasons);
        }
        catch (NumberFormatException e)
        {
            unsafeSaveReasons.add("Invalid entry in Start State: Not a number");
        }
    }

    private void saveAutomaton()
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(".")); //Select project directory
        int returnVal = jFileChooser.showSaveDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();

            try {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                fileWriter.write(numStatesString + "\n" +
                                 alphabet + "\n" +
                                 stateTransitions + "\n" +
                        startStateString + "\n" +
                                 acceptStates);
                fileWriter.close();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
