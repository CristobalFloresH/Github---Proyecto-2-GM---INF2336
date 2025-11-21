package puppy.code;

public class PowerUpEscudo extends PowerUp {

    public PowerUpEscudo() {
        super("PowerEscudo.png");
    }

    @Override
    protected void prepararAplicacion(Nave4 nave) {
        // por si le agregaramos un reproducir sonido o efecto de partículas
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {
        nave.activarEscudo();
    }

    @Override
    protected String obtenerModeloNave() {
        return "NaveConEscudo.png";  
    }

    @Override
    protected void finalizarAplicacion(Nave4 nave) {
        // por si se necesita, solo es para la estructura
    }

    @Override
    public String getDescripcion() {
        return "Otorga un escudo protector a la nave.";
    }
}
