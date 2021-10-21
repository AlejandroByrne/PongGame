package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public Rectangle hitbox;
    public Color color;
    public int speed;

    public enum animationState {
        STILL,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        SHOOTING
    };

    public Player(Color color) {
        this.color = color;
        hitbox = new Rectangle();
        hitbox.x = (int) (Math.random() * 30);
        hitbox.y = (int) (Math.random() * 30);
        hitbox.width = 30;
        hitbox.height = 30;
        speed = 4;
    }

    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
    }

}
