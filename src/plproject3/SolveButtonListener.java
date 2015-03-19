package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class SolveButtonListener implements ActionListener{

    //Parsed data
    private ArrayList<String> parsedAlphabet = null;
    private ArrayList<Integer> parsedAcceptStates = null;
    private ArrayList<String> parsedStateTransitions = null;
    private int numStates = -1;
    private int startState = -1;
    private int currentState;

    //Boolean for valid word
    private boolean valid = true;

    //Parent JPanel and load listener with data
    FsmPanelPart2 parent = null;
    LoadButtonListener loadButtonListener = null;

    //word to check
    String word = null;

    //Thread to run checking
    ExecutorService checkWordThread = Executors.newSingleThreadExecutor();


    /**
     * Word solving class
     * @param fsmSolverPanel Parent to the solve button
     * @param listener Load button listener to retrieve strings
     */
    SolveButtonListener(FsmPanelPart2 fsmSolverPanel, LoadButtonListener listener){
        loadButtonListener = listener;
        parent = fsmSolverPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setData();
        checkWord();
    }

    /**
     * Sets all necessary data needed to solve the word
     */
    private void setData(){
        int acceptStates[] = loadButtonListener.getParsedAcceptStates();
        parsedAcceptStates = new ArrayList<Integer>();
        for(int i : acceptStates) {
            parsedAcceptStates.add(i);
        }
        parsedAlphabet = new ArrayList<String>(Arrays.asList(loadButtonListener.getParsedAlphabet()));
        parsedStateTransitions = new ArrayList<String>(Arrays.asList(loadButtonListener.getParsedStateTransitions()));
        numStates = loadButtonListener.getNumStates();
        startState = loadButtonListener.getStartState();
        currentState = startState;
        word = parent.getWordEntryString();
    }

    /**
     * Submits data to checkWordThread
     */
    private void checkWord(){
        valid = true;
        checkWordThread.submit(new CheckWord(checkOption.generateStateTable));
        checkWordThread.submit(new CheckWord(checkOption.solveEntry));
        checkWordThread.submit(new CheckWord(checkOption.isValidWord));
    }




    //Options for runnable thread
    enum checkOption{checkAlphabet, generateStateTable, solveEntry, isValidWord}

    //Table containing state date
    ArrayList<State> stateTable;


    /**
     * Runnable class to solve word
     */
    private class CheckWord implements Runnable {
        private checkOption option;

        CheckWord(checkOption option){
            this.option = option;
        }

        public void run(){

            switch(option){
                case checkAlphabet:
                    checkAlphabet();
                    break;
                case generateStateTable:
                    if(!valid) return; //If invalid, do nothing
                    generateStateTable();
                    break;
                case solveEntry:
                    if(!valid) return; //If invalid, do nothing
                    solveEntry();
                    break;
                case isValidWord:
                    isValidWord();
                    break;
                default:
                    break;
            }
        }


        /**
         * Checks that all letters exist in the alphabet
         */
        private void checkAlphabet() {
            int wordLength = word.length();

            for (int i = 0; i < wordLength; i++) {
                if (!parsedAlphabet.contains(word.substring(i, i+1))) {
                    valid = false;
                    return;
                }
                }
            valid = true;// all letters exist in alphabet
        }

        //Indices for state transitions
        private final int CUR_STATE = 0,
                          TRANS_STATE = 1,
                          CHAR = 2;

        /**
         * Generates the state transition table
         */
        private void generateStateTable(){
            stateTable = new ArrayList<State>();
            for(int i = 0; i < numStates; i++){ //Create states
                stateTable.add(new State());
                if(parsedAcceptStates.contains(Integer.valueOf(i))) //Set accept state var if applicable
                    stateTable.get(i).setAcceptState(true);
            }

            //then fill states with transitions
            for(int i = 0; i < parsedStateTransitions.size(); i++){
                String s[] = parsedStateTransitions.get(i).split("\\(");
                s = s[1].split("\\)");
                s = s[0].split(":");
                try{
                    int state = Integer.parseInt(s[CUR_STATE]);
                    int transitionState = Integer.parseInt(s[TRANS_STATE]);
                    stateTable.get(state).addTransition(transitionState, s[CHAR]);
                } catch(NumberFormatException e){
                    System.err.println("Failed to convert state transition! Data was already parsed... this shouldn't happen!");
                };
            }
        }


        /**
         * Attempts to solve the word entered.
         * If the word is not valid, the valid variable is set to false
         */
        private void solveEntry(){

            for(int i = 0; i < word.length(); i++) {
                currentState = stateTable.get(currentState).getTransition(word.substring(i, i+1));
                if(-1 == currentState){
                    valid = false;
                    break; //Out of for loop
                }
            }

            if(!stateTable.get(currentState).isAcceptState()) //If not accept state
            valid = false;
        }

        /**
         * Checks if a valid word was found and shows a message dialog
         */
        private void isValidWord(){
            System.out.println("Accept states: ");
            for(int i : parsedAcceptStates)
                System.out.println(" " + i);
            System.out.println("Ending state: " + currentState);
            if(valid){
                JOptionPane.showMessageDialog(parent, "String is valid for this FSM!", "Valid String!", JOptionPane.OK_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(parent, "String is not valid for this FSM", "Invalid String!", JOptionPane.OK_OPTION);
            }
        }

    }

}
