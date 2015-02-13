package Control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Model.Block;
import Model.Function;
import Model.Game;
import Model.Gerbil;


/**
 * Controller class will make all necessary modifications to data in order to send it to the control. 
 * It will concern itself with the data of one user and one game at any given point, provided
 *  by the Backend.
 *  @author Amulya,Leslie
 */
public class Controller {
	/**Holds the current game being played */
	private Game gamePlaying;
	/**Final list of blocks to run to play animation */
	ArrayList<String> finalblocks= new ArrayList<String>();
	/**Line Numbers to display for the final blocks when play is clicked */
	ArrayList<Integer> finalblocksLineNumbers= new ArrayList<Integer>();
	/**Holds the function blocks when they are still being created */
	HashMap<Integer,Block> tempFunctionBlockInstructions= new HashMap<Integer,Block>();
	/**Used for create blocks since only one nesting level and it's the parent */
	public Block parent = null;
	/**Used for create blocks since it is only one nesting level and it is the child*/
	public Block userCodingNow = null;
	/**Used for creating Function blocks for nesting levels and it's the parent */
	Block parentFunction = null;
	/**Used for creating Function blocks for nesting levels and it's the child*/
	Block userCodingNowFunction = null;



	/**Temporary grid used to check for parsing/compiling*/
	char[][] tempgrid= new char[17][17];
	/**Temporary gerbil used to check for parsing/compiling*/
	Gerbil tempgerbil= new Gerbil(); //Gerbil used only for "parsing/compiling"
	/**is it a function or not*/
	boolean isFunction=false;
	boolean isFunctionLine=false;
	int parseFunctionLine;

	/**block counting*/
	int countblocks=1;


	/**
	 * Method used for testing parser/compiler only 
	 * So not part of code 
	 * @author Leslie
	 */
	public void testingStuff() { 
		tempgerbil = gamePlaying.getGerbil();
		initTempGrid();
	}

	/**
	 * Returns string array of the un-finished isntructions to display in the Jlist for the conditionals screen
	 * 
	 * @assumes There may or may not be unfinished insertions
	 * @exception none
	 * @postcondition none
	 * @return String array of unfinished instructions returned
	 * @author Amulya
	 */
	public String[] getUnFinIns(){ // unfinished insertion
		Block tempPar=null;
		ArrayList<String> ins = new ArrayList<String>();

		for(Block p = this.userCodingNow; p!=null; p=p.getParent()){
			tempPar = p;
		}//get to main nesting level
		if(tempPar==null){
			return ins.toArray(new String[ins.size()]);
		}else{
			printNotDoneBlock(0,tempPar.getNestedBlocks(), ins);
			return ins.toArray(new String[ins.size()]);
		}
	}

	/**
	 * Returns string array of the un-finished functions to display in the Jlist for the userFunctions screen
	 * 
	 * @assumes There may or may not be unfinished functions insertions
	 * @exception none
	 * @postcondition none
	 * @return String array of unfinished functions returned
	 * @author Amulya
	 */
	public String[] userFunctionShowJList(){
		ArrayList<String> temp = new ArrayList<String>(); 
		getJList(0,this.tempFunctionBlockInstructions,temp);
		String [] toReturn = new String[temp.size()];

		for(int i=0; i< toReturn.length; i++){
			toReturn[i] = temp.get(i);
		}

		return toReturn;
		
	}
	
