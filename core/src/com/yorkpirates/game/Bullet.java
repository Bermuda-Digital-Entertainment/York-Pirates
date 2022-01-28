package com.yorkpirates.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

	public float speed;
	public float range = 0;
	public float distance = 0;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private static Texture texture;

	float x, y;

	public boolean remove = false;

	public Bullet (float x, float y, float speed, float range) {
		this.range = range;
		this.speed = speed;
		this.x = x;
		this.y = y;


		if (texture == null)
		texture = new Texture(Gdx.files.internal("cannon_ball.png"));
	}

	public void update (float deltaTime) {
		distance = y + (speed * deltaTime);
		y += speed * deltaTime;
		if (distance > this.range)
			remove = true;
	}

	public void render (SpriteBatch batch) {
		batch.draw(texture, x, y);
	}

}
