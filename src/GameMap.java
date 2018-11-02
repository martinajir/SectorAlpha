import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * This class holds all the rooms and items in the game.
 * At the start of the game, all the rooms, objects in them
 * and their exits are initialized. Throughout the game,
 * characters are shuffled around in regular intervals.
 *
 * @author Martina Jireckova
 */
public class GameMap {
   
    private ArrayList<Room> rooms;
    private ArrayList<Character> people;
    private Random rand; 
    
    /**
     * significant rooms and items are declared here
     * in order to avoid having to go through the entire array
     * to fetch them specifically
    */
    private Room docks; //starting room
    private Room labOne; //room where one character is locked
    private Room mainEngineering; //room where one character is locked
     
    //significant items
    private Item key;
    private Item letterFromWife;
    private Item chocolate;
    private Item antibiotics;
    private Item shotgun;

    /**
     * Constructor for objects of class Map
     */
    public GameMap() {
        rand = new Random();
        rooms = new ArrayList<Room>();
        people = new ArrayList<Character>();
        initializeMap();
        initializeCharacters();
    }
   
   /**
    * Returns the starting room as a Room
    * @return the starting room of the game = docks
    */
    public Room getStartingRoom() {
        return docks;
    }
    
    /**
     * Initializes the entire map with all rooms and items. 
     * First, rooms are created and items are put in them.
     * Then, exits are added to the rooms.
     */
    private void initializeMap() {
       
       //initialize rooms
       //starting point
       docks = new Room("in the docks");
       rooms.add(docks);
       docks.putItem(new Item(0.1, "flashlight", true));
       docks.putItem(new Item(1000, "spaceship", false));
       shotgun = new Item(2, "shotgun", true);
       docks.putItem(shotgun);
       
       //hallways
       Room rightHallway, leftHallway, mainCorridor;
       
       rightHallway = new Room("in the right hallway");
       rooms.add(rightHallway);
       leftHallway = new Room("in the left hallway");
       rooms.add(leftHallway);
       mainCorridor = new Room("in the main corridor");
       rooms.add(mainCorridor);
       
       //rooms accessible from left corridor
       Room gym, doctorsOffice, captainsQuarters, labLobby, labTwo;
  
       gym = new Room("in the gym");
       rooms.add(gym);
       gym.putItem(new Item(10, "weights", true));
       gym.putItem(new Item(20, "bench", false));
       gym.putItem(new Item(0.2, "clothes", true));
       
       doctorsOffice = new Room("in the doctor's office");
       Container doctorsTable = new Container(10, "table");
       antibiotics = new Item(0.01, "antibiotics", true);
       doctorsTable.storeItem(antibiotics);
       doctorsTable.storeItem(new Readable(0.02, "prescription", 
               "For: Emily, Take one every day and"
               + " the thoughts should go away. "
               + "Text me if the feelings persist."));
       doctorsOffice.putItem(doctorsTable);
       Container fileStorage = new Container(20, "fileStorage");
       Readable yellowFile = new Readable(0.02, "file", 
               "21/02/2155\n Health evaluation: Emily\n The "
               + "patient reports feelings of increased anxiety, "
               + "panic attacks and maniacal mental state. "
               + "Even though all physical examinations show no problems, "
               + "it is questionable whether the patient should continue"
               + " the voyage due to safety concerns for the rest of the crew.");
       fileStorage.storeItem(yellowFile);
       doctorsOffice.putItem(fileStorage);
       rooms.add(doctorsOffice);
       
       captainsQuarters = new Room("in the captain's quarters");
       Readable voyageDiary = new Readable(0.02, "diary", 
               "The iridium findings have been confirmed."
               + " Today is the day. Now to the plan. Nobody's "
               + "going to get in my way.");
       captainsQuarters.putItem(voyageDiary);
       Item bed = new Item(30, "bed", false);
       captainsQuarters.putItem(bed);
       captainsQuarters.putItem(new Item(5, "chair", true));
       rooms.add(captainsQuarters);
       
       labLobby = new Room("in the laboratory entrance lobby");
       labLobby.putItem(new Item(0.3, "flower", true));
       labLobby.putItem(new Item(20, "couch", false));
       Readable notes = new Readable(0.1, "notes", "Sector alpha "
               + "seems to be full of hostile life. Dangerous. Evacuate ship.");
       labLobby.putItem(notes);
       rooms.add(labLobby);
       
       labOne = new Room("in lab 1");
       labOne.putItem(new Item(40, "operatingTable", false));
       labOne.putItem(new Item(0.05, "syringe", true));
       labOne.putItem(new Item(0.2, "mouse", true));
       Container box = new Container(1, "box");
       Readable entries = new Readable(0.02, "notebook", 
               "I'm starting to suspect everyone on this ship. "
               + "Iridium is extremely expensive these days and we "
               + "are supposed to report every finding back to "
               + "Earth. However, Emily wouldn't want us to make the call. "
               + "Why? What is happenning? "
               + "Our defence spending is already spread thin. "
               + "We need to make the call now! What if someone "
               + "attacks us? We wouldn't be able to defend ourselves. "
               + "We need to go back to Earth.");
       box.storeItem(entries);
       labOne.putItem(box);
       rooms.add(labOne);
       
       labTwo = new Room("in lab 2");
       labTwo.putItem(new Item(3, "crowbar", true));
       Container labCabinet = new Container(50, "cabinet");
       Readable greenFile = new Readable(0.01, "greenFile", 
               "Sector alpha seems to be completely devoid "
               + "of all life. The atmosphere on the surrounding planets "
               + "would never be able to support any kind "
               + "of life. We have been discussing possibilities of "
               + "future terraforming, however "
               + "so far no viable method comes to mind.");
       Readable redFile = new Readable(0.01, "redFile", 
               "New elements found near quadrant 12!"
               + " We witnessed an enormous amout of iridium nearby. "
               + "We need confirmation "
               + "but if everything goes well, the iridium will significantly "
               + "help us in scientific advance.");
       labCabinet.storeItem(greenFile);
       labCabinet.storeItem(redFile);
       labTwo.putItem(labCabinet);
       rooms.add(labTwo);
       
       //rooms accessible from main corridor
       Room mainBridge, teleporterRoom;
       
       mainBridge = new Room("at the main bridge");
       key = new Item(0.01, "key", true);
       Container bridgeTable = new Container(20, "desk");
       bridgeTable.storeItem(key);
       mainBridge.putItem(bridgeTable);
       mainBridge.putItem(new Item(200, "controlpanel", false));
       mainBridge.putItem(new Item(0.1, "coffee", true));
       mainBridge.putItem(new Item(5, "chair", true));
       mainBridge.putItem(new Readable(0.1, "map", 
               "There are many asteroids in this "
               + "quadrant, some of which could contain valuable minerals."));
       rooms.add(mainBridge);
       
       
       teleporterRoom = new Room("in the teleporter room");
       rooms.add(teleporterRoom); 
       
       //rooms accessible from right corridor
       Room powerSupply, serverRoom, kitchens;
       Room diningHall, staffMeetingRoom;
       
       mainEngineering = new Room("in the main engineering room");
       mainEngineering.putItem(new Item(5, "transformer", true));
       mainEngineering.putItem(new Item(10, "tools", true));
       Container engDesk = new Container(20, "desk");
       Readable manual = new Readable(0.01, "manual", 
               "The manual is all torn up and some "
               + "pages seem to be missing.");
       mainEngineering.putItem(engDesk);
       rooms.add(mainEngineering);
       
       powerSupply = new Room("in the power supply room");
       powerSupply.putItem(new Item(100,"powerCore", false));
       powerSupply.putItem(new Item(20, "bench", false));
       powerSupply.putItem(new Item(10, "tools", true));
       powerSupply.putItem(new Readable(0.05, "aliensGuide", 
               "In case of a hostile "
               + "alien attack, proceed to the nearest safe room. "
               + "In your case this will be "
               + "the exit to the main engineering room. "
               + "Stay safe. Run, hide, don't fight!"));
       rooms.add(powerSupply);
       
       serverRoom = new Room("in the server room");
       serverRoom.putItem(new Item(5, "computer", false));
       serverRoom.putItem(new Item(10, "mainServer", false));
       serverRoom.putItem(new Readable(0.01, "printedEmail",
               "Price of iridium is rising! Commence operation red."));
       serverRoom.putItem(new Readable(0.01, "yourOrder", 
               "10 kg of red paint have been loaded on board"));
       letterFromWife = new Readable(0.03, "letter", 
               "Dearest Mark,\n I hope you are well. I have"
               + " been thinking of you a lot. I miss you dearly and "
               + "I can't wait for your return!\n"
               + "Yours truly,\n Honeybunny");
       Container envelope = new Container(0.05, "envelope");
       envelope.storeItem(letterFromWife);
       serverRoom.putItem(envelope);
       rooms.add(serverRoom);
       
       
       kitchens = new Room("in the kitchens");
       Container kitchenCabinet = new Container(10, "kitchenCabinet");
       kitchenCabinet.storeItem(new Item(0.2, "tomatoes", true));
       kitchenCabinet.storeItem(new Item(0.1, "garlic", true));
       kitchenCabinet.storeItem(new Item(0.05, "salt", true));
       kitchenCabinet.storeItem(new Item(0.2, "peach", true));
       kitchens.putItem(kitchenCabinet);
       kitchens.putItem(new Item(20, "stove", false));
       kitchens.putItem(new Item(20, "fridge", false));
       kitchens.putItem(new Item(20, "table", false));
       Container smallCabinet = new Container(10, "smallCabinet");
       chocolate = new Item(0.2, "chocolate", true);
       smallCabinet.storeItem(chocolate);
       kitchens.putItem(smallCabinet);
       rooms.add(kitchens);
       
       diningHall = new Room("in the dining hall");
       diningHall.putItem(new Item(20, "table", false));
       diningHall.putItem(new Item(20, "chairs", false));
       diningHall.putItem(new Readable(2, "chandelier", 
               "IIIII'm gonna swwiiiiing from the chandeliiiier")); //easter egg
       rooms.add(diningHall);
       
       staffMeetingRoom = new Room("in the staff meeting room");
       staffMeetingRoom.putItem(new Readable(0.01, "boardNotice", 
               "Possible alien attack. Evacuate immediately!"));
       staffMeetingRoom.putItem(new Item(20, "table", false));
       staffMeetingRoom.putItem(new Item(5, "chair", false));
       staffMeetingRoom.putItem(new Item(2, "palm", false));
       rooms.add(staffMeetingRoom);
       
       //set exits
       docks.setExit("right", rightHallway);
       docks.setExit("left", leftHallway);
       docks.setExit("main corridor", mainCorridor);
       
       mainCorridor.setExit("teleporter room", teleporterRoom);
       mainCorridor.setExit("bridge", mainBridge);
       mainCorridor.setExit("docks", docks);
       
       teleporterRoom.setExit("out", rightHallway);
       
       mainBridge.setExit("main corridor", mainCorridor);
       mainBridge.setExit("captain's quarters", captainsQuarters);
       mainBridge.setExit("meeting room", staffMeetingRoom);
       
       rightHallway.setExit("engineering", mainEngineering);
       rightHallway.setExit("kitchens", kitchens);
       rightHallway.setExit("meeting room", staffMeetingRoom);
       rightHallway.setExit("docks", docks);
       
       mainEngineering.setExit("power supply", powerSupply);
       mainEngineering.setExit("server room", serverRoom);
       mainEngineering.setExit("out", rightHallway);
       
       powerSupply.setExit("out", mainEngineering);
       serverRoom.setExit("out", mainEngineering);
       
       kitchens.setExit("dining hall", diningHall);
       kitchens.setExit("out", rightHallway);
       
       diningHall.setExit("kitchens", kitchens);
       diningHall.setExit("out", rightHallway);
       
       staffMeetingRoom.setExit("bridge", mainBridge);
       staffMeetingRoom.setExit("out", rightHallway);
       
       leftHallway.setExit("gym", gym);
       leftHallway.setExit("doctor's office", doctorsOffice);
       leftHallway.setExit("lab", labLobby);
       leftHallway.setExit("docks", docks);
       leftHallway.setExit("captain's quarters", captainsQuarters);
       
       gym.setExit("out", leftHallway);
       labLobby.setExit("out", leftHallway);
       labLobby.setExit("lab 1", labOne);
       labLobby.setExit("lab 2", labTwo);
       
       labOne.setExit("out", labLobby);
       labTwo.setExit("out", labLobby);
      
       doctorsOffice.setExit("out", leftHallway);
       captainsQuarters.setExit("left hallway", leftHallway);
       captainsQuarters.setExit("bridge", mainBridge);
      
    }
    
