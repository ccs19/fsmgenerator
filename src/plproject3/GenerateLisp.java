package plproject3;

import java.util.ArrayList;

/**
 * Created by Chris on 3/30/2015.
 */
public class GenerateLisp {

    private ArrayList<State> stateTable;
    private ArrayList<String> parsedAlphabet;
    private int startState;






    //Necessary strings to generate FSM that will be used repeatedly
    String startfunction = "(DEFUN "; /**FunctionName**/
    String startFunctionCheckAtom = "(L)" +
            "\n\t(COND\n\t" +
            "((ATOM L) NIL)\n" +
            "((NULL L) "; /**T or NIL**/
    String equal = "((EQUAL '";


    String car = "(CAR L))";
    String cdr = "(CDR L)))";
    String end = "\n\t\t(T NIL)\n\t)\n)";



    public GenerateLisp(ArrayList<State> stateTable, ArrayList<String> parsedAlphabet, int startState){
        this.parsedAlphabet = parsedAlphabet;
        this.startState = startState;
        this.stateTable = stateTable;
    }

    public void generateLisp(){
        int numStates = stateTable.size();

        for(int i = 0; i < numStates; i++){

        }
    }



}
