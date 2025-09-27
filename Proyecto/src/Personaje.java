public abstract class Personaje {
    protected String nombre;
    protected int HP;
    protected int MP;
    protected int ataque;
    protected int defensa;
    protected int velocidad;

    public Personaje(String nombre, int HP, int MP, int ataque, int defensa, int velocidad ){
        this.nombre = nombre;
        this.HP = HP;
        this.MP = MP;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velociadad = velocidad;
    }
    
}
