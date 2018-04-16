package com.td.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Monster {
    private Map map;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private int healthMonster;
    private boolean isDead;
    private Map.Route route; // есть ссылка на маршрут(routes). они все ссылаються на один и тот же маршрут
    private int routeCounter; // монстр знает сколько раз он повернул
    private int lastCellX, lastCellY; //где он повернул. та ячейка на которой мы впоследний раз повернули, чтобы 2 раза на 1-ой месте не поворачиваться

    public Vector2 getPosition() {
        return position;
    }

    public Monster(TextureAtlas atlas, Map map, int routeIndex) { //мы говорим что у тебя есть карта и ты стоиш на n-ом маршруте
        this.map = map;
        this.texture = atlas.findRegion("monster");//в случае с атласом. все они ссылаються на одну текстуру
        this.speed = 100.0f;
        this.healthMonster = 3;
        this.isDead = false;
        this.route = map.getRoutes().get(routeIndex);//наш текуший маршрут равен.запросим у карты маршрут с номером route.запоминаем маршрут
        this.position = new Vector2(route.getStartX() * 80 + 40, route.getStartY() * 80 + 40);//когда мы узнали нашу клетку мы запрашиваем начальную точку. + 40, чтобы встать в центр клетке
        this.lastCellX = route.getStartX();//последняя ячейка на которой мы повернулись это наша стартовая позиция. потом эти переменные будут меняться
        this.lastCellY = route.getStartY();
        this.routeCounter = 0; // мы говорим что последний раз мы повернули на маршруте с индексом ноль.
        this.velocity = new Vector2(route.getDirections()[0].x * speed, route.getDirections()[0].y * speed);// у стартовой точки мы спрашиваем куда мы должны пойти
    }

    public void render(SpriteBatch batch) {
        //тут теперь запрашиваем texture.getRegionWidth() длину региона и высоту
        batch.draw(texture, position.x - texture.getRegionWidth() / 2, position.y - texture.getRegionHeight() / 2);
    }

    public void update(float dt) {
        if(isdeadMonster(0)){
            position.set( -40 , -40);
            velocity.set(0,0);
        }

        position.mulAdd(velocity, dt);

        int cx = (int) (position.x / 80);//постоянно проверяем на какой клетке мы находимся
        int cy = (int) (position.y / 80);

        float dx =  Math.abs(cx * 80 + 40 - position.x);// смотрим на сколько далеко от центра клетки мы находимся?
        float dy =  Math.abs(cy * 80 + 40 - position.y);
        //если мы на развилке и растояние между ним и центром клетки меньше чем его удвоенная скорость, то тогда мы считаем что мы вошли в центр клетки
        //если это проверки не сделать, есть вероятность что монст проскочить перекресток.

        if (map.isCrossroad(cx, cy) && Vector2.dst(0, 0, dx, dy) < velocity.len() * dt * 2) {//проверяем дошли ли мы донтра точки
            if (!(lastCellX == cx && lastCellY == cy)) {//если дошли то проверяем а поворачивали мы на ней или нет
                position.set(cx * 80 + 40, cy * 80 + 40);//если мы еще не были на этой точке, то цинтрируемся на ней
                routeCounter++;// говорим что мы еще раз повернули
                lastCellX = cx;//говорим что эту клетку мы посетили
                lastCellY = cy;
                if (routeCounter > route.getDirections().length - 1) {// если бошле маршрутов(точек) нет то
                    velocity.set(0, 0);// скидиваем скорость
                    return;
                }
                //мы меняем вектор скорости и у route запрашиваем какой должен быть x,y на данном повороте для данного маршрута
                velocity.set(route.getDirections()[routeCounter].x * speed, route.getDirections()[routeCounter].y * speed);
            }
        }
    }
    public boolean isdeadMonster(int shot){
        healthMonster -= shot;
        if(healthMonster <= 0) {
            isDead = true;
        }
        return isDead;
    }
}
