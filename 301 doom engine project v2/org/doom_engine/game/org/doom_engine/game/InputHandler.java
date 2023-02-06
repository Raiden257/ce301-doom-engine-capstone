package org.doom_engine.game;



import static java.awt.event.KeyEvent.*;
import java.awt.event.FocusEvent;  
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;



public class InputHandler
		implements KeyListener, FocusListener, MouseMotionListener, MouseListener, MouseWheelListener {

	public boolean keys[];
	public static double mouse_x, mouse_y;
	public double mouseDx,mouseDy;
	//public double mouse_movement;
	public boolean focus=true;
	
	
	Game g;
	
	
	
	

	public InputHandler() {
		keys = new boolean[65535];
		//mouse_movement=320.0;
		 g= new Game();
	}

	
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	}

	
	public void mouseClicked(MouseEvent arg0) {
	}

	
	public void mouseEntered(MouseEvent arg0) {
	}

	
	public void mouseExited(MouseEvent arg0) {
	}

	
	public void mousePressed(MouseEvent arg0) {
	}

	
	public void mouseReleased(MouseEvent arg0) {
	}

	
	public void mouseDragged(MouseEvent arg0) {
	}

	
	public void mouseMoved(MouseEvent arg0) {
	
		mouse_x=arg0.getX();
		mouse_y=arg0.getY();
		//System.out.println(mouse_x);
		 g.update_mouse_x(mouse_x);
		
	}

	
	public void focusGained(FocusEvent arg0) {
		focus=true;
	}

	
	public void focusLost(FocusEvent arg0) {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;}
		focus=false;
	}

	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
//		if(e.getKeyCode()==VK_P) {
//			if(g.gameState==g.playState) {
//				g.gameState=g.pauseState;
//			}
//			if(g.gameState==g.pauseState) {
//				g.gameState=g.playState;
//			}
//			
//		}
		
		
	}

	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}


	public void keyTyped(KeyEvent arg0) {

	}

}
