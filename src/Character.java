/**
 * This class describes an NPC.
 * It's a subclass of class Person, since they both
 * share some fields and methods. Character extends
 * Person's fields by description, capableOfMoving, neededItem, dialog fields
 * and finalCondition.
 * 
 * @author Martina Jireckova
 * @version 2017.12.03
 */
public class Character extends Person {
    
    private String firstDialog; //what an NPC says when they fist meet player
    private String finalDialog; //what an NPC says after final condition is met
    private Item neededItem; 
    private boolean capableOfMoving;
    private String description; 
    //describe whether the person has been given the item they need
    private boolean finalCondition; 
   
    
    /**
     * Creates a new in-game Character.
     * A character has infinite inventory space
     * @param name name of the character
     * @param description the person's job position
     * @param neededItem an Item a person needs to be savable   
     * @param capableOfMoving described whether a character can move around
     */
    public Character(String name, String description, 
            Item neededItem, boolean capableOfMoving) {
        super(name, Double.MAX_VALUE);
        this.neededItem = neededItem;
        this.capableOfMoving = capableOfMoving;
        this.description = description;
        finalCondition = false;
    }
    
    /**
     * Store an item in character's inventory space.
     * In this game, the item is given to the character
     * by the player. Overriden because this method in 
     * class Person checks for inventory space - that is not
     * needed here. Plus, character name is now given.
     * 
     * If the item to be stored is the character's needed item,
     * the character becomes savable.
     * 
     * @param item the item to be stored
     * @return true because a character has infinite inventory space
     */
    @Override
    public boolean takeItem(Item item) {
        
        getItems().add(item);
        System.out.println(getName() + " took " + item.getName());
        
        if (neededItem!= null && item.equals(neededItem)){
            finalCondition = true;
            System.out.println("* " + getName() + " can now be saved! *");
        }
        return true;
    }
 
    /**
     * Return first piece of dialog.
     * This String is returned when a player
     * first talks to an NPC
     * @return first dialog as a String
     */
    public String getFirstDialog() {
        return firstDialog;
    }

    /**
     * Set the first piece of dialog.
     * @param firstDialog the String to be returned
     * when a player first talks to an NPC
     */
    public void setFirstDialog(String firstDialog) {
        this.firstDialog = firstDialog;
    }

    /**
     * Return the final piece of dialog.
     * This String is returned when a player
     * gives the character his/her needed item and
     * the character becomes savable.
     * @return final piece of dialog as a String
     */
    public String getFinalDialog() {
        return finalDialog;
    }

    /**
     * Set the final piece of dialog.
     * @param finalDialog the String to be returned
     * when a player talks to the NPC after they become
     * savable
     */
    public void setFinalDialog(String finalDialog) {
        this.finalDialog = finalDialog;
    }
    
    /**
     * Return whether the character is savable.
     * When character's needed item is in his/her inventory,
     * the character becomes savable.
     * @return true if character is savable
     */
    public boolean getFinalCondition() {
        return finalCondition;
    }
    
    /**
     * Return whether the character can be moved around.
     * When a character is capable of moving, he/she moves
     * around the map.
     * @return true if a character can move around the map
     */
    public boolean isCapableOfMoving() {
        return capableOfMoving;
    }

    /**
     * Return character's job position
     * @return the description as a String
     */
    public String getDescription() {
        return description;
    }
   
    
}
