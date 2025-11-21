package puppy.code;

public class PowerUpSpeed extends PowerUp {

    public PowerUpSpeed() {
        super("PowerSpeed.png");
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {
        nave.aumentarVelocidad();
    }

    @Override
    protected String getModeloNave() {
        return "NaveVelocidad.png";
    }
}
