package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Mario;
import com.mygdx.game.sceenes.Hud;

public class Brick extends InteractiveTitleObject{
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Mario.BRICK_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(Mario.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}
