package plproject3;

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

    FsmPanelPart1 listenPanel;
    String numStatesString = "";
    int numStates = 0;
    int startState = 0;
    int alphabetLength = 0;
    String alphabet = "";
    String parsedAlphabet[];
    String stateTransitions = "";
    String startStateString = "";

    String acceptStates = "";

    ArrayList<String> unsafeSaveReasons;


    SaveButtonListener(FsmPanelPart1 jPanel)
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
        numStatesString = listenPanel.getNumStates();
        numStates = FsmChecker.checkNumStates(numStatesString, unsafeSaveReasons);

    }

    private void checkAlphabet()
    {

        alphabet = listenPanel.getAlphabet();
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
        acceptStates = listenPanel.getAcceptStates();
        FsmChecker.checkAcceptStates(acceptStates, unsafeSaveReasons, numStates);
    }

    private void checkStateTransitions()
    {

        stateTransitions = listenPanel.getTransitions();

        FsmChecker.checkStateTransitions(stateTransitions, unsafeSaveReasons, numStates, parsedAlphabet);
    }

    private void checkStartState()
    {
        startStateString = listenPanel.getStartState();
        startState = FsmChecker.checkStartState(numStates, startStateString, unsafeSaveReasons);
    }

    private void saveAutomaton()
    {
        if(FsmChecker.getNfaFound()){
            JOptionPane.showMessageDialog(listenPanel, "You are about to save an NFA", "Notice", JOptionPane.OK_OPTION);
        }

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


}
