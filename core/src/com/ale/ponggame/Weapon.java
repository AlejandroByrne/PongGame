package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Weapon {

    public int burst;
    public int damage;
    public int bulletWidth;
    public int bulletHeight;
    public int bulletSpeed;
    public int bulletBouncesAllowed;
    public int magazineSize;
    public int currentAmmoCount;
    public int totalAmmo;
    public double timeBetweenShots;
    public Texture bulletTexture;
    public Texture weaponTexture;

    public Weapon(int width, int height, int damage, int bulletSpeed, int bulletBouncesAllowed, int magazineSize, double timeBetweenShots, int burst, Texture weaponTexture, Texture bulletTexture) {
        this.bulletWidth = width;
        this.bulletHeight = height;
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.bulletBouncesAllowed = bulletBouncesAllowed;
        this.magazineSize = magazineSize;
        this.currentAmmoCount = magazineSize;
        this.totalAmmo = magazineSize * 3;
        this.timeBetweenShots = timeBetweenShots;
        this.burst = burst;
        this.weaponTexture = weaponTexture;
        this.bulletTexture = bulletTexture;
    }

    public void reload() {
        if(totalAmmo >= magazineSize) {
            totalAmmo -= (magazineSize-currentAmmoCount);
            currentAmmoCount = magazineSize;
        } else if(totalAmmo > 0 && totalAmmo >= (magazineSize-currentAmmoCount)) {
            totalAmmo -= (magazineSize-currentAmmoCount);
            currentAmmoCount = magazineSize;
        } else {
            currentAmmoCount += totalAmmo;
            totalAmmo = 0;
        }
    }

}
