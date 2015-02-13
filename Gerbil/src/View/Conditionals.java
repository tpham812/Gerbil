package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Katiuska Nunez
 * 
 * This screen creates a GUI for the Conditional Screen. 
 */
public class Conditionals {
	/**Type of conditional frame**/
	public static String type= "";

	/**LHS and RHS panels**/
	static JPanel leftPanel = new JPanel(); 
	static JPanel rightPanel = new JPanel();

	/**Right side panel: labels, buttons, other**/
	JLabel conditionalsL = new JLabel("Conditional Statements"); 	
	JLabel givenFunctionsL = new JLabel("Given Functions"); 
	JLabel userDefinedL = new JLabel("User Defined Functions");

	String cond=null;

	JButton moveForwardB;
	JButton turnLeftB;
	JButton eatB;

	static JFrame frame; 

	private int startLineNumber;
	private int endLineNumber;
	private DefaultListModel<String> instructions;
	
	static DefaultComboBoxModel model;
	static DefaultListModel listModel;

	public static JComboBox userDefinedFunctions; /*get from control*/ 

	/**Left side panel: labels, buttons, other**/

	JLabel label = new JLabel();
	
	JLabel bodyL = new JLabel("Body :");

	JLabel beginL = new JLabel("Begin");
	JLabel endL = new JLabel("End");

	JButton okB;
	JButton cancelB; 

	JComboBox conditionalDD; /*get from control*/ 

	public static JList conditionalscodeList;
	private static JScrollPane scrollpane;

	/** Repeat stuff **/
	JLabel repeatL= new JLabel("Repeat"); 
	String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox repeatNumTimes = new JComboBox(nums);

	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public Conditionals(String typeIn) {
		type = typeIn; 
		createButtons();
		setRightComponents();
		setLeftComponents();
		createAndShowGUI();
	}

