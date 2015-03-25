package plproject3;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by chris_000 on 3/26/2015.
 */
public class Fsm implements Runnable{

    private int currentState;
    private int numStates;


    //Options for runnable thread
    enum checkOption{checkAlphabet, generateStateTable, solveEntry, isValidWord}

    //Table containing state date
    ArrayList<State> stateTable;

    //Boolean for valid word
    private boolean valid = true;

    private checkOption option;

    Fsm(int startState, int numStates) {
        currentState = startState;
    }

    Fsm(checkOption option){
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
    public void generateStateTable(){
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




