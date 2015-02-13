package Control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Backend;
import Model.Block;
import Model.Game;
import Start.StartGerbil;
import View.Conditionals;
import View.DeleteFunction;
import View.ErrorDialog;
import View.Finish;
import View.Instructions;
import View.Main;
import View.NewGame;
import View.OkYesDialog;
import View.Play;
import View.PlayOptions;
import View.SavedGames;
import View.UserFunction;


/**
 * @author Truong Pham, Katiuska, Amulya
 */
public class ActionListenersControl {
	/**New Game screen */
	static NewGame newGame;
	/**Delete function scren */
	static DeleteFunction deleteFunction;
	/**Error dialog screen*/
	static ErrorDialog errorDialog;
	/**Finish screen */
	static Finish finish;
	/**Instructions screen */
	static Instructions instructionsScreen;
	/**Main screen */
	static Main main; 
	/**Play Screen	 */
	static Play playScreen;
	/**Play options screen */
	static PlayOptions playOptions;
	/**Saved games screen */
	static SavedGames savedGames;
	/**User functions screen */
	static UserFunction userFunction;
	/**Conditionals screen	 */
	static Conditionals conditionals;
	/**Ok or back Dialog screen	 */
	static OkYesDialog okNoDialog;
	/**erorr dialog screen	 */
	static ErrorDialog errorDialogRun;
	/**Index for the select in play screen */
	int selectedIndexPlayScreen; /*Code list in Play Screen*/
	/**	Index for selection in play screen jlist*/
	static int selectedIndexPlayCodeList;
	/**Index for selection in function lsit screen */
	static int funcListSelect;
	/**If we are creating function first*/
	boolean selectedCreateFunctionFirst;

	/**Inserting mode on or not */
	public boolean inserting;
	/**Deleting mode on or not */
	public boolean deleting;
	/**Editing mode on or not */
	public boolean editing;
	/**Stop animation mode on or not */
	public boolean stop;
	/**Play mode on or not */
	public boolean play;

	/**Delete the current game or not */
	boolean deleteCurrGame;

	/**Parent screen determined by enumerated type*/
	static int parentScreen;
	/**Variable for backslash T */
	String backT="     ";
	
	
	/**
	 * DeleteFunction 1
	 * Main 2
	 * NewGame 3
	 * Play 4
	 * PlayOptions 5
	 * SavedGame 6
	 * UserFunction 7
	 */
	
	/**Enumeration for delete function screen*/
	int DELETEFUNCTION = 1; 
	/**Enumeration for main screen*/
	int MAIN = 2; 
	/**Enumeration for new game screen*/
	int NEWGAME=3;
	/**Enumeration for play screen*/
	int PLAY= 4;
	/**Enumeration for play options screen*/
	int PLAYOPTIONS = 5;
	/**Enumeration for saved games screen*/
	int SAVEDGAME = 6; 
	/**Enumeration for user functions screen*/
	int USERFUNCTION = 7;

	/**Sets up the screens and actions listeners for components*/
	public ActionListenersControl(){
		Start.StartGerbil.controller.setCurrentGame(new Game("setUpGame")); // DO NOT remove. kthx.
		initGrid();

		newGame = new NewGame();
		deleteFunction = new DeleteFunction();
		errorDialog = new ErrorDialog();
		errorDialogRun = new ErrorDialog();
		finish = new Finish();
		instructionsScreen = new Instructions();
		main = new Main(); 
		conditionals = new Conditionals(" ");

		playOptions = new PlayOptions();

		savedGames = new SavedGames();

		okNoDialog = new OkYesDialog();

		userFunction = new UserFunction();
		playScreen = new Play();
		playScreen.initialPlayScreen();

		inserting = false;
		deleting = false;
		editing = false;

		initEventHandlers();
		initBooleans();
		main.show();
	}

	/**Initializes the grid to Play screen */
	private void initGrid() {
		Play.setNewGrid(Start.StartGerbil.controller.getCurrGame().getGrid().getGridRepresentation());
		Play.setGerbilLocation(Start.StartGerbil.controller.getCurrGame().getGerbil().getY(), Start.StartGerbil.controller.getCurrGame().getGerbil().getX());
	}

