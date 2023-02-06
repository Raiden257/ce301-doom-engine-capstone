package org.doom_engine.game.Level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.doom_engine.game.entities.Enemy;
import org.doom_engine.game.gfx.Sprite;

public class Level {

	private static Map<String, Level> levels = new HashMap<String, Level>();

	public String name;
	public int width;
	public int height;
	public int[] pixels;
	public Block[] tile;

	public int xSpawn[] = { 0 };
	public int ySpawn[] = { 0 };
	public int[] enemy_spawnx = new int[256], enemy_spawny = new int[256], fire_spawnx = new int[20],
			fire_spawny = new int[20];

	public Level(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		tile = new Block[width * height];
		this.pixels = new int[width * height];
	}

	public void load() {
		int e = 0, f = 0;
		for (int i = 0; i < width * height; i++)
			pixels[i] = pixels[i] & 0xffffff;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = new Block();

				int type = pixels[x + y * width];

				if (type == 0xFFFFFF) {
					block = new SolidBlock();
					block.col = 0x667CDB & 0x555555;
				} else if (type == 0xFFFF00) {
					xSpawn[0] = x;
					ySpawn[0] = y;
				} else if (type == 0x00FF00) {
					block.addSprite(new Sprite(0, 0, 0, 0, 0x003300));
				} else if (type == 0x00FFFF) {
					enemy_spawnx[e] = x;
					enemy_spawny[e] = y;
					e++;
//					block.addSprite(new Sprite(0, 0, 0, 1, 0x005555));
				} else if (type == 0xFF8D1C) {
					fire_spawnx[f] = x;
					fire_spawny[f] = y;
					f++;
				} else if (type == 0xff00ff) {
					block.ceilCol = 0x550055;
					block.floorCol = 0x550000;
				}

				tile[x + y * width] = block;
			}
		}
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return new SolidBlock();

		return tile[x + y * width];
	}

	public static Level loadLevel(String name) {
		if (levels.containsKey(name))
			return levels.get(name);

		try {
			BufferedImage image = ImageIO.read(Level.class.getResourceAsStream("/levels/" + name + ".png"));
			int w = image.getWidth();
			int h = image.getHeight();

			Level res = new Level(name, w, h);
			image.getRGB(0, 0, res.width, res.height, res.pixels, 0, res.width);
			res.load();

			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
