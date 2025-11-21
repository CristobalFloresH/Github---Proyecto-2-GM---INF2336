public class PowerUpSpeed extends PowerUp {
    
    public PowerUpSpeed() {
        super("PowerSpeed.png");
    }

    @Override
    protected void aplicarEfectoEspecifico(Nave4 nave) {
        nave.aumentarVelocidad();
    }

    @Override
    protected String obtenerModeloNave() {
        return "NaveVelocidad.png";
    }
}
