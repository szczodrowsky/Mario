package com.mygdx.game.lv1.cos.Mario.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;

    }
}
