package org.doom_engine.game.entities;

import org.doom_engine.game.Game;
import org.doom_engine.game.Level.Level;
import org.doom_engine.game.gfx.Bitmap3D;

public class Fire {
	public Game game;
	public Level level;
	public double x;
	public double y;

	public Fire(Game game) {
		this.game = game;
		this.level = game.level;
		x = level.fire_spawnx[0];
		y = level.fire_spawny[0];
	}

	public void renderFire(Bitmap3D bitmap) {
		bitmap.renderSprite(x, 0, y, 1, 0xFF4B24);

	}

	public int burnDamage() {
		int damage = 10;

		return damage;
	}
}
