package plproject3;

/**
 * Created by chris_000 on 4/5/2015.
 */
public class InvalidFsmFormatException extends Exception {

    /**
     * If invalid file format found, throw this exception
     */
        public InvalidFsmFormatException(){
            super("Invalid FSM File Format");
        }
}
