
package com.zigabyte.lighting2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.zigabyte.lighting2d.input.Input;


@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {

	// Display width, height, title
	public static int width;
	public static int height;
	public static String title = "Just Some Lines";

	// Our frame for the canvas
	private JFrame frame;

	// Thread that starts the run method;
	private Thread thread;
	private boolean running;

	// The Game running
	private Game game;

	// The constructor
	public Main(int w, int h) {
		width = w;
		height = h;

		// Set the dimension to the canvas
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);

		// Set up the frame, add the canvas to it
		frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Init the game
		game = new Game();

		// Start the thread
		start();

		// Add the input
		Input input = new Input();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}

	// Set up the thread -> start the run method
	private void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// The run method, with the game loop that renders and updates everything
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (delta >= 1) {
				updates++;
				delta--;
				update();
			}
			frames++;
			render();
			if (System.currentTimeMillis() - timer > 1000) {
				frame.setTitle(title + "   " + frames + " fps,   " + updates + " ups");
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}
	}

	private void update() {
		// Update the game
		game.update();
	}

	// Render on the canvas
	private void render() {
		// Get canvas's bufferStrategy and create it if it's still null
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		// Create the Graphics object for rendering
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();


		// Clear the display
		g.setColor(new Color(0x6F5846)); // 6F5846,A95A52,E35B5D,F18052,FFA446
		g.fillRect(0, 0, getWidth(), getHeight());

		// Render the game
		game.render(g);

		// End the rendering, update the display
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Main(800, 600);
	}
}
