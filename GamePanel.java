import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener
{
	
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=75;
	//for the pause function
	static boolean pause=false;
	//body parts of snake:
	//x-->head coordinate
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts=6;
	int hScore;
	
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	//Constructor:
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT ));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
      
		
		
	}
	
	public void startGame()
	{
		
		newApple();
		running = true;
		timer=new Timer(DELAY, this);
		timer.start();
		
		
	}
	
	
	
	
	//Pause Function
	public void pauseGame()
	{
		
		GamePanel.pause=true;
		timer.stop();
		
	}
	//Resume function
	public void resumeGame()
	{
		GamePanel.pause=false;
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
		
	}
	
	

	
	
	
	 
	public void draw(Graphics g)
	{
		if(running)
	{
			
       
		
		//apple colour
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		//Draw Snake:
		for(int i=0; i<bodyParts; i++)
		{
			if(i==0) 
			{
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
			}
			else
			{
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
			}
		}
		g.setColor(Color.blue);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
	}
		else
		{
			gameOver(g);
			g.setColor(Color.blue);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			
			//HighScore
			/*
			gameOver(g);
			g.setColor(Color.blue);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("High Score: "+applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			*/
			
			
		}
		
		
}
	
	//Generate new apple coordinates, whenever this method is called
	public void newApple()
	{
		appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	
	public void move()
	{
		for(int i=bodyParts; i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction)
		{
		case 'U':
			y[0]=y[0]-UNIT_SIZE;
			break;
		case'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		
		case'L':
			x[0]=x[0]-UNIT_SIZE;
			break;
		case'R':
			x[0]=x[0]+UNIT_SIZE;
			break;
			
		}
		
		
	}
	
	public void checkApple()
	{
		if((x[0]==appleX)&&(y[0]==appleY))
		{
			bodyParts++;
			//increment score of apples eaten
			applesEaten++;
			//generate new apple once eaten
			newApple();
		}
		

		
	}
	
	public void checkCollisions()
	{
		//check if head of snake collides with the body itself:
		for(int i=bodyParts; i>0;i--)
		{
			if((x[0]==x[i])&& (y[0]==y[i]))
			{
				running=false;
			}
		}
		
		//check if head touches LEFT border
		if(x[0]<0)
		{
			running = false;
		}
		//check if head touches RIGHT border
		if(x[0]> SCREEN_WIDTH)
		{
			running= false; 
		}
		//check if head touches TOP border
		if(y[0]<0)
		{
			running = false;
		}
		//check if head touches BOTTOM border
		if(y[0]>SCREEN_HEIGHT)
		{
			running = false;
		}
		
		//stop timer
		if(!running)
		{
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g)
	{
		
		
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics= getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
			
		}
		
		repaint();
		
	}

	
	
	
	public class MyKeyAdapter extends KeyAdapter
	{
		
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction!='R')
				{
					direction='L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction!='L')
				{
					direction='R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction!='D')
				{
					direction='U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction!='U')
				{
					direction='D';
				}
				break;
				
			case KeyEvent.VK_ESCAPE:
				if(GamePanel.pause) {
					resumeGame();
				}
				else 
				{
					pauseGame();
					
				}
				break;
			
				
			}
			
			
			
		}
		
	}
	
	
	
	
}
 