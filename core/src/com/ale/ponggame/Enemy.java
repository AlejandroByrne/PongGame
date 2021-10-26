package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    public Rectangle hitbox;
    public Color color;
    public int speed;
    public int health;


    public Enemy(int width, int height, int speed, int health, Color color) {
        this.hitbox = new Rectangle();
        this.hitbox.width = width;
        this.hitbox.height = height;
        this.speed = speed;
        this.health = health;
        this.color = color;
        this.hitbox.x = (int) (Math.random() * 1000);
        this.hitbox.y = (int) (Math.random() * 700);
    }

    public void draw(ShapeRenderer sr, SpriteBatch batch, BitmapFont font) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
        batch.begin();
        font.draw(batch, ("HP: " + this.health), this.hitbox.x, this.hitbox.y + this.hitbox.height + 2);
        batch.end();
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        if(this.health <= 0) {
            return false;
        } else {
            return true;
        }
    }

}
