package com.yorkpirates.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class Boat extends Rectangle {
  public Texture texture;
  public Health Integer;
  public Level Integer;

  public void move_left(){
    x--;
  }
  public void move_right(){
    x++;
  }
  public void move_up(){
    y++;
  }
  public void move_down(){
    y--;
  }
}
