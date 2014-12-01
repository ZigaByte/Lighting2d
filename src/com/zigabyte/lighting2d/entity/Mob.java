
package com.zigabyte.lighting2d.entity;

import java.awt.Graphics2D;

import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.math.Vector2f;


public class Mob extends Entity {

	protected float hp;


	protected Vector2f vel;

	protected boolean canMove = true;

	public Mob(Vector2f pos, Vector2f size) {
		super(pos, size);

		collidable = true;
		this.pos = pos;
	}

	public void move(Vector2f v) {
		if (!canMove)
			return;

		if (v.x != 0 && v.y != 0) {
			move(v.mul(1, 0));
			move(v.mul(0, 1));
		} else if (Level.level.collides(this, v) == null) {
			pos = pos.add(v);
		}
	}

	@Override
	public void update() {

		if (hp <= 0)
			Level.level.removeEntity(this);

	}

	@Override
	public void render(Graphics2D g) {
		g.drawRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
	}

	public void hit(Projectile projectile) {
		hp -= projectile.getDamage();
		if (!(this instanceof Player))
			Level.level.removeEntity(this);
	}

}
