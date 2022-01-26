package com.yorkpirates.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.yorkpirates.game.Boat;
import com.yorkpirates.game.College;
import com.yorkpirates.game.Projectile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

;
public class YorkPirates extends ApplicationAdapter {
	private OrthographicCamera camera;
	private OrthographicCamera camera2;
	private SpriteBatch batch;
	private Boat player;
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
	public void resize(int width, int height){
		camera.setToOrtho(false, width, height);
		camera2.setToOrtho(false, width, height);
		camera2.update();
		stage.draw();
		stage.act();

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
		stage.draw();
		stage.act();
		camera2.update();
		move();
	}
	//public void update(float deltaTime) {
	//	ScreenUtils.clear(0, 0, 0, 1);
	//}
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
