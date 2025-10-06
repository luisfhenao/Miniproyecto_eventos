public class GolpePesado extends Habilidad {
    public GolpePesado() {
        super("Golpe Pesado", 6); // Cuesta 6 MP
    }

    public void aplicar(Personaje usuario, Personaje objetivo) {
        if (!puedeUsar(usuario)) {
            System.out.println(usuario.getNombre() + " no tiene suficiente MP para usar " + nombre + ".");
            return;
        }

        usuario.setMp(usuario.getMP() - costoMP);

        // Golpe físico con bonificación de daño
        int danioBase = usuario.getAtaque() + 10;
        int danioFinal = Math.max(5, danioBase - objetivo.getDefensa());

        System.out.println(usuario.getNombre() + " usa " + nombre + " contra " + objetivo.getNombre() + ".");
        objetivo.recibirDanio(danioFinal);
    }
}
