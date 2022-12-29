package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Mario;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.sceenes.Hud;

public class Coin extends InteractiveTitleObject{
    public static TiledMapTileSet set;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen,bounds);
        set = map.getTileSets().getTileSet("set");
        fixture.setUserData(this);
        setCategoryFilter(Mario.COIN_BIT);


    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","Collision");
       // setCategoryFilter(Mario.DESTROYED_BIT);
        getCell().setTile(set.getTile(BLANK_COIN));
        Hud.addScore(150);
    }
}
