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

    // Nueva: estrategia de movimiento (DIP)
    private MovementStrategy movement;

    // Constructor nuevo: permite inyectar estrategia
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

        // si viene null, usa la estrategia por defecto (rebote clásico)
        this.movement = (movement != null) ? movement : new DefaultBounceMovement();
    }

    // Constructor original (compatibilidad con tu código existente)
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        this(x, y, size, xSpeed, ySpeed, tx, new DefaultBounceMovement());
    }

    // Delegación a la estrategia (OCP)
    public void update() {
        movement.update(this);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Rebote entre meteoros (se mantiene igual que tu versión)
    public void checkCollision(Ball2 b2) {
        if (spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())) {
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + b2.getXSpeed() / 2);
            if (b2.getXSpeed() == 0) b2.setXSpeed(b2.getXSpeed() + getXSpeed() / 2);
            setXSpeed(-getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());

            if (getySpeed() == 0) setySpeed(getySpeed() + b2.getySpeed() / 2);
            if (b2.getySpeed() == 0) b2.setySpeed(b2.getySpeed() + getySpeed() / 2);
            setySpeed(-getySpeed());
            b2.setySpeed(-b2.getySpeed());
        }
    }

    // --- Métodos de apoyo usados por estrategias y por tu lógica actual ---

    public int getX() { return x; }
    public int getY() { return y; }

    /** Actualiza posición interna y del sprite (para estrategias). */
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

    /** (Opcional) Permite cambiar la estrategia en caliente si quieres power-ups de IA. */
    public void setMovementStrategy(MovementStrategy movement) {
        if (movement != null) this.movement = movement;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }
}
