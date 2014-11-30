
package com.zigabyte.lighting2d.entity;

import java.awt.Graphics2D;

import com.zigabyte.lighting2d.math.Vector2f;


public abstract class Entity {

	protected boolean collidable = false;

	public Vector2f pos;
	public Vector2f size;

	public Entity(Vector2f pos, Vector2f size) {
		this.pos = pos;
		this.size = size;
	}

	public abstract void update();

	public abstract void render(Graphics2D g);

	public boolean getCollidable(Entity e) {
		return collidable;
	}


}
