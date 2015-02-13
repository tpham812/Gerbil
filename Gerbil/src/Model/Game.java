package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
 


  
/**
 * Game class that holds all the information for a game. 
 * Games can be saved for users and they can be loaded to continue to be played. 
 * A game consists of an obstacle course through which the the user must
 * navigate the avatar to get to the goal of the water drinking area. 
 * In the Fancy features, the User can create the obstacle course 
 * @author Amulya 
 */
@SuppressWarnings("serial")
public class Game implements Serializable{
	/**User written code for which each node shows up as a block of code in the GUI*/
	public HashMap<Integer,Block> blocks; 
	/**Grid user is playing on for this game*/
	private Grid grid; 
	/**User created functions for this game that appear in drop down list for user to select*/
	public ArrayList<Function> functions; 
	/**Gerbil object to give gerbil's position*/
	private Gerbil gerbil; 
	/**Name of the game that user designates when saving*/
	private String name =""; 
	

	/**
	 * Constructor to create the game 
	 * 
	 * @assumes assumes name has been varified to be unique
	 * @exception none
	 * @postcondition creates a unique (due to name) game object 
	 * 
	 * @param name The name of the game
	 */
	public Game(String name){
		this.gerbil= new Gerbil();
		this.grid = new Grid(17,17);
		this.functions= new ArrayList<Function>();
		this.blocks= new HashMap<Integer,Block>();
		this.name = name;
	}
	

	/**
	 * Gets the gerbil object
	 * 
	 * @assumes assumes gerbil object exists
	 * @exception none
	 * @postcondition returns gerbil object that has been instantiated.
	 * 
	 * @return The gerbil object for the game
	 */
	public Gerbil getGerbil(){
		return gerbil;
	}
	/**
	 * Gets the HashMap of user written code that shows up in the GUI
	 * 
	 * @assumes assumes blocks hashmap object exists
	 * @exception none
	 * @postcondition returns blocks hashmap object that has been instantiated.
	 * 
	 * @return The Hashmap of user written code in GUI
	 */
	public HashMap<Integer,Block> getBlocks(){
		return blocks;
	}
	/**
	 * Sets the hashmap of blocks in main code
	 * @assumes assumes valid parameters given
	 * @exception none
	 * @postcondition the blocks' blocks are changed to the parameter 
	 */
	public void setBlocks(HashMap<Integer,Block> newBlocks){
		this.blocks=newBlocks;
	}
	/**
	 * Gets the Grid object of the game
	 * 
	 * @assumes assumes grid object exists
	 * @exception none
	 * @postcondition none
	 * 
	 * @return The Grid with fruit, water can, and wall placements
	 */
	public Grid getGrid(){
		return grid;
	}
	
	/**
	 * SEts the name of the Game
	 * @param gameName Name of the game 
	 * 
	 * @assumes game exist and unique name assumed
	 * @exception none
	 * @postcondition name of the game gets set/ changed
	 */
	public void setName(String gameName) {	
		name = gameName;
	}
	/**
	 * Gets the name of the game
	 * 
	 * @assumes assumes unique name exists
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Name of the name as entered by the user
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gets the user defined functions arraylist 
	 * 
	 * @assumes assumes user can create functions
	 * @exception none
	 * @postcondition none
	 * 
	 * @return User defined functions arraylist
	 */
	public ArrayList<Function> getfunction(){
		return functions;
	}

	
	/**
	 * Adds a function to the functions list
	 * 
	 * @assumes Function is valid function so name not already in array list is checked
	 * @exception none
	 * @postcondition Adds function to function list
	 * 
	 * @param functionToAdd function to be added to Functions list 
	 * @return boolean: true if successful add, false otherwise
	 * 

	 */
	public void addFunction(Function functionToAdd){
		this.functions.add(functionToAdd);//means index 0 +
	}
	
	
	/**
	 * Deletes a function from the functions list
	 * 
	 * @assumes Function with the parameter name exists within the function list
	 * @exception none
	 * @postcondition Deletes function
	 * 
	 * @param functionToDelete name of function to be deleted
	 * @return true if successful deletion, false otherwise
	 */
	public boolean deleteFunction(String funcNameToDelete){
		for(int i=0; i<this.functions.size(); i++){
			if(this.functions.get(i).name.equals(funcNameToDelete)){
				this.functions.remove(i);
				return true;
			}
		}
		return false;
		
	}
	
}

