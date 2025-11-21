package puppy.code;

public class PowerUpTripleDisparo extends PowerUp {

    public PowerUpTripleDisparo() {
        super("PowerTriple.png");
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {
        nave.activarTripleDisparo();
    }

    @Override
    protected String getModeloNave() {
        return "NaveDisparoMultiple.png";
    }
}
