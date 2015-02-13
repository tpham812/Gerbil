package View; 

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Katiuska Nunez 
 * @author Truong Pham: (Grid related components)- setGridComponents, setGridIcons, showMove, showTurnLeft
 * 
 * This class creates a GUI for the Play screen.
 */
public class Play extends Screen{
	static DefaultListModel model;
	
	private static JFrame frame;

	/**Panels**/
	private static JPanel upperPanel = new JPanel();
	private static JPanel leftPanel = new JPanel(); 
	private static JPanel gridPanel = new JPanel();
	private static JPanel lowerPanel = new JPanel();

	/**Upper Panel Components**/
	public static JButton menuB;
	public static JButton playB;
	public static JButton stopB; 
	public static JButton insertB; 
	public static JButton editB; 
	public static JButton deleteB;
	public static JButton clearAllB; 
	public static JButton saveB;

	/**Left Panel Components**/
	public static JList playcodeList;
	private static JScrollPane scrollpane;
	private static JLabel beginL; 
	private static JLabel endL;

	/**Grid Panel components**/
	private static JLabel[][] gridBoxes;

	/**Lower Panel Components**/
	public static JButton createFunctionB;
	public static JButton deleteFunctionB;
	public static JComboBox conditionalsDD;
	public static JComboBox givenFunctionsDD;
	public static JComboBox userFunctionsDD;
	public static JComboBox checksDD;
	public static JComboBox numsDD;

	private static JLabel conditionalStatementsL;
	private static JLabel givenFunctionsL;
	private static JLabel userDefinedFunctionsL;
	private static JLabel checksL;
	private static JLabel numsL;


