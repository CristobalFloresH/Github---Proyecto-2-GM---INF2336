package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpSpeed extends PowerUp {

    private float duracion;

    public PowerUpSpeed() {
        super("PowerSpeed.png"); 
        this.duracion = 5f;             

    }
    
    @Override
    public void aplicarEfecto(Object jugador) {
      
        if (jugador instanceof Nave4) {
            Nave4 nave = (Nave4) jugador;
            nave.cambiarModelo("NaveVelocidad.png"); // 
            nave.aumentarVelocidad();
            desactivar();
        }
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    
}
