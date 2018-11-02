/**
 * This class is part of the "Sector Alpha" application.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling, David J. Barnes, Martina Jireckova
 * @version 2017.03.12
 */

public class CommandWords {
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "take", "read", "drop", "items", "talk", "give", 
        "save", "open", "back"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word.
     * @param aString
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        for (String validCommand : validCommands) {
            if (validCommand.equals(aString)) {
                return true;
            }
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Return a list of all commands as a long String
     * @return a String containing all valid commands
     */
    public String getAll() {
        String returnString = "";
        for(String command: validCommands) {
            returnString += command + " ";
        }
        return returnString;
    }
}