	/**
	 * Returns string array of the un-finished functions's inside code to display in the Jlist for the conditionals screen from the
	 * user functions screen
	 * 
	 * @assumes There may or may not be unfinished functions insertions
	 * @exception none
	 * @postcondition none
	 * @return String array of unfinished functions returned
	 * @author Amulya
	 */
	public String[] FunctionUnFin(){
		Block tempPar=null;
		ArrayList<String> ins = new ArrayList<String>();
		for(Block p = this.userCodingNowFunction; p!=null; p=p.getParent()){
			tempPar = p;
		}//get to main nesting level

		if(tempPar!=null){
			printNotDoneBlock(0,tempPar.getNestedBlocks(), ins);
		}
			
		return ins.toArray(new String[ins.size()]);
	}
	
	
	/**
	 * Returns string arraylist of the hashmap of blocks provided
	 * 
	 * @assumes tab starts as 0
	 * @exception none
	 * @postcondition Modifies the list to inclue the block's info in string form
	 * @param tab Given as 0 so we start from the inner most nesting level when it is printing as a string
	 * @param blocks Blocks you want to turn into readable strings
	 * @param list List you want to insert the instrings into so this is the final answer
	 * @author Amulya
	 */
	public void printNotDoneBlock(int tab, HashMap<Integer,Block> blocks, ArrayList<String> list){
		int type;
		String tabStr="";
		for(int i =0; i<tab*5; i++){
			tabStr+=" ";
		} 
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			String lBeg = Integer.toString(block.getlineBegin());
			//list.add(Integer.toString(block.getlineBegin()));
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				list.add(lBeg +" "+tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				list.add(lBeg +" "+tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				list.add(lBeg +" "+tabStr+"Move");
			}else if(type==3){ //if
				list.add(lBeg +" "+tabStr+"If "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==4){ //else if
				list.add(lBeg +" "+tabStr+"ElseIf "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==5){//else
				list.add(lBeg +" "+tabStr+"Else ");
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==6){//while
				list.add(lBeg +" "+tabStr+"While "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");;
				}
			}else if(type==7){//repeat
				list.add(lBeg +" "+tabStr+"Repeat "+block.getRepeat());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				list.add(lBeg +" "+tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab*5; j++){//reset the tabs
				tabStr+=" ";
			}
		}
	}

	/**
	 * Returns string array of the hashmap of the blocks to display on the play screen
	 * 
	 * @assumes game playing's blocks may or may not have things to print
	 * @exception none
	 * @postcondition none
	 * @author Amulya
	 */
	public String[] JListString(){
		ArrayList<String> temp = new ArrayList<String>(); 
		getJList(0,this.gamePlaying.getBlocks(),temp);
		String [] toReturn = new String[temp.size()];

		for(int i=0; i< toReturn.length; i++){
			toReturn[i] = temp.get(i);
		}

		return toReturn;
	}
	
	/**
	 * Returns string array of functions created by user
	 * 
	 * @assumes User defined functions may or may not exist
	 * @exception none
	 * @postcondition none
	 * @author Amulya
	 */
	public String[] JListFunctions(){ 
		String [] toReturn = new String[this.gamePlaying.functions.size()];

		for(int i=0; i< this.gamePlaying.functions.size(); i++){
			toReturn[i] = this.gamePlaying.functions.get(i).getName();
		}

		return toReturn;
	}


	/**
	 * modifies the list to be a string arraylist of the hashmap of blocks provided
	 * 
	 * @assumes tab starts as 0
	 * @exception none
	 * @postcondition Modifies the list to inclue the block's info in string form
	 * @param tab Given as 0 so we start from the inner most nesting level when it is printing as a string
	 * @param blocks Blocks you want to turn into readable strings
	 * @param list List you want to insert the instrings into so this is the final answer
	 * @author Amulya
	 */
	public int getJList(int tab, HashMap<Integer,Block> blocks, ArrayList<String> list){
		int type;
		String tabStr="";
		for(int i =0; i<tab*5; i++){
			tabStr+=" ";
		} 
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			String lBeg = Integer.toString(block.getlineBegin());
			//list.add(Integer.toString(block.getlineBegin()));
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				list.add(lBeg+" "+tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				list.add(lBeg+" "+tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				list.add(lBeg+" "+tabStr+"Move");
			}else if(type==3){ //if
				list.add(lBeg+" "+tabStr+"If "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==4){ //else if
				list.add(lBeg+" "+tabStr+"ElseIf "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==5){//else
				list.add(lBeg+" "+tabStr+"Else ");
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==6){//while
				list.add(lBeg+" "+tabStr+"While "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");;
				}
			}else if(type==7){//repeat
				list.add(lBeg+" "+tabStr+"Repeat "+block.getRepeat());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				list.add(lBeg+" "+tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab*5; j++){//reset the tabs
				tabStr+=" ";
			}
		}
		return 0;
	}

	//////////////////////////////////DEBUGGIN METHODS BEGIN/////////////////////////////////////
	/**
	 * returns finalblocks arraylist for parse/compiling
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * @return String arraylsit of final blocks
	 * @author Leslie
	 */
	public ArrayList<String> getFinalBlocks(){
		return this.finalblocks;
	}
	
	/**
	 * returns integer arraylist for parse/compiling to show highlighting
	 * 
	 * @assumes none
	 * @exception none
	 * @postcondition none
	 * @return String arraylsit of final blocks line numbers
	 * @author Leslie
	 */
	public ArrayList<Integer> getFinalBlocksLineNumbers(){
		return this.finalblocksLineNumbers;
	}
	
	/**
	 * Prints the tempgrid in console to see values
	 * 
	 * @assumes debugging reasons
	 * @exception none
	 * @postcondition nothing
	 * @author Leslie
	 */
	public void printTempGrid(){
		for (int i =0; i<tempgrid.length;i++){
			for (int j = 0; j<tempgrid[0].length;j++){
				System.out.print(tempgrid[i][j]+" "); //prints out row in one line
			}
			System.out.println(); //new line for new row. 
		}
	}


	/**
	 * Gets the current game that is playing for debugging reasons
	 * @assumes game playing is initialized properly
	 * @exception none
	 * @postcondition none
	 * @return Current Game playing
	 * @author Amulya
	 */
	public Game getCurrGame(){
		return gamePlaying;
	}

	/**
	 * prints string of the hashmap of blocks provided
	 * 
	 * @assumes tab starts as 0
	 * @exception none
	 * @postcondition none
	 * 
	 * @param tab The indentation level of the block to be printed out
	 * @param blocks Takes hashmap to print out the contents of the hashmap
	 * eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8)
	 * @author Amulya
	 */
	public void printBlocks(int tab, HashMap<Integer,Block> blocks){
		int type;
		String tabStr="";
		for(int i =0; i<tab; i++){
			tabStr+='\t';
		}
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			System.out.print(block.getlineBegin());
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				System.out.println(tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				System.out.println(tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				System.out.println(tabStr+"Move");
			}else if(type==3){ //if
				System.out.println(tabStr+"If "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==4){ //else if
				System.out.println(tabStr+"ElseIf "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==5){//else
				System.out.println(tabStr+"Else ");
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==6){//while
				System.out.println(tabStr+"While "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");;
			}else if(type==7){//repeat
				System.out.println(tabStr+"Repeat "+block.getRepeat());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				System.out.println(tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab; j++){//reset the tabs
				tabStr+='\t';
			}
		}

	}
	//////////////////////////////////DEBUGGIN METHODS END////////////////////////////////////////////
	/**
	 * First View calls this, and then when user has entered the information, they will call
	 * finishCreateBlocks method if the user clicks ok, otherwise, click cancelBlock, if user clicks cancel
	 * 
	 * @assumes if same function is called with c, the function was cancelled at some point so we ignore what we have currently 
	 * @assuems if same function is called with e, the function was finished so we add to the list
	 * @exception none
	 * @postcondition creates blocks if valid. 
	 * 
	 * @param type Enumerated type of the object
	 * @param begin The beginLine fo the object so the line number it starts at
	 * @param numLines is the number of lines of the code entered since this method is called several times
	 * @param cond This is for while and if statements AND it also sends the integer for Repeat!!
	 * @return integer to indicate error or went as planned if 0. See below for values. 
	 * 0 is good
	 * 1 is ///////////////////ERROR: Number of repetitions was not selected!//////////////
	 * 2 is ///////////ERROR: Function not selected////////////////////////////
	 * 3 is ///////////////ERROR: Illegal funciton entered!!!!!/////////////
	 * 4 is //////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
	 * 5 is ////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
	 */
	public int createBlocks(int type, int begin, int numLines, String cond){
		if(type=='c'){//tried to create block but canceled so cancel the block we have currently
			this.userCodingNow=this.parent;
			return 0;
		}else if((type=='e') && (this.userCodingNow!=null)){//finished coding for the block so put into the correct spot
			this.userCodingNow.setLineEnd(begin+numLines-1);	
			int currType= this.userCodingNow.getType();
			if(currType==7){//repeat block so turn cond into int and store in repeat
				int repeat=-1;
				if(cond==null){
					
					///////////////////ERROR: Number of repetitions was not selected!//////////////
					return 1;
				}else{ //no need to check if cond is int or not since view will provide int for it 
					repeat =Integer.valueOf(cond);
				}
				this.userCodingNow.setRepeat(repeat);

			}else if(currType==8){//user-defined FUNCTION block so find int for cond and store int in functionNum
				int functionNum=-1;
				if(cond==null){
					
					///////////ERROR: Function not selected////////////////////////////
					return 2;
				}else{
					for(int i=0; i<gamePlaying.functions.size(); i++){
						if(gamePlaying.functions.get(i).getName().equals(cond)){
							functionNum= i;
							break;
						}
					}
				}
				if(functionNum==-1){ //despite searching for it!! 
				
					///////////////ERROR: Illegal funciton entered!!!!!/////////////
					return 3;
				}
				this.userCodingNow.setFunctionNum(functionNum);	
			}else if(currType==3 || currType==6){ //if and while loops
				this.userCodingNow.setCond(cond);
			}else if(currType==4){ //for else if, we need to check if parent == if => parent cannot be null
				this.userCodingNow.setCond(cond);
			}

			if(parent==null){ //insert into gamePlaying.blocks and cascade!!!
				/*So insert only happens to main, the rest are edit and delete so 
				we first check if the begin line we are given already exsits in the 
				main, if it does, we cascade, then insert to not delete the current 
				block at that number. else we simply add = works for both between lines 
				and end of code.*/
				for (int key: this.gamePlaying.getBlocks().keySet()){
					if(key==begin){
						cascadeNumberingChanges(begin, this.userCodingNow.getlineEnd()-this.userCodingNow.getlineBegin()+1, this.userCodingNow, gamePlaying.getBlocks(),true);
						this.gamePlaying.getBlocks().put(begin, this.userCodingNow);
						this.userCodingNow=null;
						return 0;
					}
					
				}//get past this means, end of lines!
				this.gamePlaying.getBlocks().put(begin, this.userCodingNow);
			} //we ended this so parent is now the currBlock coded
			this.userCodingNow=parent;
			if(this.parent!=null){
				this.parent=this.userCodingNow.getParent();
			}
			return 0;
		}else{ //first time making a block
			Block b = new Block();
			b.setlineBegin(begin);
			b.setType(type);
			if(type=='e'){
				return 0;
			}
			if((type==4) || (type==5)){ //SPECIAL FOR ELSE IF AND ELSE!!!
				Block parIf = null; 
				if(this.userCodingNow==null){ //find in main level = no nesting
					for(int k: this.gamePlaying.getBlocks().keySet()){
						if(this.gamePlaying.getBlocks().get(k).getType()==3 && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = gamePlaying.getBlocks().get(k);
						}
					}
				}else{ //find in parent's level!
					for(int k: this.userCodingNow.getNestedBlocks().keySet()){
						int tempTP = this.userCodingNow.getNestedBlocks().get(k).getType();
						if((tempTP==3 || tempTP==4) && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.userCodingNow.getNestedBlocks().get(k);
						}
					}
				}
				if(parIf==null){
				
					//////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
					//not valid cuz the parent for else if and else has to be if!!! so tell them not valid code
					return 4;
				}else if(parIf.getlineEnd()+1!=begin){
					
					//So we are trying to insert the else if or else after the if for else if OR if/else if for ELSE!!!
					////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
					return 5;
				}else{
					if(this.userCodingNow!=null){ //curr not null so we need to set current to user playing and parent to curr
						b.setParent(parIf.getParent()); //set else if or else stuff's parent to the parent of if block!!
						this.parent=parIf.getParent();
						this.userCodingNow=b;
						if(parent!=null){ //insert into parent's block
							if(parent.getNestedBlocks().get(begin)==null){ //nothing there put
								parent.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
							}else{ 
								
								cascadeNumberingChanges(begin, 1, b, gamePlaying.getBlocks(), true); //cascade first then put into it!!
								parent.getNestedBlocks().put(begin, b);
							}
						}
					}else{
						b.setParent(parIf.getParent());
						this.userCodingNow=b; //don't put in if in main;s nesting
					}
				}
			}else{
				if(this.userCodingNow!=null){ //curr not null so we need to set current to user playing and parent to curr
					b.setParent(this.userCodingNow);
					this.parent=this.userCodingNow;
					this.userCodingNow=b;
					if(parent!=null){ //insert into parent's block
						if(parent.getNestedBlocks().get(begin)==null){ //nothing there put
							parent.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
						}else{ 
							
							cascadeNumberingChanges(begin, 1, b, gamePlaying.getBlocks(), true); //cascade first then put into it!!
							parent.getNestedBlocks().put(begin, b);
						}
					}
				}else{
					this.userCodingNow=b; //don't put in if its in main's nesting
				}
			}
			return 0;
		}
	}
	
	/**
	 * Deletes/clears all blocks in the game
	 * @assumes We want to clear all
	 * @exception none
	 * @postcondition game playing's blocks are all destroyed
	 * @author Amulya
	 */
	public void clearBlocks(){
		gamePlaying.getBlocks().clear();
	}

	/**
	 * initializes temp grid by copying values of game grid
	 * 
	 * @assumes We want to use temp grid to show the animation and test it out
	 * @exception none
	 * @postcondition temp grid has all values that real grid has
	 * @author Leslie
	 */
	public void initTempGrid(){
		for(int i=0; i<17; i++){
			for(int j=0; j<17; j++){
				tempgrid[i][j]= gamePlaying.getGrid().getSquareContent(i, j);
			}
		}
	}


	/**
	 * Creates a new Game object
	 * 
	 * @assumes Provided name must be validated for uniqueness/validity
	 * @exception none
	 * @postconditions Iff unique/valid game name provided, new Game object created
	 * 
	 * @param name User provided game name, must be unique/valid
	 * @return newly created and instantiated Game object
	 * @author Leslie
	 */
	public void createGame(String name){
		this.gamePlaying = new Game(name);
	}

	/**
	 * Determines whether or not game name provided is unique and contains valid characters
	 * 
	 * @assumes Provided data must be validated
	 * @exception none
	 * @postconditions Determines if data provided is valid
	 * 
	 * @param name user provided game name
	 * @return false/true; false if invalid game name, true if valid game name
	 * @author Leslie
	 */
	public int validGameName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				return 1;

			}
		}
		ArrayList<Game> gamelist= Start.StartGerbil.backend.getGameList();
		for(int j=0; j<gamelist.size();j++){
			if(gamelist.get(j).getName().equals(name)){
				return 2;
			}
		}
		return 3;
	}


	/**
	 * sort key values in ascending order
	 * 
	 * @assumes Provided data must be validated
	 * @exception none
	 * @postconditions sorts the parameter provided
	 * 
	 * @param keylist ArrayList of integers
	 * @return the parameter but sorted in increasing order
	 * @author Leslie
	 */
	public ArrayList<Integer> sortKeys(ArrayList<Integer> keylist){
		Collections.sort(keylist);
		return keylist;
	}
	
	/**
	 * resets the tempgrid to original values and tempgerbil to original position
	 * 
	 * @assumes we want to put the temp grid and gerbil back to the original positions
	 * @exception none
	 * @postconditions returns temp grid and temp gerbil to their original locations
	 * 
	 * @author Leslie
	 */
	public void resetTempGrid(){
		initTempGrid();
		this.tempgerbil = new Gerbil();
	}
	
	/**
	 * returns tempgerbil
	 * @assumes none
	 * @exception none
	 * @postconditions none
	 * 
	 * @return the temp Gerbil object
	 * @author Leslie
	 */
	public Gerbil getTempGerbil(){
		return this.tempgerbil;
	}
	
	/**
	 * runs the user written code by first
	 * compiling blocks (finalblocks arraylist) using tempgrid and tempgerbil
	 * to show what is happening step by step
	 * until error/ends at goal
	 * 	  
	 * @assumes Provided data must be validated and sees if there are errors in code/ what gerbil does
	 * @exception none
	 * @postconditions runs user written code by compiling
	 * @return int- (0)-win the game 
	 * (1)-error eating/no food on square trying to eat
	 * (2)-error wall/cannot move forward bc there is wall
	 * (3)-error turning left OR miscellaneous error on runBlocks()
	 * (4)-error failure to reach goal/water
	 * 
	 * (-1)-error in parsing/could not parse
	 * (-2)-error INFINITE LOOP 
	 * @author Leslie
	 */
	public int runBlocks(){
		int success=compileBlocks();
		resetTempGrid();
		if(success==-1){// error in parsing
			return -1;
		}
		if(success==-2){ // infinite loop error
			return -2;
		}
		ArrayList<String> templist= new ArrayList<String>();
		ArrayList<Integer> templistLineNumbers= new ArrayList<Integer>();
		String command;
		for(int i=0; i<finalblocks.size(); i++){
			command=finalblocks.get(i);
			if(command.equals("Eat")){
				boolean eat= eat(tempgerbil.getX(), tempgerbil.getY(),tempgrid);
				if(eat==false){
					//errorEat() dialogue box??;
					int error=0;
					if(i==0){
						error=-1;
					}
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
						templistLineNumbers.add(finalblocksLineNumbers.get(j));
						error=j;
					}
					templistLineNumbers.add(finalblocksLineNumbers.get(error+1));
					finalblocks= templist;
					finalblocksLineNumbers= templistLineNumbers;
					return 1;
				}
			}else if(command.equals("Turn Left")){
				boolean turnleft= turnLeft(tempgerbil);
				if(turnleft==false){
					//error turning left, this shouldn't ever happen
					int error=0;
					if(i==0){
						error=-1;
					}
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
						templistLineNumbers.add(finalblocksLineNumbers.get(j));
						error=j;
					}
					templistLineNumbers.add(finalblocksLineNumbers.get(error+1));
					finalblocks= templist;
					finalblocksLineNumbers= templistLineNumbers;
					return 3;
				}
			}else if(command.equals("Move Forward")){
				boolean moveforward= moveForward(tempgerbil);
				if(moveforward==false){
					//errorWall() dialogue box??
					int error=0;
					if(i==0){
						error=-1;
					}
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
						templistLineNumbers.add(finalblocksLineNumbers.get(j));
						error=j;
					}
					templistLineNumbers.add(finalblocksLineNumbers.get(error+1));
					finalblocks= templist;
					finalblocksLineNumbers= templistLineNumbers;
					return 2;
				}else{
					if(isthereWater(tempgerbil.getX(), tempgerbil.getY())){
						//YOU WIN THE GAME dialogue box??
						for(int j=0; j<=i; j++){
							templist.add(finalblocks.get(j));
							templistLineNumbers.add(finalblocksLineNumbers.get(j));
						}
						finalblocks= templist;
						finalblocksLineNumbers= templistLineNumbers;
						return 0;
					}
				}
			}else{
				int error=0;
				if(i==0){
					error=-1;
				}
				for(int j=0; j<i; j++){
					templist.add(finalblocks.get(j));
					templistLineNumbers.add(finalblocksLineNumbers.get(j));
					error=j;
				}
				templistLineNumbers.add(error+1);
				finalblocks= templist;
				finalblocksLineNumbers= templistLineNumbers;
				return 3;
			}
		}
		//error Did not reach water
		return 4;
	}

	
	/**
	 * goes through user coded blocks and stores commands in arraylist finalblocks in the order and
	 * number of times they will be executed for play
	 * (compiles blocks)
	 * @assumes Provided data must be validated and sees if there are errors in code/ what gerbil does
	 * @exception none
	 * @postconditions runs user written code by compiling
	 * @return int if there is an error
	 * @author Leslie
	 */
	public int compileBlocks(){
		//clears finalblocks arraylist in case it's not empty
		finalblocks.clear();
		finalblocksLineNumbers.clear();
		//resets tempgrid and tempgerbil to original states
		resetTempGrid();
		HashMap<Integer,Block> blocklist= gamePlaying.getBlocks();
		ArrayList<Integer> keylist= new ArrayList<Integer>();

		//initialize visited hashmap and arraylist of keys
		//visited= new HashMap<Integer,Boolean>();
		for(Entry<Integer,Block> entry: blocklist.entrySet()){
			//visited.put(entry.getKey(), false);
			keylist.add(entry.getKey());

		}
		//get sort list of keys	
		ArrayList<Integer> sortedkeys= sortKeys(keylist);
		//parse unvisited blocks
		for(int i=0; i<sortedkeys.size(); i++){
			Block block= blocklist.get(sortedkeys.get(i));
			int success=parseBlock(block);
			if(success==-1){
				return -1;   //error in parsing
			}
			if(success==-2){//infinite loop error
				return -2;
			}
			if(success==1){//if or else if so skip rest of else if or else statements
				if(i+1<sortedkeys.size()){
					int j;
					for(j=i+1; j<sortedkeys.size(); j++){
						if(blocklist.get(sortedkeys.get(j)).getType()!=4 || blocklist.get(sortedkeys.get(j)).getType()!=5){
							j=j-1;
							break;
						}
					}
					i=j;
					if(blocklist.get(sortedkeys.get(i)).getType()==4 || blocklist.get(sortedkeys.get(i)).getType()==5){
						break; //this means last block is else if or else block
					}
				}
			}
			//}
		}
		return 0;
	}

	/**
	 * Will parse blocks created by the user and store in ArrayList finalblocks for play
	 * 
	 * @assumes Block data may be incorrect
	 * @exception none
	 * @postcondition stored ArrayList of order of commands "move,eat,turnleft"
	 * 
	 * @return integer for the error parse block encounters
	 * @author Leslie
	 */
	public int parseBlock(Block block){
		if(this.finalblocks.size()>1000){
			boolean loop=true;
			String command= finalblocks.get(finalblocks.size()-1);
			for(int i=finalblocks.size()-2; i>finalblocks.size()-900; i--){
				if(!finalblocks.get(i).equals(command)){
					loop=false;
					break;
				}
			}
			if(loop){
				//ERROR infiniteLoop() error
				this.finalblocks= new ArrayList<String>(); //clear finalblocks arraylist
				return -2;
			}
		}
		HashMap<Integer,Block> nestedblocklist= block.getNestedBlocks();
		//initialize and sort nested keys
		ArrayList<Integer> nestedkeylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: nestedblocklist.entrySet()){
			nestedkeylist.add(entry.getKey());
		}
		ArrayList<Integer> nestedsortedkeys= sortKeys(nestedkeylist);

		if(block.getType()==3){//"if"
			if(block.getCond().equals("There'sWall?")){
				if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0; 					
				}
			}else if(block.getCond().equals("There'sFood")){
				if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}
			}else if(block.getCond().equals("There'sNoWall")){
				if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}

			}else if(block.getCond().equals("There'sNoFood")){
				if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}

			}
			return -1;
		}else if(block.getType()==5){//"else"
			for(int i=0; i<nestedsortedkeys.size(); i++){
				Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
				int success=parseBlock(nestedblock);
				if(success==-1){
					return -1;
				}
				if(success==1){//if or else if so skip rest of else if or else statements
					if(i+1<nestedsortedkeys.size()){
						int j;
						for(j=i+1; j<nestedsortedkeys.size(); j++){
							if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
								break;
							}
						}
						i=j;
						if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
							break; //this means last block is else if or else block
						}
					}
				}
			}
			return 0;
		}else if(block.getType()==4){//"else if"
			if(block.getCond().equals("There'sWall?")){
				if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0; 					
				}
			}else if(block.getCond().equals("There'sFood")){
				if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}
			}else if(block.getCond().equals("There'sNoWall")){
				if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}

			}else if(block.getCond().equals("There'sNoFood")){
				if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}

			}
			return -1;
		}else if(block.getType()==6){//"while"
			if(block.getCond().equals("There'sWall?")){
				while(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success= parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
				}
				return 0; 					
			}else if(block.getCond().equals("There'sFood")){
				while(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
				}
				return 0;		
			}else if(block.getCond().equals("There'sNoWall")){
				while(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
				}
				return 0;			
			}else if(block.getCond().equals("There'sNoFood")){
				while(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
				}
				return 0;					
			}
			return -1;	
		}else if(block.getType()==7){//"repeat"
			int repeat= block.getRepeat();
			if(repeat==-1){
				return -1;
			}
			for(int i=0; i<repeat; i++){
				for(int j=0; j<nestedsortedkeys.size(); j++){
					Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(j));
					int success=parseBlock(nestedblock);
					if(success==-1){
						return -1;
					}
					if(success==1){//if or else if so skip rest of else if or else statements
						if(i+1<nestedsortedkeys.size()){
							int k;
							for(k=i+1; k<nestedsortedkeys.size(); k++){
								if(nestedblocklist.get(nestedsortedkeys.get(k)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(k)).getType()!=5){
									break;
								}
							}
							i=k;
							if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
								break; //this means last block is else if or else block
							}
						}
					}
				}
			}
			return 0;	
		}else if(block.getType()==0){//"eat"
			finalblocks.add("Eat");
			if(this.isFunctionLine){
				finalblocksLineNumbers.add(this.parseFunctionLine);
			}else{
				finalblocksLineNumbers.add(block.getlineBegin());
			}
			eat(tempgerbil.getX(),tempgerbil.getY(),tempgrid);
			return 0;
		}else if(block.getType()==1){//"turn left"
			finalblocks.add("Turn Left");
			if(this.isFunctionLine){
				finalblocksLineNumbers.add(this.parseFunctionLine);
			}else{
				finalblocksLineNumbers.add(block.getlineBegin());
			}
			turnLeft(tempgerbil);
			return 0;
		}else if(block.getType()==2){//"move forward"
			finalblocks.add("Move Forward");
			if(this.isFunctionLine){
				finalblocksLineNumbers.add(this.parseFunctionLine);
			}else{
				finalblocksLineNumbers.add(block.getlineBegin());
			}
			moveForward(tempgerbil);
			return 0;
		}else if(block.getType()==8){//user defined function
			ArrayList<Function> functionlist= gamePlaying.functions;
			Function function= functionlist.get(block.getFunctionNum());
			HashMap<Integer,Block> fnestedblocklist= function.getBlockInstructions();
			ArrayList<Integer> fnestedkeylist= new ArrayList<Integer>();
			for(Entry<Integer,Block> fentry: fnestedblocklist.entrySet()){
				fnestedkeylist.add(fentry.getKey());
			}
			ArrayList<Integer> fnestedsortedkeys= sortKeys(fnestedkeylist);				
			for(int i=0; i<fnestedsortedkeys.size(); i++){
				Block fnestedblock= fnestedblocklist.get(fnestedsortedkeys.get(i));
				this.isFunctionLine=true;
				this.parseFunctionLine= block.getlineBegin();
				int success=parseBlock(fnestedblock);
				this.isFunctionLine=false;
				if(success==-1){
					return -1;
				}
				if(success==1){//if or else if so skip rest of else if or else statements
					if(i+1<fnestedsortedkeys.size()){
						int j;
						for(j=i+1; j<fnestedsortedkeys.size(); j++){
							if(fnestedblocklist.get(fnestedsortedkeys.get(j)).getType()!=4 || fnestedblocklist.get(fnestedsortedkeys.get(j)).getType()!=5){
								break;
							}
						}
						i=j;
						if(fnestedblocklist.get(fnestedsortedkeys.get(i)).getType()==4 || fnestedblocklist.get(fnestedsortedkeys.get(i)).getType()==5){
							break; //this means last block is else if or else block
						}
					}
				}
			}
			//this.isFunction=false;
			return 0;
		}
		return -1;
		//ERROR block does not have a valid type
	}	

	
	/**
	 * Gets the block from the main's nesting level
	 * @assumes Block is present
	 * @exception none
	 * @postcondition none
	 * @param line Line number to search for the block
	 * @return the block if we find the block we are looking for based on line, else null
	 * @author Amulya
	 */
	public Block getBlockByLineMain(int line){
		for(int k: this.gamePlaying.getBlocks().keySet()){
			Block temp = this.gamePlaying.getBlocks().get(k);
			if(line>=temp.getlineBegin()&& line <=temp.getlineEnd()){
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * Gets the block from the user Function screen's nesting level
	 * @assumes Block is present
	 * @exception none
	 * @postcondition none
	 * @param lineS Line number to search for the block
	 * @return the block if we find the block we are looking for based on line, else null
	 * @author Amulya
	 */
	public Block getBlockByLineUserFunction(int lineS) {
		for(int k: this.tempFunctionBlockInstructions.keySet()){
			Block temp = this.tempFunctionBlockInstructions.get(k);
			if(lineS>=temp.getlineBegin()&& lineS <=temp.getlineEnd()){
				return temp;
			}
		}
		return null;
	}

	
	/**
	 * For any line number selected, it will return the block in that position inside main. gets inner most block
	 * @assumes Block is present and if end or begin from the block are clicked, you want the entire block
	 * @exception none
	 * @postcondition none
	 * @param line Line Number
	 * @return Block at that line Number in the main, else null
	 * @author Amulya
	 */
	public Block getBlockByLine(int line){
		for(int k: this.gamePlaying.getBlocks().keySet()){
			Block temp = this.gamePlaying.getBlocks().get(k);
			if(line>=temp.getlineBegin()&& line <=temp.getlineEnd()){
				for(int z: temp.getNestedBlocks().keySet()){
					Block b = temp.getNestedBlocks().get(z);
					if(line>=b.getlineBegin() && line <=b.getlineEnd()){
						return b;
					}
				}
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * Deletes theblock you give it to delete
	 * @assumes Block is present
	 * @exception none
	 * @postcondition removes the parameter block from the game
	 * @param toDel Block to be deleted
	 * @author Amulya
	 */
	public void deleteBlock(Block toDel) {
		
		if(toDel.getParent()==null){ //in main nesting 
			int currDiff = toDel.getlineEnd()-toDel.getlineBegin()+1;
			this.gamePlaying.getBlocks().remove(toDel.getlineBegin());
			cascadeNumberingChanges(toDel.getlineBegin(),-1*currDiff, toDel, gamePlaying.getBlocks(), true);//MAKE SURE -1*currDIFF!!!!!
		}else{ //some other blocks's nesting
			Block p = toDel.getParent();
			p.getNestedBlocks().remove(toDel.getlineBegin());
			int currDiff = toDel.getlineEnd()-toDel.getlineBegin()+1;
			cascadeNumberingChanges(toDel.getlineBegin(),-1*currDiff, toDel, gamePlaying.getBlocks(), true);//MAKE SURE -1*currDIFF!!!!!
		}
		
	}


	/**
	 * Will insert a NEW block of code into a user specified index/position in main
	 *
	 * @assumes Index to insert block is valid
	 * @exception none
	 * @postcondition Inserts new block of code
	 * 
	 * @param pos index/position of block to insert = it is the line number 
	 * @param b block to be inserted
	 * @return false/ true; false if inserting the Block fails, true if it succeeds
	 * @author Amulya
	 */
	public void insertBlockToMain(int id, Block b){
		//Block parent = findParentInMain(id); = parent is null! => cannot find parent
		Block reference = null;
		if(b.getParent()==null){//no parents, parent is MAIN => can insert to main 
			for(int key: gamePlaying.getBlocks().keySet()){
				if(id<key){ //if key is bigger than id 
					reference = gamePlaying.getBlocks().get(key);//find sibling block as reference when cascade
				}
			}
		}else{ //parent block not null
			for(int key: parent.getNestedBlocks().keySet()){
				reference = parent.getNestedBlocks().get(key);//find sibling block as reference when cascade
				break;
			}
		}
		if(reference==null){
			//no existing blocks in game
			gamePlaying.getBlocks().put(id, b);
			return;
		}
		int currdiff= this.countblocks; //number of blocks/lines in block to insert
		this.countblocks=1; //reset countblocks
		cascadeNumberingChanges(id,currdiff, reference, gamePlaying.getBlocks(), true);
		//after cascade, should have a free spot in hashmap with key=id since moved original id block down to diff key
		parent.getNestedBlocks().put(id, b);

	}

	/**
	 * counts the total number of lines(blocks) in a block (including nested blocks)
	 * @assumes Block is present
	 * @exception none
	 * @postcondition none
	 * @author Leslie
	 */
	public void countBlocks(Block b){
		if(b.getNestedBlocks().isEmpty()){
			return;
		}
		for(int key: b.getNestedBlocks().keySet()){
			this.countblocks++;
			countBlocks(b.getNestedBlocks().get(key));
		}
	}

	/**
	 * Search for possible parent of block to insert in MAIN
	 * 
	 * @assumes valid number value given
	 * @exception none
	 * @postcondition none
	 * @param pos line number of block whose parent we are finding
	 * @author Leslie
	 */
	public Block findParentInMain(int pos){
		int n=pos+1;
		Block b;
		while(n>0){
			b= searchForBlock(n,gamePlaying.getBlocks());
			if(b.getlineEnd()==pos && b.getlineEnd()-b.getlineBegin()!=0){
				//return block found at n if block lineEnd==pos AND is not a function block
				return b;
			}
			n=n-1; //traverse upwards in line numbers
		}
		//at this point, no parent was found, so no parent exists
		return null;	
	}


	/**
	 * Will insert a NEW block of code into a user specified index/position
	 *
	 * @assumes Index to insert block is valid
	 * @exception none
	 * @postcondition Inserts new block of code
	 * 
	 * @param nested HashMap to recurse on for nesting initial value is the one in gamePlaying
	 * @param pos index/position of block to insert = it is the line number 
	 * @param b block to be inserted
	 * @return false/ true; false if inserting the Block fails, true if it succeeds
	 * @author Amulya
	 */
	public void insertToBlock(int id, Block b){
		Block parent = searchForBlock(id, gamePlaying.getBlocks()); //get parent since we know parent's line number
		//note: even if the nested blocks has that key already, we need to move it down by calling this funciton again with b's begin+end 
		if(parent.getNestedBlocks().keySet().contains(b.getlineBegin())){
			Block temp = parent.getNestedBlocks().get(b.getlineBegin());
			parent.getNestedBlocks().put(b.getlineBegin(), b); 
			//Will call searchForBlock to find block of the given id and insert insert b to it
			int currDiff = b.getlineEnd()-b.getlineBegin();
			cascadeNumberingChanges(b.getlineBegin(),currDiff,b, gamePlaying.getBlocks(), true);
			insertBlockToMain(b.getlineBegin()+b.getlineEnd(), temp);
		}
		parent.getNestedBlocks().put(b.getlineBegin(), b); 
		//Will call searchForBlock to find block of the given id and insert insert b to it
		int currDiff = b.getlineEnd()-b.getlineBegin();
		cascadeNumberingChanges(b.getlineBegin(),currDiff,b, gamePlaying.getBlocks(), true);
	}

	/**
	 * Calls highlighting function to get the begin and end numbers in array form for the block we are searching for 
	 * @assumes valid line number entered
	 * @exception none
	 * @postcondition none
	 * @param line Line to search for and find the block that line is in.
	 * @return Int array of begin and end numbers of the block we find at that line.
	 * @author Amulya
	 */
	public int[] callHighlight(int line){
		int[] bInfo;
		Block b = getHighlighting(line,this.gamePlaying.getBlocks());
		bInfo = new int[b.getlineEnd()-b.getlineBegin()+1];
		for(int index =0, i =b.getlineBegin();index<bInfo.length;index++,i++){
			bInfo[index]=i;
		}
		return bInfo;
	}


	/**
	 * Figures out the given line number's parent block's line number = good for highlighting
	 * 
	 * @assumes the line number exists so we search for it and then see the type to return parent or current block
	 * @exception none
	 * @postcondition none
	 * @param b the hashmap of blocks to search for the parent block
	 * @return the parent block is returned
	 * @author Amulya
	 */
	public Block getHighlighting(int line, HashMap<Integer,Block> blocks){
		Block b = searchForBlock(line, blocks); //gets the block the line is in
		
		int type = b.getType();

		//eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8),
		if(type>=0 && type <=2){ //[0-2]
			return b;

			//below is for terminal so see if they belong in larger block. no parent, then return them
			/**if (b.getParent()!=null){
				return b.getParent();
			}else{
				return b;
			}*/
		}else if (type==3|| type==6 || type==7 || type==8){
			return b;
		}else if(type==4 || type==5){ //else if and else so return their parent which should be "if"
			return b.getParent();
		}

		return null;
	}

	/**
	 * Cascades the line number changes to the rest of the code after insert, delete or edit
	 * @assumes valid parameters provided
	 * @exception none
	 * @postcondition All the lines after the block provided are incremented by the block's line difference
	 * 
	 * @param lineBegin The block that was changed, inserted, deleted etc's line begin. 
	 * 			/IMPORTANT: lineBegin is the line number above SELECTED line number in view
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occurred in
	 * @param mainmap differentiates between the user functions hashmap(false) and the game playings blocks hashmap(true)
	 * @author Leslie
	 */
	public void cascadeNumberingChanges(int lineBegin, int currDiff, Block b, HashMap<Integer,Block> mainblocks, boolean mainmap){
		if(b.getParent()!=null){
			b.getParent().setLineEnd(b.getParent().getlineEnd()+currDiff);
		}
		HashMap<Integer,Block> nb;
		HashMap<Integer,Block> tempnb= new HashMap<Integer,Block>();
		boolean lastBlocks=false;
		if(b.getParent()==null){
			nb= mainblocks;
			//nb= gamePlaying.getBlocks();
			lastBlocks=true;
		}else{
			nb = b.getParent().getNestedBlocks();//get hashmap containing b and sister blocks
		}
		Block temp=null;
		for(int key: nb.keySet()){
			if (key>=lineBegin){ //cascade the difference to the blocks after b!
				temp=nb.get(key); //get the object
				if(!temp.getNestedBlocks().isEmpty()){
					for(int nestedkey: temp.getNestedBlocks().keySet()){
						cascadeInward(lineBegin,currDiff,temp.getNestedBlocks().get(nestedkey));
						break;
					}
				}
				temp.setlineBegin(temp.getlineBegin()+currDiff); //change line begin with the difference
				temp.setLineEnd(temp.getlineEnd()+currDiff); 
				tempnb.put(temp.getlineBegin(), temp); //put each updated block in temp hashmap with new key
			}else{ //put each un-updated block in temp hashmap with original key
				tempnb.put(key, nb.get(key));
			}
		} 
		if(lastBlocks==true){
			if(mainmap){
				gamePlaying.setBlocks(tempnb);
				return;
			}else{
				this.tempFunctionBlockInstructions=tempnb; //replace original MAIN hashmap with new/updated MAIN hashmap
				return; //no more higher level to get to
			}
		}
		b.getParent().setNestedBlocks(tempnb); //replace original nested hashmap with new/updated nested hashmap
		cascadeNumberingChanges(lineBegin,currDiff,b.getParent(),mainblocks,mainmap); //recurse to go higher
	}
		
	/**
	 * cascadesNumberingChanges Inward to nested blocks
	 * @assumes valid parameters provided
	 * @exception none
	 * @postcondition All the lines after the block provided are incremented by the block's line difference
	 * @param lineBegin The block that was changed, inserted, deleted etc's line begin. 
	 * 			/IMPORTANT: lineBegin is the line number above SELECTED line number in view
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occurred in
	 * @author Leslie
	 */
	public void cascadeInward(int lineBegin, int currDiff, Block b){
		HashMap<Integer,Block> nb;
		HashMap<Integer,Block> tempnb= new HashMap<Integer,Block>();
		nb = b.getParent().getNestedBlocks();//get hashmap containing b and sister blocks
		Block temp=null;
		for(int key: nb.keySet()){
			if (key>=lineBegin){ //cascade the difference to the blocks after b!
				temp=nb.get(key); //get the object
				if(!temp.getNestedBlocks().isEmpty()){
					for(int nestedkey: temp.getNestedBlocks().keySet()){
						cascadeInward(lineBegin,currDiff,temp.getNestedBlocks().get(nestedkey));
						break;
					}
				}
				temp.setlineBegin(temp.getlineBegin()+currDiff); //change line begin with the difference
				temp.setLineEnd(temp.getlineEnd()+currDiff); 
				tempnb.put(temp.getlineBegin(), temp); //put each updated block in temp hashmap with new key
			}else{ //put each un-updated block in temp hashmap with original key
				tempnb.put(key, nb.get(key));
			}	
		} 
		b.getParent().setNestedBlocks(tempnb);
	}


	/**
	 * Searches for a Block by id field
	 * 
	 * @assumes Block to search for must exist!!!
	 * @exception none
	 * @postcondition Finds block being searched for
	 * 
	 * @param id id of Block to be searched for so it is the line number 
	 * @param blocks Hashamp of blocks to look for the block that has that id
	 * @return Block with the given id if found, else returns null
	 * @author Amulya
	 */
	public Block searchForBlock(int id, HashMap<Integer,Block> blocks){
		if(blocks.keySet().isEmpty()){ //no more nesting
			return null;
		}
		for (int curr: blocks.keySet()){
			if(curr==id){
				return blocks.get(curr);
			}else if(blocks.get(curr).getlineBegin()<=id && blocks.get(curr).getlineEnd()>=id){ 
				//the block contains the line number in it so search inside
				return blocks.get(curr);
			}
		}
		//exit out without finding it meaning it is not a particular block's begin line.
		//meaning we have to find the inside the closest inside and that is temp curr 
		return null;
	}

	/*Checks for conditionals*/

	/**
	 * Determines whether there is food in the selected coordinates (x,y), regardless of food type
	 * 
	 * @assumes int x, y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's food at a given (x,y)
	 * 
	 * @param x X position in the grid to check if there is food, regardless of food type
	 * @param y Y position in the grid to check if there is food, regardless of food type
	 * @return false/true; false if there is no food in the given (x,y) coordinates, true if there is food in the selected coordinates
	 * @author Leslie
	 */
	public boolean isthereFood(int x, int y){ //checks if square has food or not
		if(gamePlaying.getGrid().getSquareContent(y, x)=='k'
				|| gamePlaying.getGrid().getSquareContent(y, x)=='p'
				|| gamePlaying.getGrid().getSquareContent(y, x)=='a'){
			return true;
		}
		return false;
	}

	/**
	 * Determines if there is a wall in the selected (x,y) coordinates
	 * 
	 * @assumes int x,y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's a wall  at a given (x,y)
	 * 
	 * @param x X position in the grid to check if there is a wall
	 * @param y Y position in the grid to check if there is a wall
	 * @return false/true; false if there is no wall in the given (x,y) coordinates, true if there is a wall in the selected coordinates
	 * @author Leslie
	 */
	public boolean isthereWall(int x, int y){
		if(gamePlaying.getGrid().getSquareContent(y, x)=='w'){
			return true;
		}
		return false;
	}

	/**
	 * Determines if the water container is at position (x,y)
	 * 
	 * @assumes int x,y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's water  at a given (x,y)
	 * 
	 * @param x X position in the grid to check if the water container is there
	 * @param y Y position int the grid to check if the water container is there
	 * @return false/true; false if water container not found at (x,y), true if water container found at (x,y)
	 * @author Leslie
	 */
	public boolean isthereWater(int x, int y){
		if(gamePlaying.getGrid().getSquareContent(y, x)=='t'){
			return true;
		}
		return false;
	}

	/*Function stuff*/

	/**
	 * Will create a function to be added to list of functions
	 * 	 * First View calls this, and then when user has entered the information, they will call
	 * finishCreateBlocks method if the user clicks ok, otherwise, click cancelBlock, if user clicks cancel
	 * 
	 * @assumes if same function is called with c, the function was cancelled at some point so we ignore what we have currently 
	 * @assumes if same function is called with e, the function was finished so we add to the list
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Creates function iff function name is unique
	 * 
	 * @param type Enumerated type of the object
	 * @param begin The beginLine fo the object so the line number it starts at
	 * @param numLines is the number of lines of the code entered since this method is called several times
	 * @param cond This is for while and if statements AND it also sends the integer for Repeat!! 
	 * @return newly instantiated Function object
	 * @author Amulya
	 */
	public int createFunctionBlocks(int type, int begin, int numLines, String cond){
		if(type=='c'){//tried to create block but canceled so cancel the block we have currently
			this.userCodingNowFunction=this.parentFunction;
			return 0;
		}else if((type=='e') && (this.userCodingNowFunction!=null)){//finished coding for the block so put into the correct spot
			this.userCodingNowFunction.setLineEnd(begin+numLines-1);	
			int currType= this.userCodingNowFunction.getType();
			if(currType==7){//repeat block so turn cond into int and store in repeat
				int repeat=-1;
				if(cond==null){
					
					///////////////////ERROR: Number of repetitions was not selected!//////////////
					return 1;
				}else{ //no need to check if cond is int or not since view will provide int for it 
					repeat =Integer.valueOf(cond);
				}
				this.userCodingNowFunction.setRepeat(repeat);

			}else if(currType==8){//user-defined FUNCTION block so find int for cond and store int in functionNum
				int functionNum=-1;
				if(cond==null){
					
					///////////ERROR: Function not selected////////////////////////////
					return 2;
				}else{
					for(int i=0; i<gamePlaying.functions.size(); i++){
						if(gamePlaying.functions.get(i).getName().equals(cond)){
							functionNum= i;
							break;
						}
					}
				}
				if(functionNum==-1){ //despite searching for it!! 
				
					///////////////ERROR: Illegal funciton entered!!!!!/////////////
					return 3;
				}
				this.userCodingNowFunction.setFunctionNum(functionNum);	
			}else if(currType==3 || currType==6){ //if and while loops
				this.userCodingNowFunction.setCond(cond);
			}else if(currType==4){ //for else if, we need to check if parent == if => parent cannot be null
				this.userCodingNowFunction.setCond(cond);
			}

			if(parentFunction==null){ //insert into gamePlaying.blocks and cascade!!!
				/*So insert only happens to main, the rest are edit and delete so 
				we first check if the begin line we are given already exsits in the 
				main, if it does, we cascade, then insert to not delete the current 
				block at that number. else we simply add = works for both between lines 
				and end of code.*/
				for (int key: this.tempFunctionBlockInstructions.keySet()){
					if(key==begin){
						cascadeNumberingChanges(begin, this.userCodingNowFunction.getlineEnd()-this.userCodingNowFunction.getlineBegin()+1, this.userCodingNowFunction, this.tempFunctionBlockInstructions,false);
						this.tempFunctionBlockInstructions.put(begin, this.userCodingNowFunction);
						this.userCodingNowFunction=null;
						return 0;
					}
					
				}//get past this means, end of lines!
				this.tempFunctionBlockInstructions.put(begin, this.userCodingNowFunction);
			} //we ended this so parent is now the currBlock coded
			this.userCodingNowFunction=parentFunction;
			if(this.parentFunction!=null){
				this.parentFunction=this.userCodingNowFunction.getParent();
			}
			return 0;
		}else{ //first time making a block
			Block b = new Block();
			b.setlineBegin(begin);
			b.setType(type);
			if(type=='e'){
				return 0;
			}
			if((type==4) || (type==5)){ //SPECIAL FOR ELSE IF AND ELSE!!!
				Block parIf = null; 
				if(this.userCodingNowFunction==null){ //find in main level = no nesting
					for(int k: this.tempFunctionBlockInstructions.keySet()){
						if(this.tempFunctionBlockInstructions.get(k).getType()==3 && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.tempFunctionBlockInstructions.get(k);
						}
					}
				}else{ //find in parent's level!
					for(int k: this.userCodingNowFunction.getNestedBlocks().keySet()){
						int tempTP = this.userCodingNowFunction.getNestedBlocks().get(k).getType();
						if((tempTP==3 || tempTP==4) && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.userCodingNowFunction.getNestedBlocks().get(k);
						}
					}
				}
				if(parIf==null){
				
					//////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
					//not valid cuz the parent for else if and else has to be if!!! so tell them not valid code
					return 4;
				}else if(parIf.getlineEnd()+1!=begin){
					
					//So we are trying to insert the else if or else after the if for else if OR if/else if for ELSE!!!
					////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
					return 5;
				}else{
					if(this.userCodingNowFunction!=null){ //curr not null so we need to set current to user playing and parent to curr
						b.setParent(parIf.getParent()); //set else if or else stuff's parent to the parent of if block!!
						this.parentFunction=parIf.getParent();
						this.userCodingNowFunction=b;
						if(parentFunction!=null){ //insert into parent's block
							if(parentFunction.getNestedBlocks().get(begin)==null){ //nothing there put
								parentFunction.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
							}else{ 
								
								cascadeNumberingChanges(begin, 1, b, this.tempFunctionBlockInstructions, false); //cascade first then put into it!!
								parentFunction.getNestedBlocks().put(begin, b);
							}
						}
					}else{
						b.setParent(parIf.getParent());
						this.userCodingNowFunction=b; //don't put in if in main;s nesting
					}
				}
			}else{
				if(this.userCodingNowFunction!=null){ //curr not null so we need to set current to user playing and parent to curr
					b.setParent(this.userCodingNowFunction);
					this.parentFunction=this.userCodingNowFunction;
					this.userCodingNowFunction=b;
					if(parentFunction!=null){ //insert into parent's block
						if(parentFunction.getNestedBlocks().get(begin)==null){ //nothing there put
							parentFunction.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
						}else{ 

							cascadeNumberingChanges(begin, 1, b, this.tempFunctionBlockInstructions, false); //cascade first then put into it!!
							parentFunction.getNestedBlocks().put(begin, b);
						}
					}
				}else{
					this.userCodingNowFunction=b; //don't put in if its in main's nesting
				}
			}
			return 0;
		}
	}
	

	/**
	 * Creates a new Function object, stores createdFunctionBlocks in this function, and adds it to functions list 
	 * @assumes valid function names are checked since they must be unique
	 * @exception none
	 * @postcondition Creates function iff function name is unique
	 * @param name Name of the function we are creating
	 * @author Leslie
	 */
	public int createFunction(String name){
		int temp = validFunctionName(name);
		if(temp==1){
			/////////////Error: Functions names can only consists of letters or numbers///////////
			return 1;
		}else if(temp==2){
			///////////////////////////Error: Function name already exists////////////////
			return 2;
		}
		Function newfunction= new Function(name);
		newfunction.setBlockInstructions(this.tempFunctionBlockInstructions);
		this.tempFunctionBlockInstructions= new HashMap<Integer,Block>();//reset to empty
		addFunction(newfunction); //add to this.functions list
		return 0;
	}
	
	/**
	 * Deletes the tempFunctionBlockInstructions
	 * @assumes we are done using the funciton block temp instructions storage since we have created the function or cancelled it's creation
	 * @exception none
	 * @postcondition no more instructions are in the function creation hashmap
	 * @author Leslie
	 */
	public void clearTempFunctionBlockInstructions(){
		this.tempFunctionBlockInstructions= new HashMap<Integer,Block>();
	}

	/**
	 * Will determine whether user provided function name is unique and contains valid characters
	 * 
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Determines if function name is unique/valid
	 * 
	 * @param name User provided name for function
	 * @return int; 1 if function name does not consist of letters or digits, 2 if the function name is not unique && valid, 3 if unique && valid
	 * @author Leslie
	 */
	public int validFunctionName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c) && c!=' '){
				return 1;
			}
		}
		for(int j=0; j<gamePlaying.functions.size(); j++){
			if(gamePlaying.functions.get(j).getName().equals(name)){
				return 2;
			}
		}

		return 3;
	}


	/**
	 * Will add a function to the function list
	 * 
	 * @assumes function is unique
	 * @assumes function has been parsed and is valid
	 * @exception none
	 * @postcondition Will add function to function list
	 * 
	 * @param functionToAdd function to be added to function list
	 * @author Leslie
	 */
	public void addFunction(Function functionToAdd){
		gamePlaying.functions.add(functionToAdd); // +kat
	}


	/**
	 * Will delete a function a user has selected for deletion
	 * 
	 * @assumes function exists
	 * @exception none
	 * @postcondition Deletes function from function list
	 * 
	 * @param name User selected function to delete
	 * @return false/true, false if deletion could not be performed, true if deletion performed
	 * @author Leslie
	 */
	public boolean deleteFunction(String name){
		//Will not call any functions/classes
		for(int i=0; i<gamePlaying.functions.size(); i++){
			if(gamePlaying.functions.get(i).getName().equals(name)){
				gamePlaying.functions.remove(i);
				return true;
			}
		}
		return false;
	}


	/**
	 * Will return an functions list in order to display in drop down menu of GUI
	 * 
	 * @assumes User and built in functions exist
	 * @exception none
	 * @postcondition Provides string list of all functions in a game
	 * 
	 * @return ArrayList of strings with all functions that have been created in the program
	 * @author Leslie
	 */
	public String[] getFunctions(){
		ArrayList<String> functionnames= new ArrayList<String>();

		ArrayList<Function> gameFunctions= gamePlaying.getfunction();
		for(int i=0; i<gameFunctions.size(); i++){
			functionnames.add(gameFunctions.get(i).getName());
		}
		ArrayList<String> sortedfunctions= functionnames;
		String[] returnstring= new String[gamePlaying.functions.size()];
		for(int j=0; j<gamePlaying.functions.size(); j++){
			returnstring[j]= sortedfunctions.get(j);
		}
		return returnstring;
	}
	
	/**
	 * Returns the functions of the current game being played
	 * 
	 * @assumes we want to see the functions created by users
	 * @exception none
	 * @postcondition Arraylist of the functions array list is returned
	 * @author Leslie
	 */
	public ArrayList<String> getFunctionsArrayList(){
		ArrayList<String> functionnames= new ArrayList<String>();
		
		ArrayList<Function> gameFunctions= gamePlaying.getfunction();
		for(int i=0; i<gameFunctions.size(); i++){
			functionnames.add(gameFunctions.get(i).getName());
		}
		ArrayList<String> sortedfunctions= functionnames;
		
		return sortedfunctions;
	}

	/**
	 * Will return an alphabetically sorted ArrayList of strings for the drop down menu items
	 * 
	 * @assumes Provided list has not been sorted alphabetically
	 * @exception none
	 * @postcondition Provides an alphabetically sorted list of strings
	 * 
	 * @param toSort ArrayList of strings to be sorted for the menu items
	 * @return Alphabetically sorted araryList of strings upon success, null upon failure
	 * @author Leslie
	 */
	public ArrayList<String> sortAlphabetical(ArrayList<String> toSort){

		Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>(){
			public int compare(String str1, String str2){
				int res= String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
				if(res==0){
					res= str1.compareTo(str2);
				}
				return res;
			}
		};
		List<String> list= new ArrayList<String>();
		for(int i=0; i<toSort.size();i++){
			list.add(toSort.get(i));
		}
		Collections.sort(list, ALPHABETICAL_ORDER);
		return (ArrayList<String>) list;
	}

	/*Movement stuff*/
	//////go through array list that consists move, eat, or turn left and see after each movement if valid or not!!!!

	/**
	 * Will change the position of the gerbil to one cell forward
	 * 
	 * @assumes Move may be invalid => (ex. there is wall there so after motion)
	 * @exception none
	 * @postcondition Makes move iff move is valid
	 * @param gerbil the gerbil object to move forward
	 * @return false/true; false if the move was unsuccessful, true if the move was successful 
	 * @author Leslie
	 */
	public boolean moveForward(Gerbil gerbil){
		if(gamePlaying.getGrid().getSquareContent(gerbil.getFrontY(), gerbil.getFrontX())=='w'){
			return false;
		}
		int diffX = gerbil.getFrontX()-gerbil.getX();
		int diffY = gerbil.getFrontY()-gerbil.getY();
		gerbil.setX(gerbil.getFrontX());
		gerbil.setY(gerbil.getFrontY());
		gerbil.setFrontX(gerbil.getFrontX()+diffX); //resets frontX and y accordingly
		gerbil.setFrontY(gerbil.getFrontY()+diffY);
		return true;
	}


	/**
	 * Will change the orientation of the Gerbil left 
	 * 
	 * @assumes Gerbil exists
	 * @exception none
	 * @postcondition Orientation of the Gerbil will be changed
	 * @param gerbil the gerbil object to turn left
	 * @return false/true; false if the Gerbil orientation was not changed, true otherwise
	 * @author Leslie, Amulya
	 */
	public boolean turnLeft(Gerbil gerbil){

		//determine if gerbil is facing South --> will face East
		if(gerbil.getFrontX()==gerbil.getX() && gerbil.getFrontY()==gerbil.getY()+1){
			gerbil.setFrontX(gerbil.getX()+1);
			gerbil.setFrontY(gerbil.getY());
			gerbil.setCompass('e');

			return true;
		}
		//determine if gerbil is facing North --> will face West
		if(gerbil.getFrontX()==gerbil.getX() && gerbil.getFrontY()==gerbil.getY()-1){
			gerbil.setFrontX(gerbil.getX()-1);
			gerbil.setFrontY(gerbil.getY());
			gerbil.setCompass('w');
			return true;
		}
		//determine if gerbil is facing East --> will face North
		if(gerbil.getFrontX()==gerbil.getX()+1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()-1);
			gerbil.setCompass('n');
			return true;
		}
		//determine if gerbil is facing West --> will face South
		if(gerbil.getFrontX()==gerbil.getX()-1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()+1);
			gerbil.setCompass('s');
			return true;
		}

		return false;
	}

	/**
	 * Will remove item from x, y, call move()
	 * 
	 * @assumes item at x,y may not exist
	 * @exception none 
	 * @postcondition removes item from (x,y) iff it exists
	 * 
	 * @param x pos of food to eat
	 * @param y pos of food to eat
	 * @return boolean if there is food else return false
	 * @author Leslie
	 */
	public boolean eat(int x, int y, char[][] grid){
		//create pointer to grid
		if(grid[y][x]=='k' //if food
				|| grid[y][x]=='p'
				|| grid[y][x]=='a'){
			grid[y][x]='0'; //eat
			return true;
		}
		//will need grid from Grid.java
		return false;
	}


	/**
	 * This method will delete a given game
	 * @assumes valid game nameare checked since they must be unique
	 * @exception none
	 * @postcondition deletes the game permenentely
	 * @param gameName Name of the game to delete
	 * @return True if deletion is successful, otherwise False
	 * @author Leslie
	 */
	public boolean deleteGame(String gameName) {
		return Start.StartGerbil.backend.deleteGame(gameName);
	}


	/**
	 * This method will load a given game
	 * @assumes valid function names are checked since they must be unique
	 * @exception none
	 * @postcondition Creates function iff function name is unique
	 * @param gameName Name of the game to load
	 * @return True if loading is successful, otherwise False
	 * @author Leslie, Katiuska
	 */
	public Game loadGame(String gameName) {
		this.gamePlaying= Start.StartGerbil.backend.getGame(gameName);
		if(gamePlaying == null) {
			return null;
		}
		Start.StartGerbil.backend.deleteGame(gameName); //delete game so ther is only one isntance at a time

		return this.gamePlaying;
	}

	/**
	 * Save current game
	 * @assumes valid games are loaded into backend
	 * @exception none
	 * @postcondition saves the games to the backend
	 * @return True if save is successful, otherwise False
	 * @author Leslie, Katiuska 
	 */
	public boolean saveGame() {

		for(int j=0; j<Start.StartGerbil.backend.getGameList().size(); j++){
			if(Start.StartGerbil.backend.getGameList().get(j).getName().equals(this.gamePlaying.getName())){
				//skip adding the game to backend bc already exists
				return true;
			}
		}
		Start.StartGerbil.backend.addGame(this.gamePlaying);
		return true;
	}


	/**
	 * This method will return a list of instructions of the current game
	 * @assumes all valid instructions
	 * @exception none
	 * @postcondition none
	 * @return List of instructions
	 * @author Leslie
	 */
	public ArrayList<Block> getInstructions() {
		ArrayList<Block> blocklist= new ArrayList<Block>();
		ArrayList<Integer> keylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: gamePlaying.getBlocks().entrySet()){
			keylist.add(entry.getKey());
		}
		keylist=sortKeys(keylist);
		for(int i=0; i<keylist.size(); i++){
			Block block= gamePlaying.getBlocks().get(keylist.get(i));
			blocklist.add(block);
		}
		return blocklist;
	}



	/**
	 * Sets the current game to the parameter
	 * @assumes valid game
	 * @exception none
	 * @postcondition current game is set
	 * @param g Game to set the current game to
	 * @author Leslie
	 */
	public void setCurrentGame(Game g) {
		this.gamePlaying=g;

	}
}