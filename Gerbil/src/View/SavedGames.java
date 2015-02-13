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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Katiuska Nunez
 */
public class SavedGames extends Screen{

	private JFrame frame;
	private JPanel panel, listPanel, buttonPanel;
	private JButton openGame, deleteGame, cancel;
	public  JComboBox gamesList;
	private JLabel gamesLabel;
	private BufferedImage image;

	public SavedGames(){

		createButtons();
		gamesLabel = new JLabel("Saved Games");
		ArrayList<String> test = Start.StartGerbil.backend.getGamesStringArray();
		gamesList = new JComboBox(Start.StartGerbil.backend.getGamesStringArray().toArray());
		frame = new JFrame("Saved Games");
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

		openGame = new JButton("Open Game") {
			public void paint(Graphics g) {
				openGame.setFont(new Font(null, Font.PLAIN, 30)); 
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
		deleteGame  = new JButton("Delete Game") {
			public void paint(Graphics g) {
				deleteGame.setFont(new Font(null, Font.PLAIN, 30)); 
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

		cancel  = new JButton("Cancel") {
			public void paint(Graphics g) {
				cancel.setFont(new Font(null, Font.PLAIN, 30)); 
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
	 */
	protected void createScreen() {
		GridBagConstraints c = new GridBagConstraints();
		gamesList.setPreferredSize(new Dimension(250,50));
		gamesList.setFont(new Font(null, Font.BOLD, 28));
		gamesLabel.setForeground(Color.WHITE);
		gamesLabel.setFont(new Font(null, Font.BOLD, 48));
		openGame.setFont(new Font(null, Font.BOLD,20));
		deleteGame.setFont(new Font(null, Font.BOLD,20));
		cancel.setFont(new Font(null, Font.BOLD,20));
		Dimension dimension = new Dimension(1024, 768);

		listPanel.add(gamesList);
		buttonPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,60,0,20);
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 35;
		c.ipadx = 30;
		buttonPanel.add(openGame,c);
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 35;
		c.ipadx = 30;
		buttonPanel.add(deleteGame,c);
		c.gridx = 2;
		c.gridy = 0;
		c.ipady = 35;
		c.ipadx = 30;
		buttonPanel.add(cancel,c);

		panel.setLayout(new GridBagLayout());
		c.insets = new Insets(0,350,45,0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(gamesLabel,c);
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

	/*List Selection Handler*/
	/**
	 * Listener for drop down in saved games scren
	 * @param listener Listens for actions in the games list drop down
	 */
	public void addGameListSelectionListener(ActionListener listener) {
		gamesList.addActionListener(listener);
	}

	/*Button Handlers*/
	/**
	 * Listener for open button
	 * @param listener Listens for open button being clicked
	 */
	public void addOpenGameEventHandler(ActionListener listener) {

		openGame.addActionListener(listener);
	}
	/**
	 * Listener for delete button
	 * @param listener Listens for delete button being clicked
	 */
	public void addDeleteGameEventHandler(ActionListener listener) {

		deleteGame.addActionListener(listener);
	}

	/**
	 * Listener for cancel button
	 * @param listener Listens for cancel button being clicked
	 */
	public void addCancelEventHandler(ActionListener listener) {

		cancel.addActionListener(listener);
	}
	
	/**
	 * Refreshes the game list
	 */
	public void refreshGamesList(){
		//ArrayList<String>  games = Start.StartGerbil.backend.getGamesStringArray();
		String [] temp =  Start.StartGerbil.backend.getGamesStringArray().toArray(new String[ Start.StartGerbil.backend.getGamesStringArray().size()]);
				
		gamesList.removeAllItems();
		for(int i = 0; i < temp.length; i++) {
			gamesList.addItem(temp[i]);
		}
	}
}
