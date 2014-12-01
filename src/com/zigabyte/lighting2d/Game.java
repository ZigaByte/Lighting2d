
package com.zigabyte.lighting2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.level.TutorialLevel;


public class Game {

	private Level level;

	private int stage;
	private final int MAX_STAGE = 5;

	private boolean won = false;

	public Game() {
		level = new TutorialLevel();
	}

	public void update() {
		if (!won)
			level.update();

		if (level.isWon() && level.getWonTimer() > 120 && stage <= MAX_STAGE) {
			stage++;
			level = new Level(10 + 5 * stage);
		} else if (stage >= MAX_STAGE) {
			won = true;
		}
	}

	public void render(Graphics2D g) {
		if (!won)
			level.render(g);
		else {
			// 6F5846,A95A52,E35B5D,F18052,FFA446
			g.setColor(new Color(0xFFA446));
			g.fillRect(0, 0, Main.width, Main.height);
			g.setColor(new Color(0xA95A52));
			g.setFont(new Font("Tahoma", 1, 80));
			g.drawString("You have escaped!", 40, 280);
			g.drawString("the Maze!", 200, 380);
		}

	}


}
