import java.util.ArrayList;

/**
 * Created by Chris on 3/5/2015.
 */
public class FsmChecker {


    public static int checkStartState(int numStates, String startStateString, ArrayList<String> errorList) throws NumberFormatException
    {
        int startState = Integer.parseInt(startStateString);
        if(startState < 0)
        {
            errorList.add("Invalid entry in Start State: Less than 0");
        }
        else if(startState > numStates)
        {
            errorList.add("Invalid entry in Start State: Exceeds number of states");
        }
        return startState;
    }


    public static int checkNumStates(String numStatesString, ArrayList<String> errorList) throws NumberFormatException
    {
        int numStates = Integer.parseInt(numStatesString);
        if(numStates < 0)
        {
            errorList.add("Invalid entry in Number of States: Less than 0");
        }
        return numStates;
    }




}