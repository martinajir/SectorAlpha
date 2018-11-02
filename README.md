# SectorAlpha
"Sector Alpha" is a sci-fi text-based game set on a spaceship. The game takes place in the
future where mankind has started to explore unknown space. A spaceship was sent to explore
a newly discovered star system. Once the ship reaches sector alpha of the new star system, the
ship sends a distress signal and then loses all contact with Earth. The player is put in the shoes
of a seasoned space explorer who was summoned to find out what happened to the ship and
save as many people from the crew as possible.

# Goal of the game
The goal is to save as many people from the ship as the player can. All NPCs (non-player
characters) require some objects to agree to leave the ship - to become saveable. These objects
can include medicine or letters. NPCs give clues as to which items they need in conversation
with the player. These items can be found scattered throughout the map in various containers.
Some NPCs are however dangerous and if you attempt to save them, you will die. Clues are
given throughout the map in the form of notes, printed emails and diary entries to help the
player determine who to save and who to leave alone. The game is won when the player saves
all people except for the dangerous NPC.

# Player experience
Players start in the docks of the spaceship where they awaken after surviving a crash-like event,
which killed the crew of the rescue mission. First, the player inputs their name and then how
much weight they can carry. This player input greatly influences the game as a large portion of
it is comprised of picking up items and interacting with them. The player walks around, interacts
with objects and NPCs to find out what happened on the ship. Some objects can be picked up,
others cannot. Some items can be opened, some items can be read. Items can also be given to
NPCs. By giving NPCs the items they desire, they can become saveable. The player is notified
when the item they give to the NPC is the correct one. Then the player can save them, which
contributes to their overall stats. Even if the player doesn’t manage to save all of the good NPCs,
he/she can still get an overview of their accomplishments at the end of the game.

# Overview
The game has 16 rooms (including a teleporter room). Each room has certain exits, contains some items and some
people. Rooms are initialized in class GameMap along with all items and people in the game. The player can walk 
through the rooms by using the “go” command followed by the name of the room exit the player wishes to go through. 
This functionality is kept the same as in the original except some rooms can have two-word names.
There are items in some rooms. All items are of the base class Item, have a certain
weight and name. Some cannot be picked. Some items are Readable, some are Containers (those are subclasses of Item), 
which contain more items and can be opened.

# Command list
To view command list, type help

