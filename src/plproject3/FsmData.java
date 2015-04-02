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


    public FsmData(ArrayList<Integer> acceptStates, ArrayList<String> stateTransitions,
                   ArrayList<String> alphabet, int numStates, int startState){
        this.acceptStates = acceptStates;
        this.stateTransitions = stateTransitions;
        this.alphabet = alphabet;
        this.numStates = numStates;
        this.startState = startState;
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
