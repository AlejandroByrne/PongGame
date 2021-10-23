package com.ale.ponggame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PickUp {

    private Texture texture;
    public Rectangle hitbox;
    public Weapon weapon;

    public PickUp(Texture texture, int x, int y, int height, int width) {
        this.texture = texture;
        this.hitbox = new Rectangle(x, y, height, width);
    }

    public PickUp(Texture texture, int x, int y, int height, int width, Weapon weapon) {
        this.texture = texture;
        this.hitbox = new Rectangle(x, y, height, width);
        this.weapon = weapon;
    }

    public void addToInventory(Player player) {
        if(this.weapon != null) {
            player.weapons.add(this.weapon);
            player.currentWeapon = player.weapons.get(1);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(this.texture, hitbox.x, hitbox.y, hitbox.height, hitbox.width);
        batch.end();
    }

}
