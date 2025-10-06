public class Curacion extends Habilidad {
    public Curacion() {
        super("Curación", 8);
    }

    @Override
    public void aplicar(Personaje usuario, Personaje objetivo) {
        if (!puedeUsar(usuario)) {
            System.out.println(usuario.getNombre() + " no tiene suficiente MP.");
            return;
        }

        usuario.setMp(usuario.getMP() - costoMP);
        int cantidad = usuario.getAtaque() + 20;
        objetivo.setHp(objetivo.getHP() + cantidad);
        System.out.println(usuario.getNombre() + " cura a " + objetivo.getNombre() + " por " + cantidad + " HP.");
    }
}
