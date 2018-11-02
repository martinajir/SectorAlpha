import java.util.Scanner;

/**
 * This class is part of the "Sector Alpha" application. 
 * 
 * This parser reads user input and tries to interpret it as a command. 
 * Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two or three word command. 
 * It returns the command as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kölling, David J. Barnes, Martina Jireckova
 * @version 2017.12.03
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
            }
            if(tokenizer.hasNext()) {
                word3 = tokenizer.next();      // get third word
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            if (word3 != null)
                return new Command(word1, word2, word3);
            else
                return new Command(word1, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }

    /**
     * Get a String input from the user.
     * @return all of user's input as a String
     */
    public String getInput()
    {
        System.out.print("> ");
        Scanner readerLocal = new Scanner(System.in);
        String input = readerLocal.nextLine();
        return input;
    }
    
    /**
     * Get an integer input from the user.
     * @return user's input as an int
     */
    public int getIntInput()
    {
        System.out.print("> ");
        Scanner readerLocal = new Scanner(System.in);
        int input = readerLocal.nextInt();
        return input;
    }
    
    /**
     * Return a String of all valid commands
     * @return long String containing all available commands
     */
    public String getCommands()
    {
        return(commands.getAll());
    }
}
