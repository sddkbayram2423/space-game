package com.bayram.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.bayram.mygame.SpaceGame;
import com.bayram.mygame.tools.ScrollingBackround;

import static com.badlogic.gdx.Gdx.*;

public class GameOverScreen implements Screen {

    private SpaceGame spaceGame;
    int score, highScore;

    BitmapFont scorefont;

    //game_over.png

    public GameOverScreen(SpaceGame spaceGame, int score) {
        this.spaceGame = spaceGame;
        this.score = score;

        //Get high Score from save file
        Preferences preferences = app.getPreferences("spacegame");
        this.highScore = preferences.getInteger("highscore", 0);

        //check if score beats higscore
        if (score > highScore) {
            preferences.putInteger("highscore", score);
            preferences.flush();
        }

        scorefont = new BitmapFont(files.internal("fonts/score.fnt"));

        spaceGame.getBackround().setSpeedFixed(false);
        spaceGame.getBackround().setSpeed(ScrollingBackround.DEFAULT_SPEDD);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spaceGame.getBatch().begin();

        spaceGame.getBackround().updateAndRender(delta,spaceGame.getBatch());
        GlyphLayout scoreLayout = new GlyphLayout(scorefont, "Score: " + score, Color.GREEN, 50, Align.center, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scorefont, "High Score: " + highScore, Color.RED, 0, Align.center, false);
        GlyphLayout gameOver=new GlyphLayout(scorefont,"GAME OVER!!!",Color.PINK,0,Align.center,false);
        scorefont.draw(spaceGame.getBatch(), scoreLayout, graphics.getWidth() / 2, graphics.getHeight() / 3 - scoreLayout.height / 2 - 50);
        scorefont.draw(spaceGame.getBatch(), highScoreLayout, graphics.getWidth() / 2, graphics.getHeight() / 3);
        scorefont.draw(spaceGame.getBatch(), gameOver, graphics.getWidth() /2, graphics.getHeight() -500);


        GlyphLayout tryAgainLayout = new GlyphLayout(scorefont, "Try Again");
        GlyphLayout mainManuLayout = new GlyphLayout(scorefont, "Main Menu");

        float tryAgainX = Gdx.graphics.getWidth() / 3 - tryAgainLayout.width / 2;
        float tryAgainY = 250;
        float mainMenüAgainX = tryAgainX + tryAgainLayout.width + 100;
        float mainMenüAgainY = 250;

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight()-Gdx.input.getY();
        System.out.println("tY: "+touchY);
        System.out.println("tX: "+touchX);

        if (Gdx.input.isTouched()) {
            //if try again pressed
            if (touchX >= tryAgainX && touchX <= tryAgainX + tryAgainLayout.width && touchY >= tryAgainY && touchY <= tryAgainY + tryAgainLayout.height) {
                this.dispose();
                spaceGame.getBatch().end();
                System.out.println("Try pressed");
                spaceGame.setScreen(new MainGameScreen(spaceGame));
                return;
                //if main menu pressed
            }
            if (touchX >= mainMenüAgainX && touchX <= mainMenüAgainX + mainManuLayout.width && touchY >= mainMenüAgainY && touchY <= mainMenüAgainY + mainManuLayout.height) {
                this.dispose();
                spaceGame.getBatch().end();
                System.out.println("MainMenuScreen pressed");
                spaceGame.setScreen(new MainMenuScreen(spaceGame));
                return;
            }
        }
        scorefont.draw(spaceGame.getBatch(), tryAgainLayout, tryAgainX, tryAgainY);
        scorefont.draw(spaceGame.getBatch(), mainManuLayout, mainMenüAgainX, mainMenüAgainY);
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
}
