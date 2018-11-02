/**
 * This class represents all readable items in game.
 * 
 * All readables (books, notes, etc.) are items that also contain "content", 
 * which is the text that the player can read. All readables are pickable, since
 * to be able to read a text, the player has to add the item to his/her inventory
 * first.
 *
 * @author Martina Jireckova
 */
public class Readable extends Item {
    private String content; 

    /**
     * Create a readable item and set the content straight away
     * @param weight weight of the object
     * @param name name of the object
     * @param content the readable text that the object contains
     */
    public Readable(double weight, String name, String content) {
        super(weight, name, true); //all readables are pickable
        this.content = content;
    }
    
    /**
     * Create a readable item and leave content blank
     * @param weight weight of the object
     * @param name name of the object
     */
     public Readable(double weight, String name) {
        super(weight, name, true); //all readables are pickable
        this.content = "There seems to be nothing here!";
    }

    /**
     * Set the content of the readable item
     * @param content the text that can be read
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Return the content of the readable item
     * @return readable text as a String
     */
    public String getContent() {
        return content;
    }
    
    
}
