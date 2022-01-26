package com.yorkpirates.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.yorkpirates.game.Projectile;

;
public class YorkPirates extends ApplicationAdapter {
	private OrthographicCamera camera;
	private OrthographicCamera camera2;
	private SpriteBatch batch;
	private Boat player;
	private ArrayList<Boat> ships;
	private ArrayList<College> colleges;
	private College halifax;
	private College james;
	private College goodricke;
	private Stage stage;
	private Skin skin;
	private Image background;
	private ScreenViewport viewport;


	@Override
	public void create() {
		//batch camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//stage camera
		camera2 = new OrthographicCamera();
		camera2.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		//ui skin
		Skin skin = new Skin(Gdx.files.internal("skin.json"));
		colleges = new ArrayList<College>();
		ships = new ArrayList<Boat>();
		batch = new SpriteBatch();
		viewport = new ScreenViewport();
		viewport.setCamera(camera2);
		stage = new Stage(viewport);
		//table for UI background
		Table background = new Table(skin);
		background.setFillParent(true);
		background.setBackground(skin.getDrawable("default-pane"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		//table for UI buttons
		Table table = new Table(skin);
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.top);
		table.setPosition(viewport.getLeftGutterWidth(), viewport.getScreenHeight()/2);


        Button buttonA = new TextButton("LEFT", skin);
		buttonA.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//System.out.println("TAP ME");
				stage.dispose();
			}
		});
        Button buttonB = new TextButton("RIGHT", skin);
		buttonB.setHeight(300);
		buttonB.setWidth(600);

		stage.addActor(background);
		table.add(buttonA).align(Align.center).size(Value.percentWidth(.4f, table), Value.percentWidth(.04f, table)).growX();
		table.row();
		table.add(buttonB);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);

		//Creates the player's boat
		player = new Boat();
		player.setPosition(1000,1000);
		player.setSize(48,48);
		player.texture = new Texture(Gdx.files.internal("boat.png"));

		//Creates each college
		colleges.add(new College());
		colleges.add(new College());
		colleges.add(new College());

		colleges.get(0).setPosition(400,500);
		colleges.get(0).setSize(64,64);
		colleges.get(0).texture = new Texture(Gdx.files.internal("halifax.png"));

		colleges.get(1).setPosition(300,800);
		colleges.get(1).setSize(64,64);
		colleges.get(1).texture = new Texture(Gdx.files.internal("goodricke.png"));

		colleges.get(2).setPosition(700,100);
		colleges.get(2).setSize(64,64);
		colleges.get(2).texture = new Texture(Gdx.files.internal("james.png"));

	}


	@Override
	public void resize(int width, int height){
		camera.setToOrtho(false, width, height);
		camera2.setToOrtho(false, width, height);
		camera2.translate(300-width/2,100-height/2);
	}

	@Override
	public void render () {
		ScreenUtils.clear(.3f, .3f, 1, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
 		batch.begin();
 		batch.draw(player.texture, player.getX(), player.getY());
		for (Integer x=0; x<colleges.size(); x++) {
			 batch.draw(colleges.get(x).texture, colleges.get(x).getX(), colleges.get(x).getY());
		}
		for (Integer x=0; x<ships.size(); x++) {
			 batch.draw(ships.get(x).texture, ships.get(x).getX(), ships.get(x).getY());
		}
 		batch.end();
		stage.draw();
		stage.act();
		camera2.update();
		move(5);
	}
	//public void update(float deltaTime) {
	//	ScreenUtils.clear(0, 0, 0, 1);
	//}


	/**
	* Method that performs basic motion control for the player's boat and the camera.
	* @speed Provides a speed at which the boat moves
	*/
	protected void move(Integer speed){

		Boolean collision;

		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.translateX(-speed);
			if (player.collides(colleges,ships)) player.translateX(speed);
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player.translateX(speed);
			if (player.collides(colleges,ships)) player.translateX(-speed);
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			player.translateY(speed);
			if (player.collides(colleges,ships)) player.translateY(-speed);
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			player.translateY(-speed);
			if (player.collides(colleges,ships)) player.translateY(speed);
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT)) camera.translate(-1,0);
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) camera.translate(1,0);
		if(Gdx.input.isKeyPressed(Keys.UP)) camera.translate(0,1);
		if(Gdx.input.isKeyPressed(Keys.DOWN)) camera.translate(0,-1);
	}
}
