package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


/**
* Boat Class. Implements a sprite with additional Properties for Health, boat Level, etc. It can be an AI boat or a player
*/
public class Boat extends Sprite {
  private Float x;
  private Float y;
  public Texture texture;
  public float health;
  public float maxHealth;
  public Integer level;
  public float speed = 50f;
  public float firingRate = 1f;
  public Float projectileDamage = 10f;
  public Float projectileRange = 500f;
  public Float projectileSpeed = 60f;
  public Integer lastDirectionMoved; //0 is up, 1 is right, 2 is down, 3 is left
	public float lastShotTime;
  public final int ID;


  /**
  * Constructor method for Boar. It simply gives the boat a unique ID
  * @param boatID int Passes a unique ID to the boat (to prevent an object hitting itself with a cannon ball)
  */
  public Boat(int boatID){
    lastShotTime=6;
    health = 50f;
    maxHealth = 50f;
    this.ID=boatID;
  }

  /**
  * Method checking if the boat has collided with another object
  * @param collisionColleges A list of all the colleges the boat could collide with
  * @param collisionBoats A list of all the boats the boat could collide with
  * @return If the boat is overlapping another object in either list, return true
  */
  public Boolean collides(ArrayList<College> collisionColleges, ArrayList<Boat> collisionBoats){
    Boolean collisionExists = false;
    for (Integer i=0; i<collisionColleges.size(); i++) {
      if (getBoundingRectangle().overlaps(collisionColleges.get(i).getBoundingRectangle())) collisionExists=true;
    }
    for (Integer i=0; i<collisionBoats.size(); i++) {
      if (getBoundingRectangle().overlaps(collisionBoats.get(i).getBoundingRectangle())) collisionExists=true;
    }
    return collisionExists;
  }

  /**
  * Method checking if the boat has collided with a cannon ball
  * @param collisionProjectile A bullet that is to be checked
  * @return If the boat is overlapping another a bullet, return true
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
  * A method creating a bullet
  * @return A bullet fired forwards relative to the boat
  */
  public Bullet fire() {
    Bullet cannonBall;
    switch (lastDirectionMoved) {
      case 0:
        cannonBall = new Bullet(0,50,this);
        break;
      case 1:
        cannonBall = new Bullet(50,0,this);
        break;
      case 2:
        cannonBall = new Bullet(0,-50,this);
        break;
      default:
        cannonBall = new Bullet(-50,0,this);
        break;
    }
    resetFire();
    return cannonBall;
  }

  /**
  * A method to get the current texture
  * @return The current texture of the boat (or that the boat should have)
  */
  public Texture getTexture(){
    Texture texture;
    switch (lastDirectionMoved){
    case 0:
      texture = new Texture(Gdx.files.internal("pirate_ship_up.png"));
      break;
    case 1:
      texture = new Texture(Gdx.files.internal("pirate_ship_right.png"));
      break;
    case 2:
      texture = new Texture(Gdx.files.internal("pirate_ship_down.png"));
      break;
    default:
      texture = new Texture(Gdx.files.internal("pirate_ship_left.png"));
      break;
    }
  return texture;
  }

  /**
  * A method to check if the boat can fire yet
  * @return If more time has passed since the previous shot than firingRate, return true
  */
  public Boolean canFire(){
    return (lastShotTime > firingRate);
  }

  /**
  * Adds deltaTime to the lastShotTime
  * @param time Time that has ellapsed between two renders of the screen.
  */
  public void addTime(float time){
    lastShotTime+=time;
  }

  /**
  * Resets lastShotTime to 0
  */
  public void resetFire(){
    lastShotTime=0;
  }

  /**
  * Returns the speed at which a bullet can be fireds
  * @return projectileSpeed
  */
  public float getBulletSpeed(){
    return projectileSpeed;
  }

  /**
  * Returns the proportion of the maximum health left of the boat
  * @return health/maxhealth
  */
  public float health(){
    return health/maxHealth;
  }

  /**
  * Returns whether or not the boat has been destroyed
  * @return If the health of the boat is less than or equal to 0, return true
  */
  public Boolean isDestroyed(){
    if (health<=0)
      return true;
    else
      return false;
  }
}
