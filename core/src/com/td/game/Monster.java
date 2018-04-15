package com.td.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster {
    private Map map;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private Map.Route route;
    private int routeCounter;
    private int lastCellX, lastCellY;

    public Monster(TextureAtlas atlas, Map map, int routeIndex) {
        this.map = map;
        this.texture = atlas.findRegion("monster");
        this.speed = 100.0f;
        this.route = map.getRoutes().get(routeIndex);
        this.position = new Vector2(route.getStartX() * 80 + 40, route.getStartY() * 80 + 40);
        this.lastCellX = route.getStartX();
        this.lastCellY = route.getStartY();
        this.routeCounter = 0;
        this.velocity = new Vector2(route.getDirections()[0].x * speed, route.getDirections()[0].y * speed);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getRegionWidth() / 2, position.y - texture.getRegionHeight() / 2);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);

        int cx = (int) (position.x / 80);
        int cy = (int) (position.y / 80);

        float dx = (float) Math.abs(cx * 80 + 40 - position.x);
        float dy = (float) Math.abs(cy * 80 + 40 - position.y);

        if (map.isCrossroad(cx, cy) && Vector2.dst(0, 0, dx, dy) < velocity.len() * dt * 2) {
            if (!(lastCellX == cx && lastCellY == cy)) {
                position.set(cx * 80 + 40, cy * 80 + 40);
                routeCounter++;
                lastCellX = cx;
                lastCellY = cy;
                if (routeCounter > route.getDirections().length - 1) {
                    velocity.set(0, 0);
                    return;
                }
                velocity.set(route.getDirections()[routeCounter].x * speed, route.getDirections()[routeCounter].y * speed);
            }
        }
    }
}
