import java.util.ArrayList;

/**
 * Created by chris_000 on 3/15/2015.
 */
public class State {

    private boolean acceptState;
    private int numStates;
    private int alphabetSize;
    private ArrayList<Integer> transitions;

    State(int numStates, int alphabetSize){
        this.acceptState = false;
        this.numStates = numStates;
        this.alphabetSize = alphabetSize;
        transitions = new ArrayList<Integer>();

    }

    public void addTransition(int transitionTo, String character){
        int intCharacter = (int)character.charAt(0);
        transitions.add(Integer.valueOf(intCharacter));
        transitions.add(Integer.valueOf(transitionTo));

        //DEBUG
        System.out.println("Added " + transitionTo + character);
    }

    public void setAcceptState(boolean isAcceptState){
        this.acceptState = isAcceptState;
    }

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
