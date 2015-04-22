package plproject3;

import java.util.ArrayList;

/**
 * A class representing a finite state machine
 */
public class Fsm implements Runnable{

    private int currentState;
    private int numStates;
    private int startState;
    private String word = null;
    private ArrayList<String> parsedAlphabet;
    private ArrayList<String> parsedStateTransitions;
    private ArrayList<Integer> parsedAcceptStates;
    private ArrayList<String> queryStrings;


    //Options for runnable thread
    enum checkOption{ generateStateTable, solveEntry}

    private checkOption option;

    //Table containing state date
    private ArrayList<State> stateTable;

    //Boolean for valid word
    private boolean valid = true;

    /**
     *
     * @param fsmData Initialized fsm data
     * @param word Word to be checked
     */
    Fsm(FsmData fsmData, String word){
        this.word = word;
        setFsmData(fsmData);
    }

    /**
     * Constructor if no word is to be analyzed
     * @param fsmData Initialized fsm data
     */
    Fsm(FsmData fsmData) {
        setFsmData(fsmData);
    }


    /**
     * Sets fsmData
     * @param fsmData Initialized FsmData object
     */
    private void setFsmData(FsmData fsmData){
        currentState = fsmData.getStartState();
        this.startState = fsmData.getStartState();
        this.numStates = fsmData.getNumStates();
        this.parsedAlphabet = fsmData.getAlphabet();
        this.parsedStateTransitions = fsmData.getStateTransitions();
        this.parsedAcceptStates = fsmData.getAcceptStates();
        this.queryStrings = fsmData.getQueryStrings();
    }

    /**
     * Set the runnable option
     * @param option  Runnable option
     */
    public void setOption(checkOption option){
        this.option = option;
    }



    public void run(){
        switch(option) {
            case generateStateTable:
                generateStateTable();
                break;

            case solveEntry:
                if(null == word) throw new RuntimeException("Word in Fsm not Initialized!");
                checkAlphabet();
                if (!valid) return; //If invalid, do nothing
                generateStateTable();
                solveEntry();
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
    private static final int CUR_STATE = 0,
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
        for (String parsedStateTransition : parsedStateTransitions) {
            String s[] = parsedStateTransition.split("\\(");
            s = s[1].split("\\)");
            s = s[0].split(":");
            try {
                int state = Integer.parseInt(s[CUR_STATE]);
                int transitionState = Integer.parseInt(s[TRANS_STATE]);
                stateTable.get(state).addTransition(transitionState, s[CHAR]);
            } catch (NumberFormatException e) {
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


    /**
     *
     * @return If FSM/String is valid
     */
    public boolean getValid(){
        return valid;
    }


    /**
     *
     * @return State table
     */
    public ArrayList<State> getStateTable(){
        return stateTable;
    }

    /**
     *
     * @return Parsed alphabet
     */
    public ArrayList<String> getParsedAlphabet(){
        return parsedAlphabet;
    }

    /**
     *
     * @return Starting state
     */
    public int getStartState(){
        return startState;
    }

    public ArrayList<String> getQueryStrings(){return queryStrings;}

    /**
     * Needed in case user loads Fsm before setting Prolog query strings
     */
    public void setQueryStrings(ArrayList<String> queryStrings){
        this.queryStrings = queryStrings;
    }
}




