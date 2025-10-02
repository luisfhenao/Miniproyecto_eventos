import java.util.*;

public class Batalla {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;

    public Batalla(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }

    public void iniciar() {
        System.out.println("¡La batalla ha comenzado!");

        while (hayVivos(heroes) && hayVivos(enemigos)) {
            // Crear lista de todos los personajes que actúan este turno
            List<Personaje> turno = new ArrayList<>();
            turno.addAll(heroes);
            turno.addAll(enemigos);

            // Ordenar por velocidad (mayor primero)
            turno.sort((a, b) -> b.getVelocidad() - a.getVelocidad());

            // Cada personaje actúa si está vivo
            for (Personaje p : turno) {
                if (p.estaVivo()) {
                    p.realizarTurno(this);
                }
            }
        }

        // Resultado final
        if (hayVivos(heroes)) {
            System.out.println("\n¡Los héroes ganaron!");
        } else {
            System.out.println("\nLos enemigos ganaron...");
        }
    }

    // Método genérico para revisar si queda alguien vivo
    public boolean hayVivos(List<? extends Personaje> lista) {
        return lista.stream().anyMatch(Personaje::estaVivo);
    }

    // Seleccionar primer enemigo vivo
    public Personaje seleccionarObjetivoEnemigo() {
        return enemigos.stream()
                       .filter(Personaje::estaVivo)
                       .findFirst()
                       .orElse(null);
    }

    // Seleccionar primer héroe vivo
    public Personaje seleccionarObjetivoHeroe() {
        return heroes.stream()
                     .filter(Personaje::estaVivo)
                     .findFirst()
                     .orElse(null);
    }
}
