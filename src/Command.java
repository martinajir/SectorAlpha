/**
 * This class is part of the "Sector Alpha" application.   
 *
 * This class holds information about a command that was issued by the user.
 * A command consists of two or three strings: a command word and a second
 * part, which can be either one or two words. The third word only is 
 * used in the "give" command and "go" command when the room has a name 
 * consisting of two words.
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is null.
 *
 * If the command had only one word, then the second word is null.
 * 
 * @author  Michael Kölling, David J. Barnes, Martina Jireckova
 * @version 2017.03.12
 */

public class Command
{
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }
    
    /**
     * Create a command object. First, second and third word must be supplied.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The third word of the command
     */
    public Command(String firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * Return the second word of the command.
     * This can be an item's name in the case of the "give" command or
     * the first part of a room's name in case of the "go" command.
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Return the third word of the command.
     * This can be either a character's name in the case of the 
     * "give" command or a second part of a room's name in case
     * of the "go" command
     * @return the third word as a String
     */
    public String getThirdWord() {
        return thirdWord;
    }
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * @return true if the command has a third word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
    
    
}

