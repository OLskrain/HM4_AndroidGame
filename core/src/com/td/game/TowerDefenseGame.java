package com.td.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

public class TowerDefenseGame extends ApplicationAdapter {
	SpriteBatch batch;
	Map map;
	Monster monster1;
	Monster monster2;
	ArrayList<Monster> monsterList;
	TextureAtlas atlas;
	Turret turret;


	// 1. Разбор кода
	// 2. При нажатии кнопки на карте, в то место должна переместиться пушка
	// ее можно ставить только на землю
	// 3. Если монстр подходит к пушке на расстояние 100 пикселей, она должна
	// "выстрелить в него" уменьшив у него здоровье, стрельба производится
	// каждые 1 секунду, у монстров 3 ед. здоровья
	// *. Пушка должна поворачиваться в сторону монстра

	@Override
	public void create () {
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("game.pack"));
		map = new Map(atlas);
		monsterList = new ArrayList<Monster>();

		monster1 = new Monster(atlas, map, 0);//монстр идет по 0-му маршруту
		monster2 = new Monster(atlas, map, 1);//по первому маршруту
		monsterList.add(monster1);
		monsterList.add(monster2);
		turret = new Turret(atlas, map,monsterList, -40 , -40);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		monster1.render(batch);
		monster2.render(batch);
		turret.render(batch);
		batch.end();
	}

	public void update(float dt) {
		monster1.update(dt);
		monster2.update(dt);
		turret.update(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
