import java.util.Scanner;
public class Heroe extends Personaje {
    public Heroe (String nombre, int HP, int MP, int ataque, int defensa, int velociadad ){
        super(nombre, HP, MP, ataque, defensa, velociadad);
    } 
    @Override
    public void realizarTurno(Batalla batalla){
        if(!vivo) return;

        Scanner sc = new Scanner(System.in);
        System.out.println("\n Turno de "+ nombre);
        System.out.println("1. Atacar  2. Defender  3. Pasar");
        int opcion = sc.nextInt();

        switch (opcion) {
    case 1:
        Personaje enemigo = batalla.seleccionarObjetivoEnemigo();
        if (enemigo != null) {
            enemigo.recibirDanio(ataque);
        }
        break;

    case 2:
        defensa += 5;
        System.out.println(nombre + " se defiende (defensa +5)");
        break;

    default:
        System.out.println(nombre + " pasa su turno");
        break;
}


    }
}
