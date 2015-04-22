package plproject3;

import javax.swing.*;
import java.awt.*;

/**
 * A basic window where the user can input Strings for Prolog queries
 */
public class PrologQueriesWindow extends JDialog {

    private final JPanel parent;
    private final JTextField queryStrings[] = new JTextField[3];

    private static final String windowTitle = "Prolog Queries";

    PrologQueriesWindow(JPanel parent){
        super();
        this.setTitle(windowTitle);
        this.parent = parent;
        this.setEnabled(true);

        generateGui();
    }

    private void generateGui(){
        this.setLayout(new GridLayout(4,0));


    }


}
