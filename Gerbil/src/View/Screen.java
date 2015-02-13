package View;
 
/**
 * Abstract class that includes 4 methods: createScreen, show, hide, addEventListeners. All must be implemented.
 *@author Truong Pham
 */
@SuppressWarnings("serial")
public abstract class Screen {

	/**
	 * Method to be implemented: Creates the screen.
	 * 
	 */
	protected abstract void createScreen();
	
	/**
	 * Method to be implemented: Shows the screen.
	 *  
	 */
	public abstract void show();
		
	/**
	 * Method to be implemented: Hides the screen.
	 * 
	 */
	public abstract void hide();
	
	/**
	 * Method to be implemented: Enable screen
	 */
	public abstract void enable();
	
	/**
	 * Method to be implemented:  Disable screen
	 */
	public abstract void disable();
	
}
