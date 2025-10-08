

public class Enemigo extends Personaje {
    public Enemigo(String nombre, int HP, int MP, int ataque, int defensa, int velociadad ){
        super(nombre, HP, MP, ataque, defensa, velociadad);
    }
    @Override
    public void realizarTurno(Batalla batalla){
        if(!vivo) return;

       
        Personaje heroe = batalla.seleccionarObjetivoHeroe();
        if(heroe != null){
            System.out.println(nombre + " ataca a " + heroe.getNombre());
            heroe.recibirDanio(ataque);
        }
    }
}