    /**
     * Initializes all NPCs in the game.
     * First, NPCs are created and then randomly put into rooms
     * all over the map if they are movable.
     */
    private void initializeCharacters() {
        Character labAssistant = new Character("Anne", "Lab assistant", key, false);
        labAssistant.setFirstDialog("Oh my god, thank god someone came. "
                + "Who is this, is this you, Emily?"
                + " I couldn't find anyone, please help me, I'm locked in here. "
                + "I heard the alarm when it went off and I knew something"
                + " very bad happened so I activated code red immediately. "
                + "I however forgot the combination to the lock and I don't "
                + "know how to get out now. Are there aliens? What happened? "
                + "I thought everything was fine but then I heard the screams.. "
                + "and the hallway filled with blood and the alarm went off... "
                + "What happened?? Please, just get me out of here!"
                + " I'm losing my mind! I keep hearing whispers.. everywhere... "
                + "I need to get out!");
        labAssistant.setFinalDialog("Thank you SO MUCH, you're the best. "
                + "I have no idea what I would do without you. "
                + "Please just get me off this ship, I cannot take it anymore.");
        labOne.putCharacter(labAssistant);
        people.add(labAssistant);
        
        Character cook = new Character("Mark", "Cook", letterFromWife, true);
        cook.setFirstDialog("I... see them... everywhere... The blood just "
                + "keeps pouring from them. How could anyone ... forget?"
                + " Martin was there and Eva too. Both dead. I saw it with my "
                + "own eyes. I went back to the kitchen to try to forget but"
                + " I just can't. I can't take it. I'm not an adventurer, I'm "
                + "just a cook. I shouldn't have enlisted in the first place. "
                + "They said this sector is safe. But our captain, Emily, she "
                + "had her doubts. She told us not to go. "
                + "She was right... And now my wife, what if it was me instead "
                + "of Martin? What if I never saw my wife again? "
                + "The only memory I have of her is the letter she sent me. "
                + "And now I can't even find that letter. And I can't "
                + "leave this ship"
                + " without it, what if I never make it back, "
                + "I need something...");
        cook.setFinalDialog("Thank you. I needed that letter. Grace, "
                + "I'm coming home! I will see her again, I know it! "
                + "Poor Martin and Eva..."
                + " I think they were the first to die after the alarm rang...");
        people.add(cook);
        
        Character engineer = new Character("David", "Engineer", antibiotics, false);
        engineer.setFirstDialog("Please, someone! It hurts, so much. I can't... "
                + "Help me! Something... came to the lab and the transformer just"
                + " exploded on me. My leg. I can't stand up. I... it's "
                + "festering. I don't think I can... Please, give me some meds.");
        engineer.setFinalDialog("Thank you, that's a bit better. I really need "
                + "to find a doctor soon. I still wonder what came to the lab"
                + " that night. It was a small figure and it did something and "
                + "then it just exploded. Why...? I don't get it.."
                + " Even if it was aliens... they seemed too sure of what they "
                + "were doing.");
        mainEngineering.putCharacter(engineer);
        people.add(engineer);
        
        Character magda = new Character("Magda", "?", chocolate, true); 
        magda.setFirstDialog("Isn't it a lovely day? The flowers are in bloom, "
                + "the birds are singing. I can hear the trees whisper "
                + "in the wind. Aren't the flowers nice and red? The fields are "
                + "red, the rivers are red. Isn't red such a wonderful "
                + "color? I'm hungry. I'm so hungry. I need something sweet. "
                + "The day is so nice though. It used to be better but then "
                + "code red went off and now everything is red. Emily is red, "
                + "the floor is red. Poor Martin. I saw him, he was red..");
        magda.setFinalDialog("Chocolate! Yes! I love chocolate it's so not red. "
                + "Refreshing!");
       people.add(magda);
        
        Character captain = new Character("Emily","Captain", shotgun, true);
        captain.setFirstDialog("Oh, I'm so glad you're here! I haven't seen you "
                + "in forever! How have you been? "
                + "Oh yeah, I'm sorry, uhm we've had a bit of a crisis here, "
                + "it's sad, really. I have no idea what happened. "
                + "People seemed to have spotted some creatures running around "
                + "and causing some trouble. I have no idea what to do, "
                + "I even seem to have lost my shotgun and now I'm really "
                + "worried something's gonna happen! Would you be a dear "
                + "and kindly get me the shotgun? I'd feel so much safer "
                + "with a gun in my hands...");
        captain.setFinalDialog("Ha, you gullible fool. What did you think was "
                + "going to happen? Ha ha ha ha. Now kneel and beg "
                + "for your miserable life, because I just always wanted to see "
                + "you do that. Now, I was worried I made mistakes but "
                + "now I see that everything in my plan was brilliant. You "
                + "didn't suspect a thing, did you? "
                + "Well let me tell you what really happened. We found iridium. "
                + "Tons of it. "
                + "So much of it, you wouldn't believe me if I told you exactly "
                + "how much. And you know how much money that "
                + "can make you. Tons of money. And what would happen if the "
                + "IASE knew? They'd send their little scientists and dig and "
                + "no one would have anything of it. But how to stop it? The "
                + "only way is to have this sector declared unsafe. For example "
                + "say there are hostile aliens. Make those gullible fools believe. "
                + "Just like you did. And all the iridium is mine to sell. "
                + "Well, too bad. Seems like the gun is now in my hands. "
                + "See you in hell, game over!");
        people.add(captain);
        
        //this loop puts people in random rooms if they are movable
        //this means all people except for Anne and David will be put 
        //in a random room
        for (Character character : people) {
            int random = rand.nextInt(rooms.size());
            if (character.isCapableOfMoving())
                rooms.get(random).putCharacter(character);
        }
        
    }
    
