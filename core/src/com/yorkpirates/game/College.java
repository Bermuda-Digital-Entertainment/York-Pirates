package com.yorkpirates.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
* College Class. Implements a sprite with additional Properties for Health.
*/
public class College extends Sprite {
  public Texture texture;
  public float health=5;

  public Boolean isHit(Bullet collisionProjectile) {
    Rectangle testRectangle = new Rectangle(collisionProjectile.x,collisionProjectile.y,16,16);
    Boolean hit = false;
    if (getBoundingRectangle().overlaps(testRectangle)){
      collisionProjectile.remove=true;
      hit = true;
      health -= collisionProjectile.damage;
    }
    return hit;
  }
}
