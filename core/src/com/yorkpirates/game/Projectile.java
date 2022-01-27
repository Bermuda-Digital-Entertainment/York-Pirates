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
  protected Sprite firing;
  public Texture texture;

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
    x=firingObject.getY();
    setSize(16,16);
    distenceTravelled=0;
    texture = new Texture(Gdx.files.internal("boat.png"));
  }

  /**
  * Method to move a projectile by a set amount. It is called on every update
  */
  public Boolean moveTick (){
    x=speedX + x;
    y=speedY + y;
    distenceTravelled += Math.pow((Math.pow(speedX,2) + Math.pow(speedY,2)),.5);
    if (distenceTravelled >= range) return true;
    else return false;
  }

  public float getX(){
    return x;
  }

  public float getY(){
    return x;
  }
}
