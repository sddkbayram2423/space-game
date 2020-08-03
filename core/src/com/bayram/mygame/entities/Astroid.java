package com.bayram.mygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bayram.mygame.tools.CollisionRect;

public class Astroid {

    private static final int SPEED = 400;
    public static final int WIDTH = 75;
    public static final int HEIGHT = 60;
    private static Texture astroidImage;
    private float x, y;
    private boolean remove = false;
    private CollisionRect collisionRect;

    public Astroid(float x) {
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        this.collisionRect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if (astroidImage == null) {
            astroidImage = new Texture("rock.png");
        }

    }

    public void update(float delta) {
        y -= SPEED * delta;
        if (y < -HEIGHT) {
            remove = true;
        }
        collisionRect.move(x, y);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(astroidImage, x, y, WIDTH, HEIGHT);
    }

    public boolean isRemove() {
        return remove;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static void setAstroidImage(Texture astroidImage) {
        Astroid.astroidImage = astroidImage;
    }
}
