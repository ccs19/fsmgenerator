import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class LoadButtonListener implements ActionListener {


    //Parent of the listener
    private FsmPanelPart2 listenPanel;

    //Raw data
    private String stateTransitionsString = "";
    private String startStateString = "";
    private String acceptStatesString = "";
    private String numStatesString = "";
    private String alphabetString = "";


    //Parsed data
    private String parsedAlphabet[] = null;
    private int parsedAcceptStates[] = null;
    private String parsedStateTransitions[] = null;
    private int numStates = -1;
    private int startState = -1;

    //I/O
    private  BufferedReader fileReader;

    //Safe verification parameters
    private ArrayList<String> unsafeLoadReasons;


    LoadButtonListener(FsmPanelPart2 jPanel){
        listenPanel = jPanel;
        unsafeLoadReasons = new ArrayList<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e){

        resetEntry(); //If we've failed once before, we need to reset parameters.

        if(loadAutomaton()) {
            checkAutomaton();
            checkValues();
        }
        else return;

        if(unsafeLoadReasons.size() > 0) {  //If any invalid input in text fields...

            String dialogMessage = "\n";

            for (String unsafeSaveReason : unsafeLoadReasons) {
                dialogMessage += unsafeSaveReason + "\n";
            }
            //Show dialog with input errors found
            JOptionPane.showMessageDialog(listenPanel, dialogMessage, "Cannot load", JOptionPane.OK_OPTION);
            listenPanel.enableSolveButton(false);
        }
        else
            listenPanel.enableSolveButton(true);
    }

    private boolean loadAutomaton(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(".")); //Select project directory
        int returnVal = jFileChooser.showOpenDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();
            try {
                fileReader = new BufferedReader(new FileReader(file));
                return true;
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(listenPanel, "Failed to load file. Cannot load from disk.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else{
            return false;
        }
    }


    private void checkAutomaton() {
        try {
            numStatesString = readNextLine();
            alphabetString = readNextLine();
            stateTransitionsString = readNextLine();
            startStateString = readNextLine();
            acceptStatesString = readNextLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(listenPanel, "Error Reading File", "File Read Error", JOptionPane.ERROR_MESSAGE);
            listenPanel.enableSolveButton(false);
        } catch (InvalidFsmFormatException e) {
            JOptionPane.showMessageDialog(listenPanel, "Invalid file format", "Invalid File Format", JOptionPane.ERROR_MESSAGE);
            listenPanel.enableSolveButton(false);
        }finally {
            try{
                fileReader.close();
            } catch (IOException e){
                JOptionPane.showMessageDialog(listenPanel, "Failed to close file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String readNextLine() throws IOException, InvalidFsmFormatException{
        String s = fileReader.readLine();
        if(s == null) {
            throw new InvalidFsmFormatException();
        }
        return s;
    }

    private void checkValues(){
        numStates = FsmChecker.checkNumStates(numStatesString,unsafeLoadReasons);
        parsedAlphabet = FsmChecker.checkAlphabet(alphabetString, unsafeLoadReasons);
        parsedAcceptStates = FsmChecker.checkAcceptStates(acceptStatesString, unsafeLoadReasons, numStates);
        parsedStateTransitions = FsmChecker.checkStateTransitions(stateTransitionsString, unsafeLoadReasons,
                numStates, parsedAlphabet);
        startState = FsmChecker.checkStartState(numStates, startStateString, unsafeLoadReasons);
    }


    private void resetEntry(){
        unsafeLoadReasons = new ArrayList<String>();
        numStates = -1;
        parsedAlphabet = null;
        parsedAcceptStates = null;
        parsedStateTransitions = null;
        startState = -1;
        stateTransitionsString = "";
        startStateString = "";
        acceptStatesString = "";
        numStatesString = "";
        alphabetString = "";
    }


    public int getStartState() {
        return startState;
    }

    public int getNumStates() {
        return numStates;
    }

    public String[] getParsedStateTransitions() {
        return parsedStateTransitions;
    }

    public int[] getParsedAcceptStates() {
        return parsedAcceptStates;
    }

    public String[] getParsedAlphabet() {
        return parsedAlphabet;
    }


    private class InvalidFsmFormatException extends Exception
    {
        public InvalidFsmFormatException(){
            super("Invalid FSM File Format");
        }
    }


}



