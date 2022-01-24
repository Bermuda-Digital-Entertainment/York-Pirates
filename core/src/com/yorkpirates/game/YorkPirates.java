package com.yorkpirates.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.yorkpirates.game.Boat;
import com.yorkpirates.game.College;


public class YorkPirates extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Boat player;
	private College halifax;
	private College james;
	private College goodricke;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		batch = new SpriteBatch();

		//Creates the player's boat
		player = new Boat();
		player.setPosition(0,0);
		player.setSize(48,48);
		player.texture = new Texture(Gdx.files.internal("boat.png"));

		//Creates each college
		halifax = new College();
		halifax.setPosition(400,500);
		halifax.setSize(64,64);
		halifax.texture = new Texture(Gdx.files.internal("halifax.png"));

		james = new College();
		james.setPosition(300,800);
		james.setSize(64,64);
		james.texture = new Texture(Gdx.files.internal("james.png"));

		goodricke = new College();
		goodricke.setPosition(700,100);
		goodricke.setSize(64,64);
		goodricke.texture = new Texture(Gdx.files.internal("goodricke.png"));
	}

	@Override
	public void render () {
		ScreenUtils.clear(.3f, .3f, 1, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
 		batch.begin();
 		batch.draw(player.texture, player.getX(), player.getY());
		batch.draw(halifax.texture, halifax.getX(), halifax.getY());
		batch.draw(james.texture, james.getX(), james.getY());
		batch.draw(goodricke.texture, goodricke.getX(), goodricke.getY());
 		batch.end();
		move();
	}

	public void move(){
		if(Gdx.input.isKeyPressed(Keys.A)) player.translateX(-1);
		if(Gdx.input.isKeyPressed(Keys.D)) player.translateX(1);
		if(Gdx.input.isKeyPressed(Keys.W)) player.translateY(1);
		if(Gdx.input.isKeyPressed(Keys.S)) player.translateY(-1);

		if(Gdx.input.isKeyPressed(Keys.LEFT)) camera.translate(-1,0);
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) camera.translate(1,0);
		if(Gdx.input.isKeyPressed(Keys.UP)) camera.translate(0,1);
		if(Gdx.input.isKeyPressed(Keys.DOWN)) camera.translate(0,-1);
	}
}
