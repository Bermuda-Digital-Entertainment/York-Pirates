package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.yorkpirates.game.Boat;
import com.yorkpirates.game.College;
import com.yorkpirates.game.Hud;
import com.yorkpirates.game.Bullet;

;
public class YorkPirates extends ApplicationAdapter {
	private OrthographicCamera camera;
	private OrthographicCamera camera2;
	private SpriteBatch batch;
	private SpriteBatch batchUi;
	private Boat player;
	private ArrayList<Boat> ships;
	private ArrayList<College> colleges;
	private ArrayList<Bullet> bullets;
	private Hud stage;
	private BitmapFont font;
	private String score;
	private String gold;
	private Texture blank;
	private int topID = 4;

	@Override
	public void create() {
		//batch camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//UI overlay camera
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		colleges = new ArrayList<College>();
		ships = new ArrayList<Boat>();
		bullets = new ArrayList<Bullet>();
		batch = new SpriteBatch();
		//overlay batch, font, score, gold and contructors
		batchUi = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("score.fnt"));
		score = "69420";
		gold = "420";
		blank = new Texture("blank.png");
		//ui call
		stage = new Hud(new Stage());
		Gdx.input.setInputProcessor(stage.getStage());

		//Creates the player's boat
		player = new Boat(0);
		player.setPosition(100,100);
		player.setSize(48,20);
		player.lastDirectionMoved=3;
		player.texture = new Texture(Gdx.files.internal("pirate_ship_up.png"));

		//Creates each college
		colleges.add(new College(1));
		colleges.add(new College(2));
		colleges.add(new College(3));

		colleges.get(0).setPosition(400,500);
		colleges.get(0).setSize(64,64);
		colleges.get(0).texture = new Texture(Gdx.files.internal("derwent.png"));

		colleges.get(1).setPosition(300,800);
		colleges.get(1).setSize(64,64);
		colleges.get(1).texture = new Texture(Gdx.files.internal("goodricke.png"));

		colleges.get(2).setPosition(700,100);
		colleges.get(2).setSize(64,64);
		colleges.get(2).texture = new Texture(Gdx.files.internal("james.png"));
	}


	@Override
	public void resize(int width, int height){

		//check if updating the stage for hud is neededd
		//Checks if the start menu still exists.
		if (stage.isStage == true){
			stage.getStage().getViewport().update(width, height, true);
		}
		else{
			camera.setToOrtho(false, width, height);
			camera2.setToOrtho(false, width, height);
			camera.translate(player.getX()-(Gdx.graphics.getWidth()/2), player.getY()-(Gdx.graphics.getHeight()/2));
		}


	}

	@Override
	public void render () {
		float delta;
		delta = Gdx.graphics.getDeltaTime();
		//Add time from last shot
		player.addTime(delta);
		//fire bullet
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && player.canFire()){
			bullets.add(player.fire());
		}
		//update bullet
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets){
			bullet.update(delta);
			player.isHit(bullet);
			for (College college : colleges){
				college.isHit(bullet);
			}
			if (bullet.remove)
				bulletsToRemove.add(bullet);
		}
		//remove bullets
		bullets.removeAll(bulletsToRemove);

		ScreenUtils.clear(.3f, .3f, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
 		batch.begin();
		for (Bullet bullet : bullets) {
			bullet.render(batch);
		}
		if (player.lastDirectionMoved == 0 || player.lastDirectionMoved ==2)
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 37, 80);
		if (player.lastDirectionMoved == 1 || player.lastDirectionMoved ==3)
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 80, 37);
		for (Integer x=0; x<colleges.size(); x++) {
			 batch.draw(colleges.get(x).texture, colleges.get(x).getX(), colleges.get(x).getY());
		}
		for (Integer x=0; x<ships.size(); x++) {
			 batch.draw(ships.get(x).texture, ships.get(x).getX(), ships.get(x).getY());
		}
 		batch.end();
		//draw ui overlay
		camera2.update();
		batchUi.begin();
		batchUi.setProjectionMatrix(camera2.combined);
		//draw points
		font.setColor(Color.WHITE);
		font.draw(batchUi, score, camera2.viewportWidth - 50 -(score.length()*32), camera2.viewportHeight-80);
		//draw gold
		font.setColor(Color.GOLD);
		font.draw(batchUi, gold, camera2.viewportWidth - 50 -(gold.length()*32), camera2.viewportHeight-30);
		//draw health
		batchUi.setColor(Color.BLACK);
		batchUi.draw(blank, 0, 0, camera.viewportWidth, 8);
		if (player.health() > 0.6)
			batchUi.setColor(Color.GREEN);
		else if (player.health() > 0.3)
			batchUi.setColor(Color.ORANGE);
		else
			batchUi.setColor(Color.RED);
		batchUi.draw(blank, 0, 0, player.health()*camera.viewportWidth, 12);
		batchUi.setColor(Color.WHITE);
		batchUi.end();
		//draw the hud - welcome screen
		stage.getStage().act();
		stage.getStage().draw();

		move(50*delta);
	}


	/**
	* Method that performs basic motion control for the player's boat and the camera.
	* @speed Provides a speed at which the boat moves
	*/
	protected void move(float speed){
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.translateX(-speed);
			player.lastDirectionMoved=3;
			if (player.collides(colleges,ships)) player.translateX(speed);
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player.translateX(speed);
			player.lastDirectionMoved=1;
			if (player.collides(colleges,ships)) player.translateX(-speed);
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			player.translateY(speed);
			player.lastDirectionMoved=0;
			if (player.collides(colleges,ships)) player.translateY(-speed);
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			player.translateY(-speed);
			player.lastDirectionMoved=2;
			if (player.collides(colleges,ships)) player.translateY(speed);
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT))camera.translate(-1,0);
		if(Gdx.input.isKeyPressed(Keys.RIGHT))camera.translate(1,0);
		if(Gdx.input.isKeyPressed(Keys.UP))camera.translate(0,1);
		if(Gdx.input.isKeyPressed(Keys.DOWN)) camera.translate(0,-1);
	}

}
