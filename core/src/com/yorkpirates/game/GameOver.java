package com.yorkpirates.game;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
* GameOver class. Creates the GameOver screen and initializes it
*/
public class GameOver extends Stage{

    private Stage stage;
    private FitViewport uiViewport;
    private Skin skin;
	private Camera cameraHud;
	private Image backgroundImage;
	public Boolean isStage;

	/**
	* Constructor method for GameOver Class. It creates the whole GameOver Stage with its actors
	* @param stage Stage to be modified into the Game Over Screen
	*/
    public GameOver(Stage hud) {

		//Check for screen dimensions
		final float GAME_WORLD_WIDTH = Gdx.graphics.getWidth();
		final float GAME_WORLD_HEIGHT = Gdx.graphics.getHeight();
		isStage = false;
		//Set up the camera and FitViewport for the Stage.
		cameraHud = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraHud.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    	uiViewport = new FitViewport(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, cameraHud);
    	stage = new Stage(uiViewport);
		
		//Load the background and skin
		backgroundImage = new Image(new Texture(Gdx.files.internal("background.png")));
    	skin = new Skin(Gdx.files.internal("uiskin.json"));

		//table for UI background
		Table background = new Table();
		background.setFillParent(true);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		//table for UI buttons
		Table table = new Table(skin);
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.top);
		table.setPosition(uiViewport.getLeftGutterWidth(), uiViewport.getScreenHeight()/2);

		//Label for the death message
		Label deathMessage = new Label("You have died!", skin);

		//Restart button functionality - opens new application and closes the old one
    	Button StartButton = new TextButton("Restart", skin);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					Runtime.getRuntime().exec("java -jar desktop-1.0.jar");
				} catch (IOException e) {
	
					e.printStackTrace();
				}
				System.exit(0);
			}
		});

		//Exit Button funcitonality - closes the application
		Button ExitButton = new TextButton("Exit Game", skin);
		ExitButton.addListener(new ClickListener()
		{
	       @Override
		     public void clicked(InputEvent event, float x, float y) {
		         System.exit(0);
			}
		});

		//Table build
		background.add(backgroundImage).size(Value.percentWidth(1.0f, background), Value.percentWidth(1.0f, background)).growX();
		stage.addActor(background);
		table.pad(150);
		table.add(deathMessage).align(Align.center).size(Value.percentWidth(.12f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(StartButton).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.row();
		table.add(ExitButton).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX();
		stage.addActor(table);



    }
	/**
	* Method that returns the build GameOver Screen
	* @return the built stage
	*/
    public Stage getStage() { return stage; }

	/**
	* Method that disposes of the GameOver Screen
	*/
    public void dispose(){
        stage.dispose();
    }
	
}
