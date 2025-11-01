package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;


    private MovementStrategy movement;


    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx, MovementStrategy movement) {
        spr = new Sprite(tx);

        this.x = x;
        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;

        this.y = y;
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;

        spr.setPosition(this.x, this.y);

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.movement = (movement != null) ? movement : new MovimientoDefault();
    }


    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        this(x, y, size, xSpeed, ySpeed, tx, new MovimientoDefault());
    }


    public void update() {
        movement.update(this);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }


    public void checkCollision(Ball2 b2) {
        Rectangle r1 = spr.getBoundingRectangle();
        Rectangle r2 = b2.spr.getBoundingRectangle();

        if (!r1.overlaps(r2)) return;


        float c1x = r1.x + r1.width  / 2f;
        float c1y = r1.y + r1.height / 2f;
        float c2x = r2.x + r2.width  / 2f;
        float c2y = r2.y + r2.height / 2f;

        float dx = c1x - c2x;
        float dy = c1y - c2y;

        float overlapX = (r1.width  / 2f + r2.width  / 2f) - Math.abs(dx);
        float overlapY = (r1.height / 2f + r2.height / 2f) - Math.abs(dy);


        int pad = 1;

        int x1 = getX();
        int y1 = getY();
        int x2 = b2.getX();
        int y2 = b2.getY();

        int vx1 = getXSpeed();
        int vy1 = getySpeed();
        int vx2 = b2.getXSpeed();
        int vy2 = b2.getySpeed();


        if (overlapX < overlapY) {
            int push = Math.round(overlapX / 2f) + pad;
            if (dx > 0) {

                setPosition(x1 + push, y1);
                b2.setPosition(x2 - push, y2);
            } else {
                setPosition(x1 - push, y1);
                b2.setPosition(x2 + push, y2);
            }

            if (vx1 == 0 && vx2 == 0) { vx1 = 1; vx2 = -1; } 
            setXSpeed(-vx1);
            b2.setXSpeed(-vx2);
        } else {
            int push = Math.round(overlapY / 2f) + pad;
            if (dy > 0) {

                setPosition(x1, y1 + push);
                b2.setPosition(x2, y2 - push);
            } else {
                setPosition(x1, y1 - push);
                b2.setPosition(x2, y2 + push);
            }

            if (vy1 == 0 && vy2 == 0) { vy1 = 1; vy2 = -1; } 
            setySpeed(-vy1);
            b2.setySpeed(-vy2);
        }
    }




    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int nx, int ny) {
        this.x = nx;
        this.y = ny;
        spr.setPosition(nx, ny);
    }

    public int getSpriteWidth()  { return (int) spr.getWidth(); }
    public int getSpriteHeight() { return (int) spr.getHeight(); }

    public int getXSpeed() { return xSpeed; }
    public void setXSpeed(int xSpeed) { this.xSpeed = xSpeed; }

    public int getySpeed() { return ySpeed; }
    public void setySpeed(int ySpeed) { this.ySpeed = ySpeed; }


    public void setMovementStrategy(MovementStrategy movement) {
        if (movement != null) this.movement = movement;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }
    
    public Sprite getSprite() {
        return spr;
    }

}
