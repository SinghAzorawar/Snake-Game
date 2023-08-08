


import javax.swing.JButton;
import javax.swing.JFrame;


public class GameFrame extends JFrame 
{
	SnakeGame snakeGame;
	JButton resetButton;
	//Constructor
	GameFrame()
	{
		
		
		//Instead of saying
		// GamePanel panel= new GamePanel()
		// this.add(panel);
		//Just type the below:
		this.add(new GamePanel());
		this.setTitle("Snake");
	//reset
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.setResizable(false);
		this.pack();
		
		//window to appear in the middle of the computer screen
		this.setLocationRelativeTo(null);
		
		
		
		
	}
	

} 