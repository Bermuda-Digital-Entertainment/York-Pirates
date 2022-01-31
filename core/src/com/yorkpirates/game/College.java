package com.yorkpirates.game;

import java.lang.Math;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;



/**
* College Class. Implements a sprite with additional Properties for Health.
*/
public class College extends Sprite {
  public Texture texture;
  public float health=150;
  public float maxHealth=150;
  public Float projectileDamage = 15f;
  public Float projectileRange = 500f;
  protected float projectileSpeed = 50;
  protected float firingRate = 3;
  public final int ID;
  public float lastShotTime=0;

  public College(int collegeID) {
    this.ID=collegeID;
  }

  public Bullet fire(Float boatX, Float boatY) {
    Bullet cannonBall;
    float vectorX,vectorY;
    float distance;
    distance = (float)Math.sqrt(Math.pow(this.getX() - boatX.doubleValue(),2) + Math.pow(this.getY() - boatY.doubleValue(),2));
    vectorX=(float) (boatX.doubleValue() - this.getX());
    vectorX = (vectorX / distance) * projectileSpeed;
    vectorY=(float) (boatY.doubleValue() - this.getY());
    vectorY = (vectorY / distance) * projectileSpeed;

    cannonBall = new Bullet(vectorX,vectorY,500,this);
    resetFire();
    return cannonBall;
  }

  public Boolean isHit(Bullet collisionProjectile) {
    Rectangle testRectangle = new Rectangle(collisionProjectile.x,collisionProjectile.y,16,16);
    Boolean hit = false;
    if (getBoundingRectangle().overlaps(testRectangle) && collisionProjectile.firingObjectID != this.ID){
      collisionProjectile.remove=true;
      hit = true;
      health -= collisionProjectile.damage;
    }
    return hit;
  }

  public float health() {
    return health/maxHealth;
  }

  public Boolean isDestroyed() {
    if (health<=0)
      return true;
    else
      return false;
  }

  public Boolean canFire(Float boatX, Float boatY){
    float distance;
    distance = (float)Math.sqrt(Math.pow(this.getX() - boatX.doubleValue(),2) + Math.pow(this.getY() - boatY.doubleValue(),2));
    return (lastShotTime > firingRate && distance < projectileRange.doubleValue());
  }

  public void resetFire(){
    lastShotTime=0;
  }

  public void addTime(float time){
    lastShotTime+=time;
  }

  public float getBulletSpeed(){
    return projectileSpeed;
  }
}