    /**
     * Shuffles characters around the map.
     * When this method is called, the character (if movable)
     * goes through a random exit of the room he/she is currently in.
     * 
     */  
    public void shuffleCharacters(){
        
        //stores characters that have already moved this method call
        ArrayList<Character> movedCharacters = new ArrayList<>(); 

        for (Room room : rooms) {
            ArrayList<Character> peopleInRoom = room.getPeople();
            HashMap<String, Room> roomExits = room.getExits();
            Object[] exits = roomExits.values().toArray();
            
            //only execute if there are people in the room
            if (!peopleInRoom.isEmpty()) {
                //to avoid concurrentmodification exception
                 //holds people to be removed from currentRoom
                ArrayList<Character> removalList = new ArrayList<>();

                for (Character character : peopleInRoom) {
                    if (character.isCapableOfMoving() 
                            && !movedCharacters.contains(character)){
                        //This can be downcast because we know that the values 
                        //of the HashMap are Rooms
                        //If modified however, this could be a problem
                        //To avoid, if (exitRoom instanceof Room) could be used 
                        int randomExitNum = new Random().nextInt(exits.length);
                        Room exitRoom = (Room)exits[randomExitNum];
                        exitRoom.putCharacter(character);
                        removalList.add(character);
                        movedCharacters.add(character);
                    }
                }
                //finally, remove the people from the room
                for (Character character : removalList){
                    room.removeCharacter(character);
                }
            }
        } 
    }

    /**
     * Returns the people in game
     * @return ArrayList of class Character objects
     */
    public ArrayList<Character> getPeople() {
        return people;
    }
    
    /**
     * Returns the rooms in game
     * @return ArrayList of class Room objects
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
    
    
  
    

