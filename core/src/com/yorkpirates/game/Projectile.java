package com.yorkpirates.game;

import java.lang.Math;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

/**
* Projectile Class. Implements a sprite with additional Properties for speed, range and damage. It can be fired by AI boat or a player
*/
public class Projectile extends Sprite {
  private float x;
  private float y;
  private float distenceTravelled;
  protected float speedX;//Velocity vector for x
  protected float speedY;//Velocity vector for y
  protected float range;
  protected Integer damage;
  protected Integer speed;
  protected Boat firing;
  public Texture texture;
  public Boolean remove = false;

  /**
  * Projectile Class Constructor.
  *
  * @param srcX The source X coordinate for a projectile
  * @param srcY The source Y coordinate for a projectile
  * @param srcX The X speed vector for a projectile
  * @param srcY The Y speed vector for a projectile
  * @param initRange The number of 'ticks'
  */
  public Projectile (Float srcSpeedX, Float srcSpeedY, Float initRange, Boat firingObject) {
    speedX=srcSpeedX;
    speedY=srcSpeedY;
    range=initRange;
    firing=firingObject;
    x=firingObject.getX();
    y=firingObject.getY();
    setSize(48,48);
    distenceTravelled=0;
    texture = new Texture(Gdx.files.internal("boat.png"));
  }

  /**
  * Method to move a projectile by a set amount. It is called on every update
  */
  public Boolean moveTick (){
    x+=speedX;
    y+=speedY;
    distenceTravelled += Math.pow((Math.pow(speedX,2) + Math.pow(speedY,2)),.5);
    System.out.print(range);
    System.out.print(", ");
    System.out.println(distenceTravelled);
    System.out.println(distenceTravelled > range);
    return (distenceTravelled > range);
  }

  public float getX(){
    return x;
  }

  public void update (float deltaTime) {
  
		if (this.getY() > this.range){
			remove = true;
    }
  }
		
    
  public float getY(){
    return y;
  }
  public float getRange(){
    return range;
  }
}
