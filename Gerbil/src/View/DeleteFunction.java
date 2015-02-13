package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
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
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 

/**
 * @author Truong Pham
 * This class create the GUI for Delete Function Screen.
 */
public class DeleteFunction extends Screen{

	private JFrame frame;
	private JPanel panel, listPanel, buttonPanel;
	private JButton done, delete;
	private JComboBox<String> functionsList;
	private JLabel functionsLabel;
	private BufferedImage image; 
	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	public DeleteFunction() {
		
		createButtons();
		functionsLabel = new JLabel("User Defined Functions");
		functionsList = new JComboBox<String>();
		frame = new JFrame("Delete Functions");
		try {
			image = ImageIO.read(new File("pics/background.jpg"));
		} catch (Exception ex) {
			System.out.println("Couldn't load image");
		}
		
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		listPanel = new JPanel();
		listPanel.setOpaque(false);
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		createScreen();
	}
	private void createButtons(){
		
		done = new JButton("Done") {
			public void paint(Graphics g) {
				done.setFont(new Font(null, Font.PLAIN, 30)); 
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(),getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth()-1,getHeight()-1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth()-r.getWidth()) / 2;
				float yMargin = (float)(getHeight()-getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize()+yMargin);
			}
		};
		delete  = new JButton("Delete") {
			public void paint(Graphics g) {
				delete.setFont(new Font(null, Font.PLAIN, 30)); 
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				super.paint(g);
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, getWidth(),getHeight(), 18, 18);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRoundRect(0, 0, getWidth()-1,getHeight()-1, 18, 18);
				FontRenderContext frc = new FontRenderContext(null, false, false);
				Rectangle2D r = getFont().getStringBounds(getText(), frc);
				float xMargin = (float)(getWidth()-r.getWidth()) / 2;
				float yMargin = (float)(getHeight()-getFont().getSize()) / 2;
				g2d.drawString(getText(), xMargin, (float)getFont().getSize()+yMargin);
			}
		};
	}
	
	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {	
		
		GridBagConstraints c = new GridBagConstraints();
		functionsList.setPreferredSize(new Dimension(250,50));
		functionsList.setFont(new Font(null, Font.BOLD, 28));
		functionsLabel.setForeground(Color.WHITE);
		functionsLabel.setFont(new Font(null, Font.BOLD, 48));
		done.setFont(new Font(null, Font.BOLD,20));
		delete.setFont(new Font(null, Font.BOLD,20));	
		Dimension dimension = new Dimension(1024, 768);
		functionsList.setMinimumSize(new Dimension(200,25));
		listPanel.add(functionsList);
		buttonPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,50,0,20);
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 35;
		c.ipadx = 55;
		buttonPanel.add(delete,c);
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 35;
		c.ipadx = 75;
		buttonPanel.add(done,c);
		panel.setLayout(new GridBagLayout());
		c.insets = new Insets(0,65,45,0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(functionsLabel,c);
		c.insets = new Insets(100,50,0,100);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 0;
		c.ipadx = 100;
		panel.add(listPanel,c);
		c.insets = new Insets(155,0,75,90);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(buttonPanel,c);
		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
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
	 * Enable the screen
	 */
	public void enable() {
		
		frame.setEnabled(true);
	}

	/**
	 * Disable the screen
	 */
	public void disable() {
		
		frame.setEnabled(false);
	}
	
	/**
	 * Set new function selection in combobox
	 */
	public void setNewSelection() {
		
		if(functionsList.getItemCount() > 0) {
			functionsList.setSelectedIndex(0);
		}
		else {
			functionsList.setSelectedIndex(-1);
		}
	}
	
	/**
	 * Update list of functions in combobox
	 * @param newFunctions List of function anems
	 */
	public void updateFunctionsList(String[] newFunctions){
		
		functionsList.removeAllItems();
		for(int i = 0; i < newFunctions.length; i++) {
			functionsList.addItem(newFunctions[i]);
		}
	}
	
	/**
	 * Get selected function name to delete
	 * @return Function name to delte
	 */
	public String getSelectedFunction() {
		return (String)functionsList.getSelectedItem();
	}
	
	/**
	 * Add event listener for delete button
	 * @param Event Listener
	 */
	public void addDeleteEventHandler(ActionListener listener) {
		
		delete.addActionListener(listener);
	}
	
	/**
	 * Add event listener for done button
	 * @param listener Event Listener
	 */
	public void addDoneEventHandler(ActionListener listener) {
		
		done.addActionListener(listener);
	}
}