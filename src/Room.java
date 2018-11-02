import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Room - one location in the game.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * Each Room stores a varied number of Items.
 * 
 * Rooms are created and stored by the Map class.
 * 
 * @author  Michael Kölling, David J. Barnes, Martina Jireckova
 * @version 2017.12.3.
 */

public class Room {
    private String description;
    private HashMap<String, Room> exits; //exits of this room.
    private ArrayList<Item> items; //items in this room
    private ArrayList<Character> people; //people in this room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is the name of the room
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        people = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit. Since this game
     * is on a spaceship, directions are often names of the rooms
     * or their shorthands
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Return a short name of the room.
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north | west |
     *     Items: item, item
     *     People: Person(description), Person(description)
     * @return A long description of this room
     */
    public String getLongDescription() {
        return "\nYou are " + description + ".\n" + getExitString() 
                + "\n" + getItemString() + "\n" + getPeopleString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north | west |".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit + " |";
        }
        return returnString;
    }
    
    /**
     * Returns a String containing the room's items.
     * For example: "Items: flowers, desk"
     * @return Details of items in the room
     */
    private String getItemString() {
        String returnString = "Items: ";
        if (!items.isEmpty()) {
            for(Item item : items) {
                returnString += item.getName();
                if (items.indexOf(item) != items.size()-1)
                    returnString += ", "; 
            }
        }
        return returnString;
    }
    
    /**
     * Return a String containing the people in the room and their description.
     * For example: "People: Anne (lab assistant), Emily (Captain)"
     * @return 
     */
    private String getPeopleString() {
        String returnString = "People: ";
        
        if (!people.isEmpty()) {
            for (Character character : people) {
                if (people.indexOf(character) == people.size()-1)
                    returnString += character.getName() + " (" 
                            + character.getDescription() + ")";
                else
                    returnString += character.getName() + " (" 
                            + character.getDescription() + ")" + ", ";
            }
        }
        return returnString;
    }
   
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    /**
     * Put an Item into the room.
     * @param item to be put into the room
     */
    public void putItem(Item item) { 
        items.add(item);
    }
    
    /**
     * Put an ArrayList of Items into the room.
     * @param itemArray to be put into the room
     */
    public void putAllItems(ArrayList<Item> itemArray) {
        items.addAll(itemArray);
    }
    
    /**
     * Remove an Item from the room
     * @param item to be removed from room
     */
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    /**
     * Return ArrayList of items in the room
     * @return an ArrayList of Items in the room
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    
    /**
     * Put a Character(NPC) in the room.
     * @param character to be put in the room
     */
    public void putCharacter(Character character) {
        people.add(character);
    }
    
    /**
     * Remove a Character(NPC) from the room
     * @param character to be removed from the room
     */
    public void removeCharacter(Character character) {
        people.remove(character);
    }
    
    /**
     * Return people in the room
     * @return people in the room as an ArrayList
     */
    public ArrayList<Character> getPeople() {
        return people;
    }
    
    /**
     * Return all exits of the room
     * @return all exits as a HashMap
     */
    public HashMap<String, Room> getExits() {
        return exits;
    }
    
}

