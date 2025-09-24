public abstract class Personaje {
    protected String nombre;
    protected int HP;
    protected int MP;
    protected int ataque;
    protected int defensa;
    protected int velociadad;

    public Personaje(String nombre, int HP, int MP, int ataque, int defensa, int velociadad ){
        this.nombre = nombre;
        this.HP = HP;
        this.MP = MP;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velociadad = velociadad;
    }
    
}
