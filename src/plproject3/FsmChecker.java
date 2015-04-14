package plproject3;

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
    private static final boolean ACCEPT_NFA = true;
    private static boolean nfaFound;

    private static void initNfaVariable(){
        nfaFound = false;
    }

    /**
     * Checks the start state and verifies it exists
     * @param numStates Number of states
     * @param startStateString String representing start state
     * @param errorList List of errors encountered
     * @return An int representing the starting state
     */
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


    /**
     * Checks the number of states and verifies the string isn't empty
     * @param numStatesString String representing number of states
     * @param errorList List of errors encountered
     * @return The number of states
     */
    public static int checkNumStates(String numStatesString, ArrayList<String> errorList)
    {
        initNfaVariable();
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
                unsafeAdd(UnsafeReasons.notNumber, fieldName, errorList);
            }
        }

        return numStates;
    }


    /**
     * Checks that the accept states entered are valid
     * @param acceptStatesString String of the accept states
     * @param errorList List of errors encountered
     * @param numStates Number of states
     * @return int array representing accept states
     */
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

    /**
     * Checks that all state transitions entered are valid
     * @param stateTransitionsString String representing state transitions
     * @param errorList List of errors encountered
     * @param numStates Number of states
     * @param alphabet Alphabet of FSA
     * @return Parsed state transitions in string array
     */
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
                return null;
            }

            //Check that all possible state transitions are accounted for
            if (parsedTransitions.length != (numStates * alphabet.length)) {
                if (parsedTransitions.length < numStates * alphabet.length)
                    unsafeAdd(UnsafeReasons.missingTransitions, fieldName, errorList);
                else
                    unsafeAdd(UnsafeReasons.tooManyTransitions, fieldName, errorList);
            }

            //compile regex pattern and check each transition syntax
            Pattern transitionsPattern = Pattern.compile(transitionsFormat);
            for (String parsedTransition : parsedTransitions) {
                Matcher transitionMatcher = transitionsPattern.matcher(parsedTransition);
                if (!transitionMatcher.matches()) {
                    unsafeAdd(UnsafeReasons.patternSplitFail, fieldName, errorList);
                    return null;
                }
            }

            //Check each transition for validity
            for (String parsedTransition : parsedTransitions) {
                String[] openParen = parsedTransition.split("\\(");
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
                if (!Arrays.asList(alphabet).contains(result[2])) {
                    unsafeAdd(UnsafeReasons.notInAlphabet, fieldName, errorList);
                    return null;
                }
            }
        }
        return parsedTransitions;
    }

    /**
     * Checks that the entered alphabet is valid
     * @param alphabetString String representing alphabet
     * @param errorList List of errors encountered
     * @return Parsed string array of alphabet
     */
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

    /**
     * Adds error message to error ArrayList
     * @param reason Error message to add
     * @param fieldName Text field where error was encountered
     * @param invalidReasons List of errors encountered
     */
    @SuppressWarnings("PointlessBooleanExpression")
    private static void unsafeAdd(UnsafeReasons reason, String fieldName, ArrayList<String> invalidReasons){
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
                if(!ACCEPT_NFA)messageToAdd += "Too many transitions";
                else {
                    setNfaFound(true);
                    return;
                }
                break;
            case missingTransitions:
                if(!ACCEPT_NFA) messageToAdd += "All possible transitions not accounted for";
                else{
                    setNfaFound(true);
                    return;
                }
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

    /**
     * Checks the length of a string isn't zero
     * @param string String to check
     * @return true if string length is zero, false if not zero
     */
    private static boolean isZeroLength(String string){
        return string.length() == 0;
    }

    /**
     * Sets the nfa found variable
     * @param found true or false
     */
    private static void setNfaFound(boolean found){
        nfaFound = found;
    }

    /**
     * If an NFA was found, returns true, else false
     * @return Nfa found
     */
    public static boolean getNfaFound(){
        return nfaFound;
    }

}
