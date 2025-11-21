package puppy.code;

public class PowerUpSpeed extends PowerUp {

    private float boost = 2.0f;

    public PowerUpSpeed() {
        super("PowerSpeed.png");
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {
        nave.setVelocidad(nave.getVelocidad() * boost);
    }

    @Override
    protected void aplicarCambioVisual(Nave4 nave) {
        // en este caso no cambia textura 
    }

    @Override
    protected void finalizarAplicacion(Nave4 nave) {
        // para darle estructura 
    }

    @Override
    public String getDescripcion() {
        return "Incrementa la velocidad de movimiento.";
    }
}
