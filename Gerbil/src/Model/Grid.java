package Model;

import java.io.Serializable;
import java.util.HashMap;
  


/** 
 * The Grid class that holds the information about how the grid looks by using characters to represent
 * location of fruit and gerbil. The default location of avatar is bottom left and 
 * water can (the goal) is top right. 
 * 
 * Index of characters on grid:
 * k = Pumpkin, p for pear, a for apple, w for Wall, t for water container
 * 
 * Gerbil object already has it's own locations but if Gerbil does step
 * on a square with fruit, the character for the fruit will become upper 
 * case as to not lose data for the fruit's location
 * 
 * Note: In Java, for 2d Arrays (0,0) is in the top left of array.
 * @author Amulya,Truong
 * */
public class Grid implements Serializable{

	/**Serial Version UID to stop the error we get. = used during deserialization to verify */
	private final long serialVersionUID = 1L;
	/**17x17 grid size because the outer edges will have walls  
	 * Grid does not have gerbil location = gerbil object has the location info.*/
	private char[][] grid;
	/**Temporary grid to help see if fruit are reachable */
	private int[][] visited;
	/**Holds the fruit location to verify if fruits are reachable or not */
	HashMap<String,Character> fruitCoordinates = new HashMap<String, Character>(); 

	/**
	 * Creates a random grid that can still be completed (i.e. no walls blocking path 
	 * of gerbil from all sides) for standard version 
	 * In Fancy, we will have the user built screen that will allow user to 
	 * customize and build the grid.
	 *  
	 * @assumes random grid needs to be created
	 * @exception none
	 * @postcondition creates a grid we can access and can still play
	 *  @author Amulya
	 */
	public Grid(int rows, int columns){	
		grid = new char[rows][columns];
		visited = new int[rows][columns];
		initGrid();
		//printGrid(); //testing method
	}

	/**
	 * Initializes the grid field in this class by making sure the grid is playable
	 * meaning the gerbil has ways to get to every fruit and water can. 
	 * 
	 * @assumes grid object has been created but is empty
	 * @exception none
	 * @postcondition populates the grid object with walls, fruits, and water can placed
	 * in random location with a valid path to them making the grid playable
	 * @author Amulya
	 */
	private void initGrid(){
		do{//put walls all around and init 0s = empty grid area.
			resetVisited();
			fruitCoordinates.clear();
			createEmptyGrid();
			randomGrid(); //places walls and fruit
			checkValidFruits(grid.length-2, 1);
			resetVisited();
		}while(!(hasValidPath(grid.length-2,1)) //start from bottom left corner = gerbil location 
			 || (fruitCoordinates.size()!=0)); //reach all fruit
	}
	
	/**
	 * Creates the grid and makes sure it is empty but has walls around it before we try to randomize it.
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition clear grid except for walls in the four sides of walls and gerbil temporarily placed as 'g'
	 * @author Truong
	 */
	private void createEmptyGrid() {
		for (int i = 0; i<grid.length;i++){ //have to clear the grid before randomizing it
			for (int j = 0; j<grid[0].length;j++){ 
				if((j==0)||(j==grid[0].length-1) || (i==0)||(i==grid.length-1)){
					grid[i][j]='w'; //wall surrounds entire grid. 
				}else{
					grid[i][j] = '0'; //empty grid area.
				}
			}
		}
		grid[grid.length-2][1] = 'g';
	}

