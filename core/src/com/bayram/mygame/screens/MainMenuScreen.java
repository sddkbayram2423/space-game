package com.bayram.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.bayram.mygame.SpaceGame;
import com.bayram.mygame.tools.ScrollingBackround;

public class MainMenuScreen implements Screen {

    private final SpaceGame spaceGame;
    private Texture exitButtonActiv;
    private Texture playButtonActiv;
    private Texture exitButtonInActiv;
    private Texture playButtonInActiv;

    float buttonWidth = Gdx.graphics.getWidth() / 2;
    float buttonHeight = Gdx.graphics.getHeight() / 5;
    float buttonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2;
    float buttonActivX = 70;
    float buttonExitX = 500;

    public MainMenuScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
    }

    @Override
    public void show() {
        exitButtonActiv = new Texture("exit_button_active.png");
        playButtonActiv = new Texture("play_button_active.png");
        exitButtonInActiv = new Texture("exit_button_inactive.png");
        playButtonInActiv = new Texture("play_button_inactive.png");

        spaceGame.getBackround().setSpeedFixed(false);
        spaceGame.getBackround().setSpeed(ScrollingBackround.DEFAULT_SPEDD);

        final MainMenuScreen menuScreen=this;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                //play button
                if ((Gdx.input.getX() >= buttonActivX && Gdx.input.getX() <= buttonActivX + buttonWidth && Gdx.input.getY() >= buttonY && Gdx.input.getY() < buttonY + buttonHeight)) {
                    menuScreen.dispose();
                    spaceGame.getBatch().begin();
                    spaceGame.getBatch().draw(playButtonActiv, buttonActivX, buttonY, buttonWidth, buttonHeight);
                    spaceGame.getBatch().end();
                   spaceGame.setScreen(new MainGameScreen(spaceGame));
                }
                //exit button
                if ((Gdx.input.getX() >= buttonExitX && Gdx.input.getX() <= buttonExitX + buttonWidth && Gdx.input.getY() >= buttonY && Gdx.input.getY() < buttonY + buttonHeight)) {
                    menuScreen.dispose();
                    spaceGame.getBatch().begin();
                    spaceGame.getBatch().draw(exitButtonActiv, buttonExitX, buttonY, buttonWidth, buttonHeight);
                    spaceGame.getBatch().end();
                    Gdx.app.exit();
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        spaceGame.getBatch().begin();
        spaceGame.getBackround().updateAndRender(delta,spaceGame.getBatch());
        spaceGame.getBatch().draw(exitButtonInActiv, buttonExitX, buttonY, buttonWidth, buttonHeight);
        spaceGame.getBatch().draw(playButtonInActiv, buttonActivX, buttonY, buttonWidth, buttonHeight);


        /*
        if (Gdx.input.isTouched()) {
            if ((Gdx.input.getX() >= buttonExitX && Gdx.input.getX() <= buttonExitX + buttonWidth && Gdx.input.getY() >= buttonY && Gdx.input.getY() < buttonY + buttonHeight)) {

                spaceGame.getBatch().draw(exitButtonActiv, buttonExitX, buttonY, buttonWidth, buttonHeight);
                Gdx.app.exit();
            }
            if ((Gdx.input.getX() >= buttonActivX && Gdx.input.getX() <= buttonActivX + buttonWidth && Gdx.input.getY() >= buttonY && Gdx.input.getY() < buttonY + buttonHeight)) {
                spaceGame.getBatch().draw(playButtonActiv, buttonActivX, buttonY, buttonWidth, buttonHeight);
                spaceGame.setScreen(new MainGameScreen(spaceGame));
            }
        }

         */


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

        Gdx.input.setInputProcessor(null);

    }
}
