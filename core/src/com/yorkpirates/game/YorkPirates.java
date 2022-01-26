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
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		Skin skin = new Skin(Gdx.files.internal("skin.json"));
		batch = new SpriteBatch();
		viewport = new ScreenViewport();
		viewport.setCamera(camera);
		stage = new Stage(viewport);
		//TextButton button = new TextButton("Tap Me", skin);
		//button.setSize(100, 50);
		//button.setPosition(1920/2 -100, 1080/2 -50);
		//button.addListener(new ClickListener()
		//{
		//	@Override
		//	public void clicked(InputEvent event, float x, float y) {
		//		//System.out.println("TAP ME");
		//		stage.clear();
		//	}
		//});

		//stage.addActor(button);
		Table background = new Table(skin);
		background.setFillParent(true);
		background.setBackground(skin.getDrawable("default-pane"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Container<Table> tableContainer = new Container<Table>();

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

        tableContainer.setSize(cw, ch);
        tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
        tableContainer.fillX();

		Table table = new Table(skin);

        Label topLabel = new Label("A LABEL", skin);
        topLabel.setAlignment(Align.center);
        Slider slider = new Slider(0, 100, 1, false, skin);
        Label anotherLabel = new Label("ANOTHER LABEL", skin);
        anotherLabel.setAlignment(Align.center);

        CheckBox checkBoxA = new CheckBox("Checkbox Left", skin);
        CheckBox checkBoxB = new CheckBox("Checkbox Center", skin);
        CheckBox checkBoxC = new CheckBox("Checkbox Right", skin);

        Table buttonTable = new Table(skin);

        TextButton buttonA = new TextButton("LEFT", skin);
		buttonA.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//System.out.println("TAP ME");
				stage.dispose();
			}
		});
        TextButton buttonB = new TextButton("RIGHT", skin);

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(slider).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(anotherLabel).fillX();
        table.row().expandX().fillX();

        table.add(checkBoxA).expandX().fillX();
        table.add(checkBoxB).expandX().fillX();
        table.add(checkBoxC).expandX().fillX();
        table.row().expandX().fillX();;

        table.add(buttonTable).colspan(3);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(buttonA).width(cw/3.0f);
        buttonTable.add(buttonB).width(cw/3.0f);

		stage.addActor(background);
        tableContainer.setActor(table);
        stage.addActor(tableContainer);
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
		move();
	}
	//public void update(float deltaTime) {
	//	(deltaTime);
	//	ScreenUtils.clear(0, 0, 0, 1);
	//}
	public void move(){
		if(Gdx.input.isKeyPressed(Keys.A)) player.translateX(-1);
		if(Gdx.input.isKeyPressed(Keys.D)) player.translateX(1);
		if(Gdx.input.isKeyPressed(Keys.W)) player.translateY(1);
		if(Gdx.input.isKeyPressed(Keys.S)) player.translateY(-1);
		
		collision = True;
		if(Gdx.input.isKeyPressed(Keys.LEFT)) camera.translate(-1,0);
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) camera.translate(1,0);
		if(Gdx.input.isKeyPressed(Keys.UP)) camera.translate(0,1);
		if(Gdx.input.isKeyPressed(Keys.DOWN)) camera.translate(0,-1);
	}
}
