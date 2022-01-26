package com.yorkpirates.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public class Projectile extends Sprite {
  private Integer x;
  private Integer y;
  protected Integer speedX;//Velocity vector for x
  protected Integer speedY;//Velocity vector for y
  protected Integer range;
  protected Integer damage;
  protected Integer speed;
  protected Boat firingShip;

  public Projectile (Integer srcX, Integer srcY, Integer srcSpeedX, Integer srcSpeedY, Integer initRange, Boat ship) {
    x=srcX;
    y=srcY;
    speedX=srcSpeedX;
    speedY=srcSpeedY;
    range=initRange;
    firingShip=ship;
  }

  public void moveTick (){
    x+=speedX;
    y+=speedY;
  }
}
