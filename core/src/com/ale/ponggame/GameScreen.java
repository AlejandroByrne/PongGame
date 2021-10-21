package com.ale.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {

    private PongGame game;

    private ArrayList<Player> players;
    private ArrayList<Bullet> bullets;
    private ArrayList<PickUp> pickUps;
    private IntSet keysPressedNow = new IntSet();

    private float circleX = 300;
    private float circleY = 150;
    private float circleRadius = 15;
    private float xSpeed = 4;
    private float ySpeed = 3;

    public GameScreen(PongGame game) {
        this.game = game;
        Player p1 = new Player(Color.YELLOW);
        players = new ArrayList<Player>();
        bullets = new ArrayList<Bullet>();
        players.add(p1);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = Gdx.graphics.getHeight() - y;
                // shoot bullet
                double a = Math.abs(x - (players.get(0).hitbox.x + players.get(0).hitbox.width/2));
                double b = Math.abs(renderY - (players.get(0).hitbox.y + players.get(0).hitbox.height/2));
                double hypotenuse = Math.sqrt((Math.pow( a, 2)) + Math.pow(b, 2)); // working
                double angle = Math.asin(Math.sin(Math.PI/2) * b / hypotenuse);

                bullets.add(new Bullet(players.get(0).hitbox.x + players.get(0).hitbox.width/2, players.get(0).hitbox.y + players.get(0).hitbox.height/2, players.get(0).currentWeapon.bulletWidth, players.get(0).currentWeapon.bulletHeight, players.get(0).currentWeapon.bulletSpeed, players.get(0).currentWeapon.bulletBouncesAllowed, players.get(0).currentWeapon.bulletColor, angle, (x - (players.get(0).hitbox.x + players.get(0).hitbox.width/2)), (renderY - (players.get(0).hitbox.y + players.get(0).hitbox.height/2))));

                if (Vector2.dst(circleX, circleY, x, renderY) < circleRadius) {
                    game.setScreen(new EndScreen(game));
                }
                return true;
            }

            @Override
            public boolean keyDown(int keyCode) {
                keysPressedNow.add(keyCode);
                return true;
            }

            @Override
            public boolean keyUp(int keyCode) {
                keysPressedNow.remove(keyCode);
                return true;
            }

        });
    }

    @Override
    public void render(float delta) {

        // INPUT/MOVEMENT

        // Player
        onKeysPressed();

        // Bullets
        for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet b = iterator.next();
            b.hitbox.x += b.xSpeed;
            b.hitbox.y += b.ySpeed;
            //edges of screen
            if(b.hitbox.x < 0 || b.hitbox.x > Gdx.graphics.getWidth()) {
                b.bounces++;
                if(b.bounces > b.bouncesAllowed) {
                    b.hit();
                    iterator.remove();
                } else {
                    b.xSpeed *= -1;
                    b.a *= -1;
                }
            } else if(b.hitbox.y < 0 || b.hitbox.y > Gdx.graphics.getHeight()) {
                b.bounces++;
                if(b.bounces > b.bouncesAllowed) {
                    b.hit();
                    iterator.remove();
                } else {
                    b.ySpeed *= -1;
                    b.b *= -1;
                }
            }
            if(b.hitbox.overlaps(new Rectangle(circleX, circleY, circleRadius, circleRadius))) {
                game.setScreen(new EndScreen(game));
            }
        }

        // walls

        //other entities

        // Ball
        circleX += xSpeed;
        circleY += ySpeed;
        if(circleX < 0 || circleX > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }
        if(circleY < 0 || circleY > Gdx.graphics.getHeight()) {
            ySpeed *= -1;
        }

        // RENDERING
        Gdx.gl.glClearColor(0, 0, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //player
        for(Player p : players) {
            p.draw(game.shapeRenderer);
        }
        //bullets
        for(Bullet b: bullets) {
            b.draw(game.batch);
        }
        // ball
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0, 1);
        game.shapeRenderer.circle(circleX, circleY, circleRadius);
        game.shapeRenderer.end();

        game.batch.begin();
        game.font.draw(game.batch, ("Active Bullets: " + bullets.size()), Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void onKeysPressed() {
        if(keysPressedNow.contains(Input.Keys.W)) { // forward
            players.get(0).hitbox.y += players.get(0).speed;
        }
        if(keysPressedNow.contains(Input.Keys.S)) { // backward
            players.get(0).hitbox.y -= players.get(0).speed;
        }
        if(keysPressedNow.contains(Input.Keys.A)) { // left
            players.get(0).hitbox.x -= players.get(0).speed;
        }
        if(keysPressedNow.contains(Input.Keys.D)) { // right
            players.get(0).hitbox.x += players.get(0).speed;
        }
    }
}
