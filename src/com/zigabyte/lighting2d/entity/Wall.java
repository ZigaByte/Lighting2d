
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.math.Vector2f;


public class Wall extends Entity {

	// Color of the rendered wall
	private Color color = new Color(0xE35B5D);

	public Wall(Vector2f pos, Vector2f size) {
		super(pos, size);
		collidable = true;
	}

	@Override
	public void update() {
		//pos.x += 0.10f;
		//pos.y += 0.5f;

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(color);

		g.fillRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
		
	}

	/*
	 * 0 1
	 * 2 3
	 * */
	public Vector2f getPoint(int i) {
		switch (i) {
			case 0:
				return pos;
			case 1:
				return pos.add(size.mul(1, 0));
			case 2:
				return pos.add(size.mul(0, 1));
			case 3:
				return pos.add(size);
		}
		return null;
	}

}
