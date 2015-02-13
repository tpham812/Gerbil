package Model;
 
import java.io.Serializable;
import java.util.HashMap;
   


/**
 * Block holds instructions which are the strings of the information user enters for 
 * the gerbil to do
 * Note: the following is the type ennumeration
 * Enumeration{eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), �e�,�c�} 
 * @author Amulya
 */ 
@SuppressWarnings("serial")
public class Block implements Serializable{
	/**Nested instructions go in this adjacency hashmap*/
	HashMap<Integer, Block> nestedBlocks = new HashMap<Integer,Block>();
	int functionNum=-1;
	/** What the conditional is for while, if,else if*/
	String conditional=null;// there's food, there's walls
	/**Enumeration{eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8), 'c','e'} to find type 
	 * Basically tells u what the block corresponsds to*/
	int type=-1; 
	/**What line in the main screen does this block begin at */
	int lineBegin=-1;
	/**What line in the main screen does this block end at*/
	int lineEnd=-1;
	/**Pointer to the parent block in which this block is nested in = makes it easier to go to upper levels */
	Block parent=null;
	/**Number of times to repeat, set by user*/
	int repeat=-1;

	/**Sets the function number to indicate that the instructions are refering to a function
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * 
	 * @param numOfFunction The index number of the function in the arraylist of functions
	 */
	public void setFunctionNum(int numOfFunction){ 
		this.functionNum=numOfFunction;
	}
	
	/**
	 * Gets the function number of the current block so this block refers to a function.
	 * @assumes function number is initialized
	 * @exception none
	 * @postcondition none
	 * @return The function number (same as the index in the array list of funcitons in game)
	 *  
	 */
	public int getFunctionNum(){
		return this.functionNum;
	}
	/**
	 * Sets the parent block of this object
	 * @assumes Valid parameter given
	 * @exception none
	 * @postcondition Parent of the object is changed to the parameter
	 * 
	 * @param b Parent block to set for this object
	 */
	public void setParent(Block b){
		this.parent=b;
	}
	
	/**
	 * Gets the parent of the current node that holds it in the nesting
	 * 
	 * @assumes Assumes parent block is null if it is at the first level in main screen
	 * @exception none
	 * @postcondition none
	 * @return the parent block
	 */
	public Block getParent(){
		return this.parent;
	}
	
	/**
	 * Gets the repeat number of block
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * 
	 * @return The number of times it repeats, if it's a repeat conditional
	 */
	public int getRepeat(){
		return repeat;
	}
	/**
	 * Sets the repeat number of block
	 * @assumes this is a repeat block and valid repeat number given
	 * @exception none
	 * @postcondition The repeat number of the block is changed so it is a repeat block
	 */
	public void setRepeat(int repeat){
		this.repeat=repeat;
	}
	/**
	 * Gets the line number where this block began at
	 *
	 * @assumes assumes block was properly created
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Beginning line of block in main screen 
	 */
	public int getlineBegin(){
		return lineBegin;
	}
	/**
	 * Sets the line number where block begins
	 * 
	 * @assumes block properly created
	 * @postcondition block will have lineBegin of line
	 * @param line int of line to set lineBegin for block
	 */
	public void setlineBegin(int line){
		this.lineBegin=line;
	}
	
	/**
	 * Gets the line number where this block ends at
	 *
	 * @assumes assumes block was properly created
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Ending line of block in main screen 
	 */
	public int getlineEnd(){
		return lineEnd;
	}
	
	/**
	 * Sets the line number where this block ends at
	 * 
	 * @assumes block was properly created and valid parameter entered
	 * @postcondition new lineEnd of block will be set to line
	 * @param line int of line to set lineEnd for block
	 * @return nothing
	 */
	public void setLineEnd(int line){
		this.lineEnd=line;

	}
	/**
	 * Gets the block's type based on innumeration
	 *
	 * @assumes assumes block was properly created and type was initialized
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Type of the block based on block
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * Sets the block's type based on enumeration
	 * 
	 * @assumes block properly created and assumes parameter is valid
	 * @param type Type to set to the block
	 * @return nothing
	 */
	public void setType(int type){
		this.type=type;
	}
	
	/**
	 * Gets the conditional
	 *
	 * @assumes assumes block was properly created
	 * @exception none
	 * @postcondition none
	 * 
	 * @return Conditional, if any, associated with this block
	 */
	public String getCond(){
		return conditional;
	}
	/**
	 * Sets the conditional
	 * 
	 * @assumes block has no conditional so far and valid conditional entered
	 * @exception none
	 * @postcondition none
	 * 
	 * @param conditonal The conditional to set for this Block
	 * @return nothing
	 */
	public void setCond(String conditional){
		this.conditional=conditional;
	}
	
	/**
	 * Gets the hashmap of nested blocks. This structure is useful 
	 * for the loop structure and the conditional statements
	 * 
	 * @assumes nested Blocks exist
	 * @exception none
	 * @postcondition returns nested blocks
	 * 
	 * @return Hashmap of blocks which are for nesting other blocks inside a block.
	 */
	public HashMap<Integer,Block> getNestedBlocks(){
		return this.nestedBlocks;
	}

	/**
	 * Sets the nested blocks field
	 * 
	 * @assuems parameters are valid block
	 * @exception none
	 * @postcondition Changes the nested blocks of the object
	 * @param instructionblocks Nested blocks to set the object's field to
	 */
	public void setNestedBlocks(HashMap<Integer, Block> instructionblocks) {
		this.nestedBlocks=instructionblocks;
		
	}
}


