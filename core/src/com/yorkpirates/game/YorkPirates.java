package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.XmlReader.Element;
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
	private Integer score;
	private Integer gold;
	private Texture blank;
	private GameOver gameOverScreen;
	private Texture waterBackground;
	private int topID=7;

	/**
	* The initialisation method for the game. This creates most of the game objects (except bullets/cannon balls which are created during gameplay)
	*/
	@Override
	public void create() {
		//batch camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//UI overlay camera
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		colleges = new ArrayList<College>();
		ships = new ArrayList<Boat>();
		bullets = new ArrayList<Bullet>();
		batch = new SpriteBatch();
		waterBackground = new Texture(Gdx.files.internal("water_background.png"));
		waterBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		TextureRegion waterBackgroundRegion = new TextureRegion(waterBackground);
		waterBackgroundRegion.setRegion(0,0,waterBackground.getWidth()*5,waterBackgroundRegion.getRegionHeight()*5);
		//overlay batch, font, score, gold and contructors
		batchUi = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("score.fnt"));
		score = 0;
		gold = 0;
		blank = new Texture("blank.png");
		//ui call
		stage = new Hud(new Stage());
		gameOverScreen = new GameOver(new Stage());

		Gdx.input.setInputProcessor(stage.getStage());
		//Creates the player's boat
		player = new Boat(0);
		player.setPosition(100,100);
		player.setSize(30,144);
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

		colleges.get(2).setPosition(700,1500);
		colleges.get(2).setSize(64,64);
		colleges.get(2).texture = new Texture(Gdx.files.internal("james.png"));

		//Creates some idle ships
		ships.add(new Boat(4));
		ships.add(new Boat(5));
		ships.add(new Boat(6));

		ships.get(0);
		ships.get(0).setPosition(300,250);
		ships.get(0).setSize(20,48);
		ships.get(0).texture = new Texture(Gdx.files.internal("derwent_ship.png"));

		ships.get(1);
		ships.get(1).setPosition(950,650);
		ships.get(1).setSize(20,48);
		ships.get(1).texture = new Texture(Gdx.files.internal("goodricke_ship.png"));

		ships.get(2);
		ships.get(2).setPosition(480,600);
		ships.get(2).setSize(20,48);
		ships.get(2).texture = new Texture(Gdx.files.internal("james_ship.png"));
	}


	/**
	* Method that resizes the cameras if the window is resized
	* @param width The new width of the window
	* @param height The new height of the window
	*/
	@Override
	public void resize(int width, int height){

		//check if updating the stage for hud is neededd
		//Checks if the start menu still exists.
		if (stage.isStage == true){
			stage.getStage().getViewport().update(width, height, true);
		}
		else if (gameOverScreen.isStage == true){
			gameOverScreen.getStage().getViewport().update(width, height, true);
		}
		else if (gameOverScreen.isStage == false && stage.isStage == false){
			camera.setToOrtho(false, width, height);
			camera2.setToOrtho(false, width, height);
			camera.translate(player.getX()-(Gdx.graphics.getWidth()/2), player.getY()-(Gdx.graphics.getHeight()/2));
		}


	}


	/**
	* Method that performs the majority of the live gameplay and calls most of the other parts of the code
	*/
	@Override
	public void render () {
		float delta;
		delta = Gdx.graphics.getDeltaTime();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ObjectiveFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 26;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 3;
		BitmapFont ObjectiveFont = generator.generateFont(parameter);
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
			try{
				if (bullet.getFiringObject() instanceof Boat){
					bullet.texture = new Texture(Gdx.files.internal("cannon_ball.png"));
				}
				else if (bullet.getFiringObjectC() instanceof College){
					bullet.texture = new Texture(Gdx.files.internal("fire_ball.png"));
				}
			}
			finally{}
			
		
		
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
		batch.draw(waterBackground, -1000, -1000, 5000, 5000);
		for (Bullet bullet : bullets) {
			bullet.render(batch);
		}
		if (player.lastDirectionMoved == 0 || player.lastDirectionMoved ==2){
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 60, 144);
			player.setSize(30, 144);
		}
		else if (player.lastDirectionMoved == 1 || player.lastDirectionMoved ==3){
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 144, 60);
			player.setSize(144, 30);
		}
		for (Integer x=0; x<colleges.size(); x++) {
			 batch.draw(colleges.get(x).texture, colleges.get(x).getX(), colleges.get(x).getY());
		}
		for (Integer x=0; x<ships.size(); x++) {
			 batch.draw(ships.get(x).texture, ships.get(x).getX(), ships.get(x).getY(), 37, 80);
		}
		// draw college health
		for (College college : colleges){
			college.addTime(delta);
			if (college.canFire(player.getX(),player.getY())){
				bullets.add(college.fire(player.getX(),player.getY()));
			}
			if (college.health() > 0.6){
				batch.setColor(Color.GREEN);
				batch.draw(blank, college.getX(), college.getY()+college.getHeight()+5, college.health()*college.getWidth(), 8);
			}
			else if (college.health() > 0.3){
				batch.setColor(Color.ORANGE);
				batch.draw(blank, college.getX(), college.getY()+college.getHeight()+5, college.health()*college.getWidth(), 8);
			}
			else if (college.health() < 0.3 && college.health() > 0){
				batch.setColor(Color.RED);
				batch.draw(blank, college.getX(), college.getY()+college.getHeight()+5, college.health()*college.getWidth(), 8);
			}
			else if (college.isDestroyed() && college.markedDestroyed==0){
				batch.draw(blank, college.getX(), college.getY()+college.getHeight()+5, college.health()*0, 8);
				score += 200;
				gold += 100;
				college.markedDestroyed=1;
				college.texture = new Texture(Gdx.files.internal("destroyed.png"));
			}

		batch.setColor(Color.WHITE);
		}
 		batch.end();
		//draw ui overlay
		camera2.update();
		batchUi.begin();
		batchUi.setProjectionMatrix(camera2.combined);
		//draw points
		font.setColor(Color.WHITE);
		font.draw(batchUi, score.toString(), camera2.viewportWidth - 50 -(score.toString().length()*32), camera2.viewportHeight-80);
		//draw gold
		font.setColor(Color.GOLD);
		font.draw(batchUi, gold.toString(), camera2.viewportWidth - 50 -(gold.toString().length()*32), camera2.viewportHeight-30);
		//draw health
		batchUi.setColor(Color.BLACK);
		batchUi.draw(blank, 0, 0, camera.viewportWidth, 12);
		ObjectiveFont.draw(batchUi, "OBJECTIVES:", 5, camera.viewportHeight - 30);
		parameter.size = 15;
		ObjectiveFont = generator.generateFont(parameter);
		if (colleges.get(0).isDestroyed()){
			ObjectiveFont.setColor(Color.GREEN);
			ObjectiveFont.draw(batchUi, "Destroy Derwent College: 1/1", 5, camera.viewportHeight - 60);
		}
		else {
			ObjectiveFont.setColor(Color.RED);
			ObjectiveFont.draw(batchUi, "Destroy Derwent College: 0/1", 5, camera.viewportHeight - 60);
		}
		if (colleges.get(1).isDestroyed()){
			ObjectiveFont.setColor(Color.GREEN);
			ObjectiveFont.draw(batchUi, "Destroy Goodricke College: 1/1", 5, camera.viewportHeight - 80);
		}
		else {
			ObjectiveFont.setColor(Color.RED);
			ObjectiveFont.draw(batchUi, "Destroy Goodricke College: 0/1", 5, camera.viewportHeight - 80);
		}
		if (colleges.get(2).isDestroyed()){
			ObjectiveFont.setColor(Color.GREEN);
			ObjectiveFont.draw(batchUi, "Destroy James College: 1/1", 5, camera.viewportHeight - 100);
		}
		else {
			ObjectiveFont.setColor(Color.RED);
			ObjectiveFont.draw(batchUi, "Destroy James College: 0/1", 5, camera.viewportHeight - 100);
		}
		if (player.health() > 0.6)
			batchUi.setColor(Color.GREEN);
		else if (player.health() > 0.3)
			batchUi.setColor(Color.ORANGE);
		else if (player.health() < 0.3 && player.health() > 0)
			batchUi.setColor(Color.RED);
		else if (player.health() <= 0){
			gameOverScreen.getStage().act();
			gameOverScreen.getStage().draw();
			Gdx.input.setInputProcessor(gameOverScreen.getStage());
			gameOverScreen.isStage = true;
		}

		batchUi.draw(blank, 0, 0, player.health()*camera.viewportWidth, 12);
		batchUi.setColor(Color.WHITE);
		batchUi.end();
		//draw the hud - welcome screen
		stage.getStage().act();
		stage.getStage().draw();
		generator.dispose();

		move(50*delta);
	}


	/**
	* Method that performs basic motion control for the player's boat and the camera.
	* @param speed Provides a speed at which the boat moves
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
