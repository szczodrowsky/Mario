package com.mygdx.game.lv1.cos.Mario.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Screens.GameOverScreen;
import com.mygdx.game.lv1.cos.Mario.Screens.Menu;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;

public class EndGame extends InteractiveTitleObject{
    Mario game;
    public boolean EndGame;

    public EndGame(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Mario.END_GAME_BIT);
        //screen.dispose();
    }


    @Override
    public void onHeadHit(CMario mario) {

        game.setScreen(new GameOverScreen(game));
    }


}
