package plproject3;

import java.util.ArrayList;

/**
 * Class representing data relevant to a finite state machine
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


    /**
     * Constructor for finite state machine data
     * @param acceptStates Accept states
     * @param stateTransitions Transitions
     * @param alphabet Alphabet
     * @param numStates Number of states
     * @param startState Starting stae
     */
    public FsmData(ArrayList<Integer> acceptStates, ArrayList<String> stateTransitions,
                   ArrayList<String> alphabet, int numStates, int startState){
        this.acceptStates = acceptStates;
        this.stateTransitions = stateTransitions;
        this.alphabet = alphabet;
        this.numStates = numStates;
        this.startState = startState;
    }


    /**
     *
     * @return Accept states
     */
    public ArrayList<Integer> getAcceptStates() {
        return acceptStates;
    }


    /**
     *
     * @return State transitions
     */
    public ArrayList<String> getStateTransitions() {
        return stateTransitions;
    }

    /**
     *
     * @return FSM Alphabet
     */
    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    /**
     *
     * @return Number of states
     */
    public int getNumStates() {
        return numStates;
    }


    /**
     *
     * @return Starting state
     */
    public int getStartState() {
        return startState;
    }



}
