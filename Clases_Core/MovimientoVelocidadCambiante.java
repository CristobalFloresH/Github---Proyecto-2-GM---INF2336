package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class MovimientoVelocidadCambiante implements MovementStrategy {
    private boolean inited = false;
    private int baseMagX;   
    private int baseMagY;   
    private float t = 0f;

    // escala = base + amplitude * sin(2π * freqHz * t)
    private final float base;       // 1.0f = mantiene la magnitud base
    private final float amplitude;  // 0.5f = ±50% sobre la base
    private final float freqHz;     // cambios por segundo
    private final int   cap;        // límite superior por seguridad

    public MovimientoVelocidadCambiante(float base, float amplitude) {
        this(base, amplitude, 0.8f, 10); 
    }
    public MovimientoVelocidadCambiante(float base, float amplitude, float freqHz, int cap) {
        this.base = base;
        this.amplitude = amplitude;
        this.freqHz = freqHz;
        this.cap = cap;
    }

    @Override
    public void update(Ball2 b) {
        if (!inited) {
            baseMagX = Math.max(1, Math.abs(b.getXSpeed()));
            baseMagY = Math.max(1, Math.abs(b.getySpeed()));
            inited = true;
        }

        float dt = Gdx.graphics.getDeltaTime();
        t += dt;
        float omega = MathUtils.PI2 * freqHz;

        float scale = base + amplitude * MathUtils.sin(omega * t);


        int magX = MathUtils.clamp(Math.round(baseMagX * scale), 1, cap);
        int magY = MathUtils.clamp(Math.round(baseMagY * scale), 1, cap);


        int signX = (b.getXSpeed() >= 0) ? 1 : -1;
        int signY = (b.getySpeed() >= 0) ? 1 : -1;

        int vx = signX * magX;
        int vy = signY * magY;

        int x = b.getX() + vx;
        int y = b.getY() + vy;


        if (x < 0 || x + b.getSpriteWidth() > Gdx.graphics.getWidth()) {
            vx = -vx;
            x = MathUtils.clamp(x, 0, Gdx.graphics.getWidth() - 44);
        }
        if (y < 0 || y + b.getSpriteHeight() > Gdx.graphics.getHeight()) {
            vy = -vy;
            y = MathUtils.clamp(y, 0, Gdx.graphics.getHeight() - 48);
        }

        b.setXSpeed(vx);
        b.setySpeed(vy);
        b.setPosition(x, y);
    }
}
