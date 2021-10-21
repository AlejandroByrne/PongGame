package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Player {
    public Rectangle hitbox;
    public Color color;
    public int speed;

    public ArrayList<Weapon> weapons;
    public Weapon currentWeapon;

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
        hitbox.width = 50;
        hitbox.height = 50;
        speed = 4;

        weapons = new ArrayList<Weapon>(); // create the player's weapon armory
        currentWeapon = new Weapon(3, 3, 40, 2, 1, new Color(1, 0, 0, 1));
        weapons.add(currentWeapon);
    }

    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
    }

}
