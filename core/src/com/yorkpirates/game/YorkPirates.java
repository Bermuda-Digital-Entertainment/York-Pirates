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


public class YorkPirates extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Boat player;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		batch = new SpriteBatch();

		player = new Boat();
		player.x = 0;
		player.y = 0;
		player.width = 48;
		player.height = 48;
		player.texture = new Texture(Gdx.files.internal("boat.png"));
	}

	@Override
	public void render () {
		ScreenUtils.clear(.3f, .3f, 1, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
 		batch.begin();
 		batch.draw(player.texture, player.x, player.y);
 		batch.end();
		if(Gdx.input.isKeyPressed(Keys.LEFT)) player.x --;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) player.x ++;
		if(Gdx.input.isKeyPressed(Keys.UP)) player.y ++;
		if(Gdx.input.isKeyPressed(Keys.DOWN)) player.y --;
	}
}
