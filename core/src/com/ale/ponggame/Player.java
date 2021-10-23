package com.ale.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
        currentWeapon = new Weapon(3, 3, 20, 1, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/pistol.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/bullet-png-pictures-1.png")));
        weapons.add(currentWeapon);
    }

    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
    }

}
