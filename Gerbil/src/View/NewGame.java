package View;
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author Katiuska Nunez
 * This class creates a GUI for the Confirmation screen.
 */
public class NewGame extends Screen {


	private JFrame frame;
	private JPanel panel;
	private JButton cancelB;
	public static JButton okB;
	private JLabel newGameNameL; 
	public static JTextField textF;
	
	/**
	 * New Game screen constructor
	 */
	public NewGame() {
		frame = new JFrame("New Game");
		panel = new JPanel();
		textF = new JTextField();
		newGameNameL = new JLabel("Enter new game name: ");
		
		cancelB = new JButton("Cancel") {
			public void paint(Graphics g) {
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
		
		okB = new JButton("OK") {
			public void paint(Graphics g) {
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
		
		createScreen();
	}
	
	/* (non-Javadoc)
	 * @see View.Screen#createScreen()
	 */
	@Override
	protected void createScreen() {
		Dimension dimension = new Dimension(500, 200);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(newGameNameL, c);
		
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(textF, c);
		
		c.gridwidth = 1;
		c.insets = new Insets(40,0,0,0);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(okB, c);
		
		
		c.insets = new Insets(40,0,0,0);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(cancelB, c);
		
		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
	}
	
	@Override
	public void show() {
		frame.setVisible(true);
	}
	
	@Override
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
	
	/**
	 * Listener for back button
	 * @param listener Listens for back button being clicked
	 */
	public void addBackEventHandler(ActionListener listener) {
		cancelB.addActionListener(listener);
	}
	
	/**
	 * Listener for ok button
	 * @param listener Listens for ok button being clicked
	 */
	public void addOkEventHandler(ActionListener listener) {
		okB.addActionListener(listener);
	}
}