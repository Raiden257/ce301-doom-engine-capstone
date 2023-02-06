package org.doom_engine.game.entities;

import org.doom_engine.game.Game;
import org.doom_engine.game.Level.Level;
import java.awt.Robot;

public class Player {

	public Game game;
	public Level level;
	public double x;
	public double y;
	public static int health = 100, health_max = 200, health_min = 0;
	public static int armor = 100, armor_max = 200, armor_min = 0;
	public static int x1;
	public static int y1;
	public double xa;
	public double ya;
	public double ra;
	public double rot;
	long lastTime = System.currentTimeMillis();
	Robot r;

	public Player(Game game) {
		this.game = game;
		this.level = game.level;
		this.x = level.xSpawn[0];
		this.y = level.ySpawn[0];
		rot = Math.PI / 2.0;
//		this.health=100;
//		this.armor=100;
		try {
			r = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(boolean up, boolean down, boolean left, boolean right, double mouseX, boolean space) {
		double wSpeed = 0.05;
		double rSpeed = 0.01;

		double xd = 0;
		double yd = 0;

		if (up) {
			yd++;

		}
		if (down) {
			yd--;

		}
		if (left) {
			xd--;

		}
		if (right) {
			xd++;

		}
//		if(damage) {
//			health-=20;
//			armor-=20;
//		}
		System.out.println(health + " " + armor);
		if (health < 1) {
			death();
		}

		double mouseDx = 320 - mouseX;

		double rCos = Math.cos(rot);
		double rSin = Math.sin(rot);

		xa += (xd * rCos + yd * -rSin) * wSpeed;
		ya += (xd * rSin + yd * rCos) * wSpeed;

		rot += mouseDx * rSpeed;

		if (isFree(x + xa, y))
			x += xa;
		if (isFree(x, y + ya))
			y += ya;

		x1 = (int) x;
		y1 = (int) y;

		xa *= 0.6;
		ya *= 0.6;
		ra *= 0.6;

		if (space) {
			use();
		}

		r.mouseMove(768, 423);
	}

	public void use() {
	}

	private boolean isFree(double xx, double yy) {
		double d = 0.15;

		int x0 = (int) (Math.round(xx - d));
		int x1 = (int) (Math.round(xx + d));
		int y0 = (int) (Math.round(yy - d));
		int y1 = (int) (Math.round(yy + d));

		if (level.getBlock(x0, y0).SOLID_MOTION)
			return false;
		if (level.getBlock(x1, y0).SOLID_MOTION)
			return false;
		if (level.getBlock(x0, y1).SOLID_MOTION)
			return false;
		if (level.getBlock(x1, y1).SOLID_MOTION)
			return false;

		return true;
	}

	public void damage(int damage, boolean damaged) {
		double h = 0.2;
		if (damaged) {

			if (health >= 0) {

				if (armor > 0) {
					armor -= (int) (0.8 * damage);
					health -= (int) (0.2 * damage);

				}
				if (armor < 1) {

					armor = 0;
					health -= (armor * -1) + damage;
				}
			}
			if (health == 0) {
				death();
			}

		}

	}

	public void death() {
		game.gameState = game.deathState;

	}

	public void reload() {
		x = level.xSpawn[0];
		y = level.ySpawn[0];
		rot = Math.PI / 2;
		health = 100;
		armor = 100;
		game.gameState = game.playState;

	}
}
