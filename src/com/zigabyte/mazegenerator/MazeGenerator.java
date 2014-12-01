
package com.zigabyte.mazegenerator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.zigabyte.lighting2d.math.Vector2f;


public class MazeGenerator {

	private static final Random random = new Random();

	private static final int WALL = 0;
	private static final int PASSAGE = 1;
	private static final int START_POINT = 2;
	private static final int END_POINT = 3;

	private static int w;
	private static int h;

	private static ArrayList<Vector2f> walls = new ArrayList<Vector2f>();

	private static int tiles[][];

	public static BufferedImage generateAMaze(int width, int height) {
		w = width;
		h = height;

		tiles = new int[width][height];

		walls.add(new Vector2f(1, 1));

		do {
			// Pick a random wall
			Vector2f currentWall = walls.get(random.nextInt(walls.size()));
			walls.remove(currentWall);

			// Check if we can change it into a passage
			int adjacentWalls = getAdjacentWalls(currentWall.getXInt(), currentWall.getYInt());
			if (adjacentWalls >= 3) {
				//Transform the current wall into a passage
				tiles[currentWall.getXInt()][currentWall.getYInt()] = PASSAGE;

				// Add adjacent walls to the wall list
				addAdjacentWalls(currentWall.getXInt(), currentWall.getYInt());
			}

		} while (walls.size() > 0);

		// Start point always at the far top left position
		tiles[1][1] = START_POINT;
		boolean ended = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				if (tiles[w - x - 1][h - y - 1] == PASSAGE) {
					tiles[w - x - 1][h - y - 1] = END_POINT;
					ended = true;
					break;
				}
			}
			if (ended)
				break;
		}

		// Find end point		

		return generateImage(tiles, width, height);
	}

	private static void addAdjacentWalls(int x, int y) {
		if (tiles[x][y + 1] == WALL && y + 1 > 0 && y + 1 < h - 1)
			walls.add(new Vector2f(x, y + 1));
		if (tiles[x][y - 1] == WALL && y - 1 > 0 && y - 1 < h - 1)
			walls.add(new Vector2f(x, y - 1));
		if (tiles[x + 1][y] == WALL && x + 1 > 0 && x + 1 < w - 1)
			walls.add(new Vector2f(x + 1, y));
		if (tiles[x - 1][y] == WALL && x - 1 > 0 && x - 1 < w - 1)
			walls.add(new Vector2f(x - 1, y));
	}

	private static int getAdjacentWalls(int x, int y) {
		int i = 0;
		if (tiles[x][y - 1] == WALL)
			i++;
		if (tiles[x][y + 1] == WALL)
			i++;
		if (tiles[x + 1][y] == WALL)
			i++;
		if (tiles[x - 1][y] == WALL)
			i++;
		return i;
	}

	private static BufferedImage generateImage(int[][] tiles, int width, int height) {
		int scale = 1;
		BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width * scale; x++) {
			for (int y = 0; y < height * scale; y++) {
				image.setRGB(x, y, tiles[x / scale][y / scale] == PASSAGE ? 0xffffff : 0);
				if (tiles[x / scale][y / scale] == START_POINT)
					image.setRGB(x, y, 0x00ff00);
				if (tiles[x / scale][y / scale] == END_POINT)
					image.setRGB(x, y, 0xff0000);
			}
		}
		return image;
	}

	public static void main(String[] args) {
		int size = 16;
		ImageIcon icon = new ImageIcon(generateAMaze(size, size));
		JOptionPane.showMessageDialog(null, "", "hello", 0, icon);
	}
}
