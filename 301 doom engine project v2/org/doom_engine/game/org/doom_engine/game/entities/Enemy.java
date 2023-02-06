package org.doom_engine.game.entities;

import org.doom_engine.game.Game;
import org.doom_engine.game.Level.Level;
import org.doom_engine.game.gfx.Bitmap3D;
import org.doom_engine.game.gfx.Sprite;
import org.doom_engine.game.Level.Block;

public class Enemy {
	public Game game;
	public Level level;
	public static double x;
	public static double y;
	public static int x1;
	public static int y1;
	public double xa;
	public double ya;
	public double ra;
	public double rot;
	public String states[] = { "idle", "patrol", "alert", "attack", "chase", "dead", "lose aggro" };
	public String state = "idle";
	Block block = new Block();
	static Bitmap3D bm = new Bitmap3D(160, 120);

	public Enemy(Game game) {
		this.game = game;
		this.level = game.level;
		x = level.enemy_spawnx[0];
		y = level.enemy_spawny[0];
		rot = -Math.PI / 2;
	}

	public void renderEnemy(Bitmap3D bitmap) {
		bitmap.renderSprite(x, 0, y, 2, 0x005555);
	}

	public void update() {

		double wSpeed = 0.01;
		double rSpeed = 0.02;
		double rCos = Math.cos(rot);
		double rSin = Math.sin(rot);
		double xd = 0;
		double yd = 1;

		xa += (xd * rCos + yd * -rSin) * wSpeed;
		ya += (xd * rSin + yd * rCos) * wSpeed;

		rot += -1 * rSpeed;

		if (isFree(x + xa, y))
			x += xa;
		if (isFree(x, y + ya))
			y += ya;

		xa *= 0.6;
		ya *= 0.6;
		ra *= 0.6;

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

}
