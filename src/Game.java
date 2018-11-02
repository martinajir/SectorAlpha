import org.apache.commons.text.*;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

/**
 *  This class is the main class of the "Sector Alpha" application. 
 *  "Sector Alpha" is a sci-fi game based on an abandoned spaceship.
 *  Players start in the docks of the spaceship where they were summoned 
 *  after the spaceship sent a distress signal. 
 *  
 *  The players walk around, interact with objects and NPCs to find out 
 *  what happened on the ship. The goal is to save as many people from the ship 
 *  as the player can. 
 *  All NPCs require some objects to agree to leave the ship - to become saveable. 
 *  Some NPCs are however dangerous and if you attempt to save them, you will die. 
 *  Clues are given throughout the map to help the player determine who to save.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method. This is currently done via the MainClass in order to make the
 *  game runnable.
 * 
 *  This class holds the main play routine, which consists of initializing the 
 *  map, creating a character and interpreting commands until either the player
 *  loses by attempting to save the wrong NPC, wins the game by saving all 
 *  the right NPCs or supplies the "quit" command.
 * 
 * @author  Michael Kölling, David J. Barnes, Martina Jireckova
 * @version 2017.03.12
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Person player;
    private GameMap map;
    private ArrayList<String> savedPeople; //NPCs the player saved so far
        
    /**
     * Create the game, initialise its internal map.
     * The starting room is currently set as the "docks".
     * At the start of the game, previous room and current room
     * are the same.
     */
    public Game() {
        map = new GameMap();
        parser = new Parser();
        currentRoom = map.getStartingRoom();
        previousRoom = currentRoom;
        savedPeople = new ArrayList<>();
        
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {            
        printWelcome();
        createPlayer();
        
        System.out.println(currentRoom.getLongDescription());
        printWrapString("Type 'help' if you need help.");

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //can only get here if "quit" command is supplied
        endGameBad();
        
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        String storyString1 = "The year is 2055. People of Earth have begun "
                + "exploring deep space looking for new habitable "
                + "planets that might help solve the overpopulation "
                + "and lack of natural resources. One of the first ships "
                + "to make the dangerous and long journey to the newly "
                + "discovered nearby star system Centaurus was named Talon. "
                + "Talon successfully launched on October 1st and the journey "
                + "seemed to go well. "
                + " The ship however sent a distress call soon after it entered "
                + "Centaurus and lost all connection to Earth soon after.";
        
        String storyString2 = "You were sent on a mission to investigate "
                + "what happened to the ship. "
                + "As a member of the newly created International Agency for "
                + "Space Exploration (IASE) you helped to discover many planets "
                + "and were one of the first people to fly into unknown space. "
                + " You were supposed retire. But you couldn't just stand idly "
                + "by when your friends' lives were at stake...";
                             
        String storyString3 = "You set out and everything seemed fine. "
                + "Your crew contained only the best Earth had to offer. There "
                + "was nothing to worry about...right? "
                + " Wrong. As soon as you entered the sector of space where "
                + "Talon was spotted last, you saw a flash and blacked out.";
                             
        
        System.out.println("Sector Alpha");
        System.out.println();
        printWrapString(storyString1);
        System.out.println();
        printWrapString(storyString2);
        System.out.println();
        printWrapString(storyString3);
    }

    /**
     * Initiates the player creation sequence.
     * In this sequence, the player supplies their name
     * and how much they can carry
     */
    private void createPlayer() {
        printWrapString("You finally woke up in a dark room. Are you okay? "
                + "Quick, do you still remember your name? It's....");
        String input = parser.getInput();
        String name = input;
        
        printWrapString("Correct, your name is " + name + "! Now let's assess "
                + "what items you have. ");
        printWrapString("You seem to have a fairly small bag, how much do you "
                + "think you can carry in kilograms?");
        
        int carryWeight = 0;
        boolean rightInput = false;
        //loops until the player supplies a valid input
        while(!rightInput){
            try{
                carryWeight = parser.getIntInput();
                rightInput = true;
                
                //if player supplies negative weight, quit the game
                if (carryWeight < 0){
                    printWrapString("You created an object of negative mass " 
                            + carryWeight + ". This created a wormhole that "
                            + "sucked you in and you disappeared! If only "
                            + "you studied theoretical physics this wouldn't "
                            + "have happened!");
                    System.exit(0);
                }
                //if player supplies unrealistic carry weight, stop him 
                if (carryWeight > 15){
                    printWrapString("Woah there, superman. I said you have a "
                            + "small bag! Try again.");
                    rightInput = false;
                }
                
                if (carryWeight == 0){
                    printWrapString("You will need to carry some items! A carry "
                            + "weight of 3 kg will be used.");
                    carryWeight = 3;
                }
            }
            //if player doesn't supply an integer
            catch(InputMismatchException e){
                System.out.println("You didn't input a valid number! Try again.");
            }
        }
        
        player = new Person(name,carryWeight);
        System.out.println("Okay okay, now let's get to it!\n");
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        
        switch (commandWord){
            case "help": 
                printHelp();
                break;
            case "go":  
                goRoom(command);
                break;
            case "quit": 
                wantToQuit = quit(command);
                break;
            case "take":
               takeItem(command);
                break;
            case "drop":
                dropItem(command);
                break;
            case "items":
                showItems();
                break;
            case "talk":
                talk(command);
                break;
            case "give":
                give(command);
                break;
            case "save":
                save(command);
                break;
            case "open":
                open(command);
                break;
            case "read":
                read(command);
                break;
            case "back":
                back();
                break;
                
        }
        return wantToQuit;
    }

    // implementations of user commands:
    
    /**
     * Print out some help information
     * Syntax: "help".
     * Here the player is reminded of what the goal of the game is
     * and a review of the command words is given.
     */
    private void printHelp() {
        String story = "You are on a spaceship that sent a distress signal and "
                + "then suddenly stopped responding."
                +" Try to figure out what went wrong "
                + "and save as many people from the crew as you can. Remember, "
                + "people can only be saved after you give them the item they "
                + "need. It would be best not to attempt to save some of them, "
                + "though! Look for clues!";
        printWrapString(story);
        printHorizontalLine();
        System.out.println("Your command words are:");
        printWrapString(parser.getCommands());
        System.out.println();
        printWrapString("go: syntax = go roomName, lets you go through "
                + "any exit. Some rooms have two-word names!");
        printHorizontalLine();
        printWrapString("quit: quits the game");
        printHorizontalLine();
        printWrapString("help: prints this help");
        printHorizontalLine();
        printWrapString("take: syntax = take itemName, lets you take an item "
                + "from the room you are currently in. Some items can be taken"
                + ", others cannot.");
        printHorizontalLine();
        printWrapString("read: syntax = read itemName, lets you read an item "
                + "that is in your inventory. If the item is in a room, you "
                + "have to take the item to your inventory first. Some items "
                + "are readable, others are not.");
        printHorizontalLine();
        printWrapString("drop: syntax = drop itemName, lets you drop an item "
                + "from your inventory. The item will remain in the room "
                + "where you dropped it.");
        printHorizontalLine();
        printWrapString("items: syntax = items, shows your current inventory");
        printHorizontalLine();
        printWrapString("talk: syntax = talk personName, initiates a conversation"
                + " with the character. In a conversation, the NPC may give tips"
                + " on what item they need.");
        printHorizontalLine();
        printWrapString("give: syntax = give itemName personName, gives an item to a "
                + "character. The item has to be in your inventory and the "
                + "person has to be in the same room. Once you give an item to "
                + "a person, you cannot take it back. If you give a character "
                + "the item they need, they become savable and their dialog "
                + "changes.");
        printHorizontalLine();
        printWrapString("save: syntax = save personName, lets you save the "
                + "person once you gave them the item they needed. You win "
                + "the game by saving as many of the innocent crew as you can");
        printHorizontalLine();
        printWrapString("open: syntax = open itemName, lets you open a container."
                + " Some items in the room are containers and others aren't. "
                + "To open an item, the item has to be in the room, not in your"
                + " inventory.");
        printHorizontalLine();
        printWrapString("back: syntax = back, this command takes you back to "
                + "the last room you were in.");
        
        System.out.println(currentRoom.getLongDescription());
    }

     /** 
     * Try to in to one direction
     * Syntax: "go exitName". 
     * If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction;
        
        //some rooms have two-word names
        if (command.hasThirdWord())
            direction = command.getSecondWord() + " " + command.getThirdWord();
        else
            direction = command.getSecondWord();

        //teleporter room teleports you to a random location
        //not a room per se
        if (direction.equals("teleporter room")){
            teleport();
            return;
        }
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        //go to the room given and shuffle characters around the map
        else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            map.shuffleCharacters();
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Take the player to the previous room
     * Syntax: "back".
     * This method takes the player to a room he was in previously.
     * If the player was teleported, this method takes him to the room he
     * was in before he entered the teleporter room.
     */
    private void back() {
        goRoom(previousRoom);
    }
    
    /**
     * Read a readable item from the player inventory
     * Syntax: "read itemName".
     * This method first identifies the second word of the 
     * command as the item name and then tries to find the item
     * in player inventory. If the item is found and is readable, 
     * the content of the readable item is displayed. If not, a warning message
     * is shown.
     * @param command second word of the command is treated as the name 
     * of the item
     */
    private void read(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Read what?");
            return;
        }
        String itemName = command.getSecondWord();
        boolean found = false;
        
        //iterate through player inventory and try to find the item
        //by comparing the itemName string and item.getName()
        for (Item item : player.getItems()){
             if (item.getName().equals(itemName)){
                 found = true;
                 //check if the item is readable
                 if (item instanceof Readable) { //can be safely downcast
                    Readable readable = (Readable) item;
                    printWrapString(readable.getContent());    
                 }
                 else
                    System.out.println("This item isn't readable");
            } 
        }
        //if unable to find item in inventory
        if (!found){
            printWrapString("You don't have such item!");
            printWrapString("Hint: the item has to be in your inventory first!");
        }
    }
    
    /**
     * Open a container in the room
     * Syntax: "open container".
     * First, this method identifies the second word of the command as the
     * name of the container to be opened. If such an item is in the room
     * and is an openable container, all items that were stored inside are
     * put in the room freely.
     * @param command the second word identifies the container to be opened
     */
    private void open(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Open what?");
            return; 
        }
        String itemName = command.getSecondWord();
        
        //create a list of Items to be added to the room
        //to avoid concurrent modification exception
        ArrayList<Item> addList = new ArrayList<>();
       
        boolean found = false;
        
        //iterate through the items in the current room
        //comparing item.getName() and itemName
        for (Item item : currentRoom.getItems()) {
            if (item.getName().equals(itemName)){
                 found = true;
                 //check whether the item is a Container
                if(item instanceof Container) {
                    //can be safely downcast
                    Container container = (Container) item; 
                    ArrayList<Item> storedItems = container.getStoredItems();
                   
                    //print out a list of Items in the container
                    String printString = "";
                    if (!storedItems.isEmpty()){
                        for (Item storedItem : storedItems) {
                            printString += storedItem.getName() + " ";
                        }
                    }
                    else
                        printString = "nothing";
                    
                    printWrapString("Opened " + container.getName() + " and "
                            + "found " + printString + ". The items are now "
                            + "lying around on the floor.");
                    
                    addList = storedItems; //add all items from container to addlist
                    container.resetStoredItems();//empty container
                }
                else
                    System.out.println("This item can't be opened");
             }
        }
        //if Item wasn't found in the room
        if(!found) {
            printWrapString("Such item isn't in this room");
            printWrapString("Hint: the item has to be in the room, not in your "
                    + "inventory! If you want to open an item from your "
                    + "inventory, drop it first.");  
        }
        //add all items from container to room
        currentRoom.putAllItems(addList);
    }
    
    /**
     * Save a person
     * Syntax: "save personName".
     * A person can only be saved once the player gave them the item they
     * needed. Once a person is saved, they are deleted from the map.
     * @param command second word = name of the person
     */
    private void save(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Save who?");
            return;
        }
        String characterName = command.getSecondWord();
        //to avoid modification exception, assign later
        Character characterToSave = null; 
        boolean found = false;
        
        //try to find the character in the current room
        for (Character character : currentRoom.getPeople()) {
            if(character.getName().equals(characterName)) {
                found = true;
                //save if character already got their needed item
                if (character.getFinalCondition()){
                    characterToSave = character;
                }
                else {
                   System.out.println("This character can't be saved yet!");
                }
            }
        }
        //character is not in this room
        if (!found) {
            System.out.println("That character isn't here");
            return;
        }
        //save character
        savedPeople.add(characterToSave.getName());
        printWrapString("You saved " + characterToSave.getName() + "!");
        currentRoom.removeCharacter(characterToSave);
        
        //if you saved everyone except for the one character that would kill you
        if (savedPeople.size() == map.getPeople().size()-1)
            endGameGood(); //you win
        }
    
    /**
     * Give an item that a player has to an NPC in the game
     * Syntax: "give item character".
     * To give an item, it has to be in the player's inventory. Once an item
     * is given, it cannot be taken back. If the item given is the character's 
     * needed item, the final dialog is initiated.
     * @param command supplied by the player, second word = item, 
     * third word = character
     */
    private void give(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Give what?");
            return;
        }
        if (!command.hasThirdWord()){
            System.out.println("Give what to whom?");
            return;
        }
        
        String itemName = command.getSecondWord();
        String characterName = command.getThirdWord();
        ArrayList<Character> people = currentRoom.getPeople();//people in the current Room
        ArrayList<Item> playerItems = player.getItems(); //player inventory
        //we will assign these later and remove them outside of the for loop to
        //avoid modification errors
        Item itemToRemove = null;
        Character characterToGive = null;
        
        //looking for the item to give in player inventory
        for (Item item : playerItems){
            if (item.getName().equals(itemName)) {
               itemToRemove = item;
            }
        }
        
        //print warning if item wasn't found, return
        if (itemToRemove==null){
            System.out.println("You don't have that item.\n Tip: The correct "
                    + "syntax is: give item character");
            return;
        }
                
        //look for the character to give the item to
        for (Character character : people){
            if (character.getName().equals(characterName)) {
                character.takeItem(itemToRemove);
                System.out.println("You gave " + itemToRemove.getName() 
                        + " to " + character.getName() );
                characterToGive = character;
            }
        }
        
        //print warning if character wasn't found, return
        if (characterToGive==null){
            System.out.println("That character isn't here.");
            return;
        }
      
        //code below only executes if we were able
        //to find the item in player inventory
        //and if the character exists
        player.removeItem(itemToRemove);
        //initiate final dialog if final condition is met
        if(characterToGive.getFinalCondition())
            talk(new Command("talk", characterToGive.getName()));
            
    }
    
    /**
     * Initiates a conversation with the NPC
     * Syntax: "talk personName".
     * Prints out what the NPC has to say. When NPCs get the item they need 
     * the dialog changes
     * @param command second word = character name
     */
    private void talk(Command command) {
        if (!command.hasSecondWord()){
            System.out.println("Talk to whom?");
            return;
        }
        
        String characterName = command.getSecondWord();
        ArrayList<Character> people = currentRoom.getPeople();//people in room
        boolean found = false;
        
        //try to find the person in current room
        for (Character character : people){
            if (character.getName().equals(characterName)) {
                found = true;
                //print the final dialog if final condition is met
                if (character.getFinalCondition()){
                    printWrapString(character.getName() + ": " 
                            + character.getFinalDialog());
                    //if you fulfill Emily's wish, you lose the game
                    if (character.getName().equals("Emily")){
                        endGameBad();
                    }
                }
                else
                    printWrapString(character.getName() + ": " 
                            + character.getFirstDialog());
                break;
            }
        }
        
        //character wasn't found here
        if (!found)
            System.out.println("That character isn't here!");
    }
    
    /**
     * Take an item from the current room
     * Syntax: "take itemName".
     * An Item from the room will be added to player's inventory using Person
     * method takeItem().
     * @param command the second word holds the name of the item
     */
    private void takeItem(Command command) {
        if (!command.hasSecondWord()){
            System.out.println("Take what?");
            return;
        }
        
        String itemName;
        itemName = command.getSecondWord();
        ArrayList<Item> items = currentRoom.getItems();//items in room
        Item itemToRemove = null; //initialize here, remove later
        boolean found = false;
        
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                //if the item can be picked
                if (item.getIsPickable()) {
                    //if there is enough space in inventory to take item
                    //not enough space in inventory is handled in Person class
                    if (player.takeItem(item)) {
                       itemToRemove = item;
                    }
                }
                else
                    System.out.println("You can't take this item");
                
                found = true;
                break; //since this can only happen once
            }
        }
        //if item wasn't found in the room
        if(!found){
            System.out.println("There is no such item");
            return;
        }
        
        //remove item from room
        currentRoom.removeItem(itemToRemove);
    }
    
    /**
     * Drop item from player's inventory
     * Syntax: "drop itemName".
     * Remove an item from player's inventory using the method removeItem() from
     * class Person and drop it on the floor of the current room.
     * @param command second word is the name of the item
     */
    private void dropItem(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        ArrayList<Item> playerItems = player.getItems(); //player inventory
        Item itemToDrop = null; //initialize later, drop outside of for loop
        boolean found = false;
        
        for (Item item : playerItems) {
            if (item.getName().equals(itemName)) {
               itemToDrop = item;
               found = true;
               break; //can only happen once
            }
        }
        //if item not in inventory
        if(!found){
            System.out.println("You don't have such item!");
            return;
        }
        //remove item from inventory and add to Items in currentRoom
         player.removeItem(itemToDrop);
         currentRoom.putItem(itemToDrop);
         System.out.println("You dropped " + itemToDrop.getName());
    }
    
    /**
     * Print the player inventory
     * Syntax: "items".
     * If player inventory is empty, nothing is added after "Your items are: "
     */
    private void showItems(){
        String outputString = "Your items are: ";
        ArrayList<Item> playerItems = player.getItems();
        for (Item item : playerItems){
            outputString += item.getName();
            if (playerItems.indexOf(item) != playerItems.size()-1)
                    outputString +=  ", ";       
        }
        printWrapString(outputString);
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    
    //internal game methods:
    
    /**
     * Return the player statistics.
     * This will only be shown if the player quits the game prematurely
     * or if he loses the game. If the player didn't manage to save any 
     * characters, "You saved: nobody" is returned.
     * @return a String containing the people the player saved
     */
    private String getGameStats() {
        String returnString = "You saved: ";
        if(!savedPeople.isEmpty()) {
            for(String s : savedPeople) {
                if (savedPeople.indexOf(s) == savedPeople.size()-1){
                   returnString += s;
                }
                else
                   returnString += s + ", "; 
            
            }
        }
        else
            returnString += "nobody";
        
        return returnString;
    }
    
    /**
     * End the game on a bad note.
     * This method executes if a player prematurely quit the game or if the
     * player tried to save a bad person. Statistics are shown and the game
     * quits.
     */
    private void endGameBad() {
        printWrapString("Game over! Better pay attention to the clues next time! "
                + "Here are your stats: ");
        printWrapString(getGameStats());
        System.exit(0);
    }
    
    /**
     * End the game on a good note.
     * This method executes only if the player won the game - if the player
     * saved all the right NPCs that could have been saved.
     */
    private void endGameGood() {
        printWrapString("You won! You managed to save all the people on the ship "
                + "except the murderous psycho! Good job! Now iridium will be used "
                + "for scientific purposes and new discoveries only!");
        System.exit(0);
    }
    
    /**
     * Teleport the player to a random location on the map.
     * Triggered by entering the teleporter room.
     * The previous room remains the room which the player used to
     * enter the teleporter room. Not the teleporter room itself.
     * This is to avoid endless teleportation.
     */
    private void teleport(){
        ArrayList<Room> allRooms = map.getRooms();
        Random rand = new Random();
        previousRoom = currentRoom;
        currentRoom = allRooms.get(rand.nextInt(allRooms.size()));
        printWrapString("You have been teleported!");
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Take the player to a room.
     * This method is only used internally for the purposes
     * of going back to the previous room. No input handling
     * implemented here. For the main goRoom function with input handling,
     * goRoom(Command command) is implemented
     * @param room the room the be taken to
     */
    private void goRoom(Room room) {
        previousRoom = currentRoom;
        currentRoom = room;
        map.shuffleCharacters();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Print wrapped string.
     * This method uses WordUtils from apache commons text to print 
     * one paragraph of text wrapped to 100 columns width.
     * @param string to be wrapped and printed
     */
    private void printWrapString(String string){
        System.out.println(WordUtils.wrap(string, 100));
    }
    
    /**
     * Print a horizontal line.
     * This method uses StringUtils methog repeat from apache commons 
     * lang package to print a horizontal line by repeating the 
     * unicode box-drawing character U+2500 100 times
     */
    private void printHorizontalLine(){
        System.out.println(StringUtils.repeat("\u2500", 100));
    }
    
}
