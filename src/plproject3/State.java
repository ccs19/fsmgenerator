package plproject3;

import java.util.ArrayList;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class State {

    private boolean acceptState;
    private ArrayList<Integer> transitions;


    /**
     * Class representing one state in an FSM
     */
    State(){
        this.acceptState = false;
        transitions = new ArrayList<Integer>();

    }

    /**
     * Adds a transition to a state
     * @param transitionTo State to transition to
     * @param character Character that triggers transition
     */
    public void addTransition(int transitionTo, String character){
        int intCharacter = (int)character.charAt(0);
        transitions.add(intCharacter);
        transitions.add(transitionTo);
    }

    /**
     *
     * @param isAcceptState Set whether or not this state is an accept state
     */
    public void setAcceptState(boolean isAcceptState){
        this.acceptState = isAcceptState;
    }


    /**
     *  Returns state to transition to
     * @param character Input
     * @return The state to transition to. On failure, return -1
     */
    public int getTransition(String character){
        int intCharacter = (int)character.charAt(0);

        int characterIndex = transitions.indexOf(Integer.valueOf(intCharacter));
        if(characterIndex != -1){ //If transition exists
            return transitions.get(characterIndex+1);
        }
        else{                   //Else it doesn't, so the string fails :(
            return characterIndex;
        }
    }

    /**
     *
     * @return If this is an accept state
     */
    public boolean isAcceptState(){
        return acceptState;
    }


}
