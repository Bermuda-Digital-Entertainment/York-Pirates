package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

import com.yorkpirates.game.College;
import com.yorkpirates.game.Projectile;

/**
* Boat Class. Implements a sprite with additional Properties for Health and boat Level. It can be an AI boat or a player
*/
public class Boat extends Sprite {
  private Float x;
  private Float y;
  public Texture texture;
  public Integer health;
  public Integer maxHealth;
  public Integer level;
  public Integer speed;
  public Float firingRate;
  public Float projectileDamage;
  public Float projectileSpeed;
  public Integer lastDirectionMoved; //0 is up, 1 is right, 2 is down, 3 is left
	private float lastShotTime;

  public Boat(){
    lastShotTime=0;
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

  public Boolean isHit(ArrayList<Projectile> collisionProjectile) {
    Boolean hit = false;
    for (Integer i=0; x<collisionProjectile.size(); i++) {
      if (getBoundingRectangle().overlaps(collisionProjectile.get(i).getBoundingRectangle())){
        hit = true;
        health -= collisionProjectile.get(i).damage;
      }
    }
    return hit;
  }

  public Projectile fire() {
    Projectile cannonBall;
    if (lastShotTime<=2){
      switch (lastDirectionMoved) {
        case 0:
          cannonBall = new Projectile(0f,10f,50f,this);
          break;
        case 1:
          cannonBall = new Projectile(10f,0f,50f,this);
          break;
        case 2:
          cannonBall = new Projectile(0f,-10f,50f,this);
          break;
        default:
          cannonBall = new Projectile(-10f,0f,50f,this);
          break;
      }
    }
    return cannonBall;
  }
}
