import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Chris on 3/5/2015.
 *
 *
 *
 */




/** Required order to check automata
 *  numstates
 *  alphabet
 *  acceptstates
 *  statetransitions
 *  startstate
 **/


public class FsmChecker {

    private static final String transitionsFormat = "\\(\\d:\\d:.\\)";


    public static int checkStartState(int numStates, String startStateString, ArrayList<String> errorList)
    {
        int startState = 0;
        final String fieldName = "Start State";

        if(isZeroLength(startStateString)) //Check that entry exists
            unsafeAdd(UnsafeReasons.emptyString, fieldName, errorList);

        else {
            try { //Try converting to int
                startState = Integer.parseInt(startStateString);
                if (startState < 0) {
                    unsafeAdd(UnsafeReasons.lessThanZero, fieldName, errorList);
                } else if (startState > numStates - 1) {
                    unsafeAdd(UnsafeReasons.stateDoesNotExist, fieldName, errorList);
                }
            }
            catch (NumberFormatException e){
                unsafeAdd(UnsafeReasons.notNumber, fieldName, errorList);
            }
        }
        return startState;
    }


    public static int checkNumStates(String numStatesString, ArrayList<String> errorList)
    {
        int numStates = 0;
        final String fieldName = "Number of States";

        if(isZeroLength(numStatesString)) //Check that entry exists
            unsafeAdd(UnsafeReasons.emptyString, fieldName, errorList);

        else{
            try
            {
                numStates = Integer.parseInt(numStatesString);
                if (numStates <= 0)
                    unsafeAdd(UnsafeReasons.lessThanZero, fieldName, errorList);
            }catch(NumberFormatException e){

            }
        }

        return numStates;
    }


    public static int[] checkAcceptStates(String acceptStatesString, ArrayList<String> errorList, int numStates)
    {
        int[] acceptStates = null;
        final String fieldName = "Accept States";

        if(isZeroLength(acceptStatesString)) //Check that entry exists
            unsafeAdd(UnsafeReasons.emptyString, fieldName, errorList);

        else {
                //Split string and get number of accept states
                String[] acceptStatesParsed = acceptStatesString.split(",");
                int numAcceptStates = acceptStatesParsed.length;
                acceptStates = new int[numAcceptStates];

                if (numAcceptStates > numStates) {
                    unsafeAdd(UnsafeReasons.tooManyStates, fieldName, errorList);
                    return null;
                }
                else
                {
                    for (int i = 0; i < numAcceptStates; i++) {
                        try { //Try to convert to int
                            acceptStates[i] = Integer.parseInt(acceptStatesParsed[i]);
                            if (acceptStates[i] > numStates - 1) //Make sure state exists
                            {
                                unsafeAdd(UnsafeReasons.stateDoesNotExist, fieldName, errorList);
                                errorList.get(errorList.size() - 1).concat(" -> " + acceptStates[i]); //Give user info on invalid state
                                return null;
                            }
                        } catch(NumberFormatException e){
                            unsafeAdd(UnsafeReasons.notNumber, fieldName, errorList);
                            return null;
                        }
                    }
                }

            }
        return acceptStates;
    }


    public static String[] checkStateTransitions(String stateTransitionsString, ArrayList<String> errorList, int numStates, String[] alphabet)
    {
        final String fieldName = "State Transitions";
        String[] parsedTransitions = null;

        if(isZeroLength(stateTransitionsString))//Check that entry exists
            unsafeAdd(UnsafeReasons.emptyString, fieldName, errorList);

        else {
            //Split up transitions
            try {
                parsedTransitions = stateTransitionsString.split(",");
            } catch (PatternSyntaxException e) {
                unsafeAdd(UnsafeReasons.patternSplitFail, fieldName, errorList);
                return parsedTransitions;
            }

            //Check that all possible state transitions are accounted for
            if (parsedTransitions.length != (numStates * alphabet.length)) {
                if (parsedTransitions.length < numStates * alphabet.length)
                    unsafeAdd(UnsafeReasons.missingTransitions, fieldName, errorList);
                else
                    unsafeAdd(UnsafeReasons.tooManyTransitions, fieldName, errorList);
                return null;
            }

            //compile regex pattern and check each transition syntax
            Pattern transitionsPattern = Pattern.compile(transitionsFormat);
            for (int i = 0; i < parsedTransitions.length; i++) {
                Matcher transitionMatcher = transitionsPattern.matcher(parsedTransitions[i]);
                if (false == transitionMatcher.matches()) {
                    unsafeAdd(UnsafeReasons.patternSplitFail, fieldName, errorList);
                    return null;
                }
            }

            //Check each transition for validity
            for (int i = 0; i < parsedTransitions.length; i++) {
                String[] openParen = parsedTransitions[i].split("\\(");
                String[] closeParen = openParen[openParen.length - 1].split("\\)");
                String[] result = closeParen[closeParen.length - 1].split(":");
                int result0 = Integer.parseInt(result[0]);
                int result1 = Integer.parseInt(result[1]);

                if (result0 > numStates - 1 || result1 > numStates - 1//Check if numerical state transitions exist
                        || result0 < 0 || result1 < 0) {
                    unsafeAdd(UnsafeReasons.stateDoesNotExist, fieldName, errorList);
                    return null;
                }

                //Then check that entry exists in alphabet
                if (Arrays.asList(alphabet).contains(result[2]) == false) {
                    unsafeAdd(UnsafeReasons.notInAlphabet, fieldName, errorList);
                    return null;
                }
            }
        }
        return parsedTransitions;
    }


    public static String[] checkAlphabet(String alphabetString, ArrayList<String> errorList)
    {
        String[] alphabet = null;
        final String fieldName = "Alphabet";

        if(isZeroLength(alphabetString)) //Check that entry exists
            unsafeAdd(UnsafeReasons.emptyString, fieldName, errorList);
        else {
            try {
                alphabet = alphabetString.split(",");
            } catch (PatternSyntaxException e) {
                unsafeAdd(UnsafeReasons.patternSplitFail, fieldName, errorList);
            }
        }
        return alphabet;
    }


    //Safe verification parameters
    enum UnsafeReasons{
        notNumber,
        emptyString,
        patternSplitFail,
        stateDoesNotExist,
        lessThanZero,
        tooManyStates,
        tooManyTransitions,
        missingTransitions,
        notInAlphabet

    }
    public static void unsafeAdd(UnsafeReasons reason, String fieldName, ArrayList<String> invalidReasons){
        String messageToAdd = "Invalid entry in " + fieldName +": ";
        switch (reason) {
            case notNumber:
                messageToAdd += "Not a number";
                break;
            case emptyString:
                messageToAdd += "No entry";
                break;
            case patternSplitFail:
                messageToAdd += "Invalid syntax";
                break;
            case stateDoesNotExist:
                messageToAdd += "State does not exist";
                break;
            case lessThanZero:
                messageToAdd += "Less than zero";
                break;
            case tooManyStates:
                messageToAdd += "Too many states";
                break;
            case tooManyTransitions:
                messageToAdd += "Too many transitions";
                break;
            case missingTransitions:
                messageToAdd += "All possible transitions not accounted for";
                break;
            case notInAlphabet:
                messageToAdd += "Not in alphabet";
                break;
            default:
                messageToAdd += "Unknown error";
                break;
        }
        invalidReasons.add(messageToAdd);
    }

    private static boolean isZeroLength(String string){
        if(string.length() == 0)
            return true;
        else return false;
    }

}
