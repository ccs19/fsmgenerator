package plproject3;

import java.util.ArrayList;

/**
 * Created by Chris on 3/30/2015.
 */
public class GenerateLisp {

    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;
    private String lispProgram = "";




    //Filename
    private static final String fileName = "fsm.lsp",


    //Necessary strings to generate FSM that will be used repeatedly
    startfunction = "(DEFUN ", /**FunctionName**/
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
     * @param fsm
     */
    public GenerateLisp(Fsm fsm){
        this.parsedAlphabet = fsm.getParsedAlphabet();
        this.startState = fsm.getStartState();
        this.stateTable = fsm.getStateTable();
    }


    /**Generates a lisp program and puts it in a string
     *
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
     * @param stateNum
     * @return
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

    private String isAcceptState(State s){
        if(s.isAcceptState() == true){
            return "T";
        }
        else return "NIL";
    }

    private String getTransitions(State state){
        String transitions = "";
        for(int i = 0; i < parsedAlphabet.size(); i++){
            int transition = state.getTransition(parsedAlphabet.get(i));
            if(transition != -1){
                transitions += equal + parsedAlphabet.get(i);
                transitions += car + "(S" + transition + cdr;
            }
        }
        if(transitions == "")  transitions += "\n";
        return transitions;
    }

    private String generateStartFunction(){
         return fsmFunction + startState + fsmFunction2;
    }


}
