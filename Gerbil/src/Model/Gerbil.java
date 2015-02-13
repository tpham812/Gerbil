package Model;
 

import java.io.Serializable;
  
/** 
 * The Gerbil Avatar object that stores the information about where the Gerbil is located
 * @author Amulya
 * */
@SuppressWarnings("serial") 
public class Gerbil implements Serializable{
	
	/**The column in front of the gerbil (used to help find the direction gerbil is facing)*/
	private int frontX;  //use this to compare with currX and currY to figure out which direciton gerbil is facing
	/**The row in front of the gerbil (used to help find the direction gerbil is facing)*/
	private int frontY;
	/**The column of the gerbil (used to help find the location and direction gerbil is facing)*/
	private int currX; 
	/**The row of the gerbil (used to help find the location and direction gerbil is facing)*/
	private int currY;
	/**Helps view out to figure out which picture to use in grid */
	private char compass;
	
	/**
	 * Constructor for the object. The gerbil starts off in the bottom left corner of grid 
	 * and facing upwards. ^
	 * 
	 * 
	 * @assumes assumes one gerbil object only for a grid
	 * @exception none
	 * @postcondition creates the gerbil that is in bottom left and is facing above row 
	 * 
	 */
	public Gerbil(){
		this.frontX = 1;
		this.frontY=14;
		this.currX=1;
		this.currY=15;
		this.compass = 'n';
	}
	
	/**
	 * Sets the current direction of Gerbil
	 * 
	 * @assumes assumes it is 'n','s','e',or 'w' only
	 * @exception none
	 * @postcondition compass is set to the parameter
	 * @param c Character to set the compass to = must be 'n','s','e', or 'w'
	 */
	public char setCompass(char c){
		return this.compass=c;
	}
	
	/**
	 * Gets the current direction of Gerbil
	 * 
	 * @assumes assumes it is 'n','s','e',or 'w' only
	 * @exception none
	 * @postcondition none
	 * 
	 * @return X value of Gerbil's direction
	 */
	public char getCompass(){
		return this.compass;
	}
	
	/**
	 * Gets the current X value of Gerbil
	 * 
	 * @assumes assumes in range of valid values
	 * @exception none
	 * @postcondition returns x location that is valid
	 * 
	 * @return X value of Gerbil's location = column of gerbil
	 */
	public int getX(){
		return currX;
	}
	
	/**Gets the current Y value of Gerbil
	 *  
	 * @assumes assumes in range of valid values
	 * @exception none
	 * @postcondition returns y location that is valid
	 * 
	 * @return Y value of Gerbil's Location = row of gerbil
	 */
	public int getY(){
		return currY;
	}
	
	/**Gets the block in front of gerbil's X value 
	 * 
	 * @assumes assumes in range of valid values
	 * @exception none
	 * @postcondition returns front location that is valid
	 * 
	 * @return X value of the block in front of Gerbil
	 */
	public int getFrontX(){
		return frontX;
	}
	
	/**Gets the block in front of gerbil's Y value
	 * 	 
	 * @assumes assumes in range of valid values
	 * @exception none
	 * @postcondition returns front location that is valid 
	 *  
	 * @return Y value of the block in front of Gerbil
	 */
	public int getFrontY(){
		return frontY;
	}
	
	/**
	 * Sets Gerbil's X location
	 * 
	 * @assumes assumes valid value is given so it has been varified
	 * @exception none
	 * @postcondition changes x value of gerbil that is valid
	 * 
	 * @param i The integer to set Gerbil's X current location
	 */
	public void setX(int i){
		this.currX= i;
	}
	
	/**
	 * Sets Gerbil's Y location
	 * 
	 * @assumes assumes valid value is given so it has been varified
	 * @exception none
	 * @postcondition changes y value of gerbil that is valid
	 * 
	 * @param i The integer to set Gerbil's Y current location
	 */
	public void setY(int i){
		this.currY= i;
	}
	
	/**
	 * Sets Gerbil's front block's X value
	 * 
	 * @assumes assumes valid value is given so it has been varified
	 * @exception none
	 * @postcondition changes frontx value of gerbil that is valid
	 * 
	 * @param i The integer to set Gerbil's front block's X value
	 */
	public void setFrontX(int i){
		this.frontX= i;
	}
	
	/**
	 * Sets Gerbil's front block's Y value
	 * 
	 * @assumes assumes valid value is given so it has been varified
	 * @exception none
	 * @postcondition changes front y value of gerbil that is valid
	 * 
	 * @param i The integer to set Gerbil's front block's Y value
	 */
	public void setFrontY(int i){
		this.frontY= i;
	}
	
}

