
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.math.Vector2f;


public class Projectile extends Entity {

	protected Vector2f vel;
	protected float speed = 1;

	protected int lifeTime;


	public Projectile(Vector2f pos, Vector2f direction) {
		super(pos, new Vector2f(5, 5));

		lifeTime = 120;

		speed = 15;
		this.vel = direction.normal().mul(speed);
	}

	@Override
	public void update() {
		lifeTime--;
		if (lifeTime <= 0)
			Level.level.removeEntity(this);

		if (Level.level.collides(this))
			Level.level.removeEntity(this);

		pos = pos.add(vel);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
	}

	@Override
	public boolean getCollidable(Entity e) {
		if (e instanceof Player)
			return true;
		return false;
	}
}
