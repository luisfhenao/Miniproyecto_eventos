public class BolaDeFuego extends Habilidad {
    public BolaDeFuego() {
        super("Bola de Fuego", 10);
    }

    @Override
    public void aplicar(Personaje usuario, Personaje objetivo) {
        if (!puedeUsar(usuario)) {
            System.out.println(usuario.getNombre() + " no tiene suficiente MP.");
            return;
        }

        usuario.setMp(usuario.getMP() - costoMP);
        int danio = usuario.getAtaque() + 15 - objetivo.getDefensa();
        objetivo.recibirDanio(Math.max(5, danio));
        System.out.println(usuario.getNombre() + " lanza Bola de Fuego a " + objetivo.getNombre());
    }
}
