package Model;
 

import java.io.Serializable;
import java.util.ArrayList;
   
/**
 * User class that holds information about the users including the games they have played, saved or made
 * THIS CLASS WAS FOR FANCY SYSTEM NOT IMPLEMENTED!
 *@author Amulya
 */
@SuppressWarnings("serial")
public class User implements Serializable{
	/**The username of the user that identifies the user*/
	private String userName;
	/**The password user uses to log in*/
	private String password;
	/**Array List that holds games of the user */
	private ArrayList<Game> games = new ArrayList<Game>();
	
	/** 
	 * Constructor to create a user object
	 * 
	 * @assumes We have checked that same named user does not exist
	 * @exception none
	 * @postcondition creates a new user with a password
	 * 
	 * @param uN Username entered by the user
	 * @param pass Password for the user account
	 */
	public User(String uN, String pass){
		this.userName=uN;
		this.password=pass;
	}

	/**
	 * Gets the user name of the user
	 * 
	 * @assumes userName exists
	 * @exception none
	 * @postcondition Retrieves user name
	 * 
	 * @return The user name of the user
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * Gets the password of the user. This is for troubleshooting only!!!!
	 * 
	 * @assumes Programmer is testing program
	 * @exception none
	 * @postcondition returns valuable information so hopefully handled well
	 * 
	 * @return The password of the user 
	 * */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * Adds a game to the arraylist of the games for the user 
	 * 
	 * @assumes Game object already created and initiallized properly
	 * @exception none
	 * @postcondition Adds to the user's game list
	 * 
	 * @param g The game object to add to the array list of games in user. 
	 * @return True if successfully added, else false
	 */
	public boolean addGame(Game g){
		 games.add(g);
		 return true;
	}
	
	/**
	 * Deletes a game from the arraylist of the games for the user 
	 * 
	 * @assumes checked for valid name of game provided
	 * @exception none
	 * @postcondition Removes game from user's game list properly
	 * 
	 * 
	 * @param in_name Name for the game given by the user. 
	 * @return True if successfully deleted, else false
	 */
	public boolean deleteGame(String in_name){
		for(int i = 0; i<games.size();i++){
			if((games.get(i).getName().compareTo(in_name))==0){
				this.games.remove(i);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Gets the game from the arraylist of user's games based on the name the user gave for the game
	 * when he/she saved the game. 
	 * 
	 * @assumes Game with that name has been verified to exist. 
	 * @exception none
	 * @postcondition retrieves game with that name
	 * 
	 * @param in_name Name of the game the user gave when he/she saved the game
	 * @return The game the user had saved before if it exists, else return null 
	 */
	public Game getGame(String in_name){
		for (Game g: this.games){
			if((g.getName().compareTo(in_name))==0){
				return g;
			}
		}
		return null;
	}
	
	/**
	 * Returns the games arraylist
	 * 
	 * @assumes Assumes games list of user exists
	 * @exception none
	 * @postcondition Retrieves user's games list
	 * 
	 * @return The array list stored in user that has all the games. 
	 */
	public ArrayList<Game> getGameList(){
		return this.games;
	}
}
