package com.yorkpirates.game;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Controls extends Stage{

    private Stage stage;
    private FitViewport uiViewport;
    private Skin skin;
	private Camera cameraHud;
	public Boolean isStage;
	private Texture controlScreen;
    public ImageButton button;
    
    public Controls(Stage controls){

        final float GAME_WORLD_WIDTH = Gdx.graphics.getWidth();
		final float GAME_WORLD_HEIGHT = Gdx.graphics.getHeight();
		isStage = true;
		cameraHud = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraHud.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    	uiViewport = new FitViewport(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, cameraHud);
    	stage = new Stage(uiViewport);
		controlScreen = new Texture(Gdx.files.internal("background.png"));

        skin = new Skin(Gdx.files.internal("skin.json"));
        button = new ImageButton(skin);
        ImageButtonStyle style = button.getStyle();
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controlScreen));
        style.imageUp = drawable;
        style.imageDown = drawable;
        //stage.addActor(myButton);
		//float aspectRatio = (GAME_WORLD_HEIGHT / GAME_WORLD_WIDTH); ... maybe needed later
    	
    }

    public Stage getStage(){
        return stage;
    }


}
