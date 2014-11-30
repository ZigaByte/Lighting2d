
package com.zigabyte.lighting2d.math;


public class Line {

	// f(x) = k*x + n

	private float k;
	private float n;

	// Contruct a new line function with 2 points
	public Line(Vector2f p1, Vector2f p2) {
		// k = delta y / delta x
		k = (p2.y - p1.y) / (p2.x - p1.x);


		if (k > 100000)
			k = 1000000;
		else if (k < -100000)
			k = -1000000;

		// n = f(0)
		n = p1.y - k * p1.x;

		//System.out.println(k + " " + n);
	}

	public float getY(float x) {
		return k * x + n;
	}

	public float getX(float y) {
		return (y - n) / k;
	}

	// Check if a certain point is above a linear function 
	public boolean isAbove(Vector2f p) {
		// Calculate the y point on the linear function for the given x of the point
		float tY = k * p.x + n;
		// Check if the point's y value is greater than the calculated one
		return p.y > tY;
	}


}
