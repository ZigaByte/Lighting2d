
package com.zigabyte.lighting2d.level;

import java.awt.image.BufferedImage;

import com.zigabyte.lighting2d.entity.Wall;
import com.zigabyte.lighting2d.math.Vector2f;


public class LevelGenerator {

	public static void createWalls(BufferedImage image, Level level) {
		int w = image.getWidth();
		int h = image.getHeight();

		int tileSize = 20;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (image.getRGB(x, y) == 0xff000000)
					level.addEntity(new Wall(new Vector2f(x * tileSize, y * tileSize), new Vector2f(tileSize, tileSize)));
			}
		}
	}
}
