package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Weapon {

    public int burst;
    public int bulletWidth;
    public int bulletHeight;
    public int bulletSpeed;
    public int bulletBouncesAllowed;
    public Color bulletColor;
    public Texture bulletTexture;

    public Weapon(int width, int height, int bulletSpeed, int bulletBouncesAllowed, int burst, Color bulletColor) {
        this.bulletWidth = width;
        this.bulletHeight = height;
        this.bulletSpeed = bulletSpeed;
        this.bulletBouncesAllowed = bulletBouncesAllowed;
        this.burst = burst;
        this.bulletColor = bulletColor;
    }
}
