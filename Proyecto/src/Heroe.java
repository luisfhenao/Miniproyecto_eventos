import java.util.*;
 


public class Heroe extends Personaje {
    private List<Habilidad> habilidades = new ArrayList<>();

    public Heroe(String nombre, int HP, int MP, int ataque, int defensa, int velocidad) {
        super(nombre, HP, MP, ataque, defensa, velocidad);
        inicializarHabilidades();
    }

    private void inicializarHabilidades() {
        if (nombre.equalsIgnoreCase("Jessica")) {
            habilidades.add(new BolaDeFuego());
            habilidades.add(new Curacion());
            habilidades.add(new Dormir( )); 
        } else if (nombre.equalsIgnoreCase("Angelo")) {
            habilidades.add(new Curacion());
        } else if (nombre.equalsIgnoreCase("Yangus")) {
            habilidades.add(new GolpePesado());
        } else {
            habilidades.add(new BolaDeFuego());
            habilidades.add(new Dormir());
        }
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    @Override
    public void realizarTurno(Batalla batalla) {
        if (!vivo) return;


        if (efectoActivo != null) {
            efectoActivo.aplicar(this);
            if (!puedeActuar) {
                puedeActuar = true; 
                efectoActivo.reducirDuracion();
                if (!efectoActivo.estaActivo()) efectoActivo = null;
                System.out.println(nombre + " está afectado por " + efectoActivo.getNombre() + " y no puede actuar este turno.");
                return;
            }
            efectoActivo.reducirDuracion();
            if (!efectoActivo.estaActivo()) efectoActivo = null;
        }

   
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTurno de " + nombre);
        System.out.println("1. Atacar  2. Defender  3. Usar habilidad  4. Pasar");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                Personaje enemigo = batalla.seleccionarObjetivoEnemigo();
                if (enemigo != null) {
                    System.out.println(nombre + " ataca a " + enemigo.getNombre());
                    enemigo.recibirDanio(ataque);
                }
                break;

            case 2:
                defensa += 5;
                System.out.println(nombre + " se defiende (+5 defensa)");
                break;

            case 3:
                if (habilidades.isEmpty()) {
                    System.out.println(" No tienes habilidades.");
                    break;
                }
                System.out.println("\n--- Habilidades disponibles ---");
                for (int i = 0; i < habilidades.size(); i++) {
                    Habilidad h = habilidades.get(i);
                    System.out.println((i + 1) + ". " + h.getNombre() + " (MP " + h.getCostoMP() + ")");
                }
                System.out.print(" Elige habilidad: ");
                int eleccion = sc.nextInt();

                if (eleccion >= 1 && eleccion <= habilidades.size()) {
                    Habilidad habilidad = habilidades.get(eleccion - 1);

                    if (!habilidad.puedeUsar(this)) {
                        System.out.println(" ⚠️ MP insuficiente para usar " + habilidad.getNombre());
                        break;
                    }

                    Personaje objetivo = (habilidad instanceof Curacion)
                            ? batalla.seleccionarObjetivoHeroe()
                            : batalla.seleccionarObjetivoEnemigo();

                    if (objetivo != null) {
                        habilidad.aplicar(this, objetivo);
                    }
                } else {
                    System.out.println(" Opcion invalida.");
                }
                break;

            default:
                System.out.println(nombre + " pasa su turno.");
                break;
        }
    }
}
