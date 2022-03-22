package com.ale.ponggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Player {
    public Circle hitbox;
    public Color color;
    public int speed;
    public int health;
    public int points;

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
        hitbox = new Circle();
        hitbox.x = (int) (Math.random() * 30);
        hitbox.y = (int) (Math.random() * 30);
        hitbox.radius = 50;
        speed = 4;
        health = 100;
        weapons = new ArrayList<Weapon>(); // create the player's weapon armory
        currentWeapon = new Weapon(3, 3, 10, 20, 1, 12, 0.4, 1, new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/pistol.png")), new Texture(Gdx.files.internal("core/src/com/ale/ponggame/images/bullet-png-pictures-1.png")));
        weapons.add(currentWeapon);
    }

    public void draw(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.circle(hitbox.x, hitbox.y, hitbox.radius);
        sr.end();
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        this.hitbox.radius += damage;
        if(this.health <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean shrink() {
        if(hitbox.radius > 6 && points > 5) {
            points-= 5;
            hitbox.radius -= 5;
            return true;
        } else {
            return false;
        }
    }

    public boolean heal() {
        if(points >= 5) {
            if(health >= 96) {
                health = 100;
                points -= 5;
                return true;
            } else {
                health += 5;
                points -=5;
                return true;
            }
        } else {
            return false;
        }
    }

}
