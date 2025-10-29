package puppy.code;

import com.badlogic.gdx.Gdx;

public class DefaultBounceMovement implements MovementStrategy {
    @Override
    public void update(Ball2 b) {
        int x = b.getX();
        int y = b.getY();
        int vx = b.getXSpeed();
        int vy = b.getySpeed();

        x += vx;
        y += vy;

        if (x + vx < 0 || x + vx + b.getSpriteWidth() > Gdx.graphics.getWidth())
            vx = -vx;
        if (y + vy < 0 || y + vy + b.getSpriteHeight() > Gdx.graphics.getHeight())
            vy = -vy;

        b.setXSpeed(vx);
        b.setySpeed(vy);
        b.setPosition(x, y);
    }
}
