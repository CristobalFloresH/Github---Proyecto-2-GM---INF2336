package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class PowerUp {
    protected Vector2 posicion;
    protected Texture textura;
    protected Rectangle hitbox;
    protected boolean activo;
    protected float velocidadY;

    public PowerUp(String rutaTextura) {
        textura = new Texture(rutaTextura);
        float posX = MathUtils.random(0, Gdx.graphics.getWidth() - textura.getWidth());
        float posY = Gdx.graphics.getHeight(); 
        posicion = new Vector2(posX, posY);
        float ancho = 32;
        float alto  = 32;
        hitbox = new Rectangle(posX, posY, ancho, alto);
        activo = true;
        velocidadY = 1f; 
    }

    public void update() {
        if (!activo) return;
        posicion.y -= velocidadY;
        hitbox.setPosition(posicion.x, posicion.y);
        if (posicion.y + hitbox.getHeight() < 0) {
            activo = false;
        }
    }


    public void render(SpriteBatch batch) {
        if (activo) {
            float ancho = hitbox.width;
            float alto  = hitbox.height;
            batch.draw(textura, posicion.x, posicion.y, ancho, alto);
        }
    }


    public boolean isActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public abstract void aplicarEfecto(Object jugador);
}
