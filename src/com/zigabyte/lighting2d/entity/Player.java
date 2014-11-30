
package com.zigabyte.lighting2d.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.zigabyte.lighting2d.input.Input;
import com.zigabyte.lighting2d.level.Level;
import com.zigabyte.lighting2d.math.Vector2f;


public class Player extends Mob {

	private int shootSpeed = 30; // lower shootspeed variable means faster shooting
	private int shootTimer = 0;

	public Player(Vector2f pos) {
		super(pos, new Vector2f(20, 20));
		vel = new Vector2f();

		hp = 5;
	}

	private void shoot() {
		shootTimer--;

		if (shootTimer <= 0) {
			if (Input.button == 1) {
				shootTimer = shootSpeed;

				Vector2f mouse = Input.getMousePositionInWorldCoordinates();
				Vector2f shootDirection = mouse.sub(pos).normal();
				Level.level.addEntity(new Projectile(pos.add(size.div(2)), shootDirection, this));
			}
		}
	}

	@Override
	public void update() {

		super.update();

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

		shoot();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		super.render(g);
		g.drawString("" + hp, pos.x + size.x / 4, pos.y + size.x / 4 * 3);
	}

	public void changeHP(float x) {
		hp += x;
	}

	public float getHP() {
		return hp;
	}

}
