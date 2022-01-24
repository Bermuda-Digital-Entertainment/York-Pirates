package com.yorkpirates.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

/**
* Projectile Class. Implements a sprite with additional Properties for speed, range and damage. It can be fired by AI boat or a player
*/
public class Projectile extends Sprite {
  private Integer x;
  private Integer y;
  protected Integer speedX;//Velocity vector for x
  protected Integer speedY;//Velocity vector for y
  protected Integer range;
  protected Integer damage;
  protected Integer speed;
  protected Sprite firing;

  /**
  * Projectile Class Constructor.
  *
  * @param srcX The source X coordinate for a projectile
  * @param srcY The source Y coordinate for a projectile
  * @param srcX The X speed vector for a projectile
  * @param srcY The Y speed vector for a projectile
  * @param initRange The number of 'ticks'
  */
  public Projectile (Integer srcSpeedX, Integer srcSpeedY, Integer initRange, Sprite firingObject) {
    speedX=srcSpeedX;
    speedY=srcSpeedY;
    range=initRange;
    firing=firingObject;
    x=firingObject.getX();
    x=firingObject.getY();
  }

  public void moveTick (){
    x+=speedX;
    y+=speedY;
  }
}