	/** Gerbil grid representation */
	private static char[][] grid;
	private static int row;
	private static int column;
	/** Image icons of all the necessary pictures */
	private static ImageIcon imageApple, imagePear, imageGrass, imagePumpkin, imageWall, imageGerbilEast, imageGerbilWest, imageGerbilNorth, imageGerbilSouth, imageWater;

	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public Play() {
		try {
			
			imageApple = new ImageIcon(ImageIO.read(new File("pics/apple icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));
			imagePear = new ImageIcon(ImageIO.read(new File("pics/pear icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageGrass = new ImageIcon(ImageIO.read(new File("pics/grass icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imagePumpkin = new ImageIcon(ImageIO.read(new File("pics/pumpkin.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageWall = new ImageIcon(ImageIO.read(new File("pics/wall icon.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageGerbilEast = new ImageIcon(ImageIO.read(new File("pics/gerbilEast.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageGerbilWest = new ImageIcon(ImageIO.read(new File("pics/gerbilWest.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageGerbilNorth = new ImageIcon(ImageIO.read(new File("pics/gerbilNorth.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageGerbilSouth = new ImageIcon(ImageIO.read(new File("pics/gerbilSouth.png")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;
			imageWater = new ImageIcon(ImageIO.read(new File("pics/waterBottle.jpg")).getScaledInstance(42, 34, Image.SCALE_SMOOTH));;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		createAndShowGUI();
	}

	/**
	 * Gets the play frame
	 * @return play frame
	 */
	public final JFrame getPlayFrame(){
		return frame;
	}

	/**
	 * Sets new grid
	 * @param newGrid grid to be set
	 */
	public static void setNewGrid(char[][] newGrid) {
		grid = newGrid;
	}

	/**
	 * Sets the gerbil location in the grid
	 * @param x x coordinate in grid of gerbil
	 * @param y y coordinate in grid of gerbil
	 */
	public static void setGerbilLocation(int x, int y) {
		row = x;
		column = y;
	}

	/**
	 * Deletes all grid components
	 */
	public static  void deleteGridComponents(){
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				gridBoxes[i][j].removeAll();
				gridPanel.remove(gridBoxes[i][j]);
			}
		}
	}

	/**
	 * Refreshes the grid
	 */
	public static  void refreshGrid(){
		deleteGridComponents();
		setGridComponents();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 */
	protected static void createAndShowGUI() { 
		frame = new JFrame("Play"); // EDIT: User defined game name?
		//TestPane p = new TestPane();//GRID

		setUpperComponents();
		setLeftComponents();
		setGridComponents();
		setLowerComponents();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Container c = frame.getContentPane();

		gc.gridwidth = 2;
		gc.gridx = 0;
		gc.gridy = 0; 
		c.add(upperPanel,gc);

		gc.gridwidth = 1; 
		gc.gridx = 0;
		gc.gridy=1;
		c.add(leftPanel,gc);

		gc.gridwidth =1;
		gc.gridx = 1;
		gc.gridy=1;
		c.add(gridPanel,gc); // ADDING GRID HERE

		gc.gridwidth = 2; 
		gc.gridx = 0;
		gc.gridy=2;
		c.add(lowerPanel,gc);

		frame.setResizable(false);
		//frame.setMinimumSize(new Dimension(875, 150));
		frame.pack();
		frame.setVisible(false);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Sets upper components of the play screen
	 */
	public static void setUpperComponents(){
		upperPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		Dimension size= upperPanel.getPreferredSize();
		size.width = 875;
		size.height = 35;
		upperPanel.setPreferredSize(size);

		ImageIcon menuIcon = new ImageIcon("pics/MenuButton_Play.png");
		Image  menuImg = menuIcon.getImage() ;   //width, height, type scaling
		Image  menuNewimg =  menuImg.getScaledInstance( 105, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		menuB = new JButton(new ImageIcon( menuNewimg));
		menuB.setBorderPainted(false);
		menuB.setFocusPainted(false);
		menuB.setContentAreaFilled(true);
		menuB.setBackground(Color.BLACK);
		menuB.setOpaque(true);
		/********/
		ImageIcon playIcon = new ImageIcon("pics/PlayButton_Play.png");
		Image  playImg = playIcon.getImage() ;   //width, height, type scaling
		Image  playNewimg =  playImg.getScaledInstance( 105, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		playB = new JButton(new ImageIcon( playNewimg));
		playB.setBorderPainted(false);
		playB.setFocusPainted(false);
		playB.setContentAreaFilled(true);
		playB.setBackground(Color.BLACK);
		playB.setOpaque(true);
		/********/
		ImageIcon stopIcon = new ImageIcon("pics/StopButton_Play.png");
		Image  stopImg = stopIcon.getImage() ;   //width, height, type scaling
		Image  stopNewimg =  stopImg.getScaledInstance(110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		stopB = new JButton(new ImageIcon( stopNewimg));
		stopB.setBorderPainted(false);
		stopB.setFocusPainted(false);
		stopB.setContentAreaFilled(true);
		stopB.setBackground(Color.BLACK);
		stopB.setOpaque(true);
		/********/
		ImageIcon insertIcon = new ImageIcon("pics/InsertButton_Play.png");
		Image  insertImg = insertIcon.getImage() ;   //width, height, type scaling
		Image  insertNewimg =  insertImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		insertB = new JButton(new ImageIcon(insertNewimg));
		insertB.setBorderPainted(false);
		insertB.setFocusPainted(false);
		insertB.setContentAreaFilled(true);
		insertB.setBackground(Color.BLACK);
		insertB.setOpaque(true);
		/********/
		ImageIcon editIcon = new ImageIcon("pics/EditButton_Play.png");
		Image  editImg = editIcon.getImage() ;   //width, height, type scaling
		Image  editNewimg =  editImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		editB = new JButton(new ImageIcon( editNewimg));
		editB.setBorderPainted(false);
		editB.setFocusPainted(false);
		editB.setContentAreaFilled(true);
		editB.setBackground(Color.BLACK);
		editB.setOpaque(true);
		/********/
		ImageIcon deleteIcon = new ImageIcon("pics/DeleteButton_Play.png");
		Image  deleteImg = deleteIcon.getImage() ;   //width, height, type scaling
		Image  deleteNewimg =  deleteImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		deleteB = new JButton(new ImageIcon( deleteNewimg));
		deleteB.setBorderPainted(false);
		deleteB.setFocusPainted(false);
		deleteB.setContentAreaFilled(true);
		deleteB.setBackground(Color.BLACK);
		deleteB.setOpaque(true);
		/********/
		ImageIcon clearallIcon = new ImageIcon("pics/ClearAllButton_Play.png");
		Image  clearallImg = clearallIcon.getImage() ;   //width, height, type scaling
		Image  clearallNewimg =  clearallImg.getScaledInstance( 110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		clearAllB = new JButton(new ImageIcon( clearallNewimg));
		clearAllB.setBorderPainted(false);
		clearAllB.setFocusPainted(false);
		clearAllB.setContentAreaFilled(true);
		clearAllB.setBackground(Color.BLACK);
		clearAllB.setOpaque(true);
		/********/
		ImageIcon saveIcon = new ImageIcon("pics/SaveButton_Play.png");
		Image  saveImg = saveIcon.getImage() ;   //width, height, type scaling
		Image  saveNewimg =  saveImg.getScaledInstance(110, 25,  java.awt.Image.SCALE_SMOOTH ) ; 

		saveB = new JButton(new ImageIcon(saveNewimg));
		saveB.setBorderPainted(false);
		saveB.setFocusPainted(false);
		saveB.setContentAreaFilled(true);
		saveB.setBackground(Color.BLACK);
		saveB.setOpaque(true);

		gc.weightx = 1; //determines how much of the space will be occupied by it
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.gridx=0;
		gc.gridy=0; 
		upperPanel.add(menuB, gc);

		gc.gridx=1;
		gc.gridy=0; 
		upperPanel.add(playB, gc);

		gc.gridx=2;
		gc.gridy=0; 
		upperPanel.add(stopB, gc);

		gc.gridx=3;
		gc.gridy=0; 
		upperPanel.add(insertB, gc);

		gc.gridx=4;
		gc.gridy=0; 
		upperPanel.add(editB, gc);

		gc.gridx=5;
		gc.gridy=0; 
		upperPanel.add(deleteB, gc);

		gc.gridx=6;
		gc.gridy=0; 
		upperPanel.add(clearAllB, gc);

		gc.gridx=7;
		gc.gridy=0;
		upperPanel.add(saveB, gc);
	}

	/**
	 * Sets the left components of the play screen
	 */
	public static void setLeftComponents(){
		Dimension size= leftPanel.getPreferredSize();
		size.width =250;
		size.height = 500;
		leftPanel.setPreferredSize(size);

		beginL = new JLabel("Begin");
		endL = new JLabel("End");

		model=new DefaultListModel();

		//Controler.gamePlaying.getBlocks
		model.add(0," ");

		playcodeList = new JList(model);
		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-1);

		playcodeList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		deselectIndexColor();

		scrollpane = new JScrollPane(playcodeList);

		playcodeList.setVisibleRowCount(20);


		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(scrollpane, BorderLayout.CENTER);
		leftPanel.add(beginL, BorderLayout.NORTH);
		leftPanel.add(endL,BorderLayout.SOUTH);
	}

	/**
	 * Sets the grid components of the play screen
	 */
	public static void setGridComponents(){
		gridPanel.setLayout(new GridLayout(grid.length, grid[0].length));	
		Dimension size= gridPanel.getPreferredSize();
		size.width =630;
		size.height = 510;
		gridPanel.setPreferredSize(size);
		gridBoxes = new JLabel[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				gridBoxes[i][j] = new JLabel();
				gridPanel.add(gridBoxes[i][j]);
			}
		}
		setGridIcons();
	}


	/**
	 * Sets the lower components of the play screen
	 */
	public static void setLowerComponents(){
		lowerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		Dimension size= lowerPanel.getPreferredSize();
		size.width =875;
		size.height =  125; 
		lowerPanel.setPreferredSize(size);

		createFunctionB = new JButton(){
			public void paint(Graphics g) {
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth() - r.getWidth()) / 2;
				float yMargin = (float)(getHeight() - getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
			}
		};

		deleteFunctionB = new JButton(){
			public void paint(Graphics g) {
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth() - r.getWidth()) / 2;
				float yMargin = (float)(getHeight() - getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
			}
		};


		createFunctionB.setText("Create Function");
		deleteFunctionB.setText("Delete Function");

		// EDIT: this should come from something else...
		String[] conditionals = { "If", "Else", "Else if", "While", "Repeat" };
		String[] functions = {"Move Forward", "Turn Left", "Eat"};
		String [] checks = {"There'sWall?", "There'sNoWall", "There'sFood","There'sNoFood"};
		String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};

		conditionalsDD = new JComboBox(conditionals);
		givenFunctionsDD = new JComboBox(functions);

		userFunctionsDD = new JComboBox(Start.StartGerbil.controller.getFunctions()); // changed: getUserDefinedFunctionsStringArray().toArray()); kat
		checksDD = new JComboBox(checks);
		numsDD = new JComboBox(nums);

		conditionalStatementsL = new JLabel("<html>Conditional </br> Statements<html>");
		givenFunctionsL = new JLabel("Given Functions");
		userDefinedFunctionsL = new JLabel("<html>User Functions</html>");
		checksL = new JLabel("Checks");
		numsL = new JLabel("Numbers");

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = .70;
		gc.weighty = 1;
		gc.gridheight= 2;
		//top, left, botton, right <- insets
		gc.insets = new Insets(0,30,10,80);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx=0;
		gc.gridy=0;
		lowerPanel.add(createFunctionB, gc);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx=0;
		gc.gridy=1;
		lowerPanel.add(deleteFunctionB, gc);

		gc.insets = new Insets(20,30,0,0);

		gc.gridheight= 1;
		/*Labels*/
		gc.gridx=1;
		gc.gridy=0;
		lowerPanel.add(conditionalStatementsL, gc);

		gc.gridx=2;
		gc.gridy=0;
		lowerPanel.add(givenFunctionsL, gc);

		gc.gridx=3;
		gc.gridy=0;
		lowerPanel.add(userDefinedFunctionsL, gc);

		/*Dropdowns*/
		gc.gridx=1;
		gc.gridy=1;
		lowerPanel.add(conditionalsDD, gc);

		gc.gridx=2;
		gc.gridy=1;
		lowerPanel.add(givenFunctionsDD, gc);

		gc.gridx=3;
		gc.gridy=1;
		lowerPanel.add(userFunctionsDD, gc);


	}
	
	public static void setGridIcons() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				switch(grid[i][j]) {
				case'0':gridBoxes[i][j].setIcon(imageGrass);
				break;
				case'w':gridBoxes[i][j].setIcon(imageWall);
				break;
				case'a':gridBoxes[i][j].setIcon(imageApple);
				break;
				case'k':gridBoxes[i][j].setIcon(imagePumpkin);
				break;
				case'p':gridBoxes[i][j].setIcon(imagePear);
				break;
				case't':gridBoxes[i][j].setIcon(imageWater);
				break;
				}
			}
		}
		gridBoxes[row][column].setIcon(imageGerbilNorth);
	}
	

	public void showMove(int gerbilCurrX, int gerbilCurrY, int gerbilNewX, int gerbilNewY, char compass, char oldGridSpotType) {

		switch(oldGridSpotType) {
		case '0': gridBoxes[gerbilCurrY][gerbilCurrX].setIcon(imageGrass);
		break;
		case 'p': gridBoxes[gerbilCurrY][gerbilCurrX].setIcon(imagePear);
		break;
		case 'k': gridBoxes[gerbilCurrY][gerbilCurrX].setIcon(imagePumpkin);
		break;
		case 'a': gridBoxes[gerbilCurrY][gerbilCurrX].setIcon(imageApple);
		break;
		}
		switch(compass) {
		case'n':gridBoxes[gerbilNewY][gerbilNewX].setIcon(imageGerbilNorth);
		break;
		case's':gridBoxes[gerbilNewY][gerbilNewX].setIcon(imageGerbilSouth);
		break;
		case'w':gridBoxes[gerbilNewY][gerbilNewX].setIcon(imageGerbilWest);
		break;
		case'e':gridBoxes[gerbilNewY][gerbilNewX].setIcon(imageGerbilEast);
		break;
		}
	}

	public void showTurnLeft(char compass, int gerbilX, int gerbilY) {

		switch(compass) {
		case'n':gridBoxes[gerbilY][gerbilX].setIcon(imageGerbilNorth);
		break;
		case's':gridBoxes[gerbilY][gerbilX].setIcon(imageGerbilSouth);
		break;
		case'w':gridBoxes[gerbilY][gerbilX].setIcon(imageGerbilWest);
		break;
		case'e':gridBoxes[gerbilY][gerbilX].setIcon(imageGerbilEast);
		break;
		}
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 */
	protected void createScreen() {	

	}	

	/**
	 * Refreshes the code list
	 */
	public static void refreshCodeList(){
		model.clear();

		
		String [] temp = Start.StartGerbil.controller.JListString();

		model.add(0, " ");
		for(int i=0; i< temp.length;i++){
			String test = temp[i];
			model.add(i,test);
		}

		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-1);
	}

	
	public void setSingleSelectionMode(){
		playcodeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}



	public void setMultipleSelectionMode(){
		playcodeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}


	/**
	 * Shows the screen.
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Hides the screen.
	 */
	public void hide() {
		frame.setVisible(false);
	}

	@Override
	public void enable() {
		frame.setEnabled(true);
	}

	@Override
	public void disable() {
		frame.setEnabled(false);
	}

	/**Button Listeners**/
	
	/**
	 * Listener for menu button
	 * @param listener Listens for menu button being clicked
	 */
	public void addMenuEventHandler(ActionListener listener) {
		menuB.addActionListener(listener);
	}

	/**
	 * Listener for play button
	 * @param listener Listens for play button being clicked
	 */
	public void addPlayEventHandler(ActionListener listener) {
		playB.addActionListener(listener);
	}
	
	/**
	 * Listener for stop button
	 * @param listener Listens for stop button being clicked
	 */
	public void addStopEventHandler(ActionListener listener) {
		stopB.addActionListener(listener);
	}

	/**
	 * Listener for insert button
	 * @param listener Listens for insert button being clicked
	 */
	public void addInsertEventHandler(ActionListener listener) {
		insertB.addActionListener(listener);
	}

	/**
	 * Listener for edit button
	 * @param listener Listens for edit button being clicked
	 */
	public void addEditEventHandler(ActionListener listener) {
		editB.addActionListener(listener);
	}

	/**
	 * Listener for delete button
	 * @param listener Listens for delete button being clicked
	 */
	public void addDeleteEventHandler(ActionListener listener) {
		deleteB.addActionListener(listener);
	}

	/**
	 * Listener for clear button
	 * @param listener Listens for clear button being clicked
	 */
	public void addClearAllEventHandler(ActionListener listener) {
		clearAllB.addActionListener(listener);
	}

	/**
	 * Listener for save button
	 * @param listener Listens for save button being clicked
	 */
	public void addSaveEventHandler(ActionListener listener) {
		saveB.addActionListener(listener);
	}
	
	/**
	 * Listener for delete function button
	 * @param listener Listens for delete function button being clicked
	 */
	public void addDeleteFunctionEventHandler(ActionListener listener) {
		deleteFunctionB.addActionListener(listener);
	}

	/**
	 * Listener for create function button
	 * @param listener Listens for create function button being clicked
	 */
	public void addCreateFunctionEventHandler(ActionListener listener) {
		createFunctionB.addActionListener(listener);
	}

	/**Code List**/
	public void addCodeListSelectionListener(ListSelectionListener listener) {
		playcodeList.addListSelectionListener(listener);
	}

	/**JComboBoxes**/
	public void addConditionalsListSelectionListener(ActionListener listener) {
		conditionalsDD.addActionListener(listener);
	}

	public void addGivenFunctionsListSelectionListener(ActionListener listener) {
		givenFunctionsDD.addActionListener(listener);

	}

	public void addUserFunctionsListSelectionListener(ActionListener listener) {
		userFunctionsDD.addActionListener(listener);

	}

	public void refreshUserFunctions(){
		userFunctionsDD.removeAllItems();
		ArrayList<String> temp = Start.StartGerbil.controller.getFunctionsArrayList();
		
		for(int i=0; i<temp.size(); i++){
			userFunctionsDD.addItem(temp.get(i));
		}
	}

	public void initialPlayScreen(){//only insert, save, menu, createFunction, deleteFunction should be active
		playB.setEnabled(false);
		stopB.setEnabled(false);
		editB.setEnabled(false);
		deleteB.setEnabled(false);
		clearAllB.setEnabled(true);
		createFunctionB.setEnabled(true);
		insertB.setBackground(Color.black);
		playcodeList.setSelectionBackground(Color.white);

		conditionalsDD.setEnabled(false);
		givenFunctionsDD.setEnabled(false);
		userFunctionsDD.setEnabled(false);
		checksDD.setEnabled(false);
		numsDD.setEnabled(false);
		Start.StartGerbil.controller.clearBlocks();
		Start.StartGerbil.controller.userCodingNow = null;
		Start.StartGerbil.controller.parent = null;
	}
	
	public void inUsePlayScreen(){//only insert, save, menu should be active
		playB.setEnabled(true);
		stopB.setEnabled(true);
		editB.setEnabled(true);
		deleteB.setEnabled(true);
		clearAllB.setEnabled(true);
		createFunctionB.setEnabled(true);

		conditionalsDD.setEnabled(true);
		givenFunctionsDD.setEnabled(true);
		userFunctionsDD.setEnabled(true);
		checksDD.setEnabled(true);
		numsDD.setEnabled(true);
	}
	
	public void enableAllButtons(){
		menuB.setEnabled(true);
		playB.setEnabled(true);
		stopB.setEnabled(true);
		insertB.setEnabled(true);
		editB.setEnabled(true);
		deleteB.setEnabled(true);
		clearAllB.setEnabled(true);
		saveB.setEnabled(true);
		createFunctionB.setEnabled(true);
		deleteFunctionB.setEnabled(true);
	}

	public  void disableAllPlayDDButChecks(){
		conditionalsDD.setEnabled(false);
		givenFunctionsDD.setEnabled(false);
		userFunctionsDD.setEnabled(false);
		numsDD.setEnabled(false);
	}

	public  void enableAllPlayDD(){
		conditionalsDD.setEnabled(true);
		givenFunctionsDD.setEnabled(true);
		userFunctionsDD.setEnabled(true);
		checksDD.setEnabled(true);
		numsDD.setEnabled(true);
	}

	public  void disableAllPlayDD(){
		conditionalsDD.setEnabled(false);
		givenFunctionsDD.setEnabled(false);
		userFunctionsDD.setEnabled(false);
		checksDD.setEnabled(false);
		numsDD.setEnabled(false);
	}

	public  void enableCreateFunction(){
		createFunctionB.setEnabled(true);
	}

	public  void disableCreateFunction(){
		createFunctionB.setEnabled(false);
	}


	public  void enableDeleteFunction(){
		deleteFunctionB.setEnabled(true);
	}

	public  void disableDeleteFunction(){
		deleteFunctionB.setEnabled(false);
	}
	
	public  void setPlaySelected(){
		stopB.setBackground(Color.black);
		insertB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		saveB.setBackground(Color.black);


		playB.setBackground(Color.yellow);
	}

	public  void setStopSelected(){
		playB.setBackground(Color.black);
		insertB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		saveB.setBackground(Color.black);

		stopB.setBackground(Color.yellow);
	}

	public  void setInsertSelected(){
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		saveB.setBackground(Color.black);


		insertB.setBackground(Color.yellow);
	}

	public  void setEditSelected(){
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		insertB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		saveB.setBackground(Color.black);

		editB.setBackground(Color.yellow);
	}


	public  void setDeleteSelected(){
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		editB.setBackground(Color.black);
		insertB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		saveB.setBackground(Color.black);


		deleteB.setBackground(Color.yellow);
	}

	public  void setClearAllSelected(){
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		insertB.setBackground(Color.black);
		saveB.setBackground(Color.black);

		clearAllB.setBackground(Color.black);
	}

	public  void setSaveSelected(){
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		insertB.setBackground(Color.black);


		saveB.setBackground(Color.yellow);
	}

	public void setSaveUnselected(){
		saveB.setBackground(Color.black);
	}

	public void resetAllHighlighting(){
		menuB.setBackground(Color.black);
		playB.setBackground(Color.black);
		stopB.setBackground(Color.black);
		editB.setBackground(Color.black);
		deleteB.setBackground(Color.black);
		clearAllB.setBackground(Color.black);
		insertB.setBackground(Color.black);

		saveB.setBackground(Color.yellow);
	}

	
	public void clearAll(){
		Start.StartGerbil.controller.clearBlocks();
		model.clear();
		Play.refreshCodeList();
		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-1);
	}
	
	public void setSeclectedIndex(int index) {
		playcodeList.setSelectedIndex(index);
	}
	/**
	 * Highlights line during run/animation green, and red for error.Line corresponds to each gerbil action
	 * @param index line of code to select
	 * @param c 'e' determines the line to be where error occurs
	 * @author Leslie
	 */
	public static void setSelectedIndexColor(int index, char c){
		playcodeList.setSelectedIndex(index);
		if(c=='e'){
			playcodeList.setSelectionBackground(Color.RED);
		}else{
			playcodeList.setSelectionBackground(Color.GREEN);
		}
		playcodeList.setSelectionForeground(Color.BLACK);
	}
	/**
	 * sets selected line back to last blank line in blue
	 * @author Leslie
	 */
	public static void deselectIndexColor(){
		playcodeList.clearSelection();
		playcodeList.setSelectionBackground(Color.BLUE);
		playcodeList.setSelectedIndex(playcodeList.getModel().getSize()-1);
	}
}