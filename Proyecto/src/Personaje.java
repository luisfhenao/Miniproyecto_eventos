public abstract class Personaje {
    protected String nombre;
    protected int HP;
    protected int MP;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected boolean vivo = true;

    public Personaje(String nombre, int HP, int MP, int ataque, int defensa, int velociadad ){
        this.nombre = nombre;
        this.HP = HP;
        this.MP = MP;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velociadad;
    }

    // Getters 

    public String getNombre() {
        return nombre;
    }

    public int getHP() {
        return HP;
    }

    public int getMP() {
        return MP;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public boolean estaVivo() {
        return vivo;
    }
    
    public void setHp(int HP){
        this.HP = Math.max(0, HP);
        if(this.HP == 0)
        this.vivo = false;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMp(int MP){this.MP = Math.max(0, ataque);}
    public void setAtaque(int ataque){this.ataque = Math.max(0, ataque);}
    public void setDefensa(int defensa){this.defensa = Math.max(0, defensa);}
    public void setVelocidad(int velociadad){this.velocidad = Math.max(0, velociadad);}

    public void recibirDanio(int danio){
        int finalDanio = Math.max(0, danio - defensa);
        setHp(this.HP - finalDanio);
        if(!vivo){
            System.out.println(nombre + "ha muerto");
        }else{
            System.out.println(nombre + "recibe" + finalDanio + "de daño. HP restante: " + HP);
        }
    }
    public abstract void realizarTurno(Batalla batalla);
}
