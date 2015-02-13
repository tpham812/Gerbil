package Model;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
 
/**
 * Kernel/Standard version of backend. 
 * The backend contains the way to save information once a user 
 * exits the program but does not wish to loose his/her work.
 * In the fancy system, users would be the arraylist<user> 
 * since each user's games would be stored in their object.
 * @author Amulya, Katiuska 
 * */
@SuppressWarnings("serial")
public class Backend implements Serializable {

	/**File to store user data*/
	public static final String storeFile = "games.dat"; 
	/**Array List that holds games of the user */
	private static ArrayList<Game> games = new ArrayList<Game>();


	/**
	 * Adds a game to the arraylist of the games for the single user (in kernel/standard)
	 * 
	 * @assumes Game name to be added is unique
	 * @exception none
	 * @postcondition Adds game to games list
	 * 
	 * @param g The game object to add to the array list of games in backend. 
	 * @return True if successfully added, else false
	 */
	public boolean addGame(Game g){
		for (Game game: this.games){
			if (game.getName().equals(g.getName())){
				return false;
			}
		}
		games.add(g);
		return true;
	}

	/**
	 * Deletes a game from the arraylist of the games for the single user (in kernel/standard)
	 * 
	 * @assumes Game to be deleted may not exist
	 * @exception none
	 * @postcondition Deletes game from games list iff it exists
	 * 
	 * @param in_name Name for the game given by the user. 
	 * @return True if successfully deleted, else false
	 */
	public boolean deleteGame(String name){
		for (int i = 0; i<games.size();i++){
			if (games.get(i).getName().compareTo(name)==0){
				this.games.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the game exists in the backend
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * 
	 * @param gameIn Game Name to search for in backend
	 * @return true a game w/ the same name already exists, else false
	 * 
	 * @author Katiuska
	 */
	public static boolean gameExists(String gameIn){
		for(int i=0; i< games.size(); i++){
			if(games.get(i).getName().equalsIgnoreCase(gameIn)){ 
				return true;
			}
		}
		
		return false;
	}


	/**
	 * Loads Saved Games from document and returns their array list
	 * 
	 * @assumes The file for the games will be found and if not found, java will create it
	 * @postcondition none Since it only returns the array list, it does not set it.
	 * 
	 * @return ArrayList of games if we can load the games from the document
	 * @throws IOException If we cannot read it in
	 * @throws ClassNotFoundException If we cannot find it
	 * 
	 * @author Katiuska
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Game> loadSavedGames() 
			throws IOException, ClassNotFoundException { 
		@SuppressWarnings("resource")
		ObjectInputStream ois = new ObjectInputStream( 
				new FileInputStream(storeFile)); 
		return (ArrayList<Game>)ois.readObject(); 
	} 
	
	
	/**
	 * Saves the games to the backend to the document
	 * 
	 * @assumes File will be found and if not found, java will create it
	 * @postcondition Saves the games to the file
	 * 
	 * @param games List of games to save in the backend
	 * @throws IOException If we have issues finding or loading from the file
	 * @author Katiuska
	 */
	public void saveGames(ArrayList<Game> games) throws IOException { 
		@SuppressWarnings("resource")
		ObjectOutputStream oos = new ObjectOutputStream( 
				new FileOutputStream(storeFile)); 
		oos.writeObject(games); 
		return;
	} 
	

	/**
	 * Gets the game from the arraylist of user's games based on the name the user gave for the game
	 * when he/she saved the game. 
	 * 
	 * @assumes game may not exist
	 * @exception none
	 * @postcondition gets game using name iff it exists
	 * 
	 * @param in_name Name of the game the user gave when he/she saved the game
	 * @return The game the user had saved before if it exists, else return null 
	 */
	public Game getGame(String name){
		for(Game g: this.games){
			if((g.getName().compareTo(name))==0){
				return g;
			}
		}
		return null;
	}

	/**
	 * Returns the games arraylist
	 * 
	 * @assumes Games list exists
	 * @exception none
	 * @postcondition gets games list iff it exists
	 * 
	 * @return The array list stored in backend that has all the games. 
	 */
	public ArrayList<Game> getGameList(){
		return this.games;
	}

	/**
	 * Sets the game list to backend. 
	 * 
	 * @assumes Games list space exists
	 * @exception none
	 * @postcondition sets game list
	 * 
	 * @param games The games list that you want to use in the program
	 */
	public void setGameList(ArrayList<Game> games){
		this.games = games;
	}

	
	
	/**
	 * Gets the list of Games as a string array list 
	 * 
	 * @assumes Games may or may not exist
	 * @exception none
	 * @postcondition none
	 * 
	 * @return ArrayList of strings of the game names in the backend
	 * @author Katiuska
	 */
	public ArrayList<String> getGamesStringArray(){
		ArrayList<String> toReturn= new ArrayList<String>(); 
		
		for(int i=0; i<games.size(); i++){
			toReturn.add(i, games.get(i).getName());
		}
		
		return toReturn;
	}
	
}
