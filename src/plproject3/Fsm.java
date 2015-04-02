package plproject3;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by chris_000 on 3/26/2015.
 */
public class Fsm implements Runnable{

    private int currentState;
    private int numStates;
    private String word;
    private ArrayList<String> parsedAlphabet;
    private ArrayList<String> parsedStateTransitions;
    private ArrayList<Integer> parsedAcceptStates;

    //Options for runnable thread
    enum checkOption{checkAlphabet, generateStateTable, solveEntry, isValidWord}

    //Table containing state date
    private ArrayList<State> stateTable;

    //Boolean for valid word
    private boolean valid = true;

    private checkOption option;

    Fsm(int startState, int numStates, String word, ArrayList<String> parsedAlphabet,
        ArrayList<String> parsedStateTransitions, ArrayList<Integer> parsedAcceptStates) {
        currentState = startState;
        this.numStates = numStates;
        this.word = word;
        this.parsedAlphabet = parsedAlphabet;
        this.parsedStateTransitions = parsedStateTransitions;
        this.parsedAcceptStates = parsedAcceptStates;
    }

    public void setOption(checkOption option){
        this.option = option;
    }

    public void run(){
                checkAlphabet();
                if(!valid) return; //If invalid, do nothing
                generateStateTable();
                if(!valid) return; //If invalid, do nothing
                solveEntry();
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
            }
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



    public boolean getValid(){
        return valid;
    }


    public ArrayList<State> getStateTable(){
        return stateTable;
    }

    public ArrayList<String> getParsedAlphabet(){
        return parsedAlphabet;
    }
}




