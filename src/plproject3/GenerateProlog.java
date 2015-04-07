package plproject3;

import java.util.ArrayList;

/**
 * Created by chris_000 on 4/7/2015.
 */
public class GenerateProlog {


    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;
    private String lispProgram = "";


    //Necessary strings to generate FSM that will be used repeatedly

    private static final String commentHeaderFooter = "%%**************************%%\n",
    acceptStatesHeader = commentHeaderFooter + "%% Accept States\t\t\t%%\n" + commentHeaderFooter,
    rulesHeader = commentHeaderFooter + "%% RULES \t\t\t\t\t%%\n" + commentHeaderFooter;


    /**
     *
     * @param fsm Initialized FSM data
     */
    public GenerateLisp(Fsm fsm){
        this.parsedAlphabet = fsm.getParsedAlphabet();
        this.startState = fsm.getStartState();
        this.stateTable = fsm.getStateTable();
    }


    /**Generates a lisp program and puts it in a string
     * @return Lisp program
     */
    public String generateLisp(){
        int numStates = stateTable.size();
        lispProgram = generateStartFunction();
        lispProgram += "\n\n";

        for(int i = 0; i < numStates; i++){
            lispProgram += generateStateFunction(i);
            lispProgram += "\n\n";
        }
        return lispProgram;
    }


    /**
     *
     * @param stateNum State number
     * @return A function representing a state
     */
    public String generateStateFunction(int stateNum){
        State state = stateTable.get(stateNum);
        String s = startfunction; // (DEFUN
        s += "S" + stateNum;      // (S(NUM))
        s += startFunctionCheckAccept +  //(L) (COND ((NULL L)
                this.isAcceptState(state) + ")"; // T OR NIL)
        s += checkAtom; //      ((ATOM L) NIL)
        s += getTransitions(state);
        s += end;


        return s;
    }

    /**
     *
     * @param s State
     * @return T or NIL depending on if the state of accepting or not
     */
    private String isAcceptState(State s){
        if(s.isAcceptState()){
            return "T";
        }
        else return "NIL";
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
        return transitions;
    }

    /**
     *
     * @return An initial function that calls the starting state function
     */
    private String generateStartFunction(){
        return "fsa(List) :- s" + startState + "(List)";
    }



    private String generateStateHeader(int stateNum){
        return
                (commentHeaderFooter + "%% s" + stateNum + "   \t\t\t\t\t%%" + commentHeaderFooter);
    }

    private String generateAcceptRule(int stateNum){
        String state = "s" + stateNum;
        return
                (state + "([]) :- accept("+ state +").\n");
    }

    private String generateFunctionCall(int stateNum){
        String state = "s" + stateNum;
        return (state + "([Head | Tail]) :- " + state + "(Head, Tail).\n");
    }
}
