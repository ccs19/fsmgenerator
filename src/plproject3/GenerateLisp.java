package plproject3;

import java.util.ArrayList;

/**
 * Generates a lisp program from a FSM object
 */
public class GenerateLisp {

    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;
    private String lispProgram = "";


    //Necessary strings to generate FSM that will be used repeatedly
    private static final String startfunction = "(DEFUN ", /**FunctionName**/
    startFunctionCheckAccept = "(L)" +
            "\n\t(COND" +
                    "\n\t\t((NULL L) ", /**T or NIL**/
    checkAtom = "\n\t\t((ATOM L) NIL)",

    equal = "\n\t\t((EQUAL '",


    car = " (CAR L))",
    cdr = " (CDR L)))",
    end = "\n\t\t(T (NULL))\n\t)\n)",

    //Starting function
     fsmFunction = "(DEFUN FSM (L)" +
            "\n\t(COND" +
            "\n\t\t" +
            checkAtom +
            "\n\t\t" +
            "((EQUAL T (S",
     fsmFunction2 = " L)) (PRINC \"This is a valid string!\"))" +
            "\n\t\t(T (PRINC \"This is an invalid string!\"))" +
            "\n\t)" +
            "\n)";


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
    private String getTransitions(State state){
        String transitions = "";
        for (String aParsedAlphabet : parsedAlphabet) {
            int transition = state.getTransition(aParsedAlphabet);
            if (transition != -1) {
                transitions += equal + aParsedAlphabet;
                transitions += car + "(S" + transition + cdr;
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
         return fsmFunction + startState + fsmFunction2;
    }


}
