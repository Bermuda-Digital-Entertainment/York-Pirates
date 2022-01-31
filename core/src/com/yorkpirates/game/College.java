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
  public int markedDestroyed=0;

  /**
  * Constructor method for College. It simply gives the college a unique ID
  * @param collegeID int Passes a unique ID to the college (to prevent an object hitting itself with a cannon ball)
  */
  public College(int collegeID) {
    this.ID=collegeID;
  }

  /**
  * Fires a projectile at a boat at coordinates boatX, boatY.
  * @param boatX X coordinate of the boat
  * @param boatY Y coordinate of the boat
  */
  public Bullet fire(Float boatX, Float boatY) {
    Bullet cannonBall;
    float vectorX,vectorY;
    float distance;
    distance = (float)Math.sqrt(Math.pow(this.getX() - boatX.doubleValue(),2) + Math.pow(this.getY() - boatY.doubleValue(),2));
    vectorX=(float) (boatX.doubleValue() - this.getX());
    vectorX = (vectorX / distance) * projectileSpeed;
    vectorY=(float) (boatY.doubleValue() - this.getY());
    vectorY = (vectorY / distance) * projectileSpeed;

    cannonBall = new Bullet(vectorX,vectorY,this);
    resetFire();
    return cannonBall;
  }

  /**
  * Detects if the college has been hit by a particular projectile
  * @param collisionProjectile A pointer to the projectile that the college is checking
  */
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

  /**
  * Returns the current health of the college
  * @return Current college health
  */
  public float health() {
    return health/maxHealth;
  }

  /**
  * Detects if the college has been successfully destroyed
  * @return True if the college has a health <= 0
  */
  public Boolean isDestroyed() {
    return (health<=0);
  }

  /**
  * Detects if the college can fire a cannon ball
  * @return True if the the time since last shot is greater than the firing rate and the player is within range
  */
  public Boolean canFire(Float boatX, Float boatY){
    float distance;
    distance = (float)Math.sqrt(Math.pow(this.getX() - boatX.doubleValue(),2) + Math.pow(this.getY() - boatY.doubleValue(),2));
    return (lastShotTime > firingRate && distance < projectileRange.doubleValue() && isDestroyed()==false);
  }

  /**
  * Resets lastShotTime to 0 when triggered
  */
  public void resetFire(){
    lastShotTime=0;
  }

  /**
  * Adds the ellapsed time since it was last executed to lastShotTime
  * @param time The time since the last run of the render routine in YorkPirates
  */
  public void addTime(float time){
    lastShotTime+=time;
  }

  /**
  * Returns the speed at which a cannon ball is fired
  * @return The speed at which a bullet is fired by the college
  */
  public float getBulletSpeed(){
    return projectileSpeed;
  }
}
