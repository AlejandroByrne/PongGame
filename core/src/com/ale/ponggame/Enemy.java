package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Enemy {
    public Circle hitbox;
    public Color color;
    public int speed;
    public int health;
    public Weapon weapon;
    public int difficulty;


    public Enemy(int radius, int speed, int health, Color color, Weapon weapon) {
        this.hitbox = new Circle();
        this.hitbox.radius = radius;
        this.speed = speed;
        this.health = health;
        this.color = color;
        this.weapon = weapon;
        this.hitbox.x = (int) (Math.random() * 1000);
        this.hitbox.y = (int) (Math.random() * 700);
        this.difficulty = (int) (Math.random() * 11);
    }

    public void draw(ShapeRenderer sr, SpriteBatch batch, BitmapFont font) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.circle(hitbox.x, hitbox.y, hitbox.radius);
        sr.end();
        batch.begin();
        font.draw(batch, ("HP: " + this.health), this.hitbox.x, this.hitbox.y + this.hitbox.radius + 2);
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
