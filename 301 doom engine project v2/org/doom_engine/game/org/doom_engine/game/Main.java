package org.doom_engine.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;




import javax.swing.JFrame;

import org.doom_engine.game.entities.Player;
import org.doom_engine.game.gfx.MiniMap;
import org.doom_engine.game.gfx.Screen;;

public class Main extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final int SCALE = 4;
	public static final String TITLE = "3d game";
	public static final double FRAME_RATE = 60.0;

	private boolean isRunning = false;

	public final BufferedImage image;
	public final int[] pixels;
	
	public MiniMap miniMap= new MiniMap(128,128);
	private Game game;
	private Screen screen;
	private InputHandler inputHandler;
	private Player player;
	

	public Main() {

		Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		

		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
        addMouseListener(inputHandler);
        addFocusListener(inputHandler);
        addMouseMotionListener(inputHandler);
        addMouseWheelListener(inputHandler);

	}

	public void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
		
		

		init();
		new Thread(this).start();
	}

	public void init() {
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		player= new Player(game);
	}

	public void run() {
		final double nsPerUpdate = 1000000000.0 / FRAME_RATE;

		long lastTime = System.nanoTime();
		double unprocessedTime = 0;

		int frames = 0;
		int updates = 0;

		long frameCounter = System.currentTimeMillis();

		while (isRunning) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - lastTime;
			lastTime = currentTime;
			unprocessedTime += passedTime;

			if (unprocessedTime >= nsPerUpdate) {
				unprocessedTime = 0;
				update();
				updates++;
			}
			
			render();
			frames++;
			
			

			if (System.currentTimeMillis() - frameCounter >= 1000) {
				System.out.println("Frames : " + frames + ", Updates :" + updates);
				frames = 0;
				updates = 0;
				frameCounter += 1000;
			}
		}

		dispose();

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}

		screen.render(game);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		if(game.gameState==game.titleState) {
			drawTitleScreen (g);
		}
		
		if(game.gameState== game.playState) {
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT* SCALE, null);
		g.setColor(Color.gray);
		g.fillRect(0,360, WIDTH*SCALE, 120);
		
		g.setFont(new Font("Serif",0,40));
		g.setColor(Color.blue);
		String text1=Integer.toString(player.health), text2=Integer.toString(player.armor);
		int x=getCenteredX(text1,g);
		int y=(SCALE*HEIGHT)*9/10;
		g.drawString(text1, x*3/4, y);
		g.drawString(text2, x+x*1/4, y);
		
		
		}
		
		
		if(game.gameState== game.mapState) {

			g.drawImage(miniMap.image1, WIDTH*SCALE, 0, -WIDTH*SCALE, HEIGHT*SCALE, null);
		}
		if(game.gameState==game.pauseState) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
			
			g.setFont(new Font("Comic Sans",0,40));
			g.setColor(Color.white);
			
			
			String text="Paused";
			
			int x=getCenteredX(text,g);
			int y= (HEIGHT*SCALE)/2;
			g.drawString(text, x, y);
			
			g.setFont(new Font("Comic Sans",0,30));
			g.setColor(Color.gray);
			
			text="Press 'P' to unpause";
			x=getCenteredX(text,g);
			y+=50;
			g.drawString(text, x, y);
			
			text="Press 'Q' to Quit";
			x=getCenteredX(text,g);
			y+=50;
			g.drawString(text, x, y);
		}
		if(game.gameState==game.deathState) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
			
			g.setFont(new Font("Comic Sans",0,40));
			g.setColor(Color.white);
			
			
			String text="You Died";
			
			int x=getCenteredX(text,g);
			int y= (HEIGHT*SCALE)/2;
			g.drawString(text, x, y);
			
			g.setFont(new Font("Comic Sans",0,30));
			g.setColor(Color.gray);
			
			text="Press 'Space' to reload the level";
			x=getCenteredX(text,g);
			y+=50;
			g.drawString(text, x, y);
			
			text="Press 'Q' to Quit";
			x=getCenteredX(text,g);
			y+=50;
			g.drawString(text, x, y);
		}
		if(game.gameState==game.closeState) {
			g.dispose();
			dispose();
		}
		
		g.dispose();
		bs.show();

	}
	private void drawTitleScreen(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		
		g.setFont(new Font("Comic Sans",0,60));
		
		String text="Running Maze Man";
		int x=getCenteredX(text,g);
		int y= (HEIGHT*SCALE)/2;
		
		g.setColor(Color.gray);
		g.drawString(text, x+3, y+3);
		
		g.setColor(Color.white);
		g.drawString(text, x, y);
		
		g.setFont(new Font("Comic Sans",0,40));
		
		
		text="Press SPACE to Start";
		x=getCenteredX(text,g);
		y+=50;
		g.drawString(text, x, y);
		
		text="Press 'Q' to Quit";
		x=getCenteredX(text,g);
		y+=50;
		g.drawString(text, x, y);
		
		
	}

	public int getCenteredX(String text, Graphics g) {
		int length=(int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int x=(WIDTH*SCALE)/2 -length/2;
		return x;
	}

	public void update() {
		game.update(inputHandler.keys);
		screen.update();
		miniMap.renderMiniMap();
	}

	public void stop() {
		if (!isRunning) {
			return;
		}
		isRunning = false;
	}

	public void dispose() {
		System.exit(0);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setResizable(false);
		Main game = new Main();
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
		
		game.start();
		
		
		
		
		
	}

}
