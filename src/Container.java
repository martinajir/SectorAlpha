import java.util.ArrayList;
/**
 * This class represents containers that can hold other items.
 * A Container can for example be a desk that contains a key or 
 * a box that can be opened. In my implementation so far, all containers 
 * can be opened and when they are opened, all items are put in the room. 
 * This can however be changed, it all depends on the exact implementation of 
 * the open command.
 * 
 * This class extends class Item by containing an ArrayList of items stored
 * within the container.
 * 
 * @author Martina Jireckova
 * @version 2017.12.03
 */
public class Container extends Item {
    
    private ArrayList<Item> storedItems; //items stored within this container 
    
    /**
     * Create a new Container and initialize storedItems ArrayList.
     * No containers are pickable right now. This can however later be changed,
     * so weight is left as a valid parameter for now.
     * @param weight weight of the container
     * @param name name of the container
     */
    public Container(double weight, String name) {
            super(weight, name, false); //no containers are pickable
           this.storedItems = new ArrayList<Item>();
    }
    
    /**
     * Return an ArrayList of Items stored within the container
     * @return items stored as an ArrayList
     */
    public ArrayList<Item> getStoredItems(){
        return storedItems;
    }
    
    /**
     * Store an item within the container
     * @param item to be stored in the container
     */
     public void storeItem(Item item){
        storedItems.add(item);
    }

     /**
      * Remove an item from the container
      * @param item to be removed from the container
      */
     public void removeItem(Item item){
         storedItems.remove(item);
     }
     
     /**
      * Empty the container.
      */
     public void resetStoredItems() {
         storedItems = new ArrayList<Item>();
     }
    
}
