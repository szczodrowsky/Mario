package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class Mario extends Game {
	public final static int v_width = 400;
	public final static int v_high = 208;
	public final static float PPM = 100.0f;

	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
