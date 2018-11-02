/**
 * This class represents items that can be found in rooms.
 * Each item has its own name and weight. Some items can be picked
 * by the player, others cannot.
 * 
 * This class is extended by classes Readable and Container.
 *
 * @author Martina Jireckova
 * @version 2017.12.03
 */
public class Item {
    private String name;
    private double weight;
    private boolean isPickable; //whether item can be picked up
   
    /**
     * Create an item.
     * @param weight the weight of the item
     * @param name the name of the item
     * @param isPickable whether item can be picked up by the player
     */
    public Item(double weight, String name, boolean isPickable) {
       this.weight = weight;
       this.name = name;
       this.isPickable = isPickable;
    }
    
    /**
     * Return the weight of the object
     * @return weight of object as a double
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Return whether the object is pickable
     * @return boolean isPickable
     */
    public boolean getIsPickable() {
        return isPickable;
    }

    /**
     * Return the name of the object
     * @return String name of object
     */
    public String getName() {
        return name;
    }
   
    
}
