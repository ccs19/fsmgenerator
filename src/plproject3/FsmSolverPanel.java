package plproject3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private final String menuLabelFile = "Load FSA";
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    //GridBagConstraints padding
    private static final Insets labelInset = new Insets(10,10,0,10);
    private static final Insets buttonInset = new Insets(0,10,10,10);

    //Buttons
    private JButton solveStringButton;

    //Text field
    private JTextField wordEntry;

    //Load listener
    private LoadButtonListener loadButtonListener = null;
    private LoadStringButtonListener loadStringButtonListener = null;

    //Lisp items
    private GenerateLispButtonListener lispButtonListener = null;
    private JButton generateLispButton;
    private JButton quickGenerateLispButton;

    //Prolog items
    //private GeneratePrologButtonListener generatePrologButtonListener = null;
    private JButton generatePrologButton;
    private JButton quickGeneratePrologButton;


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
        this.add(generatePrologButton, gbc);

        /**Quick generate Prolog**/
        //Unused until next project
        quickGeneratePrologButton = new JButton(generatePrologQuickString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = buttonInset;
        quickGeneratePrologButton.setEnabled(false);
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
        solveStringButton.setEnabled(option);
        generateLispButton.setEnabled(option);
        quickGenerateLispButton.setEnabled(option);

        /**Set necessary data to generate code**/
        ActionListener listeners[] = generateLispButton.getActionListeners();
        GenerateLispButtonListener generateLispButtonListener = (GenerateLispButtonListener)listeners[0];
        generateLispButtonListener.setFsmData(loadButtonListener.getFsmData());
        generateLispButtonListener.setQuickSave(false);

        listeners = quickGenerateLispButton.getActionListeners();
        generateLispButtonListener = (GenerateLispButtonListener)listeners[0];
        generateLispButtonListener.setFsmData(loadButtonListener.getFsmData());
        generateLispButtonListener.setQuickSave(true);

    }

    /**
     * Returns user entry in text field
     * @return User entry
     */
    public String getWordEntryString(){ return wordEntry.getText();}

    public void setWordEntryString(String entry){wordEntry.setText(entry);}

    public JMenuBar generateMenu(){
        /**Create menubar**/
        menuBar = new JMenuBar();

        /**Add file**/
        menu = new JMenu(menuLabelFile);
        menu.getAccessibleContext().setAccessibleDescription(menuLabelFile);
        menuBar.add(menu);

        /**Load FSA item**/
        loadButtonListener = new LoadButtonListener(this);
        menuItem = new JMenuItem(loadString);
        menuItem.addMouseListener(loadButtonListener);
        menu.add(menuItem);

        /**Exit item**/
        menuItem = new JMenuItem(exitString);
        menuItem.addMouseListener(new MouseAdapter(){
           @Override
            public void mousePressed(MouseEvent m){
               System.exit(0);
           }
        });
        menu.add(menuItem);

        return menuBar;
    }
}
