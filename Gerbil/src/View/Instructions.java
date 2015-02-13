package View;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class creates a GUI for the Instruction Screen (Help screen).
 *@author Truong Pham
 */
public class Instructions extends Screen{

	private JFrame frame;
	private JPanel panel, buttonPanel;
	private JButton back;
	private BufferedImage image;
	private JLabel instructions;
	private JScrollPane scrollPane;
	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	public Instructions() {
		 
		frame = new JFrame("Instructions");
		back = new JButton("Back") {
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
		instructions = new JLabel();
		try {
			instructions.setIcon(new ImageIcon(ImageIO.read(new File("pics/instructions.jpg")).getScaledInstance(800, 960, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			image = ImageIO.read(new File("pics/instruction.jpg"));
		} catch (Exception ex) {
			System.out.println("Couldn't load image");
		}
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		buttonPanel = new JPanel();
		scrollPane = new JScrollPane(instructions);
		createScreen();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {
	
		Dimension dimension = new Dimension(1024, 768);
		scrollPane.setPreferredSize(new Dimension(800, 400));
		back.setPreferredSize(new Dimension (220,75));
		back.setFont(new Font(null, Font.BOLD, 20));
		buttonPanel.add(back);
		buttonPanel.setOpaque(false);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(80,0,0,0);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(scrollPane, c);
		c.insets = new Insets(40,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(buttonPanel, c);
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
	 * Disable to screen
	 */
	public void disable() {
		frame.setEnabled(false);
	}
	
	/**
	 * Add event listener for back button
	 * @param listener ActionListner 
	 */
	public void addBackEventHandler(ActionListener listener) {
		
		back.addActionListener(listener);
	}
}