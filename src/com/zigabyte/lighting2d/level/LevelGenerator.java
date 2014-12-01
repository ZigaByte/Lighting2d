
package com.zigabyte.lighting2d.level;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.zigabyte.lighting2d.entity.Wall;
import com.zigabyte.lighting2d.math.Vector2f;
import com.zigabyte.mazegenerator.MazeGenerator;


public class LevelGenerator {

	public static void createWalls(BufferedImage image, Level level) {
		int w = image.getWidth();
		int h = image.getHeight();

		int tileSize = 40;

		level.width = w * tileSize;
		level.height = h * tileSize;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (image.getRGB(x, y) == 0xff000000)
					level.addEntity(new Wall(new Vector2f(x * tileSize, y * tileSize), new Vector2f(tileSize, tileSize)));
				else if (image.getRGB(x, y) == 0xffff0000)
					level.goalArea = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
				else if (image.getRGB(x, y) == 0xff00ff00)
					level.spawnArea = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);

			}
		}
	}

	public static void generateRandomMaze(int w, int h, Level level) {
		createWalls(MazeGenerator.generateAMaze(w, h), level);
	}
}
