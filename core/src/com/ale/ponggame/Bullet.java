package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    public int xSpeed;
    public int ySpeed;
    public Rectangle hitbox;
    public Color color;
    public int bouncesAllowed;
    public int bounces;
    public BS state;

    public Bullet(float xInitial, float yInitial, int width, int height, int speed, int bounceAllowed, Color color) {
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.hitbox = new Rectangle();
        this.hitbox.width = width;
        this.hitbox.height = height;
        this.hitbox.x = xInitial;
        this.hitbox.y = yInitial;
        this.bouncesAllowed = bounceAllowed;
        this.bounces = 0;
        this.color = color;
        this.state = BS.BOUNCE0;
    }

    public enum BS {
        BOUNCE0,
        BOUNCE1,
        BOUNCE2,
        HIT
    }

    public void draw(ShapeRenderer sr) {
        if(state == BS.BOUNCE0 || state == BS.BOUNCE1 || state == BS.BOUNCE2) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(color);
            sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            sr.end();
        } else {
            this.hit();
        }
    }

    public void hit() {

    }
}
