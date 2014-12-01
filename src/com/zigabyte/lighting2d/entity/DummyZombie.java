
package com.zigabyte.lighting2d.entity;

import com.zigabyte.lighting2d.math.Vector2f;


public class DummyZombie extends Zombie {

	public DummyZombie(Vector2f pos) {
		super(pos);
		canMove = false;
	}

}
