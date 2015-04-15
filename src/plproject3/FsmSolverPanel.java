package plproject3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class FsmSolverPanel extends JPanel {

    private static final String loadString = "Load";
    private static final String exitString = "Exit";
    private static final String solveString = "Load String";
    private static final String stringEntryString = "Word";
    private static final String generateLispQuickString = "Generate Lisp Quick";
    private static final String generateLispString = "Generate Lisp";
    private static final String genereatePrologString = "Generate Prolog";
    private static final String generatePrologQuickString = "Generate Prolog Quick";

    //Menu data
    private static final String loadFsaMenuLabel = "Load FSA";
    private static final String prologQueriesMenuLabel = "Prolog Queries";
    private static final String setQueriesMenuLabel = "Set Queries";

    //GridBagConstraints padding
    private static final Insets labelInset = new Insets(10,10,0,10);
    private static final Insets buttonInset = new Insets(0,10,10,10);

    //Buttons
    private final JButton solveStringButton;

    //Text field
    private final JTextField wordEntry;

    //Load listener
    private LoadButtonListener loadButtonListener = null;
    private LoadStringButtonListener loadStringButtonListener = null;

    //Lisp items
    private final JButton generateLispButton;
    private final JButton quickGenerateLispButton;

    //Prolog items
    private final JButton generatePrologButton;
    private final JButton quickGeneratePrologButton;


    //Constants
    private static final int JTF_STRINGENTRYLEN = 15;


    FsmSolverPanel()
    {
        super(new GridBagLayout());
        GridBagConstraints gbc;

        /**Text Entry**/
        JLabel stringEntryLabel = new JLabel(stringEntryString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = labelInset;
        this.add(stringEntryLabel, gbc);

        /**Word Loaded static text box**/
        wordEntry = new JTextField(JTF_STRINGENTRYLEN);
        wordEntry.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = buttonInset;
        this.add(wordEntry, gbc);

        /**Generate Lisp**/
        generateLispButton = new JButton(generateLispString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        generateLispButton.setEnabled(false);
        generateLispButton.addActionListener(new GenerateLispButtonListener(this));
        this.add(generateLispButton, gbc);

        /**Generate Lisp Quick**/
        quickGenerateLispButton = new JButton(generateLispQuickString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        quickGenerateLispButton.addActionListener(new GenerateLispButtonListener(this));
        quickGenerateLispButton.setEnabled(false);
        this.add(quickGenerateLispButton, gbc);

        /**Load string button**/
        solveStringButton = new JButton(solveString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        loadStringButtonListener = new LoadStringButtonListener(this);
        solveStringButton.addActionListener(loadStringButtonListener);
        solveStringButton.setEnabled(false);// Disabled to start
        this.add(solveStringButton, gbc);

        /**Generate Prolog buttons**/
        generatePrologButton = new JButton(genereatePrologString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        generatePrologButton.setEnabled(false);
        generatePrologButton.addActionListener(new GeneratePrologButtonListener(this));
        this.add(generatePrologButton, gbc);

        /**Quick generate Prolog**/
        quickGeneratePrologButton = new JButton(generatePrologQuickString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        quickGeneratePrologButton.setEnabled(false);
        quickGeneratePrologButton.addActionListener(new GeneratePrologButtonListener(this));
        this.add(quickGeneratePrologButton, gbc);

        this.setVisible(true);
    }


    /**
     * Enable or disable solve button
     * @param option Enable or disable solve button
     */
    public void enableButtons(boolean option){

        /**Solve button**/
        loadStringButtonListener.setFsmData(loadButtonListener.getFsmData());
        loadStringButtonListener.setWord(this.getWordEntryString());

        /**Enable buttons**/
        solveStringButton.setEnabled(option);
        generateLispButton.setEnabled(option);
        quickGenerateLispButton.setEnabled(option);
        generatePrologButton.setEnabled(option);
        quickGeneratePrologButton.setEnabled(option);

        /**Set necessary data to generate code**/
        //Set generateLispButton data
        ActionListener listeners[] = generateLispButton.getActionListeners();
        GenerateLispButtonListener generateLispButtonListener = (GenerateLispButtonListener)listeners[0];
        generateLispButtonListener.setFsmData(loadButtonListener.getFsmData());
        generateLispButtonListener.setQuickSave(false);

        //Set generateLispButton quicksave
        listeners = quickGenerateLispButton.getActionListeners();
        generateLispButtonListener = (GenerateLispButtonListener)listeners[0];
        generateLispButtonListener.setFsmData(loadButtonListener.getFsmData());
        generateLispButtonListener.setQuickSave(true);

        //Set generatePrologButton
        listeners = generatePrologButton.getActionListeners();
        GeneratePrologButtonListener generatePrologButtonListener = (GeneratePrologButtonListener)listeners[0];
        generatePrologButtonListener.setFsmData(loadButtonListener.getFsmData());
        generatePrologButtonListener.setQuickSave(false);

        //Set generatePrologButton quicksave
        listeners = quickGeneratePrologButton.getActionListeners();
        generatePrologButtonListener = (GeneratePrologButtonListener)listeners[0];
        generatePrologButtonListener.setFsmData(loadButtonListener.getFsmData());
        generatePrologButtonListener.setQuickSave(true);

    }

    /**
     * Returns user entry in text field
     * @return User entry
     */
    public String getWordEntryString(){ return wordEntry.getText();}

    /**
     * Sets the static text box
     * @param entry The string to put in the text box
     */
    public void setWordEntryString(String entry){wordEntry.setText(entry);}

    /**
     * Creates Menu
     * @return JMenuBar
     */
    public JMenuBar generateMenu(){
        /**Create menubar**/
        JMenuBar menuBar = new JMenuBar();

        /**Add file**/
        JMenu menu = new JMenu(loadFsaMenuLabel);
        menu.getAccessibleContext().setAccessibleDescription(loadFsaMenuLabel);
        menuBar.add(menu);

        /**Load FSA item**/
        loadButtonListener = new LoadButtonListener(this);
        JMenuItem menuItem = new JMenuItem(loadString);
        menuItem.addMouseListener(loadButtonListener);
        menu.add(menuItem);

        /**Exit item**/
        menuItem = new JMenuItem(exitString);
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent m) {
                System.exit(0);
            }
        });
        menu.add(menuItem);

        /**Add query menu option for Prolog**/
        menu = new JMenu(prologQueriesMenuLabel);
        menu.getAccessibleContext().setAccessibleDescription(prologQueriesMenuLabel);
        menuBar.add(menu);

        /**Generate queries item**/
        menuItem = new JMenuItem(setQueriesMenuLabel);
        menuItem.addMouseListener(new MouseAdapter() {
                                      @Override
                                      public void mousePressed(MouseEvent e) {
                                          super.mousePressed(e);
                                          PrologQueriesWindow prologQueriesWindow = new PrologQueriesWindow(FsmSolverPanel.this);
                                          setPrologQueriesWindowFocus(prologQueriesWindow);
                                      }
                                  });
                menu.add(menuItem);

        return menuBar;
    }




    //Prolog query strings
    private ArrayList<String> queryStrings = null;

    /**
     * Set the prolog strings to be used
     * @param queryStrings Array of strings. If no entry was made, entries may be null, or
     *                     the entire object could be null.
     */
    public void setQueryStrings(ArrayList<String> queryStrings){
        if(this.queryStrings != null && queryStrings == null){
            //don't overwrite if data already added
        }
        else{
            this.queryStrings = queryStrings;
        }
    }


    /**
     * Get the prolog query strings
     * @return Array of strings or null if nothing entered.
     */
    public ArrayList<String> getQueryStrings(){
        return this.queryStrings;
    }

    private void setPrologQueriesWindowFocus(final PrologQueriesWindow prologQueriesWindow){
        JFrame mainFrame = (JFrame)SwingUtilities.windowForComponent(this);
        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

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
                prologQueriesWindow.setAlwaysOnTop(true);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                prologQueriesWindow.setAlwaysOnTop(false);
            }
        });

    }
}