	/**
	 * Creates a random grid while trying to make sure the grid is playble (i.e. no walls 
	 * completely surround or obstruct path of gerbil from start to end)
	 * Also places food on grid
	 * 
	 * @assumes random grid is needed and just changes the field
	 * @exception none
	 * @postcondition returns randomized grid with food and walls and water placed and water can at top left
	 * @author Amulya
	 */
	private void randomGrid(){
		grid[1][grid[0].length-2]='t'; //place water can
		for (int b = 0; b <2*(grid.length-2); b++){//put in 30 walls as obstacles 
			int R = (int)(Math.random()*(grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if ((grid[R][S]=='0')){ 
				//if empty and not in location of water can or gerbil
				grid[R][S]='w';
			} 
			else{
				b--; //do not increment b without placing the total walls needed!
			}
		}		
		placeFruitsRandomly('k');//pumpkin
		placeFruitsRandomly('p'); //pear
		placeFruitsRandomly('a'); //apple
		grid[grid.length-2][1] = '0';
	}

	/**
	 * Places Fruit represented by character c onto the grid location that are empty. 
	 * The number of fruit is (2 * grid row that can be accessed by avatar )/ 3)
	 * 
	 * @assumes Valid character is entered and there are empty locations on grid
	 * @exception none
	 * @postcondition grid now has fruits placed onto it with character c representing the fruit
	 * 
	 * @param c The character representing the fruit to place onto the grid.
	 * @author Amulya 
	 */
	private void placeFruitsRandomly(char c){
		int numberOfFruit = (int)((2.0*(grid.length-2.0)/3.0));
		int count = 0;
		while(count < numberOfFruit){
			int R = (int)(Math.random()*(grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if (grid[R][S]=='0'){ //if it is empty, add the fruit
				grid[R][S]=c;
				fruitCoordinates.put(Integer.toString(R) + Integer.toString(S), c);
				count++;
			}
		}
	}

	/**
	 * Get's the contents of grid's x and y location
	 * 
	 * @assumes assumes valid square content according to characters that can go on grid
	 * @exception none
	 * @postcondition returns character that is valid
	 * 
	 * @param y the row specified
	 * @param x the column specified
	 * @return the character at the location if special, else returns " "
	 * @author Amulya
	 */
	public char getSquareContent(int y, int x){
		return grid[y][x]; //y is row and x is column!
	}

	/**
	 * Checks if the fruit locations are all reachable using the visited grid and the fruit coordinates storage
	 * 
	 * @assumes Fruits have been placed but not validated if they can be reached by Avatar's starting position or not
	 * @exception none
	 * @postcondition If they can be reached fruit coordinates will not have any nodes else it will not be empty
	 * 
	 * @param Y The row where we start at (Avatar's row)
	 * @param X The column where we start at (Avatar's column)
	 * @author Truong, Tested by Amulya
	 * 
	 */
	private void checkValidFruits(int Y, int X) {
		if (grid[Y][X]=='w' || visited[Y][X] == 1){ //wall so cannot move more in that direction
			return;
		}
		else {
			visited[Y][X] = 1;
			if(grid[Y][X] == 'k' || grid[Y][X] == 'p' || grid[Y][X] == 'a') {//get to water container so has valid path
				fruitCoordinates.remove(Integer.toString(Y) + Integer.toString(X));
			}
			checkValidFruits(Y+1,X);  
			checkValidFruits(Y-1,X);
			checkValidFruits(Y,X+1);
			checkValidFruits(Y,X-1); 
		}
	}
	/**
	 * removes fruit from Y and X coordinates/sets cell to '0'
	 * @assumes Fruit exists on specified Y and X coordinate
	 * @exception none
	 * @param Y The row where fruit to be removed is located
	 * @param X The column where fruit to be removed is located
	 * @author Amulya
	 */
	public void removeFruit(int Y, int X){
		grid[Y][X]='0';
	}
	/**
	 * Checks if grid created in randomGrid is valid. ie. valid path exists
	 * from start to finish of course. The course has to have a path from start to finish
	 * to be completable. 
	 * 
	 * @assumes assumes grid has been made but we don't know if it is playable
	 * @exception none
	 * @postcondition grid is playable so validated as such
	 * 
	 * @param Y The Row to check 
	 * @param X The Column to check
	 * @return True if the grid created does have a runnable/completable course, else false
	 * @author Truong, Tested by Amulya
	 */
	private boolean hasValidPath(int Y, int X){
		if (grid[Y][X]=='w' || visited[Y][X] == 1) { //wall so cannot move more in that direction
			return false;
		}else if(grid[Y][X] == 't') {//get to water container so has valid path
			return true;
		}else {
			visited[Y][X] = 1;
			return hasValidPath(Y-1,X) ||  
					hasValidPath(Y+1,X) ||
					hasValidPath(Y,X+1) ||
					hasValidPath(Y, X-1);
		}
	}

	/**
	 * Prints the grid 
	 * 
	 * @assumes debugging reasons
	 * @exception none
	 * @postcondition nothing
	 * @author Amulya
	 */
	public void printGrid(){
		for (int i =0; i<grid.length;i++){
			for (int j = 0; j<grid[0].length;j++){
				System.out.print(grid[i][j]+" "); //prints out row in one line
			}
			System.out.println(); //new line for new row. 
		}
	}

	/**
	 * Resets the visited grid to 0 so we can use it again for fruit reachability checking
	 * @assumes Visited Array was used and modified to not be 0 at all locations
	 * @exception none
	 * @postcondition Visited array is empty
	 * @author Truong
	 */
	private void resetVisited() {
		for (int i = 0; i < visited.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				visited[i][j] = 0;
			}
		}
	}
	
	/**
	 * Returns Grid to caller
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Grid 
	 * @author Amulya
	 */
	public char[][] getGridRepresentation() {
		return grid;
	}
	/**
	 * returns total number of fruits on this grid
	 */
	public int countFruits(){
		int count=0;
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j]=='k' || grid[i][j]=='p' || grid[i][j]=='a'){
					count++;
				}
			}
		}
		return count;
	}
}