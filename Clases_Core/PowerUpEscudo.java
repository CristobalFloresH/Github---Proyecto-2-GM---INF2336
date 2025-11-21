package puppy.code;

public class PowerUpEscudo extends PowerUp {

    public PowerUpEscudo() {
        super("PowerEscudo.png");
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {
        nave.activarEscudo();
    }

    @Override
    protected String getModeloNave() {
        return null;
    }
}
