package com.yorkpirates.game;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
* Hud class. Creates the Main menu screen and initializes it
*/
public class Hud extends Stage{

    private Stage stage;
    private FitViewport uiViewport;
    private Skin skin;
	private Camera cameraHud;
	private Image bermuda;
	private Image backgroundImage;
	public Boolean isStage;

  /**
  * Constructor method for Hud. It creates the whole Main Menu stage with its actors
  * @param stage Stage to be modified into the Menu Screen 
  */
    public Hud(Stage hud) {

		//Check for screen dimensions
		final float GAME_WORLD_WIDTH = Gdx.graphics.getWidth();
		final float GAME_WORLD_HEIGHT = Gdx.graphics.getHeight();
		isStage = true;
		//Set up the camera and FitViewport for the Stage.
		cameraHud = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraHud.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    	uiViewport = new FitViewport(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, cameraHud);
    	stage = new Stage(uiViewport);

		//Load textures and skin
		bermuda = new Image(new Texture(Gdx.files.internal("Bermuda_Entertainment.png")));
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

		//Labels for the title and instructions
		Label titleLabel = new Label("York Pirates",skin);
		Label pauseLabel = new Label("Press 'P' in game for controls and help", skin);
    	
		//Start button functionality. Turns isStage to false so its camera is no longer updated upon resize
		Button StartButton = new TextButton("Start Game", skin);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				isStage = false;
				Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth()-10, Gdx.graphics.getHeight()-10);
				YorkPirates.setPause(false);
				
			}
		});

		//Exit button functionality. Closes the application
		Button ExitButton = new TextButton("Exit Game", skin);
		ExitButton.addListener(new ClickListener()
		{
	       @Override
		     public void clicked(InputEvent event, float x, float y) {
		         System.exit(0);
			}
		});

		//Table build
		background.add(backgroundImage).size(Value.percentWidth(1.0f, background), Value.percentWidth(1.0f, background)).growX().padBottom(20);
		stage.addActor(background);
		table.pad(20);
		table.add(bermuda).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.14f, table)).growX().padBottom(20);
		table.row();
		table.add(titleLabel).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(StartButton).align(Align.center).size(Value.percentWidth(.11f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(ExitButton).align(Align.center).size(Value.percentWidth(.11f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(pauseLabel).align(Align.center).size(Value.percentWidth(.27f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		stage.addActor(table);



    }
	/**
	* Method that returns the build Main Menu Screen
	* @return the built stage
	*/
    public Stage getStage() { return stage; }

	/**
	* Method that disposes of the Menu Screen so it is no longer rendered
	*/
    public void dispose(){
        stage.dispose();
    }
}
