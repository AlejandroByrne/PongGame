package com.ale.ponggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private int xPos = 0;
	private int yPos = 0;
	private final int movementSpeed = 4;
	SpriteBatch batch;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) { // forward
			yPos += movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) { // left
			xPos -= movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) { // backwards
			yPos -= movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) { // right
			xPos += movementSpeed;
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 1, 1);
		shapeRenderer.rect(xPos, yPos, 100, 100);
		shapeRenderer.end();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
