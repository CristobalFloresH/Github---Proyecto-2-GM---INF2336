package puppy.code;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpTripleDisparo extends PowerUp {

    public PowerUpTripleDisparo() {
        super("PowerTriple.png");
        
    }

    @Override
    public void aplicarEfecto(Object jugador) {
        if (jugador instanceof Nave4) {
            Nave4 nave = (Nave4) jugador;
            nave.restaurarModelo(); 
            nave.activarTripleDisparo();
            nave.cambiarModelo("NaveDisparoMultiple.png");
            desactivar();
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
