
package com.zigabyte.lighting2d.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.zigabyte.lighting2d.Main;
import com.zigabyte.lighting2d.entity.Entity;
import com.zigabyte.lighting2d.entity.Mob;
import com.zigabyte.lighting2d.entity.Player;
import com.zigabyte.lighting2d.entity.Skeleton;
import com.zigabyte.lighting2d.entity.Wall;
import com.zigabyte.lighting2d.entity.Zombie;
import com.zigabyte.lighting2d.input.ImageLoader;
import com.zigabyte.lighting2d.math.Line;
import com.zigabyte.lighting2d.math.Vector2f;


public class Level {

	public static Level level;

	// For any randomness needed
	public static final Random random = new Random();

	// The maze size
	public int width;
	public int height;

	// An array of all entities
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Player player;

	public Rectangle goalArea;
	public Rectangle spawnArea;

	public boolean won = false;
	public boolean lost = false;
	public int lostTimer = 0;

	public Level() {
		level = this;

		LevelGenerator.createWalls(ImageLoader.loadImage("/maze3.png"), this);

		respawnPlayer();
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

		if (random.nextInt(180) == 0) {
			Zombie spawn = new Zombie(new Vector2f(random.nextInt(width), random.nextInt(height)));
			if (collides(spawn) == null)
				entities.add(spawn);
		} else if (random.nextInt(180) == 0) {
			Skeleton spawn = new Skeleton(new Vector2f(random.nextInt(width), random.nextInt(height)));
			if (collides(spawn) == null)
				entities.add(spawn);
		}

		lostTimer--;
		if (player.getHP() <= 0) {
			lost = true;
			lostTimer = 120;
			respawnPlayer();
		}

		checkVictory();
	}

	private void checkVictory() {
		Rectangle p = new Rectangle((int) player.pos.x, (int) player.pos.y, (int) player.size.x, (int) player.size.y);
		if (p.intersects(goalArea))
			won = true;
	}

	private void respawnPlayer() {
		clearAllMobs();

		player = new Player(new Vector2f(spawnArea.x + spawnArea.width / 2 - 10, spawnArea.y + spawnArea.height / 2 - 10));
		entities.add(player);
	}

	private void clearAllMobs() {
		for (int i = 0; i < entities.size();) {
			if (entities.get(i) instanceof Mob)
				entities.remove(i);
			else
				i++;
		}
	}

	private void renderEntities(Graphics2D g) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
	}

	private void renderShadows(Graphics2D g) {
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

					int maxX = (int) (Main.width + player.pos.x);
					int minX = (int) (0 - player.pos.x - Main.width / 2);
					int maxY = (int) (Main.height + player.pos.y);
					int minY = (int) (0 - player.pos.y - Main.height / 2);

					int nPoints = 6;
					Color color = new Color(0xFFA446);
					g.setColor(color);
					if (mouse.y < e.pos.y) { // position 1, 2, 3
						if (mouse.x < e.pos.x) {// position 1
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(3).x, (int) e.getPoint(1).x, maxX, maxX, maxX };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) e.getPoint(1).y, (int) line1.getY(maxX), (int) line3.getY(maxX), (int) line2.getY(maxX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else if (mouse.x < e.pos.x + e.size.x) {// position 2
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, (int) e.getPoint(1).x, (int) line1.getX(maxY), (int) line0.getX(maxY) };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) e.getPoint(1).y, maxY, maxY };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 3
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, minX, minX, minX };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) line3.getY(minX), (int) line2.getY(minX), (int) line0.getY(minX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					} else if (mouse.y < e.pos.y + e.size.y) {// position 4,5
						if (mouse.x < e.pos.x) {// position 4
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, (int) e.getPoint(2).x, maxX, maxX };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, (int) e.getPoint(2).y, (int) line2.getY(maxX), (int) line0.getY(maxX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 5
							int[] xPoints = { (int) e.getPoint(1).x, (int) e.getPoint(0).x, (int) e.getPoint(2).x, (int) e.getPoint(3).x, minX, minX };
							int[] yPoints = { (int) e.getPoint(1).y, (int) e.getPoint(0).y, (int) e.getPoint(2).y, (int) e.getPoint(3).y, (int) line3.getY(minX), (int) line1.getY(minX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					} else {// position 6, 7, 8
						if (mouse.x < e.pos.x) {// position 6
							int[] xPoints = { (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, maxX, maxX, maxX };
							int[] yPoints = { (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, (int) line3.getY(maxX), (int) line1.getY(maxX), (int) line0.getY(maxX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else if (mouse.x < e.pos.x + e.size.x) {// position 7
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(0).x, (int) e.getPoint(1).x, (int) e.getPoint(3).x, (int) line3.getX(minY), (int) line2.getX(minY) };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) e.getPoint(3).y, minY, minY };
							g.fillPolygon(xPoints, yPoints, nPoints);
						} else {// position 8
							int[] xPoints = { (int) e.getPoint(2).x, (int) e.getPoint(0).x, (int) e.getPoint(1).x, minX, minX, minX };
							int[] yPoints = { (int) e.getPoint(2).y, (int) e.getPoint(0).y, (int) e.getPoint(1).y, (int) line1.getY(minX), (int) line0.getY(minX), (int) line2.getY(minX) };
							g.fillPolygon(xPoints, yPoints, nPoints);
						}
					}

				}
			}
		}
	}

	public void render(Graphics2D g) {
		g.translate(-player.pos.x + Main.width / 2 - player.size.x / 2, -player.pos.y + Main.height / 2 - player.size.y / 2); // Center the screen on the player

		// Render all the entities
		renderEntities(g);

		// Draw lines to all the boxes' corners and draw shadows
		renderShadows(g);

		if (won) {
			g.setFont(new Font("Tahoma", 1, 150));
			// 6F5846,A95A52,E35B5D,F18052,FFA446
			g.setColor(new Color(0xA95A52));
			g.drawString("Victory!", goalArea.x - 230, goalArea.y + 200);
		}

		if (lostTimer > 0) {
			g.setFont(new Font("Tahoma", 1, 150));
			// 6F5846,A95A52,E35B5D,F18052,FFA446
			g.setColor(new Color(0xA95A52));
			g.drawString("Defeat!", spawnArea.x - 240, spawnArea.y - 100);
		}
	}

	public Entity collides(Entity e, Vector2f move) {
		Rectangle r1 = new Rectangle((int) (e.pos.x + move.x), (int) (e.pos.y + move.y), (int) e.size.x, (int) e.size.y);
		for (Entity current : entities) {

			// If the 2 entities can not collide skip the check
			if (!current.getCollidable(e))
				continue;

			if (e != current) {
				Rectangle r2 = new Rectangle((int) current.pos.x, (int) current.pos.y, (int) current.size.x, (int) current.size.y);
				if (r1.intersects(r2)) {
					//System.out.println(current);
					return current;
				}
			}
		}
		return null;
	}

	public Entity collides(Entity e) {
		return collides(e, new Vector2f());
	}
}
