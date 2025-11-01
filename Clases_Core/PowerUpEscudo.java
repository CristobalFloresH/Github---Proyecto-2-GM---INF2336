package puppy.code;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpEscudo extends PowerUp {

    public PowerUpEscudo() {
        super("PowerEscudo.png");
    }

    @Override
    public void aplicarEfecto(Object jugador) {
        if (jugador instanceof Nave4) {
            Nave4 nave = (Nave4) jugador;
            nave.restaurarModelo(); 
            nave.activarEscudo();
            desactivar();
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
