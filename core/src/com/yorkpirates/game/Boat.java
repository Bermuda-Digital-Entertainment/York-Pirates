package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


/**
* Boat Class. Implements a sprite with additional Properties for Health and boat Level. It can be an AI boat or a player
*/
public class Boat extends Sprite {
  private Float x;
  private Float y;
  public Texture texture;
  public float health;
  public Integer maxHealth;
  public Integer level;
  public float speed = 50;
  public Float firingRate;
  public Float projectileDamage;
  public Float projectileSpeed;
  public Integer lastDirectionMoved; //0 is up, 1 is right, 2 is down, 3 is left
	public float lastShotTime;

  public Boat(){
    lastShotTime=6;
    health = 1.0f;
  }

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

  public Boolean isHit(Bullet collisionProjectile) {
    Rectangle testRectangle = new Rectangle(collisionProjectile.x,collisionProjectile.y,16,16);
    Boolean hit = false;
    if (getBoundingRectangle().overlaps(testRectangle) && collisionProjectile.firingObject != this){
      collisionProjectile.remove=true;
      hit = true;
      health -= collisionProjectile.damage;
    }
    return hit;
  }

  public Bullet fire() {
    Bullet cannonBall;
    switch (lastDirectionMoved) {
      case 0:
        cannonBall = new Bullet(0,50,500,this);
        break;
      case 1:
        cannonBall = new Bullet(50,0,500,this);
        break;
      case 2:
        cannonBall = new Bullet(0,-50,500,this);
        break;
      default:
        cannonBall = new Bullet(-50,0,500,this);
        break;
    }
    resetFire();
    return cannonBall;
  }
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

  public Boolean canFire(){
    return (lastShotTime > 1);
  }

  public void addTime(float time){
    lastShotTime+=time;
  }

  public void resetFire(){
    lastShotTime=0;
  }
  public float getBulletSpeed(){
    return speed;
  }
}
