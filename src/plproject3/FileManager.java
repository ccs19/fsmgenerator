package plproject3;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Class to handle opening and writing files.
 * Used in the FsmSolver
 */
public class FileManager{

    private static String previousDir = ".";


    /**
     * Opens a file a returns a bufferedReader to that file
     * @param panel The panel associated with the JFileChooser
     * @return A BufferedReader. If failed, the BufferedReader is null
     */
    public static BufferedReader openFile(JPanel panel){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(previousDir));
        BufferedReader bufferedReader = null;
        int returnVal = jFileChooser.showOpenDialog(panel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(panel, "Failed to load file. Cannot load from disk.", "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                try {
                    previousDir = file.getParent() + "\\"; //Save directory
                }catch(Exception e){
                    previousDir = "."; //If this fails for some reason, default to application dir
                }
            }
        }
        return bufferedReader;
    }


    /**
     * Reads a line from a file
     * @param bufferedReader The buffered reader to read from
     * @return The line read
     * @throws IOException
     * @throws InvalidFsmFormatException
     */
    public static String readNextLine(BufferedReader bufferedReader) throws IOException, InvalidFsmFormatException{
        String s = bufferedReader.readLine();
        if(s == null)
            throw new InvalidFsmFormatException();
        return s;
    }


    /**
     * Saves a file through a JFileChooser
     * @param panel Panel assocaiated with JFileChooser
     * @param data A string of data to save
     * @param extension The file extension
     * @param description Description of the file to save
     */
    public static void saveFile(JPanel panel, String data, String extension, String description){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
        jFileChooser.setFileFilter(filter);
        jFileChooser.setCurrentDirectory(new File(previousDir)); //Set project or previous directory
        int returnVal = jFileChooser.showSaveDialog(panel);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();

            String fileName[] = file.getName().split("/."); //Check for extension

            if(fileName.length == 1){
                file = new File(previousDir, fileName[0]+extension);
            }

            writeFile(panel, data, file);
            try {
                previousDir = file.getParent() + "\\";
            }catch(Exception e){
                previousDir = ".";
            }
        }
    }


    /**
     * Saves a file based on a name
     * @param panel The parent panel if an error message should be shown
     * @param data The data to save
     * @param fileName The file name
     */
    public static void saveFile(JPanel panel, String data, String fileName){
        File file = new File(fileName);
        writeFile(panel, data, file);
    }


    /**
     * Writes data to a file
     * @param panel The panel to show an error message on
     * @param data The data to save
     * @param file The file name
     */
    private static void writeFile(JPanel panel, String data, File file){
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(data);
            fileWriter.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(panel, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
