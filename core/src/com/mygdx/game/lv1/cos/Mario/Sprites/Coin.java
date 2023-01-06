package com.mygdx.game.lv1.cos.Mario.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Mario;
//import com.mygdx.game.Sprites.Items.Apple;
//import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.Apple;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.ItemDef;

import com.mygdx.game.lv1.cos.Mario.sceenes.Hud;

public class Coin extends InteractiveTitleObject{
    public static TiledMapTileSet set;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, MapObject object) {
        super(screen,object);
        set = map.getTileSets().getTileSet("set");
        fixture.setUserData(this);
        setCategoryFilter(Mario.COIN_BIT);


    }

    @Override
    public void onHeadHit(CMario mario) {
        if(object.getProperties().containsKey("apple")){
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/Mario.PPM), Apple.class));
        }
        getCell().setTile(set.getTile(BLANK_COIN));
        Hud.addScore(150);
    }
}