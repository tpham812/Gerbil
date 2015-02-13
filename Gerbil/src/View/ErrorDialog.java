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
 
/**
 * @author Katiuska Nunez
 */
public class ErrorDialog extends Screen{

	private JFrame frame;
	private JPanel panel;
	private JButton backB;
	public  JLabel errorL= new JLabel();
	
	public ErrorDialog() {
		frame = new JFrame("Error");
		panel = new JPanel();
		
		backB = new JButton("Back") {
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
	
	@Override
	protected void createScreen() {
		Dimension dimension = new Dimension(500, 200);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(errorL, c);
		c.insets = new Insets(40,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(backB, c);
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
	 * Listener for Ok Button
	 * @param listener listens for Ok button being clicked
	 */
	public void addOkEventHandler(ActionListener listener) {
		backB.addActionListener(listener);
	}
}
