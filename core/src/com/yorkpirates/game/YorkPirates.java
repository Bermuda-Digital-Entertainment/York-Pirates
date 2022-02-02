package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.scenes.scene2d.Stage;


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

	public static Integer score;
	private Integer gold;
	private Texture blank;
	private GameOver gameOverScreen;
	private Victory victoryScreen;
	private Texture waterBackground;
	private Texture pauseScreen;
	public static Boolean paused = true;

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
		//Initialize lists for Colleges, Boats and Bullets
		colleges = new ArrayList<College>();
		ships = new ArrayList<Boat>();
		bullets = new ArrayList<Bullet>();
		//Initialize the game batch
		batch = new SpriteBatch();
		//Load Pause screen and background textures
		pauseScreen = new Texture(Gdx.files.internal("pause_screen.png"));
		waterBackground = new Texture(Gdx.files.internal("water_background.png"));
		waterBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		TextureRegion waterBackgroundRegion = new TextureRegion(waterBackground);
		waterBackgroundRegion.setRegion(0,0,waterBackground.getWidth()*5,waterBackgroundRegion.getRegionHeight()*5);
		//Initialize overlay batch for score, gold and objectives
		batchUi = new SpriteBatch();
		score = 0;
		gold = 0;
		blank = new Texture("blank.png");
		//Initialize the UI Stages for Main Menu, Death and Victory
		stage = new Hud(new Stage());
		gameOverScreen = new GameOver(new Stage());
		victoryScreen = new Victory(new Stage());
		Gdx.input.setInputProcessor(stage.getStage());

		//Creates the player's boat and moves game camera to his position
		player = new Boat(0);
		player.setPosition(100,100);
		player.setSize(30,144);
		player.lastDirectionMoved=3;
		player.texture = new Texture(Gdx.files.internal("pirate_ship_up.png"));
		camera.translate(player.getX()-(Gdx.graphics.getWidth()/2), player.getY()-(Gdx.graphics.getHeight()/2));

		//Creates colleges
		colleges.add(new College(1));
		colleges.add(new College(2));
		colleges.add(new College(3));

		colleges.get(0).setPosition(-800,500);
		colleges.get(0).setSize(68,128);
		colleges.get(0).texture = new Texture(Gdx.files.internal("derwent.png"));

		colleges.get(1).setPosition(800,500);
		colleges.get(1).setSize(68,128);
		colleges.get(1).texture = new Texture(Gdx.files.internal("goodricke.png"));

		colleges.get(2).setPosition(100,-500);
		colleges.get(2).setSize(68,128);
		colleges.get(2).texture = new Texture(Gdx.files.internal("james.png"));


		//Creates some idle enemy ships
		ships.add(new Boat(4));
		ships.add(new Boat(5));
		ships.add(new Boat(6));

		ships.get(0);
		ships.get(0).setPosition(300,250);
		ships.get(0).setSize(20,48);
		ships.get(0).texture = new Texture(Gdx.files.internal("derwent_ship.png"));

		ships.get(1);
		ships.get(1).setPosition(-250,350);
		ships.get(1).setSize(20,48);
		ships.get(1).texture = new Texture(Gdx.files.internal("goodricke_ship.png"));

		ships.get(2);
		ships.get(2).setPosition(-100,-300);
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

		//Check which camera needs to be updated upon window resizing.
		if (stage.isStage == true){
			stage.getStage().getViewport().update(width, height, true);
		}
		else if (gameOverScreen.isStage == true){
			gameOverScreen.getStage().getViewport().update(width, height, true);
		}
		else if (victoryScreen.isStage == true){
			victoryScreen.getStage().getViewport().update(width, height, true);
		}
		else if (gameOverScreen.isStage == false && stage.isStage == false && victoryScreen.isStage == false){
			camera.setToOrtho(false, width, height);
			camera2.setToOrtho(false, width, height);
			camera.translate(player.getX()-(Gdx.graphics.getWidth()/2), player.getY()-(Gdx.graphics.getHeight()/2));
		}


	}


	/**
	* Method that is called at each render. Renders the main Menu and Pause screens as well as checks the "paused" state
	Calls the update method if game is not paused.
	*/
	@Override
	public void render () {

		//Generate the font and set the size and border colour/width.
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ObjectiveFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 22;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 3;
		BitmapFont ObjectiveFont = generator.generateFont(parameter);

		//Check whether the game is currently paused and draw the pause screen if so
		//If not paused, call the main screen update method.
		if (paused == true){
			camera2.update();
			batchUi.begin();
			batchUi.setProjectionMatrix(camera2.combined);
			batchUi.draw(pauseScreen, 0, 25, camera2.viewportWidth, camera2.viewportHeight-50);
			ObjectiveFont.setColor(Color.RED);
			ObjectiveFont.draw(batchUi, "Press 'R' to resume the game", camera2.viewportWidth/2 - 165, camera2.viewportHeight-3);
			batchUi.end();
			if(Gdx.input.isKeyPressed(Keys.R)){
				paused = false;
			}
		}
		else {
			generalUpdate();
		}
		//Dispose of font generator to avoid memory leaks
		generator.dispose();
		//Draw the Main Menu screen
		stage.getStage().act();
		stage.getStage().draw();

	}

	/**
	* Method that performs the majority of the live gameplay and calls most of the other parts of the code
	*/
	public void generalUpdate(){
		float delta;
		delta = Gdx.graphics.getDeltaTime();

		//Generate the font again
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ObjectiveFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 3;
		parameter.size = 26;
		BitmapFont ObjectiveFont = generator.generateFont(parameter);
		//Add time from last shot
		player.addTime(delta);
		//Fire bullets
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && player.canFire()){
			bullets.add(player.fire());
		}
		//Update bullets and check for hits on players and colleges
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
	
		//Remove bullets that either hit or reached the range limit
		bullets.removeAll(bulletsToRemove);

		//Initialize game screen and draw the wave background
		ScreenUtils.clear(.3f, .3f, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
 		batch.begin();
		batch.draw(waterBackground, -2500, -2500, 5000, 5000);
		//Draw bullets
		for (Bullet bullet : bullets) {
			bullet.render(batch);
		}
		//Draw the player sprite and change hitbox depending on orientation
		if (player.lastDirectionMoved == 0 || player.lastDirectionMoved ==2){
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 60, 110);
			player.setSize(30, 110);
		}
		else if (player.lastDirectionMoved == 1 || player.lastDirectionMoved ==3){
 			batch.draw(player.getTexture(), player.getX(), player.getY(), 110, 60);
			player.setSize(110, 30);
		}

		//Draw the colleges
		for (Integer x=0; x<colleges.size(); x++) {
			 batch.draw(colleges.get(x).texture, colleges.get(x).getX(), colleges.get(x).getY());
		}
		//Draw the enemy ships
		for (Integer x=0; x<ships.size(); x++) {
			 batch.draw(ships.get(x).texture, ships.get(x).getX(), ships.get(x).getY(), 37, 80);
		}
		//Draw college health bars
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
			//Check whether a college has been destroyed
			else if (college.isDestroyed() && college.markedDestroyed==0){
				batch.draw(blank, college.getX(), college.getY()+college.getHeight()+5, college.health()*0, 8);
				score += 200;
				gold += 100;
				college.markedDestroyed=1;
				college.texture = new Texture(Gdx.files.internal("destroyed.png"));
			}
		//Reset the batch color
		batch.setColor(Color.WHITE);
		}
 		batch.end();
		//Initialize the UI batch
		camera2.update();
		batchUi.begin();
		batchUi.setProjectionMatrix(camera2.combined);
		//Draw points
		parameter.size = 26;
		ObjectiveFont.setColor(Color.WHITE);
		ObjectiveFont.draw(batchUi, "SCORE: " + score.toString(), camera2.viewportWidth - 50 -(("SCORE: "+score.toString()).length()*16), camera2.viewportHeight-80);
		//Draw gold
		ObjectiveFont.setColor(Color.GOLD);
		ObjectiveFont.draw(batchUi, "GOLD: " + gold.toString(), camera2.viewportWidth - 50 -(("GOLD: " + gold.toString()).length()*16), camera2.viewportHeight-30);

		//Draw the Objectives and check whether they have been completed
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
		//Draw player health background
		batchUi.setColor(Color.BLACK);
		batchUi.draw(blank, 0, 0, camera.viewportWidth, 12);
		//Draw player health bar and check for player death
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
		//Check whether the hidden "Boss" College has been unlocked
		if (colleges.get(0).isDestroyed() && colleges.get(1).isDestroyed() && colleges.get(2).isDestroyed() && colleges.size() == 3){
			colleges.add(new Boss(4));
			colleges.get(3).setPosition(100, 100);
			colleges.get(3).setSize(68,128);
			colleges.get(3).texture = new Texture(Gdx.files.internal("boss.png"));
		}
		//Draw the Boss College if all other colleges have been destroyed.
		//Draw victory screen if Boss College is destroyed
		else if (colleges.get(0).isDestroyed() && colleges.get(1).isDestroyed() && colleges.get(2).isDestroyed() && colleges.size() == 4){
			if (colleges.get(3).isDestroyed()){
				victoryScreen.getStage().act();
				victoryScreen.getStage().draw();
				Gdx.input.setInputProcessor(victoryScreen.getStage());
				victoryScreen.isStage = true;
			}
			else {
				ObjectiveFont.setColor(Color.RED);
				ObjectiveFont.draw(batchUi, "Destroy the BOSS College", 5, camera.viewportHeight - 120);
			}
		}
		//Reset batchUi tint and end batch
		batchUi.setColor(Color.WHITE);
		batchUi.end();
		generator.dispose();
		
		//Call the movement function
		move(50*delta);
		//Check if player has paused the game
		if(Gdx.input.isKeyPressed(Keys.P)){
			paused = true;
		}
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
	/**
	* Method that performs allows other classes to pause/unpause  the game
	* @param bool Sets the 'paused' value to either true or false.
	*/
	public static void setPause(Boolean bool){
		paused = bool;

	}
	/**
	* Method that fetches player's score
	* @return the player score
	*/
	public static Integer getScore(){
		return score;
	}
}
