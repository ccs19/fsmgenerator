package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class LoadButtonListener implements ActionListener {


    //FsmData
    private FsmData fsmData;


    //Parent of the listener
    private FsmSolverPanel listenPanel;

    //Raw data
    private String stateTransitionsString = "";
    private String startStateString = "";
    private String acceptStatesString = "";
    private String numStatesString = "";
    private String alphabetString = "";


    //Parsed data
    private ArrayList<String> parsedAlphabet = null;
    private ArrayList<Integer> parsedAcceptStates = null;
    private ArrayList<String> parsedStateTransitions = null;
    private int numStates = -1;
    private int startState = -1;

    //I/O
    private  BufferedReader fileReader;

    //Safe verification parameters
    private ArrayList<String> unsafeLoadReasons;

    //Successfully opened file
    private boolean openedFile = true;

    //String to save previous directory
    String previousDir = ".";

    /**
     * Listener for load button
     * @param jPanel The parent panel
     */
    LoadButtonListener(FsmSolverPanel jPanel){
        listenPanel = jPanel;
        unsafeLoadReasons = new ArrayList<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e){

        resetEntry(); //If we've failed once before, we need to reset parameters.



        if(openFile()) {
            checkAutomaton();
            checkValues();
        }
        else return;

        if(unsafeLoadReasons.size() > 0) {  //If any invalid input in text fields...

            String dialogMessage = "\n";

            for (String unsafeSaveReason : unsafeLoadReasons) {
                dialogMessage += unsafeSaveReason + "\n";
            }
            //Show dialog with input errors found if file successfully read
            if(openedFile)//Show dialog with input errors found if file successfully read
                JOptionPane.showMessageDialog(listenPanel, dialogMessage, "Cannot load", JOptionPane.OK_OPTION);
            listenPanel.enableButtons(false);
        }
        else {
            listenPanel.enableButtons(true);

        }
    }







    /**
     * Opens JFileChooser to load file representing FSM
     * @return true if file succesffully loaded, false if failed or cancelled.
     */
    private boolean openFile(){

        fileReader = FileManager.openFile(listenPanel);
        if(fileReader == null)
            return false;
        else
            return true;
    }

    /**
     * Attempts to read in each line from the opened file
     * If failure occurs, an error message is shown
     */
    private void checkAutomaton() {
        try {
            String fsmData[] = FileManager.readNextLine(fileReader).split(";");
            numStatesString = fsmData[FsmData.NUM_STATES];
            alphabetString = fsmData[FsmData.ALPHABET];
            stateTransitionsString = fsmData[FsmData.TRANSITIONS];
            startStateString = fsmData[FsmData.START_STATE];
            acceptStatesString = fsmData[FsmData.ACCEPT_STATES];
        } catch (IOException e) {
            JOptionPane.showMessageDialog(listenPanel, "Error Reading File", "File Read Error", JOptionPane.ERROR_MESSAGE);
            listenPanel.enableButtons(false);
            openedFile = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(listenPanel, "Invalid file format", "Invalid File Format", JOptionPane.ERROR_MESSAGE);
            listenPanel.enableButtons(false);
            openedFile = false;
        }finally {
            try{
                fileReader.close();
            } catch (IOException e){
                JOptionPane.showMessageDialog(listenPanel, "Failed to close file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Sends each string to the FsmChecker class and fills in values
     */
    private void checkValues(){
        numStates = FsmChecker.checkNumStates(numStatesString,unsafeLoadReasons);

        parsedAlphabet = new ArrayList<String>
                (Arrays.asList(FsmChecker.checkAlphabet(alphabetString, unsafeLoadReasons)));

        int acceptStates[] = FsmChecker.checkAcceptStates(acceptStatesString, unsafeLoadReasons, numStates);
        parsedAcceptStates = new ArrayList<Integer>();
        for(int i: acceptStates){
            parsedAcceptStates.add(i);
        }
        parsedStateTransitions = new ArrayList<String>
                (Arrays.asList(FsmChecker.checkStateTransitions(
                        stateTransitionsString, unsafeLoadReasons, numStates, parsedAlphabet.toArray(new String[0]))));

        startState = FsmChecker.checkStartState(numStates, startStateString, unsafeLoadReasons);
        fsmData = new FsmData(parsedAcceptStates,
                parsedStateTransitions,
                parsedAlphabet,
                numStates,
                startState);
    }


    /**
     * Resets all variables
     */
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
        openedFile = true;
    }

    public FsmData getFsmData(){
        return fsmData;
    }





}



