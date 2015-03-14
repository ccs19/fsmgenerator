import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Chris on 3/14/2015.
 */
public class SolveButtonListener implements ActionListener{

    //Parsed data
    private ArrayList<String> parsedAlphabet = null;
    private ArrayList<Integer> parsedAcceptStates = null;
    private ArrayList<String> parsedStateTransitions = null;
    private int numStates = -1;
    private int startState = -1;

    //Parent JPanel and load listener with data
    FsmSolverPanel parent = null;
    LoadButtonListener loadButtonListener = null;

    //word to check
    String word = null;


    SolveButtonListener(FsmSolverPanel fsmSolverPanel, LoadButtonListener listener){
        loadButtonListener = listener;
        parent = fsmSolverPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setData();
        checkWord();
    }


    private void setData(){
        int acceptStates[] = loadButtonListener.getParsedAcceptStates();
        parsedAcceptStates = new ArrayList<Integer>();
        for(int i : acceptStates) {
            parsedAcceptStates.add(Integer.valueOf(i));
        }
        parsedAlphabet = new ArrayList<String>(Arrays.asList(loadButtonListener.getParsedAlphabet()));
        parsedStateTransitions = new ArrayList<String>(Arrays.asList(loadButtonListener.getParsedStateTransitions()));
        numStates = loadButtonListener.getNumStates();
        startState = loadButtonListener.getStartState();
        word = parent.getWordEntryString();
    }

    private void checkWord(){
        checkAlphabet();
    }

    private boolean checkAlphabet(){
        int wordLength = word.length();

        for(int i = 0; i < wordLength; i++){
            if(!parsedAlphabet.contains(word.substring(i)))
                return false;
        }
        return true;// all letters exist in alphabet
    }

}