	/**
	 * Creates all buttons for Conditionals Screen
	 */
	void createButtons(){
		/**Right side panel**/
		moveForwardB= new JButton("Move Forward"){
			public void paint(Graphics g) {
				moveForwardB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		turnLeftB= new JButton("Turn Left"){
			public void paint(Graphics g) {
				turnLeftB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		eatB = new JButton("Eat"){
			public void paint(Graphics g) {
				eatB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		/**Left side panel**/
		okB = new JButton("OK"){
			public void paint(Graphics g) {
				okB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		cancelB = new JButton("Cancel"){
			public void paint(Graphics g) {
				cancelB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
	}

	/**
	 * Sets right components on Conditionals frame 
	 */
	public void setRightComponents(){
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setOpaque(true);
		GridBagConstraints gc = new GridBagConstraints();

		Dimension size= rightPanel.getPreferredSize();
		size.width =200;
		size.height = 600;
		rightPanel.setPreferredSize(size);

		/*Setting the Font types*/
		conditionalsL.setFont(new Font("Serif", Font.BOLD, 18)); 
		givenFunctionsL.setFont(new Font("Serif", Font.BOLD, 18)); 
		userDefinedL.setFont(new Font("Serif", Font.BOLD, 18));

		userDefinedFunctions = new JComboBox();
	
		gc.insets = new Insets(5,0,10,5);

		gc.gridx = 0;
		gc.gridy = 1;
		rightPanel.add(givenFunctionsL, gc);

		gc.gridx = 0;
		gc.gridy = 2;
		rightPanel.add(moveForwardB, gc);

		gc.gridx = 0;
		gc.gridy = 3;
		rightPanel.add(turnLeftB, gc);

		gc.gridx = 0;
		gc.gridy = 4;
		rightPanel.add(eatB, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		rightPanel.add(userDefinedL, gc);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 6;
		rightPanel.add(userDefinedFunctions, gc); //placeholder */
	}

	/**
	 * Sets left components on Conditionals frame
	 */
	public void setLeftComponents(){
		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setOpaque(true);
		GridBagConstraints gc = new GridBagConstraints();

		Dimension size= leftPanel.getPreferredSize();
		size.width =675;
		size.height = 600;
		leftPanel.setPreferredSize(size);

		bodyL.setFont(new Font("Serif", Font.BOLD, 20));

		listModel=new DefaultListModel();
		listModel.add(0," ");
		
		conditionalscodeList = new JList(listModel);
		startLineNumber=0;

		conditionalscodeList.setSelectedIndex(conditionalscodeList.getModel().getSize()-1);
		scrollpane = new JScrollPane(conditionalscodeList);

		conditionalscodeList.setVisibleRowCount(20);

		String [] checks = {"There'sWall?", "There'sNoWall", "There'sFood","There'sNoFood"};
		conditionalDD = new JComboBox(checks);

		
		gc.gridx = 0;
		gc.gridy = 0;

		label.setFont(new Font("Serif", Font.BOLD, 35));
		leftPanel.add(label, gc);

		gc.insets = new Insets(10,0,0,20);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=0; 
		leftPanel.add(conditionalDD, gc);

		gc.insets = new Insets(0,0,0,0);

		gc.fill = GridBagConstraints.NONE;
		gc.weightx = .70;
		gc.gridwidth = 1;
		gc.gridx = 0; 
		gc.gridy=1; 
		leftPanel.add(bodyL, gc);
	
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.insets = new Insets(10,0,0,0);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=1; 
		leftPanel.add(beginL, gc);
		
		gc.insets = new Insets(0,0,0,20);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=2; 
		leftPanel.add(scrollpane, gc);
		
		gc.insets = new Insets(0,0,0,0);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=3; 
		leftPanel.add(endL, gc);

		gc.fill = GridBagConstraints.NONE;

		//top, left, botton, right <- insets
		gc.insets = new Insets(60,0,0,0);

		gc.gridwidth = 1;
		gc.gridx = 1; 
		gc.gridy=4; 
		gc.ipady = 20;
		gc.ipadx = 20;
		leftPanel.add(okB, gc);

		gc.gridx = 2; 
		gc.gridy=4; 
		gc.ipady =20;
		gc.ipadx = 20;
		leftPanel.add(cancelB, gc);
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 */
	protected static void createAndShowGUI() { 
		frame = new JFrame(type);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Container c = frame.getContentPane();

		gc.gridx = 0;
		gc.gridy = 0; 

		c.add(leftPanel,gc);

		gc.gridx = 1; 
		gc.gridy = 0;

		c.add(rightPanel, gc);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(false);
	}

	/**
	 * Gets the left panel
	 * @assumes  leftPanel exists
	 * @return leftPanel component gets returned to the caller
	 */
	public JComponent getLeftComponent() {
		return leftPanel;
	}

	/**
	 *  Gets the right panel
	 * @assumes rightPanel exists
	 * @return rightPanel component gets returned to the caller
	 */
	public JComponent getRightComponent(){
		return rightPanel;
	}
	
	/**
	 * Creates a condtionals window based on conditional type
	 * @assumes type being sent in is correct
	 * @param type Type of conditional window to be created
	 */
	public void setText(String type){
		String [] conditions = {"There'sWall?", "There'sNoWall", "There'sFood","There'sNoFood"};
	
		refreshConditionalsJList(); // refresh the JList
		
		if(type == "If"){
			label.setText("If");
			
			conditionalDD.setEnabled(true);
			refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
			refreshUserFunctionsList_Other(conditions);
		}else if(type == "Else"){
			label.setText("Else");
			refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
			refreshUserFunctionsList_Else();
		}else if(type == "Else if"){
			label.setText("Else if");
			
			conditionalDD.setEnabled(true);
			refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
			refreshUserFunctionsList_Other(conditions);
		}else if(type == "While"){
			label.setText("While");
			
			conditionalDD.setEnabled(true);
			refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
			refreshUserFunctionsList_Other(conditions);
		}else if(type == "Repeat"){
			label.setText("Repeat");
			
			conditionalDD.setEnabled(true);
			refreshUserFunctionsList(Start.StartGerbil.controller.getFunctions());
			refreshUserFunctionsList_Repeat();
		}
	}
	
	/**
	 * Gets the conditional check string
	 * @return conditional check string
	 */
	public String getText(){
		return label.getText();
	}

	public void refreshUserFunctionsList(String [] newList){ 
		model=new DefaultComboBoxModel(newList);
		userDefinedFunctions.setModel(model);
	}
	
	public void refreshUserFunctionsList_Repeat(){
		conditionalDD.removeAllItems();
		for(int i = 1; i < 16; i++) {
			conditionalDD.addItem(i);
		}
	}
	
	public void refreshUserFunctionsList_Other(String[] conditions){
		conditionalDD.removeAllItems();
		for(int i = 0; i < conditions.length; i++) {
			conditionalDD.addItem(conditions[i]);
		}
	}
	
	public void refreshUserFunctionsList_Else(){
		//conditionalDD.setVisble(false);
		conditionalDD.setEnabled(false);
	}
	
	public void refreshConditionalsJList(){ 
		listModel.clear();
		listModel.add(0," ");
		conditionalscodeList.setSelectedIndex(conditionalscodeList.getModel().getSize()-1);
	}
	
	public void refreshConditionalsJList(String [] newList){ 
		listModel.clear();
		listModel.add(0," ");
		
		for(int i=0; i<newList.length; i++){
			listModel.add(i,newList[i]);
		}
		
		conditionalscodeList.setSelectedIndex(conditionalscodeList.getModel().getSize()-1);
	}
	
	public String getCond(){
		return conditionalDD.getSelectedItem().toString();
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

	public void enable() {
		frame.setEnabled(true);
	}

	public void disable() {
		frame.setEnabled(false);
	}

	public void resetLineNumber() {
		startLineNumber = 0;
	}
	
	public void setBegin(int num){
		startLineNumber = num;
	}
	
	public int getBegin(){
		return startLineNumber;
	}

	public int getEndLineNumber() {
		endLineNumber = listModel.size();
		return endLineNumber;
	}

	public int getSelectedLineNumber() {

		return conditionalscodeList.getSelectedIndex();
	}
	
	public void addCancelEventHandler(ActionListener listener) {
		cancelB.addActionListener(listener);
	}

	public void addOkEventHandler(ActionListener listener) {
		okB.addActionListener(listener);
	}
	
	public void addMoveEventHandler(ActionListener listener) {
		moveForwardB.addActionListener(listener);
	}
	
	public void addEatEventHandler(ActionListener listener) {
		eatB.addActionListener(listener);
	}
	
	public void addTurnLeftEventHandler(ActionListener listener) {
		turnLeftB.addActionListener(listener);
	}
	
	public void addFunctionsEventHandler(ActionListener listener){
		userDefinedFunctions.addActionListener(listener);
	}
}