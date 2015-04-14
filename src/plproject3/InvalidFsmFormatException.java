package plproject3;

/**
 * Exception to be thrown if invalid format is found.
 */
public class InvalidFsmFormatException extends Exception {

    /**
     * If invalid file format found, throw this exception
     */
        public InvalidFsmFormatException(){
            super("Invalid FSM File Format");
        }
}
