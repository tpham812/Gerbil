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
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;

/**
 * @author Truong Pham, Katiuska Nunez
 * This class creates a GUI for the Function Screen.
 */
public class UserFunction extends Screen{
 
	/**LHS and RHS panels**/
	static JPanel leftPanel = new JPanel(); 
	static JPanel rightPanel = new JPanel();
	private int startLineNumber;
	private int endLineNumber;
	private DefaultListModel listModel;
	static DefaultComboBoxModel model;
	public boolean addtomain;
	/**Right side panel: labels, buttons, other**/
	JLabel conditionalsL = new JLabel("Conditional Statements"); 	
	JLabel givenFunctionsL = new JLabel("Given Functions"); 
	JLabel userDefinedL = new JLabel("User Defined Functions");
	JLabel begin = new JLabel("Begin");
	JLabel end = new JLabel("End");
	
	static JFrame frame;
	
	JButton ifB;
	JButton elseB;
	JButton elseifB;
	JButton whileB;
	JButton repeatB;
	
	JButton moveAheadB;
	JButton turnLeftB;
	JButton eatB;
	
	public JComboBox<String> userDefinedFunctions; /*get from control*/ 
	
	/**Left side panel: labels, buttons, other**/
	JLabel ifL = new JLabel("If :");
	JLabel elseL= new JLabel("Else :");
	JLabel elseifL= new JLabel("Else if :");
	JLabel whileL = new JLabel("While :");
	JLabel bodyL = new JLabel("Body :");
	 
	JButton okB;
	JButton cancelB; 
	
	JTextField functionName; 
	
	public static JList functionsCodeList;
	private static JScrollPane scrollpane;
	
	/** Repeat stuff **/
	JLabel repeatL= new JLabel("Function Name:"); 
	String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
	
	/**
	 * Constructor that creates all necessary GUI components.
	 */
	public UserFunction () {
		createButtons();
		setRightComponents();
		setLeftComponents();
		createAndShowGUI();
		
	}
	