	/**Initializes the boolean used for various states such as inserting, deleting...etc */
	private void initBooleans(){
		selectedCreateFunctionFirst = false;
		inserting = false;
		deleting = false;
		editing = false;
		play = false;
		deleteCurrGame = false;
	}

	/**Sets up event handlers for each screen*/
	private void initEventHandlers() {
		addMainEventHandlers();
		addNewGameEventHandlers();
		addPlayOptionsEventHandlers();
		addInstructionsEventHandlers();
		addErrorDialogRunEventHandlers();
		addErrorDialogEventHandlers();
		addPlayEventHandlers();
		addUserFunctionEventHandlers();
		addOkYesDialogEventHandlers();
		addSavedGamesEventHandlers();
		addConditionalsEventHandlers();
		addDeleteFunctionEventHandlers();
		addFinishEventHandlers();
	}


	/**add the event handlers for the conditionals screen's components
	 * @author Amulya,Katiuska*/
	public void addConditionalsEventHandlers(){

		conditionals.addFunctionsEventHandler(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cond = (String)Conditionals.userDefinedFunctions.getSelectedItem();
				int begin = conditionals.getBegin()-2;
				if(parentScreen == PLAY){

					Start.StartGerbil.controller.createBlocks(8,begin,1,null);
					Start.StartGerbil.controller.createBlocks('e', begin, 1, cond);

					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());

				}else if(parentScreen == USERFUNCTION){

					Start.StartGerbil.controller.createFunctionBlocks(8,begin,1,null);
					Start.StartGerbil.controller.createFunctionBlocks('e', begin, 1, cond);

					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.FunctionUnFin());
				}
			}
		});

		conditionals.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int begin = conditionals.getBegin()-2;
				int numLines = conditionals.getEndLineNumber()+2;
				String cond = conditionals.getCond();

				if(parentScreen == PLAY){
					Start.StartGerbil.controller.createBlocks('e', begin, numLines, cond);
					Play.refreshCodeList();
				}else if(parentScreen == USERFUNCTION){
					Start.StartGerbil.controller.createFunctionBlocks('e', begin, numLines, cond);
					userFunction.refreshCodeList();
				}
				
				conditionals.hide();	
				showParent();
			}	
		});

		conditionals.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(parentScreen == 4){
					Start.StartGerbil.controller.createBlocks('c', 0, 0, null);
				}else if(parentScreen == 7){
					Start.StartGerbil.controller.createFunctionBlocks('c', 0, 0, null);	
				}
				conditionals.hide();	
				showParent();
			}	
		});

		conditionals.addMoveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();

				if(parentScreen == PLAY){
					Start.StartGerbil.controller.createBlocks(2,lineSelect,1, null);
					Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());					

				}else if(parentScreen == USERFUNCTION){

					Start.StartGerbil.controller.createFunctionBlocks(2,lineSelect,1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.FunctionUnFin());
				}
			}	
		});

		conditionals.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();

				if(parentScreen == 4){
					Start.StartGerbil.controller.createBlocks(0,lineSelect,1, null);
					Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());					

				}else if(parentScreen == 7){

					Start.StartGerbil.controller.createFunctionBlocks(0,lineSelect,1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.FunctionUnFin());
				}
			}	
		});

		conditionals.addTurnLeftEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lineSelect  = conditionals.getSelectedLineNumber()+conditionals.getBegin();

				if(parentScreen == PLAY){
					Start.StartGerbil.controller.createBlocks(1,lineSelect,1, null);
					Start.StartGerbil.controller.createBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.getUnFinIns());					

				}else if(parentScreen == USERFUNCTION){

					Start.StartGerbil.controller.createFunctionBlocks(1,lineSelect,1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e',lineSelect,1,null);
					conditionals.refreshConditionalsJList(Start.StartGerbil.controller.FunctionUnFin());
				}
			}	
		});
	}

	/**
	 * Add the event handlers for the error dialog screen's compoenents during running
	 * @author Truong, Leslie
	 */
	private void addErrorDialogRunEventHandlers() {
		errorDialogRun.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorDialogRun.hide();
				Play.setGridIcons();
				Start.StartGerbil.controller.resetTempGrid();
				showParent();
				Play.deselectIndexColor();
			}
		});
	}

	/**
	 * Add the event handlers for the error dialog screen's components 
	 * @author Katiuska
	 */
	private void addErrorDialogEventHandlers() {
		errorDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorDialog.hide();
				showParent();
			}		
		});
	}
	
	
	/**
	 * Finish Screen's event handlers added
	 * @author Katiuska, Truong
	 */
	private void addFinishEventHandlers(){
		finish.addLoadGamesButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen = 6;
				savedGames.refreshGamesList();
				savedGames.show();
				finish.hide();
			}		
		});
		
		finish.addNewGamesButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen = 6;
				newGame.show();
				finish.hide();
			}		
		});
	}
	
	/**
	 * Add the event handlers for the instructions screen's compoenents 
	 * @author Katiuska
	 */
	private void addInstructionsEventHandlers() {
		instructionsScreen.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionsScreen.hide();
				main.show();
			}		
		});
	}

	/**
	 * Add event handlers for the Main screen
	 * @author Katiuska
	 */
	private void addMainEventHandlers() {

		main.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playOptions.show();
				main.hide();
				parentScreen = MAIN; 
			}
		});
		main.addInstructionsEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				instructionsScreen.show();
				main.hide();
				parentScreen = MAIN; 
			}
		});
		main.addExitEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}


	/**
	 * Add event handlers for new game screen
	 * @author Katiuska 
	 */
	private void addNewGameEventHandlers() {
		newGame.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = NewGame.textF.getText(); 

				if(!text.isEmpty()){
					NewGame.textF.setText(""); //must reset the text line

					if(Backend.gameExists(text)){
						parentScreen = NEWGAME;
						errorDialog.errorL.setText("Please enter a Game name that doesn't already exist.");
						newGame.hide();
						errorDialog.show();
					}else{
						Start.StartGerbil.controller.createGame(text);
						parentScreen = PLAY;
						Start.StartGerbil.controller.resetTempGrid();
						Play.setNewGrid(Start.StartGerbil.controller.getCurrGame().getGrid().getGridRepresentation());
						Play.setGridIcons();
						playScreen.refreshUserFunctions();
						Play.refreshCodeList();
						playScreen.initialPlayScreen();

						newGame.hide();
						playScreen.show();
						playScreen.enableCreateFunction();
					}
				}else{
					parentScreen = NEWGAME;
					errorDialog.errorL.setText("Please enter a valid Game name");
					newGame.hide();
					errorDialog.show();
				}

			
			}
		});

		newGame.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewGame.textF.setText("");
				newGame.hide();
				playOptions.show(); // will always be the parent...
			}		
		});
	}

	/**
	 * Add event handlers for yes no dialog 
	 * @author Katiuska 
	 */
	private void addOkYesDialogEventHandlers(){
		okNoDialog.addNoEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okNoDialog.hide();
				playOptions.show();
			}		
		});

		okNoDialog.addOkEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.saveGame();

				try{
					Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
				}catch(Exception es){
					System.out.println("Unable to save game.");
				}

				okNoDialog.hide();
				playOptions.show();
			}		
		});
	}


	/**
	 * Add event handlers for the PlayOptions screen
	 * @author Katiuska
	 */
	private void addPlayOptionsEventHandlers() {
		playOptions.addLoadGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen =PLAYOPTIONS;
				playOptions.hide();

				//wanna update the list b4 displaying!
				savedGames.refreshGamesList();
				savedGames.show();
			}	
		});

		playOptions.addNewGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				parentScreen = PLAYOPTIONS;
				playOptions.hide();

				newGame.show();
			}	
		});

		playOptions.addBackEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen = PLAYOPTIONS;

				playOptions.hide();

				main.show();
			}
		});
	}


	/**
	 * Add event handlers for the Play screen
	 * @author Amulya, Katiuska, Truong, Leslie
	 */
	private void addPlayEventHandlers() {
		/**Button Listeners**/

		playScreen.addDeleteFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFunction.updateFunctionsList(StartGerbil.controller.getFunctions());
				deleteFunction.show();

				editing = false;
				inserting = false;
				deleting = false;

				Play.editB.setBackground(Color.BLACK);
				Play.insertB.setBackground(Color.BLACK);
				Play.deleteB.setBackground(Color.BLACK);


				playScreen.hide();
				parentScreen = PLAY;
			}
		});

		playScreen.addMenuEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentScreen = PLAY; 

				okNoDialog.okNoDialogL.setText("Would you like to save your current game?");
				okNoDialog.show();

				playScreen.hide();
			}	
		});
		
		/**
		 * @author Leslie, Truong
		 */
		playScreen.addPlayEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playScreen.insertB.setBackground(Color.black);
				stop = false;
				Thread thread = new Thread() {
					public void run() {
						int fruitCount=0;
						parentScreen=4;	
						int errortype= Start.StartGerbil.controller.runBlocks();
						ArrayList<String> instructions = Start.StartGerbil.controller.getFinalBlocks();
						ArrayList<Integer> lineNumbers= Start.StartGerbil.controller.getFinalBlocksLineNumbers();
						Start.StartGerbil.controller.resetTempGrid();//just in case, resetting grid and gerbil object
						int error=-1;
						for(int i = 0; i < instructions.size() && !stop; i++) {
							if(instructions.get(i).equals("Turn Left")) {
								Start.StartGerbil.controller.turnLeft(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showTurnLeft(Start.StartGerbil.controller.getTempGerbil().getCompass(), Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY());
								error=i;
								//HIGHLIGHT show highlighted line here using lineNumbers.get(i)
								Play.setSelectedIndexColor(lineNumbers.get(i), 'b');
							}
							else if(instructions.get(i).equals("Move Forward")) {
								int currX = Start.StartGerbil.controller.getTempGerbil().getX();
								int currY = Start.StartGerbil.controller.getTempGerbil().getY();
								char oldGridSpotType = Start.StartGerbil.controller.tempgrid[currY][currX];
								Start.StartGerbil.controller.moveForward(Start.StartGerbil.controller.getTempGerbil());
								playScreen.showMove(currX, currY, Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.getTempGerbil().getCompass(), oldGridSpotType);
								error=i;
								//HIGHLIGHT show highlighted line here using lineNumbers.get(i)
								Play.setSelectedIndexColor(lineNumbers.get(i), 'b');
							}
							else if(instructions.get(i).equals("Eat")) {
								Start.StartGerbil.controller.eat(Start.StartGerbil.controller.getTempGerbil().getX(), Start.StartGerbil.controller.getTempGerbil().getY(), Start.StartGerbil.controller.tempgrid);
								error=i;
								//HIGHLIGHT show highlighted line here using lineNumbers.get(i)
								Play.setSelectedIndexColor(lineNumbers.get(i), 'b');
								fruitCount++;
							}
							try {
								sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if(stop) {
							errorDialogRun.errorL.setText("Program stopped running. It will now reset");
							errorDialogRun.show();
						}
						else if(errortype==1){
							//ERROR: insert Dialogue BoxCannot Eat because no food here
							//HIGHLIGHT the error block using lineNumbers.get(error+1);
							Play.setSelectedIndexColor(lineNumbers.get(error+1), 'e');
							errorDialogRun.errorL.setText("Cannot Eat: there is no food at square");
							errorDialogRun.show();
						}else if(errortype==2){
							//ERROR: insert Dialogue BoxCannot Move Forward bc there is WALL
							//HIGHLIGHT the error block using lineNumbers.get(error+1);
							Play.setSelectedIndexColor(lineNumbers.get(error+1), 'e');
							errorDialogRun.errorL.setText("Cannot Move Forward: there is a wall ahead");
							errorDialogRun.show();
						}else if(errortype==3){
							//miscellaneous error, could not compile code(this shouldn't happen)
						}else if(errortype==4){
							//ERROR: insert Dialogue BoxDid not reach water/goal
							errorDialogRun.errorL.setText("Did not reach water, Try Again!");
							errorDialogRun.show();
						}else if(errortype==-1){
							//parsing error(this shouldn't happen)
						}else if(errortype==-2){
							//ERROR: insert Dialogue BoxInfiniteLoop was created, cannot run code
							//this does not run/animate the gerbil
							errorDialogRun.errorL.setText("Infinite Loop was created, please edit your code");
							errorDialogRun.show();
						}else if(errortype==0){
							//WIN THE GAME
							Start.StartGerbil.controller.resetTempGrid();
							Play.setGridIcons();
							
							Start.StartGerbil.controller.saveGame();
							
							try{
								Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
							}catch(Exception es){
								System.out.println("Unable to save game.");
							}
							
							finish.setFruitCount(fruitCount);
							finish.show();
							playScreen.hide();
						}
					}
				};
				thread.start();
			}
		});

		playScreen.addStopEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editing = false;
				inserting = false;
				deleting = false;
				stop = true;
				playScreen.setStopSelected();
			}	
		});

		playScreen.addInsertEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inserting = true;
				editing = false;
				deleting = false; 
				playScreen.enableAllPlayDD();	
				playScreen.enableAllButtons();
				playScreen.setInsertSelected();
			}	
		});

		playScreen.addEditEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editing = true;
				inserting = false;
				deleting = false;

				parentScreen = PLAY; 
				errorDialogRun.errorL.setText("Edit is for fancy system.");
				errorDialogRun.show();

				playScreen.setEditSelected();
			}	
		});

		playScreen.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleting = true;
				inserting = false;
				editing = false;

				Play.playcodeList.setSelectedIndex(Play.playcodeList.getModel().getSize()-1);
				playScreen.setDeleteSelected();
			}
		});

		playScreen.addClearAllEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playScreen.clearAll();
				inserting=false;
				playScreen.setClearAllSelected();

				Start.StartGerbil.controller.clearBlocks();
			}	
		});

		playScreen.addSaveEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				Start.StartGerbil.controller.saveGame();

				try{
					Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
				}catch(Exception es){
					System.out.println("Unable to save game.");
				}

				playScreen.setSaveSelected();
				playScreen.setSaveUnselected();
			}	
		});

		playScreen.addCreateFunctionEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedCreateFunctionFirst=true;
				parentScreen = PLAY; 

				editing = false;
				//inserting = false;
				deleting = false;

				Play.editB.setBackground(Color.BLACK);
				Play.insertB.setBackground(Color.BLACK);
				Play.deleteB.setBackground(Color.BLACK);

				userFunction.refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
				userFunction.refreshCodeList();

				userFunction.show();
				playScreen.hide();

			}	
		});

		/**JComboBoxes**/
		playScreen.addConditionalsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(inserting==true){
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
					String newType = Play.conditionalsDD.getSelectedItem().toString();
					if(newType.equals("If")){
						parentScreen = PLAY;
						Start.StartGerbil.controller.createBlocks(3,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("If");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else if(newType.equals("Else")){
						int ret = Start.StartGerbil.controller.createBlocks(5,selectedIndexPlayCodeList , 0, null);
						if(ret == 4){ //DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = PLAY;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret==5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = PLAY;

							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();

						}else{
							parentScreen = PLAY;
							conditionals.setText("Else");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("Else if")){
						int ret = Start.StartGerbil.controller.createBlocks(4,selectedIndexPlayCodeList, 0, null);
						if(ret==4){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = PLAY;
							errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
							errorDialog.show();
						}else if(ret == 5){//DO NOT OPEN CONDITIONALS = show error dialog!!!!
							parentScreen = PLAY;
							errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
							errorDialog.show();
						}else{
							parentScreen = PLAY;
							conditionals.setText("Else if");
							int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
							conditionals.setBegin(tempLine);
							conditionals.show();
							playScreen.hide();
						}
					}else if(newType.equals("While")){
						parentScreen = PLAY;
						Start.StartGerbil.controller.createBlocks(6,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("While");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}else {//if(newType.equals("Repeat")){
						parentScreen = PLAY;
						Start.StartGerbil.controller.createBlocks(7,selectedIndexPlayCodeList, 0, null);

						conditionals.setText("Repeat");
						int tempLine = selectedIndexPlayCodeList+2; //for the current statement and begin
						conditionals.setBegin(tempLine);
						conditionals.show();
						playScreen.hide();
					}
				}

			}	
		});

		playScreen.addGivenFunctionsListSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedIndexPlayCodeList = Play.playcodeList.getSelectedIndex();
				if(inserting == true){
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}

					String term = (String) Play.givenFunctionsDD.getSelectedItem();
					int type = -1;
					if(term.equals("Move Forward")){
						type = 2;
					}else if(term.equals("Turn Left")){
						type = 1;
					}else {//if(term.equals("Eat")){
						type = 0;

					}
					Start.StartGerbil.controller.createBlocks(type, selectedIndexPlayCodeList,0, null);
					Start.StartGerbil.controller.createBlocks('e', selectedIndexPlayCodeList,1, null);
					Play.refreshCodeList();
				}
			}});


		playScreen.addUserFunctionsListSelectionListener(new ActionListener() { // this doesn't work. - kat
			public void actionPerformed(ActionEvent arg0) {
				if(inserting==true){ 
					int lineS = Play.playcodeList.getSelectedIndex();
					Block bTemp = Start.StartGerbil.controller.getBlockByLineMain(lineS);
					if(lineS ==Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
						selectedIndexPlayCodeList = lineS;
					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
					}
					String funcName = (String) Play.userFunctionsDD.getSelectedItem();
					Start.StartGerbil.controller.createBlocks(8,selectedIndexPlayCodeList,1, null); // should it be using createFunctions Block? - kat 
					Start.StartGerbil.controller.createBlocks('e',selectedIndexPlayCodeList,1,funcName);
					Play.refreshCodeList();
				}
			}	
		});

		playScreen.addCodeListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(deleting == true){
					int lineS = Play.playcodeList.getSelectedIndex();

					Block bTemp = Start.StartGerbil.controller.getBlockByLine(lineS);
					if(lineS >=Play.playcodeList.getModel().getSize()-1){ //last line => keep the insert line as last line; changed to fix delete from <= to >= 
						//selectedIndexPlayCodeList = lineS;

					}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
						selectedIndexPlayCodeList = 0;
					//	Start.StartGerbil.controller.deleteBlock(bTemp);
						deleting = false;
						Play.deleteB.setBackground(Color.BLACK);
						Play.refreshCodeList(); // refreshes the code list in Play screen
					}else{ //get the block's line begin
						selectedIndexPlayCodeList = bTemp.getlineBegin();
						Start.StartGerbil.controller.deleteBlock(bTemp);
						deleting = false;
						Play.deleteB.setBackground(Color.BLACK);
						Play.refreshCodeList(); // refreshes the code list in Play screen
					}										
				}else if(inserting==true){
					playScreen.setSingleSelectionMode();
				}
			}	
		});
	}

	/**
	 * Add event handlers for saved games screen
	 * @author Katiuska
	 */
	private void addSavedGamesEventHandlers(){
		savedGames.addOpenGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oGame= (String) savedGames.gamesList.getSelectedItem();
				if(oGame==null){
					parentScreen=6;
					errorDialog.errorL.setText("Must Select a Game");
					errorDialog.show();
				}else{
					Game temp = Start.StartGerbil.controller.loadGame(oGame);
					Play.setNewGrid(Start.StartGerbil.controller.getCurrGame().getGrid().getGridRepresentation());
					Play.setGridIcons();
					Play.refreshCodeList();
					playScreen.refreshUserFunctions();
					playScreen.show();
					savedGames.hide();
				}
			}		
		});

		savedGames.addDeleteGameEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dGame= (String) savedGames.gamesList.getSelectedItem();
				if(dGame==null){
					parentScreen=SAVEDGAME;
					errorDialog.errorL.setText("Must Select a Game");
					errorDialog.show();
				}else{
					Start.StartGerbil.controller.deleteGame(dGame);
					
					try{ // just in case they exit the window - kat
						Start.StartGerbil.backend.saveGames(Start.StartGerbil.backend.getGameList());
					}catch(Exception es){
					}
				
					savedGames.refreshGamesList();
				}
			}		
		});

		savedGames.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savedGames.hide();
				playOptions.show();
			}		
		});
	}

	/**
	 * Add event handlers for user functions screen
	 * @author Amulya, Katiuska
	 */
	private void addUserFunctionEventHandlers(){
		userFunction.addFunctionListListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userFunction.addtomain){
					String funcName = (String) userFunction.userDefinedFunctions.getSelectedItem();
					Start.StartGerbil.controller.createFunctionBlocks(8, userFunction.getSelectedLineNumber(), 1, null);
					Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, funcName);
					userFunction.updateInstructionsList(Start.StartGerbil.controller.FunctionUnFin());
				}
				userFunction.dontAddToMain(false);	
			}
		});

		userFunction.addCancelEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentScreen=PLAY;
				Start.StartGerbil.controller.clearTempFunctionBlockInstructions();
				errorDialog.hide();
				showParent();
			}		
		});

		/**
		 * @author Leslie
		 */
		userFunction.addOkEventHandler(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String functionName = userFunction.getFunctionName();
				if(!functionName.equals("")) {
					errorDialog.hide();
					showParent();
					int error= Start.StartGerbil.controller.createFunction(functionName);
					if(error==1){
						parentScreen=USERFUNCTION;
						//error: function names can only consist of letters/numbers
						errorDialog.errorL.setText("Name must consist of letters/numbers");
						errorDialog.show();
					}else if(error==2){
						parentScreen=USERFUNCTION;
						//error: function name already exists, choose another
						errorDialog.errorL.setText("Name already exists, enter another name");
						errorDialog.show();
					}else{
						parentScreen=PLAY;
						userFunction.clearLabels();
						//userFunction.userDefinedFunctions.addItem(functionName); // don't think this is needed?
						userFunction.hide();
						userFunction.dontAddToMain(true);
						inserting=false;
						playScreen.refreshUserFunctions();
						showParent();
					}
				}else{
					parentScreen=USERFUNCTION;
					errorDialog.errorL.setText("You Must Enter a Function Name");
					errorDialog.show();
				}

			}		
		});


		userFunction.addUserFunctionComboBoxListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				String funcName = (String) userFunction.userDefinedFunctions.getSelectedItem();
				Start.StartGerbil.controller.createFunctionBlocks(8,funcListSelect,1, null); // should it be using createFunctions Block? - kat 
				Start.StartGerbil.controller.createFunctionBlocks('e',funcListSelect,1,funcName);
				userFunction.refreshCodeList();
			}
		});


		userFunction.addRepeatEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = USERFUNCTION;
				Start.StartGerbil.controller.createFunctionBlocks(7,funcListSelect, 0, null);
				conditionals.setText("Repeat");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}
		});

		userFunction.addWhileEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = USERFUNCTION;
				Start.StartGerbil.controller.createFunctionBlocks(6,funcListSelect, 0, null);
				conditionals.setText("While");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}
		});

		userFunction.addElseEventHandler(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}

				int fRet = Start.StartGerbil.controller.createFunctionBlocks(5,funcListSelect, 0, null);
				if(fRet == 4){ //error
					parentScreen = USERFUNCTION;
					errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
					errorDialog.show();
				}else if(fRet ==5){
					parentScreen = USERFUNCTION;
					errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
					errorDialog.show();
				}else{
					parentScreen = USERFUNCTION;
					conditionals.setText("Else");
					int tempLine = funcListSelect+2; //for the current statement and begin
					conditionals.setBegin(tempLine);
					conditionals.show(); //shows the conditionals screen so when they return we get back here
					userFunction.hide();
				}
			}
		});

		userFunction.addElseIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}

				int fRet = Start.StartGerbil.controller.createFunctionBlocks(4,funcListSelect, 0, null);
				if(fRet == 4){ //error
					parentScreen = USERFUNCTION;
					errorDialog.errorL.setText("Error: 'If' has to exist in order to use 'Else If' or 'Else'");
					errorDialog.show();
				}else if(fRet ==5){
					parentScreen = USERFUNCTION;
					errorDialog.errorL.setText("Error: Need to insert 'Else If' or 'Else' after an 'If' statement");
					errorDialog.show();
				}else{
					parentScreen = USERFUNCTION;
					conditionals.setText("Else if");
					int tempLine = funcListSelect+2; //for the current statement and begin
					conditionals.setBegin(tempLine);
					conditionals.show(); //shows the conditionals screen so when they return we get back here
					userFunction.hide();
				}
			}
		});

		userFunction.addIfEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int lineS = UserFunction.functionsCodeList.getSelectedIndex();
				Block bTemp = Start.StartGerbil.controller.getBlockByLineUserFunction(lineS);
				if(lineS ==UserFunction.functionsCodeList.getModel().getSize()-1){ //last line => keep the insert line as last line
					funcListSelect = lineS;
				}else if(bTemp==null){ //if null then nothing inside array so set the selected line to 0
					funcListSelect = 0;
				}else{ //get the block's line begin
					funcListSelect = bTemp.getlineBegin();
				}
				parentScreen = USERFUNCTION;
				Start.StartGerbil.controller.createFunctionBlocks(3,funcListSelect, 0, null);
				conditionals.setText("If");
				int tempLine = funcListSelect+2; //for the current statement and begin
				conditionals.setBegin(tempLine);
				conditionals.show(); //shows the conditionals screen so when they return we get back here
				userFunction.hide();
			}});

		userFunction.addMoveAheadEventHandler(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(2, userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.userFunctionShowJList());
			}});

		userFunction.addEatEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(0, userFunction.getSelectedLineNumber(), 1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e', userFunction.getSelectedLineNumber(), 1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.userFunctionShowJList());
			}
		});

		userFunction.addTurnLeftEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.StartGerbil.controller.createFunctionBlocks(1,userFunction.getSelectedLineNumber(),1, null);
				Start.StartGerbil.controller.createFunctionBlocks('e',userFunction.getSelectedLineNumber(),1, null);
				userFunction.updateInstructionsList(Start.StartGerbil.controller.userFunctionShowJList());

			}});
	}

	/**
	 * Add delete functions screen event handlers
	 * @author Katiuska 
	 */
	private void addDeleteFunctionEventHandlers() {

		deleteFunction.addDeleteEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String function = deleteFunction.getSelectedFunction();
				if(function != null) {
					StartGerbil.controller.deleteFunction(function);	
					deleteFunction.updateFunctionsList(StartGerbil.controller.getFunctions());
					deleteFunction.setNewSelection();
				}
			}
		});

		deleteFunction.addDoneEventHandler(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showParent();
				deleteFunction.hide();
			}
		});
	}

	/**
	 * DeleteFunction 1
	 * Main 2
	 * NewGame 3
	 * Play 4
	 * PlayOptions 5
	 * SavedGame 6
	 * UserFunction 7
	 * Show parent method to make it easier to go back a screen using enumeration
	 * @author Katiuska
	 */
	private  void showParent(){ 
		switch(parentScreen){
		case 1:
			deleteFunction.show();
			break;
		case 2:
			main.show();
			break;
		case 3:
			newGame.show();
			break;
		case 4:
			playScreen.show();
			break;
		case 5:
			playOptions.show();
			break;
		case 6:
			savedGames.show();
			break;
		case 7:
			userFunction.show();
			break;
		}
	}
}