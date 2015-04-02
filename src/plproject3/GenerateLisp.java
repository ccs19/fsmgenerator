package plproject3;

import java.util.ArrayList;

/**
 * Created by Chris on 3/30/2015.
 */
public class GenerateLisp {

    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;




    //Filename
    String fileName = "fsm.lsp";


    //Necessary strings to generate FSM that will be used repeatedly
    String startfunction = "(DEFUN "; /**FunctionName**/
    String startFunctionCheckAccept = "(L)" +
            "\n\t(COND" +
                    "\n\t\t((NULL L) "; /**T or NIL**/
    String checkAtom = "\n\t\t((ATOM L) NIL)";

    String equal = "\n\t\t((EQUAL '";


    String car = " (CAR L))";
    String cdr = " (CDR L)))";
    String end = "\n\t\t(T (NULL))\n\t)\n)";




    //Starting function
    String fsmFunction = "(DEFUN FSM (L)" +
            "\n\t(COND" +
            "\n\t\t" +
            checkAtom +
            "\n\t\t" +
            "((EQUAL T (S";
    String fsmFunction2 = " L)) (PRINC \"This is a valid string!\"" +
            "\n\t\t(T (PRINC \"This is an invalid string!\"))" +
            "\n\t)" +
            "\n)";


    public GenerateLisp(ArrayList<State> stateTable, ArrayList<String> parsedAlphabet, int startState){
        this.parsedAlphabet = parsedAlphabet;
        this.startState = startState;
        this.stateTable = stateTable;
    }

    public void generateLisp(){
        int numStates = stateTable.size();
        String state = generateStartFunction();
        state += "\n\n";

        for(int i = 0; i < numStates; i++){
            state += generateStateFunction(i);
            state += "\n\n";
        }

        System.out.println(state);
    }

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
