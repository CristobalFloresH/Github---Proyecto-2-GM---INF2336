public class PowerUpEscudo extends PowerUp {
    
    public PowerUpEscudo() {
        super("PowerEscudo.png");
    }

    @Override
    protected void aplicarEfectoEspecifico(Nave4 nave) {
        nave.activarEscudo();
    }
}
