package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class ErraticDirectionMovement implements MovementStrategy {
    private int framesToJitter = 30; // cada ~0.5s a 60fps
    private int frameCount = 0;
    private final int speedMin, speedMax;

    public ErraticDirectionMovement(int speedMin, int speedMax) {
        this.speedMin = speedMin;
        this.speedMax = speedMax;
    }

    @Override
    public void update(Ball2 b) {
        int x = b.getX();
        int y = b.getY();
        int vx = b.getXSpeed();
        int vy = b.getySpeed();

        // cada cierto tiempo “sacudimos” la dirección
        if (++frameCount >= framesToJitter) {
            frameCount = 0;
            int nvx = MathUtils.random(speedMin, speedMax) * (MathUtils.randomBoolean() ? 1 : -1);
            int nvy = MathUtils.random(speedMin, speedMax) * (MathUtils.randomBoolean() ? 1 : -1);
            // evitar quedar en (0,0)
            if (nvx == 0 && nvy == 0) nvx = speedMin;
            vx = nvx;
            vy = nvy;
            // variar próximo intervalo
            framesToJitter = MathUtils.random(20, 45);
        }

        x += vx;
        y += vy;

        if (x < 0 || x + b.getSpriteWidth() > Gdx.graphics.getWidth()) {
            vx = -vx;
            x = MathUtils.clamp(x, 0, Gdx.graphics.getWidth() - b.getSpriteWidth());
        }
        if (y < 0 || y + b.getSpriteHeight() > Gdx.graphics.getHeight()) {
            vy = -vy;
            y = MathUtils.clamp(y, 0, Gdx.graphics.getHeight() - b.getSpriteHeight());
        }

        b.setXSpeed(vx);
        b.setySpeed(vy);
        b.setPosition(x, y);
    }
}
