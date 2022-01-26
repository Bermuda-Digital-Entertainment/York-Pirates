package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

import com.yorkpirates.game.College;

/**
* Boat Class. Implements a sprite with additional Properties for Health and boat Level. It can be an AI boat or a player
*/
public class Boat extends Sprite {
  public Texture texture;
  public Integer health;
  public Integer level;
  public Integer speed;

  public Boolean collidesCollege(ArrayList<College> collisionColleges){
    Boolean collisionExists = false;
    for (Integer x=0; x<collisionColleges.size(); x++) {
      if (getBoundingRectangle().overlaps(collisionColleges.get(x).getBoundingRectangle())) collisionExists=true;
    }
    return collisionExists;
  }
}
