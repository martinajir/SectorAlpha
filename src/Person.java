import java.util.ArrayList;
/**
 * This class describes a person in the game.
 * In my implementation, this class describes the player.
 * This class acts as a super class for all NPCs in the game.
 *
 * @author Martina Jireckova
 */
public class Person {
   
    private double carryWeight;
    private ArrayList<Item> inventory;
    private String name;
   

   /**
    * Construct a new Person object
    * @param name the name of the person
    * @param carryWeight the total weight of items that the person can carry
    */
    public Person(String name, double carryWeight) {
        this.name = name;
        this.carryWeight = carryWeight;
        inventory = new ArrayList<>();   
    }
    
    /**
     * Store an item in person's inventory. Returns
     * a boolean signaling whether the method was successful
     * in storing the item in player inventory
     * @param item the item to be stored
     * @return false if carryWeight is exceeded
     */
    public boolean takeItem(Item item) {
        boolean success = false;
        
        if ((calculateTotalWeight() + item.getWeight()) > carryWeight) {
            System.out.println("This item is too heavy. "
                    + "Drop something from your inventory!");
        }
        
        else {
            inventory.add(item);
            System.out.println("You took " + item.getName());
            success = true;
        }
        return success;
    }
    
    /**
     * Remove an item from person's inventory
     * @param item the item to be removed
     */
    public void removeItem(Item item) {
        inventory.remove(item);
    }
    
    /**
     * Calculate the total weight of items in person's inventory.
     * Protected because used in subclass
     * @return total weight of all items in inventory
     */
    private double calculateTotalWeight() {
        double totalWeight = 0;
        
        for (Item item : inventory) {
            totalWeight += item.getWeight();
        }
        
        return totalWeight;
    }
    
    /**
     * Return person's inventory
     * @return inventory as an ArrayList of Items
     */
    public ArrayList<Item> getItems(){
        return inventory;
    }
    
    /**
     * Return person's name
     * @return name as a String
     */
     public String getName() {
        return name;
    }

}

  