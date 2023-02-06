package org.doom_engine.game;

import static java.awt.event.KeyEvent.*;

import java.awt.image.BufferedImage;

import org.doom_engine.game.entities.Enemy;
import org.doom_engine.game.entities.Fire;
import org.doom_engine.game.entities.Player;
import org.doom_engine.game.Level.Level;

public class Game {
	public Level level;
	public Player player;
	public Enemy enemy1;
	public Fire fire;
	public int time;
	public static double mouse_x;
	public boolean[] keys;
	public boolean showMap;
	public BufferedImage pauseScreen = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int mapState = 3;
	public final int closeState = 4;
	public final int deathState = 5;

	int iframes = 20;
	public boolean damaged = false;

	public Game() {
		level = Level.loadLevel("level0");
		player = new Player(this);
		enemy1 = new Enemy(this);
		fire = new Fire(this);

		gameState = titleState;
	}

	public void update(boolean[] keys) {
		time++;
		this.keys = keys;

		boolean up = keys[VK_W];
		boolean down = keys[VK_S];
		boolean left = keys[VK_A];
		boolean right = keys[VK_D];
		showMap = keys[VK_M];
		boolean space = keys[VK_SPACE];
		boolean pause = keys[VK_P];
		boolean quit = keys[VK_Q];
		boolean kms = keys[VK_L];
		boolean selfharm = keys[VK_K];

		if (gameState == titleState) {
			if (space == true) {
				gameState = playState;
			}
			if (quit == true) {
				gameState = closeState;
			}
		}

		if (gameState == playState) {

			enemy1.update();

			if ((player.x < fire.x + 1 && player.y < fire.y + 1) && (player.x > fire.x - 1 && player.y > fire.y - 1)) {
				if (!damaged) {
					damaged = true;
					player.damage(fire.burnDamage(), damaged);
				}
			}
			if (damaged) {
				iFrames();
				if (iframes == 0) {
					iframes = 20;
					damaged = false;
				}
			}

			player.update(up, down, left, right, mouse_x, space);
			if (showMap) {
				gameState = mapState;
				showMap = false;
				waitAMoment();

			}
			if (pause == true) {
				gameState = pauseState;
				pause = false;
				waitAMoment();
			}
			if (kms == true) {
				gameState = deathState;
			}

		}
		if (gameState == deathState) {
			if (quit == true) {
				gameState = closeState;
			}
			if (space == true) {
				player.reload();
			}
		}

		if (gameState == mapState) {
			if (showMap) {
				gameState = playState;
				showMap = false;
				waitAMoment();
			}
		}
		if (gameState == pauseState) {
			if (pause == true) {
				gameState = playState;
				pause = false;
				waitAMoment();
			}
		}

		if (quit == true) {
			gameState = closeState;
		}
	}

	public void waitAMoment() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update_mouse_x(double x) {
		// TODO Auto-generated method stub
		mouse_x = x;
	}

	public void iFrames() {
		iframes--;

	}

}
