package plproject3;

import java.util.ArrayList;

/**
 * Class to generate prolog code from a finite state automaton
 */
public class GenerateProlog {


    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;
    private String prologProgram = "";


    //Necessary strings to generate FSM that will be used repeatedly
    private static final String commentHeaderFooter = "%%**************************%%\n",
    acceptStatesHeader = commentHeaderFooter + "%% Accept States\t\t\t%%\n" + commentHeaderFooter + "\n",
    rulesHeader = commentHeaderFooter + "%% RULES \t\t\t\t\t%%\n" + commentHeaderFooter;


    /**
     *  Initializes data needed to generate code
     * @param fsm Initialized FSM data
     */
    public GenerateProlog(Fsm fsm){
        this.parsedAlphabet = fsm.getParsedAlphabet();
        this.startState = fsm.getStartState();
        this.stateTable = fsm.getStateTable();
    }


    /**Generates a prolog program and puts it in a string
     * @return prolog program in a string
     */
    public String generateProlog(){
        int numStates = stateTable.size();

        prologProgram += acceptStatesHeader;
        prologProgram += generateAcceptStates();
        prologProgram += "\n\n" + rulesHeader + "\n";
        prologProgram += generateStartRule();

        for(int i = 0; i < numStates; i++){
            prologProgram += generateStateHeader(i);
            prologProgram += generateStateData(i);
            prologProgram += getTransitions(stateTable.get(i), i);
        }

        System.out.println(prologProgram);
        return prologProgram;
    }

    /**
     * Generates state transition and returns the data in a string
     * @param state A state
     * @param stateNum The number of the state; must be unique
     * @return A string representing state transitions
     */
    private String getTransitions(State state, int stateNum){
        String transitions = "";
        for (String aParsedAlphabet : parsedAlphabet) {
            int transition = state.getTransition(aParsedAlphabet);
            if (transition != -1) {
                transitions += "s" + stateNum + "(" + aParsedAlphabet + ", List) :- s" + transition + "(List).\n";
            }
        }
        if(transitions.equals(""))  transitions += "\n";
        transitions += "\n";
        return transitions;
    }

    /**
     * Generates the starting rule
     * @return Starting rule in a string
     */
    private String generateStartRule(){
        return "fsa(List) :- s" + startState + "(List).\n\n";
    }


    /**
     * Generates accept states facts
     * @return String of accept state facts
     */
    private String generateAcceptStates(){
        String acceptStates = "";
        int i = 0;
        for(State s : stateTable){
            if(s.isAcceptState()){
                acceptStates += "accept(s" + i + ").\n\n"; //accept(si).
            }
            i++;
        }
        return acceptStates;
    }

    /**
     * Generates comment header
     * @param stateNum state number
     * @return String with comment header
     */
    private String generateStateHeader(int stateNum){
        return
                ("\n" + commentHeaderFooter + "%% s" + stateNum + "   \t\t\t\t\t%%\n" + commentHeaderFooter + "\n");
    }

    /**
     * Generates state transition data common to all states
     * @param stateNum state number
     * @return String of state transitions.
     */
    private String generateStateData(int stateNum){
        String state = "s" + stateNum;
        return (state + "([]) :- accept(" + state + ").\n" +
                state + "([Head | Tail]) :- " + state + "(Head, Tail).\n");
    }
}
