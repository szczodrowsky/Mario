package com.mygdx.game.lv1.cos.Mario.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;
import com.mygdx.game.lv1.cos.Mario.sceenes.Hud;

public class Brick extends InteractiveTitleObject{
    public Brick(PlayScreen screen, MapObject object) {
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Mario.BRICK_BIT);

    }

    @Override
    public void onHeadHit(CMario mario) {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(Mario.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}
