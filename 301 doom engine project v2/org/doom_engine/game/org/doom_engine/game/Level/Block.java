package org.doom_engine.game.Level;

import java.util.ArrayList;
import java.util.List;

import org.doom_engine.game.gfx.Sprite;

public class Block {
	
	  public boolean SOLID_RENDER = false;
	    public boolean SOLID_MOTION = false;

	    public List<Sprite> sprites = new ArrayList<Sprite>();

	    public int tex = 0;
	    public int col = 0x555555;
	    public int ceilTex = 0;
	    public int floorTex = 0;
	    public int floorCol = 0x555555;
	    public int ceilCol = 0x555555;

	    public Block()
	    {
	    }
	    
	    public void addSprite(Sprite sprite) {
	    	sprites.add(sprite);
	    }

}
