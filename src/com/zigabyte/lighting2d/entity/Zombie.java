
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


	private void hitTarget() {
		if (Level.random.nextInt(60) == 0) {
			
			Player target = Level.level.player;
			Vector2f relativePosition = (target.pos.add(target.size.div(2))).sub(pos.add(size.div(2)));
			
			if (relativePosition.length() < 25) {// If the target is close enough, hit it
				target.changeHP(-1);
			}
		}
	}

	@Override
	public void update() {
		moveToTarget();

		hitTarget();

		move(vel);
	}


	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.green);
		super.render(g);
	}
}
