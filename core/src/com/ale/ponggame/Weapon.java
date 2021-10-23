package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Weapon {

    public int burst;
    public int bulletWidth;
    public int bulletHeight;
    public int bulletSpeed;
    public int bulletBouncesAllowed;
    public Texture bulletTexture;
    public Texture weaponTexture;

    public Weapon(int width, int height, int bulletSpeed, int bulletBouncesAllowed, int burst, Texture weaponTexture, Texture bulletTexture) {
        this.bulletWidth = width;
        this.bulletHeight = height;
        this.bulletSpeed = bulletSpeed;
        this.bulletBouncesAllowed = bulletBouncesAllowed;
        this.burst = burst;
        this.weaponTexture = weaponTexture;
        this.bulletTexture = bulletTexture;
    }
}
