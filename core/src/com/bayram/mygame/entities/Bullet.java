package com.bayram.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bayram.mygame.tools.CollisionRect;


public class Bullet {

    private static final int SPEED = 2000;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final float DEFAULT_Y = 110;
    private static Texture bulletImage;
    private float x, y;
    private boolean remove = false;

    private CollisionRect collisionRect;

    public Bullet(float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.collisionRect = new CollisionRect(x, y, WIDTH, HEIGHT);


        if (bulletImage == null) {
            bulletImage = new Texture("missile.png");
        }
    }

    public void update(float delta) {
        y += SPEED * delta;
        if (y > Gdx.graphics.getHeight()) {
            remove = true;
        }
        collisionRect.move(x, y);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(bulletImage, x, y, WIDTH, HEIGHT);
    }

    public boolean isRemove() {
        return remove;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public static void setBulletImage(Texture bulletImage) {
        Bullet.bulletImage = bulletImage;
    }
}
