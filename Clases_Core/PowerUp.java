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
        hitbox = new Rectangle(posX, posY, 32, 32);
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
            batch.draw(textura, posicion.x, posicion.y, hitbox.width, hitbox.height);
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

    
    public final void aplicar(Object jugador) {
        if (!esJugadorValido(jugador)) return;

        Nave4 nave = (Nave4) jugador;

        prepararAplicacion(nave);
        aplicarEfecto(nave);
        aplicarCambioVisual(nave);
        aplicarCambiosNumericos(nave);
        finalizarAplicacion(nave);

        desactivar();
    }

    
    protected boolean esJugadorValido(Object jugador) {
        return jugador instanceof Nave4;
    }

    protected void prepararAplicacion(Nave4 nave) {
    }

    protected abstract void aplicarEfecto(Nave4 nave);

    protected void aplicarCambioVisual(Nave4 nave) {
        String modelo = obtenerModeloNave();
        if (modelo != null) {
            nave.cambiarModelo(modelo);
        }
    }

    protected String obtenerModeloNave() {
        return null;
    }

    protected void aplicarCambiosNumericos(Nave4 nave) {
    }

    protected void finalizarAplicacion(Nave4 nave) {
    }

    public abstract String getDescripcion();
}
