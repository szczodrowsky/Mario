package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.lv1.cos.Tools.Screens.PlayScreen;

public class Mario extends Game {
	public final static int v_width = 400;
	public final static int v_high = 208;
	public final static float PPM = 100.0f;

	public static  final short NOTHING_BIT = 0;
	public static  final short GROUND_BIT = 1;
	public static  final short MARIO_BIT = 2;
	public static  final short BRICK_BIT = 4;
	public static  final short COIN_BIT = 8;
	public static  final short DESTROYED_BIT = 16;
	public static  final short OBJECT_BIT = 32;
	public static  final short ENEMY_BIT = 64;
	public static  final short ENEMY_HEAD_BIT = 128;
	public static final short  ITEM_BIT = 256;
	public static  final short MARIO_HEAD_BIT = 512;



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
