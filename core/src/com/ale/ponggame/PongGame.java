package com.ale.ponggame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PongGame extends Game {

	public ShapeRenderer shapeRenderer;
	public Stage stage;
	public BitmapFont font;
	SpriteBatch batch;
	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
