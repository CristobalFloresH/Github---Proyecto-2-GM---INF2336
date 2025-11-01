package puppy.code;

import com.badlogic.gdx.Gdx;

public class MovimientoDefault implements MovementStrategy {
    @Override
    public void update(Ball2 b) {
        int x = b.getX();
        int y = b.getY();
        int vx = b.getXSpeed();
        int vy = b.getySpeed();
        int w  = b.getSpriteWidth();
        int h  = b.getSpriteHeight();
        int sw = Gdx.graphics.getWidth();
        int sh = Gdx.graphics.getHeight();

        x += vx;
        y += vy;


        if (x < 0) { x = 0; vx = -vx; }
        else if (x + w > sw) { x = sw - w; vx = -vx; }

        if (y < 0) { y = 0; vy = -vy; }
        else if (y + h > sh) { y = sh - h; vy = -vy; }

       

        b.setXSpeed(vx);
        b.setySpeed(vy);
        b.setPosition(x, y);
    }
}
