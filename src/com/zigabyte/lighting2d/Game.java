
package com.zigabyte.lighting2d;

import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;


public class Game {

	private Level level;

	public Game() {
		level = new Level();
	}

	public void update() {
		level.update();
	}

	public void render(Graphics2D g) {
		level.render(g);
	}


}
