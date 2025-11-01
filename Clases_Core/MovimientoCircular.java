package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class MovimientoCircular implements MovementStrategy {
    private final float cx, cy;     
    private final float radio;
    private final float VelocidadAngular; 
    private float Angulo;

    public MovimientoCircular(float startX, float startY, float radio, float VelocidadAngularDeg) {
        this.cx = startX;
        this.cy = startY;
        this.radio = radio;
        this.VelocidadAngular = VelocidadAngularDeg;
        this.Angulo = MathUtils.random(0f, 360f);
    }

    @Override
    public void update(Ball2 b) {
        Angulo += VelocidadAngular;
        float x = cx + radio * MathUtils.cosDeg(Angulo) - 44  / 2f;
        float y = cy + radio * MathUtils.sinDeg(Angulo) - 48 / 2f;

        b.setPosition(Math.round(x), Math.round(y));

        b.setXSpeed(0);
        b.setySpeed(0);
    }
}
