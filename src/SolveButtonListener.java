import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private int currentState;

    //Boolean for valid word
    private boolean valid = true;

    //Parent JPanel and load listener with data
    FsmPanelPart2 parent = null;
    LoadButtonListener loadButtonListener = null;

    //word to check
    String word = null;

    //Thread to run checking
    ExecutorService checkWordThread = Executors.newSingleThreadExecutor();


    SolveButtonListener(FsmPanelPart2 fsmSolverPanel, LoadButtonListener listener){
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
        currentState = startState;
        word = parent.getWordEntryString();
    }

    private void checkWord(){
        valid = true;

        //checkWordThread.submit(new CheckWord(checkOption.checkAlphabet));
        checkWordThread.submit(new CheckWord(checkOption.generateStateTable));
        checkWordThread.submit(new CheckWord(checkOption.solveEntry));
        checkWordThread.submit(new CheckWord(checkOption.isValidWord));
    }




    enum checkOption{checkAlphabet, generateStateTable, solveEntry, isValidWord};

    ArrayList<State> stateTable;

    private class CheckWord implements Runnable {
        private checkOption option;


        CheckWord(checkOption option){
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

        private void generateStateTable(){
            stateTable = new ArrayList<State>();
            for(int i = 0; i < numStates; i++){ //Create states
                stateTable.add(new State(numStates, parsedAlphabet.size()));
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

        private void isValidWord(){
            if(valid){
                JOptionPane.showMessageDialog(parent, "String is valid for this FSM!", "Valid String!", JOptionPane.OK_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(parent, "String is not valid for this FSM", "Invalid String!", JOptionPane.OK_OPTION);
            }
        }

    }

}
