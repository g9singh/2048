
package gameOperator;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.keyPressed;
import java.awt.image.BufferedImage;
public class Game extends JPanel implements KeyListener, Runnable

{

	public static final int WIDTH = 580;
	public static final int HEIGHT = 560;
	public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
	//################################################################################
    private Thread game;
    private boolean isRunning;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Gameboard board;
    private static final long serialVersionUID = 1L;
    //#################################################################################
    //private long startTime;
    //private long elapsed;
    //private boolean set;
    
    public Game()
    {
    	setFocusable(true);
    	setPreferredSize(new Dimension(WIDTH,HEIGHT));
    	addKeyListener(this);
    	
    	board = new Gameboard(WIDTH/2 - Gameboard.BOARD_WIDTH/2, HEIGHT - Gameboard.BOARD_HEIGHT -10);
    }
    
    private void update()
    { 
    	board.update();
    	Keyboard.update();
    }
    
    private void render()
    {
    	Graphics2D game2048 = (Graphics2D)image.getGraphics();
    	game2048.setColor(Color.white);
    	game2048.fillRect(0, 0, WIDTH, HEIGHT);
    	board.render(game2048);
    	game2048.dispose();
    	
    	Graphics2D gameItem = (Graphics2D)getGraphics();
    	gameItem.drawImage(image, 0, 0, null);
    	gameItem.dispose();
    	
    }
    
    
	@Override
	public void run() 
	{
		int fps = 0;
		int update = 0;
		long stopWatch = System.currentTimeMillis();
		double timeVariable = 1000000000.0/ 60;

		//last update time in nanoseconds
		double then = System.nanoTime();
		double pending = 0;

	while(isRunning)
	{
		boolean isVisable = false;
		double now = System.nanoTime();
		pending += (now - then)/ timeVariable;
		then = now;
		// update queue

		while(pending >= 1)
		{
			update++;
			update();
			pending--;
			isVisable = true;
		}
		if(isVisable)
		{
			fps++;
			render();
			isVisable = false;
		}
		else
		{
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

		if(System.currentTimeMillis() - stopWatch > 1000)
		{
			System.out.printf("%d fps %d update", fps, update);
			System.out.println("#############################");
			fps = 0;
			update = 0;
			stopWatch += 1000;

		}	
	}
	
	public synchronized void start()
	{
		if(isRunning)
			return;
		isRunning = true;
		game = new Thread(this, "game");
		game.start();

	}

	public synchronized void stop()
	{
		if(!isRunning)
			return;
		isRunning = false;
		System.exit(0);
	}

	

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keyboard.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.keyReleased(e);
		
	}
}
