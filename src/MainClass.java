/**
 * This is the main class of the "Sector Alpha" application.
 * 
 * "Sector Alpha" is a sci-fi game based on an abandoned spaceship.
 *  Players start in the docks of the spaceship where they were summoned 
 *  after the spaceship sent a distress signal. 
 *  
 *  The players walk around, interact with objects and NPCs to find out 
 *  what happened on the ship. The goal is to save as many people from the ship 
 *  as the player can. 
 *  All NPCs require some objects to agree to leave the ship - to become savable. 
 *  Some NPCs are however dangerous and if you attempt to save them, you will die. 
 *  Clues are given throughout the map to help the player determine who to save.
 * 
 * This class makes the application runnable by creating a new instance of
 * Game and evoking its play method.
 * 
 * @author Martina Jireckova
 */

public class MainClass {
    /**
     * @param args doesn't do anything at the moment
     */
    public static void main(String[] args) {
        new Game().play();
    }
    
}
