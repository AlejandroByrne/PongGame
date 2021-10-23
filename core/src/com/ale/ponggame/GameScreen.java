package com.ale.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {

    private PongGame game;

    public Player player = new Player(Color.YELLOW);
    private ArrayList<Bullet> bullets;
    private ArrayList<PickUp> pickUps;
    private IntSet keysPressedNow = new IntSet();

    private BitmapFont gameFont1;
    private BitmapFont gameFont2;

    public GameScreen(PongGame game) {
        this.game = game;
        gameFont1 = new BitmapFont(Gdx.files.internal("core/assets/gamefont1.fnt"));
        gameFont2 = new BitmapFont(Gdx.files.internal("core/assets/gamefont2.fnt"));
        bullets = new ArrayList<Bullet>();
        pickUps = new ArrayList<PickUp>();
        pickUps.add(new PickUp(new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")), 400, 200, 40, 40, new Weapon(4,4,5,0,1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")))));
        pickUps.add(new PickUp(new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/lasergun.png")), 800, 600, 40, 40, new Weapon(7, 2, 30, 4, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/lasergun.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/Red_laser.png")))));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) { // mouse click
                int renderY = Gdx.graphics.getHeight() - y;
                // shoot bullet
                double a = Math.abs(x - (player.hitbox.x + player.hitbox.width/2));
                double b = Math.abs(renderY - (player.hitbox.y + player.hitbox.height/2));
                double hypotenuse = Math.sqrt((Math.pow( a, 2)) + Math.pow(b, 2)); // working
                double angle = Math.asin(Math.sin(Math.PI/2) * b / hypotenuse);

                bullets.add(new Bullet(player.hitbox.x + player.hitbox.width/2, player.hitbox.y + player.hitbox.height/2, player.currentWeapon.bulletWidth, player.currentWeapon.bulletHeight, player.currentWeapon.bulletSpeed, player.currentWeapon.bulletBouncesAllowed, player.currentWeapon.bulletTexture, angle, (x - (player.hitbox.x + player.hitbox.width/2)), (renderY - (player.hitbox.y + player.hitbox.height/2))));

                return true;
            }

            @Override
            public boolean keyDown(int keyCode) {
                if(keyCode == Input.Keys.NUM_1) { // switch weapons "1" is used to switch through weapons
                    if(player.weapons.size() > 1) { // if the player has more than one weapon available, then check if the current weapon is at the end of the list
                        int currentNum = 0;
                        for(int i=0; i<player.weapons.size(); i++) {
                            if(player.currentWeapon.equals(player.weapons.get(i))) {
                                currentNum = i;
                            }
                        }
                        if(currentNum == player.weapons.size() - 1) { // if the weapon IS at the end of the list, then set the new current weapon to the first in the list
                            player.currentWeapon = player.weapons.get(0); // switch weapons, update the inventory
                        } else { // if not, then make the new weapon the next in the list
                            player.currentWeapon = player.weapons.get(currentNum + 1); // switch weapons, update the inventory
                        }
                    }
                }
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

        }

        // walls

        //pickups
        for(Iterator<PickUp> iterator = pickUps.iterator(); iterator.hasNext();) {
            PickUp p = iterator.next();
            if(player.hitbox.overlaps(p.hitbox)) {
                p.addToInventory(player);
                iterator.remove();
            }
        }

        //other entities


        // RENDERING
        Gdx.gl.glClearColor(0, 0, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //player
        player.draw(game.shapeRenderer);

        game.batch.begin();
        gameFont2.draw(game.batch, ("Player X: " + player.hitbox.x + " Player Y: " + player.hitbox.y), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .9f);
        game.batch.end();

        //bullets
        for(Bullet b: bullets) {
            b.draw(game.batch);
        }

        game.batch.begin();
        gameFont2.draw(game.batch, ("Active Bullets: " + bullets.size()), Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.batch.end();

        //pickUps
        for(PickUp p : pickUps) {
            p.draw(game.batch);
        }

        // inventory
        updateInventory();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void onKeysPressed() {
        if(keysPressedNow.contains(Input.Keys.W)) { // forward
            player.hitbox.y += player.speed;
        }
        if(keysPressedNow.contains(Input.Keys.S)) { // backward
            player.hitbox.y -= player.speed;
        }
        if(keysPressedNow.contains(Input.Keys.A)) { // left
            player.hitbox.x -= player.speed;
        }
        if(keysPressedNow.contains(Input.Keys.D)) { // right
            player.hitbox.x += player.speed;
        }
    }

    public void updateInventory() {
        //establish the left-hand corner coordinates which the inventory will default to, relative to the screen dimensions
        int x = (int) (Gdx.graphics.getWidth() * 0.05);
        int y = (int) (Gdx.graphics.getHeight() * 0.05);

        int currentNum = 0;
        for(int i=0; i<player.weapons.size(); i++) {
            if(player.currentWeapon.equals(player.weapons.get(i))) {
                currentNum = i;
            }
        }
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(1,1,1,1));
        game.shapeRenderer.rect(x-5 + (currentNum * 55), y-5, 60, 60);
        game.shapeRenderer.end();
        // Render inventory slots w/ a shaperenderer
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(0, 0, 0, 1));
        game.shapeRenderer.rect(x, y, 50, 50);
        game.shapeRenderer.rect(x + 55, y, 50, 50);
        game.shapeRenderer.rect(x + 110, y, 50, 50);
        game.shapeRenderer.rect(x + 165, y, 50, 50);
        game.shapeRenderer.rect(x + 220, y, 50, 50);
        game.shapeRenderer.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(1, 0.28f, 0, 1));
        game.shapeRenderer.rect(x + 5, y + 5, 45, 45);
        game.shapeRenderer.rect(x + 60, y + 5, 45, 45);
        game.shapeRenderer.rect(x + 115, y + 5, 45, 45);
        game.shapeRenderer.rect(x + 170, y + 5, 45, 45);
        game.shapeRenderer.rect(x + 225, y + 5, 45, 45);
        game.shapeRenderer.end();

        for(int i=0; i<player.weapons.size(); i++) {
            Weapon w = player.weapons.get(i);
            game.batch.begin();
            game.batch.draw(w.weaponTexture, x + 5 + (i * 55), y + 5, 45, 45);
            game.batch.end();
        }
    }

}
