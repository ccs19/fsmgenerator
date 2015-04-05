package plproject3;

import java.util.ArrayList;

/**
 * Created by chris_000 on 4/2/2015.
 */
public class FsmData {

    private ArrayList<String> stateTransitions;
    private ArrayList<String> alphabet;
    private int numStates;
    private int startState;
    private ArrayList<Integer> acceptStates;

    //Expected array index for fsm attributes
    public static final int NUM_STATES = 0, ALPHABET = 1, TRANSITIONS = 2,
                        START_STATE = 3, ACCEPT_STATES = 4;


    public FsmData(ArrayList<Integer> acceptStates, ArrayList<String> stateTransitions,
                   ArrayList<String> alphabet, int numStates, int startState){
        this.acceptStates = acceptStates;
        this.stateTransitions = stateTransitions;
        this.alphabet = alphabet;
        this.numStates = numStates;
        this.startState = startState;
    }

    public FsmData(){

    }

    public ArrayList<Integer> getAcceptStates() {
        return acceptStates;
    }

    public ArrayList<String> getStateTransitions() {
        return stateTransitions;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public int getNumStates() {
        return numStates;
    }

    public int getStartState() {
        return startState;
    }



}
