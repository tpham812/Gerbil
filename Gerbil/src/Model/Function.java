package Model;
 
import java.io.Serializable;
import java.util.HashMap;
   

/**
 * Function class to hold user created functions
 * Cannot edit functions. can only create and use them. If 
 * a function is deleted, then we parse the code to see if the function
 * deleted will be used. 
 * @author Amulya 
 */
@SuppressWarnings("serial")
public class Function implements Serializable{
	
	/**Name of the funcion*/
	String name;
	/**Instructions in the function/body of the function in form of Hashmap of blocks*/
	HashMap<Integer,Block> blockInstructions;
	
	/**
	 * Creates the user created function
	 * 
	 * @assumes valid name and name must be verified to not exist for other functions
	 * @exception none
	 * @postcondition name of the function object is changed
	 * 
	 * @param name Name of the function
	 */
	public Function(String name){
		this.name=name;
	}
	
	/**
	 * Gets the name of the function that the user gave to create it
	 * 
	 * @assumes name of function exists and is unique
	 * @exception none
	 * @postcondition returns function name that is unique
	 * 
	 * @return Name of the function
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the instructions/body of the function in form of Hashmap of blocks
	 * 
	 * @assumes blocks of instructions exist
	 * @exception none
	 * @postcondition Retrieves Hashmap of blocks that are are the instructions blocks
	 * 
	 * @return Body of the function so the instructions Hashmap in form of blocks 
	 */
	public HashMap<Integer,Block> getBlockInstructions(){
		return this.blockInstructions;
	}
	/**
	 * Sets the Block's Instructions to the parameter
	 * 
	 * @assumes Valid parameter provided
	 * @exception none
	 * @postcondition Block's instructions changed
	 */
	public void setBlockInstructions(HashMap<Integer,Block> blockinstructions){
		this.blockInstructions= blockinstructions;
	}
}

