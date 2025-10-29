package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class CircularMovement implements MovementStrategy {
    private final float cx, cy;     // centro de la órbita
    private final float radius;
    private final float angularSpeed; // grados por frame aprox
    private float angleDeg;

    public CircularMovement(float startX, float startY, float radius, float angularSpeedDeg) {
        this.cx = startX;
        this.cy = startY;
        this.radius = radius;
        this.angularSpeed = angularSpeedDeg;
        this.angleDeg = MathUtils.random(0f, 360f);
    }

    @Override
    public void update(Ball2 b) {
        angleDeg += angularSpeed;
        float x = cx + radius * MathUtils.cosDeg(angleDeg) - b.getSpriteWidth()  / 2f;
        float y = cy + radius * MathUtils.sinDeg(angleDeg) - b.getSpriteHeight() / 2f;

        b.setPosition(Math.round(x), Math.round(y));

        // en circular el “speed” lineal no aplica; mantenemos estados coherentes
        b.setXSpeed(0);
        b.setySpeed(0);
    }
}
