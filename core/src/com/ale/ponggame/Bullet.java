package com.ale.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bullet {
    public int speed;
    public double xSpeed;
    public double ySpeed;
    public double initialAngle; // the initial angle, but from 0 - pi/2 (up to 90 degrees)
    public float currentAngleFull; // current initial angle, but from 0 - 2pi (full 360 degrees)
    public Rectangle hitbox;
    public Texture texture;
    public int bouncesAllowed;
    public int damage;
    public int bounces;
    public BS state;
    public double a;
    public double b;

    public Bullet(float xInitial, float yInitial, int damage, int width, int height, int speed, int bounceAllowed, Texture texture, double initialAngle, double a, double b) {
        this.speed = speed;
        this.damage = damage;
        this.initialAngle = initialAngle;
        this.a = a;
        this.b = b;
        if(a > 0) {
            this.xSpeed = (speed * Math.cos(initialAngle));
        } else {
            this.xSpeed = -1 * (speed * Math.cos(initialAngle));
        }
        if(b > 0) {
            this.ySpeed = (speed * Math.sin(initialAngle));
        } else {
            this.ySpeed = -1 * (speed * Math.sin(initialAngle));
        }
        this.hitbox = new Rectangle();
        this.hitbox.width = width;
        this.hitbox.height = height;
        this.hitbox.x = xInitial;
        this.hitbox.y = yInitial;
        this.bouncesAllowed = bounceAllowed;
        this.bounces = 0;
        this.texture = texture;
        this.state = BS.BOUNCE0;
    }

    public enum BS {
        BOUNCE0,
        BOUNCE1,
        BOUNCE2,
        HIT
    }

    public void draw(SpriteBatch batch) {
        if(a > 0 && b > 0) { // 1st quadrant
            currentAngleFull = (float) (initialAngle);
        } else if(a < 0 && b > 0) { // 2nd quadrant
            currentAngleFull = (float) ((Math.PI/2 - initialAngle) + Math.PI/2);
        } else if(a < 0 && b < 0) { // 3rd quadrant
            currentAngleFull = (float) (initialAngle + Math.PI);
        } else if(a > 0 && b < 0) { // 4th quadrant
            currentAngleFull = (float) ((Math.PI/2 - initialAngle) + (3 * (Math.PI / 2)));
        }
        batch.begin();
        Texture texture = this.texture;
        TextureRegion txtR = new TextureRegion(texture);
        batch.draw(txtR, hitbox.x, hitbox.y, 15, 15, 30, 30, 1, 1, ((float) (currentAngleFull * 360 / (2 * Math.PI))));
        System.out.println(currentAngleFull / (2* Math.PI) * 360);
        batch.end();

    }

    public void hit() {

    }
}
