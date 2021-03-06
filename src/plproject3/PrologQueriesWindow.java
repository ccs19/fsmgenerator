package plproject3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * A window to input up to three strings.
 * These strings are later used and converted into Prolog queries
 */
public class PrologQueriesWindow extends JDialog {

    private final JPanel parent;
    private static final int numTextFields = 3;
    private final ArrayList<JTextField> queryStrings = new ArrayList<JTextField>();

    private static final int TEXT_FIELD_SIZE = 15;
    private static final String windowTitle = "Prolog Queries";


    PrologQueriesWindow(JPanel parent){
        super();
        this.setTitle(windowTitle);
        this.parent = parent;
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(generateWindowListener());

        generateGui();

        this.pack();
        this.setLocationRelativeTo(this.parent);
        this.setVisible(true);
        this.setEnabled(true);
    }

    /**
     * Fills the Dialog with necessary buttons and text boxes
     */
    private void generateGui(){
        GridLayout gridLayout = new GridLayout(4,1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        this.setLayout(gridLayout);


        for(int i = 0; i < numTextFields; i++){
            this.add(generateTextField("Entry "+ (i+1)));
        }
        ArrayList<String> queryStrings = ((FsmSolverPanel) parent).getQueryStrings();
        if(queryStrings != null){
            for(int i = 0; i < numTextFields; i++){
                if(queryStrings.get(i) != null){
                    this.queryStrings.get(i).setText(queryStrings.get(i));
                }
            }
        }

        this.add(generateButtons());
        this.pack();

    }

    /**
     * Generates text fields in a JPanel with a label and editable field
     * @param title Name of text field
     * @return A JPanel with the text field.
     */
    private JPanel generateTextField(String title){
        int size = queryStrings.size();

        GridLayout gridLayout = new GridLayout(1,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        JPanel textPanel = new JPanel(gridLayout);

        textPanel.add(new JLabel(title));
        queryStrings.add(new JTextField(TEXT_FIELD_SIZE));
        textPanel.add(queryStrings.get(size));

        return textPanel;
    }

    /**
     * Generates the buttons needed. Clear, okay, cancel
     * @return A Jpanel with the buttons inside it.
     */
    private JPanel generateButtons(){
        GridLayout gridLayout = new GridLayout(1,4);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);
        JPanel buttonPanel = new JPanel(gridLayout);

        /**Clear button**/
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JTextField jTextField : PrologQueriesWindow.this.queryStrings){
                    jTextField.setText("");
                }
            }
        });
        buttonPanel.add(clearButton);

        /**Okay button**/
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setParentStrings(parent);
                enableMainWindow(true);
                ((FsmSolverPanel) parent).unregisterWindowListener();
                PrologQueriesWindow.this.dispose();
            }
        });
        buttonPanel.add(okayButton);

        /**Cancel button**/
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((FsmSolverPanel) parent).setQueryStrings(null);
                enableMainWindow(true);
                ((FsmSolverPanel) parent).unregisterWindowListener();
                PrologQueriesWindow.this.dispose();
            }
        });
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    /**
     * Extracts strings from text boxes and sends them to the main JPanel
     * If no data is present, null is sent.
     * @param parent parent
     **/
    private void setParentStrings(JPanel parent) {
        ArrayList<String> enteredStrings = new ArrayList<String>(numTextFields);

            for(int i = 0; i < numTextFields; i++){
                String text = queryStrings.get(i).getText();
                if(text.length() > 0)
                    enteredStrings.add(i, queryStrings.get(i).getText());
                else
                    enteredStrings.add(i, null);
            }
        ((FsmSolverPanel) parent).setQueryStrings(enteredStrings);
    }

    /**
     * Generates a window listener that ensures data in text boxes that were present
     * from previous user entry are not lost if the user closes the window with the default
     * 'close X'
     * @return A window listener
     */
    private WindowListener generateWindowListener(){

        return (new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                enableMainWindow(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                enableMainWindow(true);
                ((FsmSolverPanel) parent).setQueryStrings(null);
                ((FsmSolverPanel) parent).unregisterWindowListener();
                PrologQueriesWindow.this.setVisible(false);
                PrologQueriesWindow.this.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    /**
     * Enables or disables the main JFrame
     * @param isEnabled true or false
     */
    private void enableMainWindow(boolean isEnabled){
        JFrame mainFrame = (JFrame)SwingUtilities.windowForComponent(parent);
        mainFrame.setEnabled(isEnabled);
    }
}
