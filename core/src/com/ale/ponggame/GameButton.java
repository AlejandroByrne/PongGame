package com.ale.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;

public class GameButton {

    Rectangle area;
    String message;
    Color color;
    boolean isToggled;
    float adjustmentX;
    float adjustmentY;

    public GameButton(float x, float y, float width, float height, Color color, float adjustmentX, float adjustmentY) {
        this.area = new Rectangle(x, y, width, height);
        this.color = color;
        this.isToggled = false;
        this.message = null;
        this.adjustmentX = adjustmentX;
        this.adjustmentY = adjustmentY;
    }

    public GameButton(float x, float y, float width, float height, Color color, String message, float adjustmentX, float adjustmentY) {
        this.area = new Rectangle(x, y, width, height);
        this.color = color;
        this.message = message;
        this.isToggled = false;
        this.adjustmentX = adjustmentX;
        this.adjustmentY = adjustmentY;
    }

    public void toggle() {
        this.isToggled = !this.isToggled;
    }

    public void draw(ShapeRenderer sr, SpriteBatch batch, BitmapFont font) {
        if(this.isToggled) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(this.color);
            sr.rect(area.x, area.y, area.width, area.height);
            sr.end();
            if(message != null) {
                batch.begin();
                font.draw(batch, message, area.x + adjustmentX, area.y + adjustmentY);
                batch.end();
            }
        }
    }
}
