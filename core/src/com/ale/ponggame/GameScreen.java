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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import org.w3c.dom.css.Rect;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {

    /*
            WHAT TO WORK ON NEXT:
            - Just added weapons to enemies, need to create a new arraylist in the game class to store all
            bullets from ENEMIES, create a shooting system for enemies, and track the enemy bullets, render
            them, and check if they hit the player to do damage.

     */

    private PongGame game;

    public Player player = new Player(Color.YELLOW);
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<PickUp> pickUps;
    private ArrayList<Enemy> enemies;
    private IntSet keysPressedNow = new IntSet();

    private int totalFrames;
    private double totalTime;
    private int lastShootFrames;
    private double lastShootTime;

    private int mouseX;
    private int mouseY;

    private BitmapFont gameFont1;
    private BitmapFont gameFont2;
    private BitmapFont gameFont3;

    public GameScreen(PongGame game) {
        this.game = game;
        gameFont1 = new BitmapFont(Gdx.files.internal("core/assets/gamefont1.fnt"));
        gameFont2 = new BitmapFont(Gdx.files.internal("core/assets/gamefont2.fnt"));
        gameFont3 = new BitmapFont(Gdx.files.internal("core/assets/gamefont3.fnt"));
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        pickUps = new ArrayList<PickUp>();
        enemies = new ArrayList<Enemy>();
        pickUps.add(new PickUp(new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")), 400, 200, 40, 40, new Weapon(4,4,30, 5,0,4, 1, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/ballthing.png")))));
        pickUps.add(new PickUp(new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/lasergun.png")), 800, 600, 40, 40, new Weapon(7, 2, 5, 50, 4, 16, 0.2, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/lasergun.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/Red_laser.png")))));
        Weapon enemyWeapon = new Weapon(7, 2, 5, 50, 4, 16, 0.2, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/lasergun.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/Red_laser.png")));
        enemies.add(new Enemy(20, 12, 100, new Color(Color.RED), enemyWeapon));
        enemies.add(new Enemy(20, 12, 100, new Color(Color.RED), enemyWeapon));
        enemies.add(new Enemy(20, 12, 100, new Color(Color.RED), enemyWeapon));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) { // mouse click
                // check to see if the player clicked certain buttons to purchase things or to bring up menus

                return true;
            }

            @Override
            public boolean mouseMoved(int x, int y) {
                mouseX = x;
                mouseY = y;
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
                } else if(keyCode == Input.Keys.R) {
                    if(player.currentWeapon.currentAmmoCount < player.currentWeapon.magazineSize) {
                        player.currentWeapon.reload();
                    }
                } else if(keyCode == Input.Keys.SPACE) {
                    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                    float x = Gdx.input.getX();
                    System.out.println(y);
                    if(lastShootTime > player.currentWeapon.timeBetweenShots && player.currentWeapon.currentAmmoCount > 0) {
                        // shoot bullet
                        double dx = x - (player.hitbox.x);
                        double dy = y - (player.hitbox.y);
                        System.out.println(dx + ", " + dy);
                        double angle = Math.atan2(dy, dx);
                        System.out.println(angle / (2 * Math.PI) * 360);
                        playerBullets.add(new Bullet(player.hitbox.x, player.hitbox.y, player.currentWeapon.damage, player.currentWeapon.bulletWidth, player.currentWeapon.bulletHeight, player.currentWeapon.bulletSpeed, player.currentWeapon.bulletBouncesAllowed, player.currentWeapon.bulletTexture, angle, x, y));
                        lastShootFrames = 0;
                        player.currentWeapon.currentAmmoCount--;
                    }

                } else if(keyCode == Input.Keys.ENTER) {
                    player.shrink();
                    //add code to send a pop-up notification if the player could not shrink
                } else if(keyCode == Input.Keys.SHIFT_RIGHT) {
                    player.heal();
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
        totalFrames++;
        totalTime = totalFrames/60.0;
        lastShootFrames++;
        lastShootTime = lastShootFrames/60.0;

        // INPUT/MOVEMENT

        // Player
        onKeysPressed();

        // Player Bullets
        for(Iterator<Bullet> iterator = playerBullets.iterator(); iterator.hasNext();) {
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
            // if collide with the enemies
            for(Iterator<Enemy> iterator1 = enemies.iterator(); iterator1.hasNext();) {
                Enemy e = iterator1.next();
                if(Intersector.overlaps(e.hitbox, b.hitbox)) {
                    player.points++;
                    iterator.remove();
                    if(!e.takeDamage(b.damage)) {
                        iterator1.remove();
                    }
                }
            }

        }

        // enemy shooting
        for(Enemy e : enemies) {
            if(e.difficulty * Math.random() * 10 < 0.3) {
                int x = (int) player.hitbox.x;
                int y = (int) player.hitbox.y;
                int renderY = Gdx.graphics.getHeight() - y;
                double a = Math.abs(x - (e.hitbox.x + e.hitbox.radius/2));
                double b = Math.abs(renderY - (e.hitbox.y + e.hitbox.radius/2));
                double hypotenuse = Math.sqrt((Math.pow( a, 2)) + Math.pow(b, 2)); // working
                double angle = Math.asin(Math.sin(Math.PI/2) * b / hypotenuse);
                enemyBullets.add(new Bullet(e.hitbox.x + e.hitbox.radius/2, e.hitbox.y + e.hitbox.radius/2, e.weapon.damage, e.weapon.bulletWidth, e.weapon.bulletHeight, e.weapon.bulletSpeed, e.weapon.bulletBouncesAllowed, e.weapon.bulletTexture, angle, (x - (e.hitbox.x + e.hitbox.radius/2)), (renderY - (e.hitbox.y + e.hitbox.radius/2))));
            }
        }

        // enemy bullets
        for(Iterator<Bullet> iterator = enemyBullets.iterator(); iterator.hasNext();) {
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
            // if collide with the player
            if(Intersector.overlaps(player.hitbox, b.hitbox)) {
                iterator.remove();
                if(!player.takeDamage(b.damage)) {
                    game.setScreen(new EndScreen(game));
                }
            }
        }

        // walls

        //pickups
        for(Iterator<PickUp> iterator = pickUps.iterator(); iterator.hasNext();) {
            PickUp p = iterator.next();
            if(Intersector.overlaps(player.hitbox, p.hitbox)) {
                p.addToInventory(player);
                iterator.remove();
            }
        }

        //other entities


        // RENDERING
        Gdx.gl.glClearColor(232/255, 232/255, 232/255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //player
        player.draw(game.shapeRenderer);

        //enemies
        for(Enemy e : enemies) {
            e.draw(game.shapeRenderer, game.batch, gameFont1);
        }

        // optional stats
        game.batch.begin();
        gameFont3.draw(game.batch, ("Points: " + player.points), Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.95f);
        //gameFont2.draw(game.batch, ("Player X: " + player.hitbox.x + " Player Y: " + player.hitbox.y), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .9f);
        gameFont3.draw(game.batch, String.format("Total Time: %.1f", totalTime), Gdx.graphics.getWidth() * .01f, Gdx.graphics.getHeight() * .9f);
        //gameFont2.draw(game.batch, ("Time Since Last Shot: " + lastShootTime), Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .7f);
        game.batch.end();

        //aim triangle
        //drawAimTriangle(mouseX, mouseY);

        //bullets
        for(Bullet b: playerBullets) {
            b.draw(game.batch);
        }
        for(Bullet b : enemyBullets) {
            b.draw(game.batch);
        }
        // gun stats
        game.batch.begin();
        gameFont3.draw(game.batch, ("Ammo: " + player.currentWeapon.currentAmmoCount + "/" + player.currentWeapon.magazineSize + " | " + player.currentWeapon.totalAmmo), Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.1f);
        game.batch.end();

        //pickUps
        for(PickUp p : pickUps) {
            p.draw(game.batch);
        }

        // inventory
        updateInventory();

        //buttons

        // button listeners

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
        game.shapeRenderer.setColor(new Color(0, 0, 0, 0.3f));
        game.shapeRenderer.rect(x, y, 50, 50);
        game.shapeRenderer.rect(x + 55, y, 50, 50);
        game.shapeRenderer.rect(x + 110, y, 50, 50);
        game.shapeRenderer.rect(x + 165, y, 50, 50);
        game.shapeRenderer.rect(x + 220, y, 50, 50);
        game.shapeRenderer.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(1, 0.28f, 0, 0.3f));
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

        // health bar
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.WHITE);
        game.shapeRenderer.rect((float) (Gdx.graphics.getWidth() * 0.5) - 150, (float) (Gdx.graphics.getHeight() * 0.06), 300, 14);
        if(player.health > 20) {
            game.shapeRenderer.setColor(Color.BLUE);
        } else {
            game.shapeRenderer.setColor(Color.RED);
        }
        game.shapeRenderer.rect((float) (Gdx.graphics.getWidth() * 0.5) - 150, (float) (Gdx.graphics.getHeight() * 0.06), (int) (player.health * 3), 14);
        game.shapeRenderer.end();

        // shooting timer
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect((float) (Gdx.graphics.getWidth() * 0.5) - 50, (float) (Gdx.graphics.getHeight() * 0.03), 100, 8);
        game.shapeRenderer.setColor(Color.CYAN);
        if(lastShootTime < player.currentWeapon.timeBetweenShots) {
            game.shapeRenderer.rect((float) (Gdx.graphics.getWidth() * 0.5) - 50, (float) (Gdx.graphics.getHeight() * 0.03), (int) (lastShootTime/player.currentWeapon.timeBetweenShots * 100), 8);
        } else {
            game.shapeRenderer.rect((float) (Gdx.graphics.getWidth() * 0.5) - 50, (float) (Gdx.graphics.getHeight() * 0.03), 100, 8);
        }
        game.shapeRenderer.end();
    }

    public void drawAimTriangle(int x, int y) {
        int renderY = Gdx.graphics.getHeight() - y;
        float a = x - (player.hitbox.x + player.hitbox.radius);
        float b = renderY - (player.hitbox.y + player.hitbox.radius);
        double hypotenuse = Math.sqrt((Math.pow( a, 2)) + Math.pow(b, 2)); // working
        double angle = Math.asin(Math.sin(Math.PI/2) * b / hypotenuse);
        float newA1;
        float newA2;
        float newB1;
        float newB2;
        if(a > 0 && b > 0) { // 1st quadrant
            newA1 = (float) (a + 20 * Math.cos(angle));
            newA2 = (float) (a - 20 * Math.cos(angle));
            newB1 = (float) (b - 20 * Math.sin(angle));
            newB2 = (float) (b + 20 * Math.sin(angle));
        } else if(a < 0 && b > 0) { // 2nd quadrant
            newA1 = (float) (a + 20 * Math.cos(angle));
            newA2 = (float) (a - 20 * Math.cos(angle));
            newB1 = (float) (b + 20 * Math.sin(angle));
            newB2 = (float) (b - 20 * Math.sin(angle));
        } else if(a < 0 && b < 0) { // 3rd quadrant
            newA1 = (float) (a + 20 * Math.cos(angle));
            newA2 = (float) (a - 20 * Math.cos(angle));
            newB1 = (float) (b - 20 * Math.sin(angle));
            newB2 = (float) (b + 20 * Math.sin(angle));
        } else {
            newA1 = (float) (a + 20 * Math.cos(angle));
            newA2 = (float) (a - 20 * Math.cos(angle));
            newB1 = (float) (b + 20 * Math.sin(angle));
            newB2 = (float) (b - 20 * Math.sin(angle));
        }


        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        double playerX = player.hitbox.x + player.hitbox.radius;
        double playerY = player.hitbox.y + player.hitbox.radius;
        game.shapeRenderer.triangle((float) playerX, (float) playerY, (float) playerX + newA1, (float) playerY + newB1, (float) playerX + newA2, (float) playerY + newB2);
        game.shapeRenderer.end();
    }

}
