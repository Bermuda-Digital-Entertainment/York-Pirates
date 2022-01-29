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
	public float xSpeed;
	public float ySpeed;
	public Boat firingObject;
	public float x, y;
	public Integer lastDirectionMoved = 0; //0 is up, 1 is right, 2 is down, 3 is left
	

	public boolean remove = false;

	public Bullet (float xSpeed, float ySpeed, float range, Boat firingObject) {
		this.range = range;
		this.speed = firingObject.getBulletSpeed();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		switch (firingObject.lastDirectionMoved){
			case 0:
				this.x = firingObject.getX()+10;
				this.y = firingObject.getY()+75;
				break;
			case 1:
				this.x = firingObject.getX()+75;
				this.y = firingObject.getY()+10;
				break;
			case 2:
				this.x = firingObject.getX()+10;
				this.y = firingObject.getY();
				break;
			default:
				this.x = firingObject.getX();
				this.y = firingObject.getY()+10;
				break;
		}


		if (texture == null)
		texture = new Texture(Gdx.files.internal("cannon_ball.png"));

	}

	public void update (float deltaTime) {
		distance += speed * deltaTime;
		y += (ySpeed * deltaTime);
		x += (xSpeed * deltaTime);
		if (distance > this.range)
			remove = true;
	}

	public void render (SpriteBatch batch) {
		batch.draw(texture, x, y, 16, 16);
	}

}
