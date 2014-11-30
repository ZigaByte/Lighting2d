
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.math.Vector2f;


public class Zombie extends Mob {

	public Zombie(Vector2f pos) {
		super(pos, new Vector2f(15, 15));
	}

	public void moveToTarget() {
		Entity target = Level.level.player;

		Vector2f relativePosition = target.pos.sub(pos);
		vel = relativePosition.normal();
		//vel = vel.add(relativePosition.div(relativePosition.length()));
	}

	@Override
	public void update() {
		moveToTarget();

		move(vel);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.green);
		super.render(g);
	}
}
