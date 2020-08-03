package com.bayram.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.bayram.mygame.SpaceGame;
import com.bayram.mygame.entities.Astroid;
import com.bayram.mygame.entities.Bullet;
import com.bayram.mygame.entities.Explosion;
import com.bayram.mygame.tools.CollisionRect;

import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {


    //------------------------------------------------------------------

    private Texture shippImage = null;
    private float x = 0, y = 10;
    private final static float SPEED = 15;

    private final static float SHIP_WIDTH = Gdx.graphics.getWidth() / 8;
    private final static float SHIP_HEIGHT = Gdx.graphics.getHeight() / 12;
    private final static float MAX_SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final static float SHOOT_WAIT_TIME = 0.08f;
    private final static float MIN_ASDROID_SPAWN_TIME = 0.1f;
    private final static float MAX_ASDROID_SPAWN_TIME = 3;
    private float shootTimer;
    private float astroidSpawnTimer;
    private SpaceGame spaceGame;
    private Random random;

    private ArrayList<Bullet> bullets;
    private ArrayList<Astroid> astroids;
    private ArrayList<Explosion> explosions;
    private BitmapFont bitmapFont;
    float health;//0=dead,1=health

    private Texture blankTexture;
    private CollisionRect playerRect;
    int score;


    public MainGameScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
        bullets = new ArrayList<>();
        astroids = new ArrayList<>();
        explosions = new ArrayList<>();
        shootTimer = 0;
        score = 0;
        health = 1;
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        blankTexture = new Texture("blank.png");
        playerRect = new CollisionRect(0, 0, SHIP_WIDTH, SHIP_HEIGHT);
        random = new Random();
        astroidSpawnTimer = random.nextFloat() * (MAX_ASDROID_SPAWN_TIME - MIN_ASDROID_SPAWN_TIME) - MIN_ASDROID_SPAWN_TIME;
    }

    @Override
    public void show() {
        shippImage = new Texture("ship.png");
    }

    @Override
    public void render(float delta) {


        //shoting
        shootTimer += delta;
        if (isJustRight()||isJustLeft()&& shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;
            bullets.add(new Bullet(x + SHIP_WIDTH / 2 - 25));

        }
        //update Bullet
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.isRemove()) {
                bulletsToRemove.add(bullet);
            }
        }


        //astroid  spawn code
        astroidSpawnTimer -= delta;
        if (astroidSpawnTimer <= 0) {
            astroidSpawnTimer = random.nextFloat() * (MAX_ASDROID_SPAWN_TIME - MIN_ASDROID_SPAWN_TIME) - MIN_ASDROID_SPAWN_TIME;
            int coordinat = random.nextInt((int) (Gdx.graphics.getWidth() - Astroid.WIDTH));
            if (coordinat >= SHIP_WIDTH / 2 && coordinat <= MAX_SCREEN_WIDTH - SHIP_WIDTH / 2) {
                astroids.add(new Astroid(coordinat));
            }


        }
        //Update Astroids
        ArrayList<Astroid> astroidsToRemove = new ArrayList<>();
        for (Astroid astroid : astroids) {
            astroid.update(delta);
            if (astroid.isRemove()) {
                astroidsToRemove.add(astroid);
            }
        }

        if(isLeft()||isJustLeft()){
            if (x > 0) {
                x -= SPEED;
            }
        }else if(isRight()||isJustRight()){
            if (x < MAX_SCREEN_WIDTH - shippImage.getWidth() / 2.2f) {
                x += SPEED;
            }
        }


        //After player move uptade collision
        playerRect.move(x, y);

        //After all updates , check collisions
        for (Bullet bullet : bullets) {
            for (Astroid astroid : astroids) {
                if (bullet.getCollisionRect().collidesWith(astroid.getCollisionRect())) {//Collision occured
                    bulletsToRemove.add(bullet);
                    astroidsToRemove.add(astroid);
                    explosions.add(new Explosion(astroid.getX(), astroid.getY()));
                    score += 10;
                }
            }
        }
        for (Astroid astroid : astroids) {
            if (astroid.getCollisionRect().collidesWith(playerRect)) {
                astroidsToRemove.add(astroid);
                health -= 0.1f;


                //If health is depleted, go to game over screen
                if (health <= 0) {
                    this.dispose();
                    spaceGame.setScreen(new GameOverScreen(spaceGame, score));
                    return;
                }
            }
        }

        ArrayList<Explosion> explosionToRemove = new ArrayList<>();
        for (Explosion explosion : explosions) {
            explosion.update(delta);
            if (explosion.isRemove()) {
                explosionToRemove.add(explosion);
            }
        }

        for (Astroid astroid : astroidsToRemove) {
            astroids.remove(astroid);
        }
        for (Bullet bullet : bulletsToRemove) {
            bullets.remove(bullet);
        }
        for (Explosion explosion : explosionToRemove) {
            explosions.remove(explosion);
        }

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spaceGame.getBatch().begin();
        spaceGame.getBackround().updateAndRender(delta, spaceGame.getBatch());
        spaceGame.getBatch().draw(shippImage, x, y+5, SHIP_WIDTH, SHIP_HEIGHT);
        GlyphLayout scoreLayout = new GlyphLayout(bitmapFont, " " + score,Color.YELLOW, 0, Align.center, false);
        bitmapFont.draw(spaceGame.getBatch(), scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height / 2 - 50);

        for (Bullet bullet : bullets) {
            bullet.render(spaceGame.getBatch());
        }
        for (Astroid astroid : astroids) {
            astroid.render(spaceGame.getBatch());
        }
        for (Explosion explosion : explosions) {
            explosion.render(spaceGame.getBatch());
        }

        if (health > 0.6f) {
            spaceGame.getBatch().setColor(Color.GREEN);
        } else if (health > 0.2f && health <= 0.6f) {
            spaceGame.getBatch().setColor(Color.ORANGE);
        } else {
            spaceGame.getBatch().setColor(Color.RED);
        }
        spaceGame.getBatch().draw(blankTexture, Gdx.graphics.getWidth() /4, Gdx.graphics.getHeight()-10, Gdx.graphics.getWidth() /2* health, 20);
        spaceGame.getBatch().setColor(Color.WHITE);
        spaceGame.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private boolean isRight() {
        return Gdx.input.isTouched() && Gdx.input.getX() >= Gdx.graphics.getWidth() / 2;
    }

    private boolean isLeft() {
        return Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2;
    }

    private boolean isJustRight() {
        return Gdx.input.justTouched() && Gdx.input.getX() >= Gdx.graphics.getWidth() / 2;
    }

    private boolean isJustLeft() {
        return Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2;
    }


}
