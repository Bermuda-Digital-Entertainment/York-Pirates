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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Hud extends Stage{

    private Stage stage;
    private ScreenViewport viewport;
    private Skin skin;

    public Hud(Stage hud) {
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Skin skin = new Skin(Gdx.files.internal("skin.json"));

		//table for UI background
		Table background = new Table();
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
				dispose();
			}
		});
        Button buttonB = new TextButton("RIGHT", skin);

		stage.addActor(background);
		table.add(buttonA).align(Align.center).size(Value.percentWidth(.4f, table), Value.percentWidth(.04f, table)).growX();
		table.row();
		table.add(buttonB);
		stage.addActor(table);


		
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        stage.dispose();
    }
}