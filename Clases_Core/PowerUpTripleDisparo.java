public class PowerUpTripleDisparo extends PowerUp {
    
    public PowerUpTripleDisparo() {
        super("PowerTriple.png");
    }

    @Override
    protected void aplicarEfectoEspecifico(Nave4 nave) {
        nave.activarTripleDisparo();
    }

    @Override
    protected String obtenerModeloNave() {
        return "NaveDisparoMultiple.png";
    }
}
