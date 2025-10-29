package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class SpeedOscillatingMovement implements MovementStrategy {
    private float t = 0f;
    private final float base;
    private final float amplitude; // cuánto varía sobre la base

    public SpeedOscillatingMovement(float base, float amplitude) {
        this.base = base;
        this.amplitude = amplitude;
    }

    @Override
    public void update(Ball2 b) {
        t += 0.05f; // “tiempo” discreto por frame
        // escala de velocidad entre [base - amplitude, base + amplitude]
        float scale = base + amplitude * MathUtils.sin(t);

        int vx = MathUtils.clamp(Math.round(Math.signum(b.getXSpeed()) * Math.max(1, Math.abs(b.getXSpeed()) * scale)), -10, 10);
        int vy = MathUtils.clamp(Math.round(Math.signum(b.getySpeed()) * Math.max(1, Math.abs(b.getySpeed()) * scale)), -10, 10);

        int x = b.getX() + vx;
        int y = b.getY() + vy;

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
