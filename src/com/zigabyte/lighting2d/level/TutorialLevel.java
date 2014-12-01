
package com.zigabyte.lighting2d.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.entity.DummySkeleton;
import com.zigabyte.lighting2d.entity.DummyZombie;
import com.zigabyte.lighting2d.input.ImageLoader;
import com.zigabyte.lighting2d.math.Vector2f;


public class TutorialLevel extends Level {

	public TutorialLevel() {
		super("/tutorial.png");
		level = this;

		disabledMobsSpawning = true;

		entities.add(new DummySkeleton(new Vector2f(1010, 410)));
		entities.add(new DummyZombie(new Vector2f(900, 410)));
	}

	@Override
	protected void renderText(Graphics2D g) {
		// 6F5846,A95A52,E35B5D,F18052,FFA446
		g.setColor(new Color(0xA95A52));

		g.setFont(new Font("Tahoma", 1, 30));
		g.drawString("Welcome to the Maze", 80, 100);
		g.setFont(new Font("Tahoma", 0, 19));
		g.drawString("Use your arrow keys to move around", 85, 150);

		g.setFont(new Font("Tahoma", 1, 30));
		g.drawString("Luckily you have gun", 600, 100);
		g.setFont(new Font("Tahoma", 0, 19));
		g.drawString("Aim and shoot with your mouse", 605, 150);

		g.setFont(new Font("Tahoma", 1, 19));
		g.translate(0, 70);
		g.drawString("||", 1190, 125);
		g.drawString("||", 1190, 135);
		g.drawString("||", 1190, 150);
		g.drawString("\\  /", 1186, 170);
		g.drawString("\\/", 1192, 189);
		g.translate(0, -70);

		g.setFont(new Font("Tahoma", 1, 30));
		g.drawString("Sadly you are not alone", 700, 530);

		g.setFont(new Font("Tahoma", 0, 19));
		g.drawString("Use your gun to kill these mobs", 800, 350);
		g.drawString("and then procede through the maze", 780, 380);

		g.setFont(new Font("Tahoma", 0, 15));
		g.drawString("<- Zombie", 920, 420);
		g.drawString("<- Skeleton", 1030, 420);

	}
}
