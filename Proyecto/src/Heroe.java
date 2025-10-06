import java.util.Scanner;
public class Heroe extends Personaje {
    public Heroe (String nombre, int HP, int MP, int ataque, int defensa, int velociadad ){
        super(nombre, HP, MP, ataque, defensa, velociadad);
        inicializarHabilidades();
    } 
    public void realizarTurno(Batalla batalla){
        if(!vivo) return;

        Scanner sc = new Scanner(System.in);
        System.out.println("\n Turno de "+ nombre);
        System.out.println("1. Atacar  2. Defender  3. Usar habilidad  4. Pasar");
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
    case 3:
        if (habilidades.isEmpty()) {
            System.out.println("No tienes habilidades.");
            break;
        }
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad h = habilidades.get(i);
            System.out.println((i + 1) + ". " + h.getNombre() + " (MP " + h.getCostoMP() + ")");
        }
        System.out.print("Elige habilidad: ");
        int eleccion = sc.nextInt();
        if (eleccion >= 1 && eleccion <= habilidades.size()) {
            Habilidad h = habilidades.get(eleccion - 1);
            Personaje objetivo = h instanceof Curacion ? batalla.seleccionarObjetivoHeroe() : batalla.seleccionarObjetivoEnemigo();
            if (objetivo != null) {
                h.aplicar(this, objetivo);
            }
        }
        break;
}


    }
}
private List<Habilidad> habilidades = new ArrayList<>();
private void inicializarHabilidades() {
    if (nombre.equals("Jessica")) {
        habilidades.add(new BolaDeFuego());
        habilidades.add(new Curacion());
    } else if (nombre.equals("Angelo")) {
        habilidades.add(new Curacion());
    } else if (nombre.equals("Yangus")) {
        habilidades.add(new GolpePesado());
    } else {
        habilidades.add(new BolaDeFuego()); // Héroe principal
    }
}