	/**
	 * Create buttons for Screen
	 */
	void createButtons(){
		/**Right side panel**/
		ifB= new JButton("If"){
			public void paint(Graphics g) {
				ifB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		
		elseB= new JButton("Else"){
			public void paint(Graphics g) {
				elseB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		elseifB= new JButton("Else if"){
			public void paint(Graphics g) {
				elseifB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		whileB = new JButton("While"){
			public void paint(Graphics g) {
				whileB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
		repeatB= new JButton("Repeat"){
			public void paint(Graphics g) {
				repeatB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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

		moveAheadB= new JButton("Move Ahead"){
			public void paint(Graphics g) {
				moveAheadB.setFont(new Font("Serif", Font.PLAIN, 18)); 
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
	 * Sets right component of screen
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
		
		String[] drop = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}; // EDIT : should come from somewhere else
		
		userDefinedFunctions = new JComboBox();
		
		//top, left, botton, right <- insets
		gc.insets = new Insets(5,0,10,5);
	      
		//gc.anchor = GridBagConstraints.WEST;
		
		gc.gridx = 0;
		gc.gridy = 1; 
		rightPanel.add(conditionalsL, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		rightPanel.add(ifB, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		rightPanel.add(elseB, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		rightPanel.add(elseifB, gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		rightPanel.add(whileB, gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		rightPanel.add(repeatB, gc);
		
		gc.gridx = 0;
		gc.gridy = 7;
		rightPanel.add(givenFunctionsL, gc);
		
		gc.gridx = 0;
		gc.gridy = 8;
		rightPanel.add(moveAheadB, gc);
		
		gc.gridx = 0;
		gc.gridy = 9;
		rightPanel.add(turnLeftB, gc);
		
		gc.gridx = 0;
		gc.gridy = 10;
		rightPanel.add(eatB, gc);
		
		gc.gridx = 0;
		gc.gridy = 11;
		rightPanel.add(userDefinedL, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 12;
		rightPanel.add(userDefinedFunctions, gc); //placeholder */
	}
	
	/**
	 * Sets left component of screen
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
		
		listModel = new DefaultListModel();
		listModel.add(0," ");
		
		functionsCodeList = new JList(listModel);
		startLineNumber = 0;
		
		functionsCodeList.setSelectedIndex(functionsCodeList.getModel().getSize()-1);
		scrollpane = new JScrollPane(functionsCodeList);
		
		functionsCodeList.setVisibleRowCount(20);
		
		functionName = new JTextField();
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		repeatL.setFont(new Font(null, Font.BOLD, 25));
		leftPanel.add(repeatL, gc);
		
		gc.insets = new Insets(10,0,0,20);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=0; 
		leftPanel.add(functionName, gc);
		
		gc.insets = new Insets(0,0,0,0);
		
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = .70;
		gc.gridwidth = 1;
		gc.gridx = 0; 
		gc.gridy=2; 
		leftPanel.add(bodyL, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		gc.insets = new Insets(10,0,0,0);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=1; 
		leftPanel.add(begin, gc);
		
		gc.insets = new Insets(0,0,0,20);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=2; 
		leftPanel.add(scrollpane, gc);
		
		gc.insets = new Insets(0,0,0,0);
		gc.gridwidth = 2;
		gc.gridx = 1; 
		gc.gridy=3; 
		leftPanel.add(end, gc);
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

		frame = new JFrame("Function");
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
		frame.setLocationRelativeTo(null);
	}

	public JComponent getLeftComponent() {
		return leftPanel;
	}
	
	public JComponent getRightComponent(){
		return rightPanel;
	}

	@Override
	protected void createScreen() {
	}
	
	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * Enable Screen
	 */
	public void enable() {
		frame.setEnabled(true);
	}

	/**
	 * Disable Screen
	 */
	public void disable() {
		frame.setEnabled(false);
	}
	
	public void dontAddToMain(boolean bool){
		if(bool==true){
			this.addtomain= true;
		}
		this.addtomain= false;
	}
	
	public void clearLabels(){
		this.functionName.setText("");;
	}
	
	/**
	 * Reset line number of JList
	 */
	public void resetLineNumber() {
		startLineNumber = 0;
	}

	/**
	 * Get end line number of JList
	 * @return End line number of JList
	 */
	public int getEndLineNumber() {
		endLineNumber = listModel.size() + 1;
		return endLineNumber;
	}
	
	/**
	 * Return selected index in JList
	 * @return
	 */
	public int getSelectedLineNumber() {
		
		return functionsCodeList.getSelectedIndex();
	}
	
	/**
	 * Refresh the combobox of function names
	 * @param newList
	 */
	public void refreshUserFunctionsList(String [] newList){ 
		model=new DefaultComboBoxModel(newList);
		userDefinedFunctions.setModel(model);
	}
	
	public void updateInstructionsList(String[] instructions) {
		listModel.clear();
		listModel.add(0," ");
		
		for(int i = 0; i < instructions.length; i++) {
			listModel.add(i,instructions[i]);
		}
		
		functionsCodeList.setSelectedIndex(functionsCodeList.getModel().getSize()-1);
	}
	
	/**
	 * Refresh JList that outputs code
	 */
	public void refreshCodeList(){
		listModel.clear();
	
		String [] temp = Start.StartGerbil.controller.userFunctionShowJList();
		listModel.add(0, " ");
		for(int i=0; i< temp.length;i++){
			String test = temp[i];
			listModel.add(i,test);
		}

		functionsCodeList.setSelectedIndex(functionsCodeList.getModel().getSize()-1);
	}
	
	public void addFunctionListListener(ActionListener listener) {
		userDefinedFunctions.addActionListener(listener);
	}
	
	public void addListSelectionEventHandler(ListSelectionListener LSListener) {
		functionsCodeList.addListSelectionListener(LSListener);
	}
	
	public void addIfEventHandler(ActionListener listener) {
		ifB.addActionListener(listener);
	}
	
	public String getFunctionName() {
		return functionName.getText().trim();
	}
	
	public void addRepeatEventHandler(ActionListener listener) {
		repeatB.addActionListener(listener);
	}
	
	public void addWhileEventHandler(ActionListener listener) {
		whileB.addActionListener(listener);
	}
	
	public void addElseIfEventHandler(ActionListener listener) {
		elseifB.addActionListener(listener);
	}
	
	public void addElseEventHandler(ActionListener listener) {
		elseB.addActionListener(listener);
	}
	
	public void addTurnLeftEventHandler(ActionListener listener) {
		turnLeftB.addActionListener(listener);
	}
	public void addMoveAheadEventHandler(ActionListener listener) {
		moveAheadB.addActionListener(listener);
	}
	public void addEatEventHandler(ActionListener listener) {
		eatB.addActionListener(listener);
	}
	public void addCancelEventHandler(ActionListener listener) {
		cancelB.addActionListener(listener);
	}
	
	public void addBackEventHandler(ActionListener listener) {
		cancelB.addActionListener(listener);
	}
	
	public void addOkEventHandler(ActionListener listener) {
		okB.addActionListener(listener);
	}
	
	public void addUserFunctionComboBoxListener(ActionListener listener){
		userDefinedFunctions.addActionListener(listener);
	}
}