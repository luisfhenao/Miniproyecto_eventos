public abstract class Habilidad {
    protected String nombre;
    protected int costoMP;

    public Habilidad(String nombre, int costoMP) {
        this.nombre = nombre;
        this.costoMP = costoMP;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCostoMP() {
        return costoMP;
    }

    public boolean puedeUsar(Personaje usuario) {
        return usuario.getMP() >= costoMP;
    }

    public abstract void aplicar(Personaje usuario, Personaje objetivo);
}
