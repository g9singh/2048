package gameOperator;

import java.awt.event.KeyEvent;

public class Keyboard {
	public static boolean[]trigger = new boolean[256];
	public static boolean[]previous = new boolean[256];
	
			
    private Keyboard()
    {
		
    }
    
    public static void update()
    {
    	for(int i = 0; i < 4; i++)
    	{
    		if( i == 0) previous[KeyEvent.VK_LEFT] = trigger[KeyEvent.VK_LEFT];
    		if( i == 1) previous[KeyEvent.VK_RIGHT] = trigger[KeyEvent.VK_RIGHT];
    		if( i == 2) previous[KeyEvent.VK_UP] = trigger[KeyEvent.VK_UP];
    		if( i == 3) previous[KeyEvent.VK_DOWN] = trigger[KeyEvent.VK_DOWN];		
    	}
    		
    }
    
    public static void keyPressed(KeyEvent e)
    {
    	trigger[e.getKeyCode()] = true;
    }
    
    public static void keyReleased(KeyEvent e)
    {
    	trigger[e.getKeyCode()] = false;
    }
    
    public static boolean typed(int keyEvent){
    	return !trigger[keyEvent] && previous[keyEvent];
    }

}
