
package com.zigabyte.lighting2d.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.zigabyte.lighting2d.Main;
import com.zigabyte.lighting2d.entity.Entity;
import com.zigabyte.lighting2d.entity.Player;
import com.zigabyte.lighting2d.entity.Skeleton;
import com.zigabyte.lighting2d.entity.Wall;
import com.zigabyte.lighting2d.entity.Zombie;
import com.zigabyte.lighting2d.math.Line;
import com.zigabyte.lighting2d.math.Vector2f;


public class Level {

	public static Level level;

	// For any randomness needed
	public static final Random random = new Random();

	// An array of all entities
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Player player;

	public Level() {
		level = this;

		entities.add(new Wall(new Vector2f(300, 300), new Vector2f(50, 100)));
		entities.add(new Wall(new Vector2f(500, 400), new Vector2f(70, 70)));
		entities.add(new Wall(new Vector2f(400, 100), new Vector2f(80, 40)));

		player = new Player(new Vector2f(150, 150));
		entities.add(player);
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}


	public void update() {
		// Update all the game objects
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}

		if (random.nextInt(120) == 0) {
			entities.add(new Zombie(new Vector2f(random.nextInt(800), random.nextInt(600))));
		} else if (random.nextInt(120) == 0) {
			entities.add(new Skeleton(new Vector2f(random.nextInt(800), random.nextInt(600))));

		}
	}

	public void renderEntities(Graphics2D g) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
	}

	public void renderShadows(Graphics2D g) {
		//Vector2f mouse = new Vector2f(Input.mouseX, Input.mouseY);
		Vector2f mouse = player.pos.add(player.size.mul(0.5f, 0.5f));
		for (Entity entity : entities) {
			if (entity instanceof Wall) {
				Wall e = (Wall) entity;
				// Draw lines to the boxes
				/*for (int i = 0; i < 4; i++) {
					Line line = new Line(((Wall) e).getPoint(i), mouse);
					g.setColor(new Color(0xF18052));
					g.drawLine((int) 0, (int) line.getY(0), (int) 800, (int) line.getY(800));
				}*/

				// Draw shadows
				{
					/* Possible mouse positions
					 * 1 | 2 | 3
					 * 4 | O | 5
					 * 6 | 7 | 8
					 * */
					// Lines to each of the wall's points
					Line line0 = new Line(((Wall) e).getPoint(0), mouse);
					Line line1 = new Line(((Wall) e).getPoint(1), mouse);
					Line line2 = new Line(((Wall) e).getPoint(2), mouse);
					Line line3 = new Line(((Wall) e).getPoint(3), mouse);

					int nPoints = 6;
					Color color = new Color(0xFFA446);
					g.setColor(color);
					if (mouse.y < e.pos.y) { // position 1, 2, 3
						if (mouse.x < e.pos.x) {// position 1
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(3).x, (int) e.getPoint(1).x, 800, 800, 800 };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) e.getPoint(1).y, (int) line1.getY(800), (int) line3.getY(800), (int) line2.getY(800) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else if (mouse.x < e.pos.x + e.size.x) {// position 2
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, (int) e.getPoint(1).x, (int) line1.getX(800), (int) line0.getX(800) };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) e.getPoint(1).y, 800, 800 };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 3
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, 0, 0, 0 };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) line3.getY(0), (int) line2.getY(0), (int) line0.getY(0) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					} else if (mouse.y < e.pos.y + e.size.y) {// position 4,5
						if (mouse.x < e.pos.x) {// position 4
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, (int) e.getPoint(2).x, 800, 800 };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, (int) e.getPoint(2).y, (int) line2.getY(800), (int) line0.getY(800) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 5
							int[] xPoints = { (int) e.getPoint(1).x, (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, 0, 0 };
							int[] yPoints = { (int) e.getPoint(1).y, (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) line3.getY(0), (int) line1.getY(0) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					} else {// position 6, 7, 8
						if (mouse.x < e.pos.x) {// position 6
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, 800, 800, 800 };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, (int) line3.getY(800), (int) line1.getY(800), (int) line0.getY(800) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else if (mouse.x < e.pos.x + e.size.x) {// position 7
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, (int) line3.getX(0), (int) line2.getX(0) };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, 0, 0 };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 8
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(0).x, (int) e.getPoint(1).x, 0, 0, 0 };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) line1.getY(0), (int) line0.getY(0), (int) line2.getY(0) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					}

				}
			}
		}
	}

	public void render(Graphics2D g) {
		g.translate(-player.pos.x + Main.width / 2, -player.pos.y + Main.height / 2);
		// Render all the entities
		renderEntities(g);

		// Draw lines to all the boxes' corners and draw shadows
		renderShadows(g);
	}

	public boolean collides(Entity e, Vector2f move) {
		Rectangle r1 = new Rectangle((int) (e.pos.x + move.x), (int) (e.pos.y + move.y), (int) e.size.x, (int) e.size.y);
		for (Entity current : entities) {

			// If the 2 entities can not collide skip the check
			if (!current.getCollidable(e))
				continue;

			if (e != current) {
				Rectangle r2 = new Rectangle((int) current.pos.x, (int) current.pos.y, (int) current.size.x, (int) current.size.y);
				if (r1.intersects(r2)) {
					System.out.println(current);
					return true;
				}
			}
		}
		return false;
	}

	public boolean collides(Entity e) {
		return collides(e, new Vector2f());
	}
}
