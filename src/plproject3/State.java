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

    State(){
        this.acceptState = false;
        transitions = new ArrayList<Integer>();

    }

    public void addTransition(int transitionTo, String character){
        int intCharacter = (int)character.charAt(0);
        transitions.add(intCharacter);
        transitions.add(transitionTo);
    }

    public void setAcceptState(boolean isAcceptState){
        this.acceptState = isAcceptState;
    }


    /**
     *
     * @param character
     * @return
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

    public boolean isAcceptState(){
        return acceptState;
    }


}
