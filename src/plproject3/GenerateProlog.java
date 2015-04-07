package plproject3;

import java.util.ArrayList;

/**
 * Created by chris_000 on 4/7/2015.
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
     *
     * @param fsm Initialized FSM data
     */
    public GenerateProlog(Fsm fsm){
        this.parsedAlphabet = fsm.getParsedAlphabet();
        this.startState = fsm.getStartState();
        this.stateTable = fsm.getStateTable();
    }


    /**Generates a lisp program and puts it in a string
     * @return Lisp program
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
     *
     * @param state A state
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
     *
     * @return An initial function that calls the starting state function
     */
    private String generateStartRule(){
        return "fsa(List) :- s" + startState + "(List)\n\n";
    }

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

    private String generateStateHeader(int stateNum){
        return
                ("\n" + commentHeaderFooter + "%% s" + stateNum + "   \t\t\t\t\t%%\n" + commentHeaderFooter + "\n");
    }

    private String generateStateData(int stateNum){
        String state = "s" + stateNum;
        return (state + "([]) :- accept(" + state + ").\n" +
                state + "([Head | Tail]) :- " + state + "(Head, Tail).\n");
    }
}
