
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.math.Vector2f;


public class Skeleton extends Mob {

	public Skeleton(Vector2f pos) {
		super(pos, new Vector2f(15, 15));
	}


	public void moveToTarget() {
		Entity target = Level.level.player;

		Vector2f relativePosition = target.pos.sub(pos);
		vel = relativePosition.normal();

		if (relativePosition.length() < 100)
			vel = vel.mul(-1);
		else if (relativePosition.length() < 150)
			vel = vel.mul(0);

		//vel = vel.add(relativePosition.div(relativePosition.length()));
	}


	private void shoot() {
		Entity target = Level.level.player;

		Vector2f relativePosition = target.pos.sub(pos);

		Level.level.addEntity(new Projectile(this.pos, relativePosition.normal(), this));
	}

	@Override
	public void update() {
		moveToTarget();

		move(vel);

		if (Level.random.nextInt(180) == 0)
			shoot();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.CYAN);
		super.render(g);
	}

}
