package com.yorkpirates.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

	public float speed;
	public float range;
	public float distance = 0;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public Texture texture;
	public float xSpeed;
	public float ySpeed;
	public int firingObjectID;
	public float x, y;
	public Integer lastDirectionMoved = 0; //0 is up, 1 is right, 2 is down, 3 is left
	public float damage;
	public boolean remove = false;
	private Boat firingObject;
	private College firingObjectC;

	/**
	* Constructor method for a Bullet object fired by a boat
	* Sets the range, speed, direction vector, ID of the firing object and the starting location of the buttet.
	* @param xSpeed The speed at which the bullet should travel in the x axis
	* @param ySpeed The speed at which the bullet should travel in the y axis
	* @param firingObject The object that is firing the bullet (saves parameters to do it this way)
	*/
	public Bullet (float xSpeed, float ySpeed, Boat firingObject) {
		this.speed = firingObject.getBulletSpeed();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.range = firingObject.projectileRange;
		this.firingObject = firingObject;
		this.firingObjectID=firingObject.ID;
		this.damage=firingObject.projectileDamage;
		switch (firingObject.lastDirectionMoved){
			case 0:
				this.x = firingObject.getX()+20;
				this.y = firingObject.getY()+105;
				break;
			case 1:
				this.x = firingObject.getX()+105;
				this.y = firingObject.getY()+20;
				break;
			case 2:
				this.x = firingObject.getX()+20;
				this.y = firingObject.getY();
				break;
			default:
				this.x = firingObject.getX();
				this.y = firingObject.getY()+20;
				break;
		}

		
		texture = new Texture(Gdx.files.internal("cannon_ball.png"));

	}

	/**
	* Constructor method for a Bullet object fired by a college
	* Sets the range, speed, direction vector, ID of the firing object and the starting location of the buttet.
	* @param xSpeed The speed at which the bullet should travel in the x axis
	* @param ySpeed The speed at which the bullet should travel in the y axis
	* @param firingObject The object that is firing the bullet (saves parameters to do it this way)
	*/
	public Bullet (float xSpeed, float ySpeed, College firingObject) {
		this.speed = firingObject.getBulletSpeed();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.range = firingObject.projectileRange;
		this.firingObjectC = firingObject;
		this.firingObjectID=firingObject.ID;
		this.damage=firingObject.projectileDamage;
		this.x = firingObject.getX();
		this.y = firingObject.getY();

		
		texture = new Texture(Gdx.files.internal("fire_ball.png"));

	}


	/**
	*	Updates the position of a Bullet and decides if it needs removing based on the distence travelled
	* @param deltaTime The time between two calls of render in York Pirates
	*/
	public void update (float deltaTime) {
		distance += speed * deltaTime;
		y += (ySpeed * deltaTime);
		x += (xSpeed * deltaTime);
		if (distance > this.range)
			remove = true;
	}

	/**
	*	Seb, please do this one
	* @param batch The Spritebatch on which the bullet will be drawn on to.
	*/
	public void render (SpriteBatch batch) {
		batch.draw(texture, x, y, 16, 16);
	}

	/**
	* Method to check if the Bullet has been fired by a Boat
	* @return the object that fired the bullet
	*/
	public Boat getFiringObject(){
		return this.firingObject;
	}
	/**
	* Method to check if the Bullet has been fired by a College
	* @return the object that fired the bullet
	*/
	public College getFiringObjectC(){
		return this.firingObjectC;
	}
}
