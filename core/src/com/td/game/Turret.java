package com.td.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Turret {
    private Map map;
    private ArrayList<Monster> monsterList;
    private TextureRegion textureTurret;
    private Vector2 positionTurret, turretToMonster;
    int shot;
    float cooldown = 0;


    public Turret(TextureAtlas textureAtlas, Map map, ArrayList<Monster> monsterList, float x , float y) {
        this.textureTurret = textureAtlas.findRegion("turret");
        this.map = map;
        this.monsterList = monsterList;
        this.positionTurret = new Vector2(x,y);
        this.shot = 1;
    }
    public void render (SpriteBatch batch){
        batch.draw(textureTurret, positionTurret.x - textureTurret.getRegionWidth()/2, positionTurret.y - textureTurret.getRegionHeight()/2);
    }
    public void update(float dt){
        interplay(dt);
        if(Gdx.input.isTouched()){
            int cx = (Gdx.input.getX() / 80);
            int cy = ((720 - Gdx.input.getY()) / 80);
            if(map.isGrass(cx,cy)){ //метод проверки точки на то, являеться ли она травой
                positionTurret.set(cx * 80 + 40, cy * 80 + 40);
                    }else positionTurret.set( -40, -40);
        }
    }

    public void interplay(float dt){
        //вроди как в данном случае пушка стреляет по всем монстрам разом, которые находяться в ее радиусе.
        for (int i = 0; i < monsterList.size() ; i++) {
            turretToMonster = monsterList.get(i).getPosition().cpy().sub(positionTurret);
            if(turretToMonster.len() < 200 ){ //поставил 200, а не 100, чтобы в реальном тесте пушка убивала монстра.а то он сбегал с 1 хп обычно
            cooldown = cooldown + dt;
            if((int)cooldown == 1){
                monsterList.get(i).isdeadMonster(shot);
                cooldown = 0;
            }
        }}
    }
}
