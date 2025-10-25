package puppy.code;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpSpeed extends PowerUp {

    public PowerUpSpeed() {
        super("PowerSpeed.png");         

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
