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
    String parsedAlphabet[];
    String stateTransitions = "";
    String startStateString = "";

    String acceptStates = "";


    //Safe verification parameters
    ArrayList<String> unsafeSaveReasons;
    enum UnsafeReasons{
        notNumber,
        noEntry,

    }


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
            JOptionPane.showMessageDialog(listenPanel, dialogMessage, "Cannot save", JOptionPane.OK_OPTION);
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
        final String fieldName = "Number of States";
        numStatesString = listenPanel.getNumStates();
        try
        {
            numStates = FsmChecker.checkNumStates(numStatesString, unsafeSaveReasons);
        }
        catch(NumberFormatException e )
        {
            unsafeAdd(UnsafeReasons.notNumber, fieldName);
        }
    }

    private void checkAlphabet()
    {
        final String fieldName = "Alphabet";
        alphabet = listenPanel.getAlphabet();
        if(alphabet.length() == 0)
            unsafeAdd(UnsafeReasons.notNumber, fieldName);
        parsedAlphabet = FsmChecker.checkAlphabet(alphabet, unsafeSaveReasons);
        if(parsedAlphabet != null) {
            alphabetLength = parsedAlphabet.length;
        }
        else{
            alphabetLength = 0;
        }
    }

    private void checkAccepStates()
    {
        final String fieldName = "Accept States";
        acceptStates = listenPanel.getAcceptStates();
        if(acceptStates.length() == 0)
            this.unsafeAdd(UnsafeReasons.noEntry, fieldName);
        else
        {
            try{
                FsmChecker.checkAcceptStates(acceptStates, unsafeSaveReasons, numStates);
            }
            catch(NumberFormatException e)
            {
                this.unsafeAdd(UnsafeReasons.notNumber, fieldName);
            }
        }

    }

    private void checkStateTransitions()
    {
        final String fieldName = "State Transitions";
        stateTransitions = listenPanel.getTransitions();
        if(stateTransitions.length() == 0)
        {
            this.unsafeAdd(UnsafeReasons.noEntry, fieldName);
        }
        FsmChecker.checkStateTransitions(stateTransitions, unsafeSaveReasons, numStates, parsedAlphabet);
    }

    private void checkStartState()
    {
        final String fieldName = "Start State";
        startStateString = listenPanel.getStartState();
        try
        {
            startState = FsmChecker.checkStartState(numStates, startStateString, unsafeSaveReasons);
        }
        catch (NumberFormatException e)
        {
            this.unsafeAdd(UnsafeReasons.notNumber, fieldName);
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
                JOptionPane.showMessageDialog(listenPanel, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void unsafeAdd(UnsafeReasons reason, String fieldName){
        String messageToAdd = "Invalid entry in " + fieldName +": ";
        switch (reason) {
            case notNumber:
                messageToAdd += "Not a number";
                break;
            case noEntry:
                messageToAdd += "No entry";
                break;
            default:
                messageToAdd += "Unknown error";
                break;
        }
        unsafeSaveReasons.add(messageToAdd);
    }
}
