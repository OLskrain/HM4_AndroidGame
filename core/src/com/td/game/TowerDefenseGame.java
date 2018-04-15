package com.td.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TowerDefenseGame extends ApplicationAdapter {
	SpriteBatch batch;
	Map map;
	Monster monster1;
	Monster monster2;
	TextureAtlas atlas;

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

		monster1 = new Monster(atlas, map, 0);
		monster2 = new Monster(atlas, map, 1);
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
		batch.end();
	}

	public void update(float dt) {
		monster1.update(dt);
		monster2.update(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
