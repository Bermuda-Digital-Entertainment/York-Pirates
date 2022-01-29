package com.yorkpirates.game;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud extends Stage{

    private Stage stage;
    private FitViewport uiViewport;
    private Skin skin;
	  private Camera cameraHud;
	  public Boolean isStage;

    public Hud(Stage hud) {

		final float GAME_WORLD_WIDTH = Gdx.graphics.getWidth();
		final float GAME_WORLD_HEIGHT = Gdx.graphics.getHeight();
		isStage = true;
		cameraHud = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraHud.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    	uiViewport = new FitViewport(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, cameraHud);
    	stage = new Stage(uiViewport);
		//float aspectRatio = (GAME_WORLD_HEIGHT / GAME_WORLD_WIDTH); ... maybe needed later
    	skin = new Skin(Gdx.files.internal("skin.json"));

		//table for UI background
		Table background = new Table();
		background.setFillParent(true);
		background.setBackground(skin.getDrawable("default-pane"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		//table for UI buttons
		Table table = new Table(skin);
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.top);
		table.setPosition(uiViewport.getLeftGutterWidth(), uiViewport.getScreenHeight()/2);


    Button StartButton = new TextButton("Start Game", skin);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				isStage = false;
				Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth()-1, Gdx.graphics.getHeight()-1);
			}
		});
    Button HelpButton = new TextButton("Controls", skin);
		HelpButton.addListener(new ClickListener()
		{
		    @Override
		    public void clicked(InputEvent event, float x, float y) {

			}
		});
		Button ExitButton = new TextButton("Exit Game", skin);
		ExitButton.addListener(new ClickListener()
		{
	       @Override
		     public void clicked(InputEvent event, float x, float y) {
		         System.exit(0);
			}
		});

		stage.addActor(background);
		table.pad(150);
		table.add(StartButton).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(HelpButton).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX().padBottom(20);
		table.row();
		table.add(ExitButton).align(Align.center).size(Value.percentWidth(.09f, table), Value.percentWidth(.04f, table)).growX();
		stage.addActor(table);



    }

    public Stage getStage() { return stage; }
	public void translateCentre(){

	}
    public void dispose(){
        stage.dispose();
    }
}
