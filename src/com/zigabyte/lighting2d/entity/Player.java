
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.input.Input;
import com.zigabyte.lighting2d.math.Vector2f;


public class Player extends Mob {

	public Player(Vector2f pos) {
		super(pos, new Vector2f(15, 15));
		vel = new Vector2f();
	}

	@Override
	public void update() {
		if (Input.up)
			vel = vel.add(0, -1);
		if (Input.down)
			vel = vel.add(0, +1);
		if (Input.left)
			vel = vel.add(-1, 0);
		if (Input.right)
			vel = vel.add(+1, 0);

		if (Input.space)
			vel = vel.mul(0);

		if (vel.length() > 5)
			vel = vel.normal().mul(5);

		move(vel);

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		super.render(g);
	}

	@Override
	public boolean getCollidable(Entity e) {
		if (e instanceof Projectile)
			return true;
		return super.getCollidable(e);
	}

}
