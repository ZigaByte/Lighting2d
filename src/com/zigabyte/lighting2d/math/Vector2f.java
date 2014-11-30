
package com.zigabyte.lighting2d.math;


public class Vector2f {

	public float x;
	public float y;

	public Vector2f() {
		this(0, 0);
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vector2f normal() {
		float l = length();
		return new Vector2f(x / l, y / l);
	}

	public Vector2f add(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}

	public Vector2f add(Vector2f other) {
		return new Vector2f(x + other.x, y + other.y);
	}

	public Vector2f sub(float x, float y) {
		return new Vector2f(this.x - x, this.y - y);
	}

	public Vector2f sub(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y);
	}


	public Vector2f mul(float i) {
		return new Vector2f(this.x * i, this.y * i);
	}

	public Vector2f mul(float x, float y) {
		return new Vector2f(this.x * x, this.y * y);
	}

	public Vector2f mul(Vector2f other) {
		return new Vector2f(x * other.x, y * other.y);
	}

	public Vector2f div(float i) {
		return new Vector2f(this.x / i, this.y / i);
	}

	public Vector2f div(float x, float y) {
		return new Vector2f(this.x / x, this.y / y);
	}

	public Vector2f div(Vector2f other) {
		return new Vector2f(x / other.x, y / other.y);
	}

}
