package com.ale.ponggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	private int yPosA = 0;
	private int yPosB = 0;
	private int xPosC = 400;
	private int yPosC = 240;
	private int xBallSpeed = 4;
	private int yBallSpeed = 2;
	private Rectangle playerOne;
	private Rectangle playerTwo;
	private Rectangle ball;
	private final int movementSpeed = 4;
	SpriteBatch batch;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		playerOne = new Rectangle(0, 0, 10, 100);
		playerTwo = new Rectangle(0, 0, 10, 100);
		ball = new Rectangle(0, 0, 20, 20);
	}

	@Override
	public void render () {

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		playerUpdate();
		ballUpdate();

		// Environment
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(400 - 20/2, 0, 20, 480);

		shapeRenderer.end();
		batch.end();

	}

	public void ballUpdate() {
		xPosC += xBallSpeed;
		yPosC += yBallSpeed;
		if(xPosC < 0 || xPosC > Gdx.graphics.getWidth()) {
			xBallSpeed *= -1;
		}
		if(yPosC < 0 || yPosC > Gdx.graphics.getHeight()) {
			yBallSpeed *= -1;
		}
		if(ball.overlaps(playerOne)) {
			xBallSpeed *= -1;
		}
		if(ball.overlaps(playerTwo)) {
			xBallSpeed *= -1;
		}

		// Update ball position
		ball.x = xPosC;
		ball.y = yPosC;
		// Draw ball Circle using ShapeRenderer
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.circle(ball.x, ball.y, ball.width);
		shapeRenderer.end();
	}

	public void playerUpdate() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) { // forward
			yPosA += movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) { // backwards
			yPosA -= movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) { // forward
			yPosB += movementSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // backwards
			yPosB -= movementSpeed;
		}

		// Update Rectangle Positions
		playerOne.y = yPosA;
		playerTwo.y = yPosB;

		//Draw Rectangles using ShapeRenderer
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 1, 1);
		shapeRenderer.rect(20, playerOne.y, playerOne.width, playerOne.height);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 1, 1);
		shapeRenderer.rect(770, playerTwo.y, playerTwo.width, playerTwo.height);
		shapeRenderer.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
