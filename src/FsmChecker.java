import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Chris on 3/5/2015.
 */
public class FsmChecker {

    private static final String transitionsFormat = "\\(\\d:\\d:.\\)";
    private static final int TRANS_LENGTH = 3;


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
        if(numStates <= 0)
        {
            errorList.add("Invalid entry in Number of States: Less than 0");
        }
        return numStates;
    }


    public static int[] checkAcceptStates(String acceptStatesString, ArrayList<String> errorList, int numStates) throws NumberFormatException
    {
        //Split string and get number of accept states
        String[] acceptStatesParsed = acceptStatesString.split(",");
        int numAcceptStates = acceptStatesParsed.length;
        int[] acceptStates = new int[numAcceptStates];

        if(numAcceptStates > numStates)
        {
            errorList.add("Invalid entry in Accept States: Too many accept states");
        }
        else
        {
            for(int i = 0 ; i < numAcceptStates; i++)
            {
                acceptStates[i] = Integer.parseInt(acceptStatesParsed[i]);
            }
        }
        return acceptStates;
    }


    public static String[] checkStateTransitions(String stateTransitionsString, ArrayList<String> errorList, int numStates, String[] alphabet)
    {

        //Split up transitions
        String[] parsedTransitions = null;
        try {
            parsedTransitions = stateTransitionsString.split(",");
        }
        catch (PatternSyntaxException e )
        {
            errorList.add("Invalid entry in State Transitions: Are your entries seperated by commas?");
            return parsedTransitions;
        }

        //compile regex pattern and check each transition syntax
        Pattern transitionsPattern = Pattern.compile(transitionsFormat);
        for(int i = 0; i < parsedTransitions.length; i++) {
            Matcher transitionMatcher = transitionsPattern.matcher(parsedTransitions[i]);
            if (false == transitionMatcher.matches()) {
                errorList.add("Invalid entry in State Transitions: Invalid syntax in transition " + i+1);
                return null;
            }
        }

        //Check each transition for validity
        for(int i = 0; i < parsedTransitions.length; i++) {
            String[] openParen = parsedTransitions[i].split("\\(");
            String[] closeParen = openParen[openParen.length-1].split("\\)");
            String[] result = closeParen[closeParen.length-1].split(":");
            int result0 = Integer.parseInt(result[0]);
            int result1 = Integer.parseInt(result[1]);

            if( result0 > numStates || result1 > numStates //Check if numerical state transitions exist
                    || result0 < 0 || result1 < 0 )
            {
                errorList.add("Invalid entry in State Transitions: State doesn't exist");
                return null;
            }

            //Then check that entry exists in alphabet
            if(Arrays.asList(alphabet).contains(result[2]) == false)
            {
                errorList.add("Invalid entry in State Transitions: Character not in alphabet");
                return null;
            }
        }
        return parsedTransitions;
    }


    public static String[] checkAlphabet(String alphabetString, ArrayList<String> errorList)
    {
        String[] alphabet = null;
        try{
            alphabet = alphabetString.split(",");
        }
        catch (PatternSyntaxException e)
        {
            errorList.add("Invalid entry in Alphabet: Are your entries seperated by commas?");
        }


        return alphabet;
    }


}
