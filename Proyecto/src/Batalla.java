import java.util.*;

public class Batalla {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private List<String> eventos = new ArrayList<>();

    public Batalla(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }
    

    // 🔹 Registra los mensajes en lugar de imprimirlos
    public void registrarEvento(String texto) {
        eventos.add(texto);
    }

    public List<String> getEventos() {
        return eventos;
    }

    // 🔹 Ejecuta un solo turno (usado por el botón en la interfaz)
    public void ejecutarTurno() {
        List<Personaje> turno = new ArrayList<>();
        turno.addAll(heroes);
        turno.addAll(enemigos);

        // Ordenar por velocidad (mayor primero)
        turno.sort((a, b) -> b.getVelocidad() - a.getVelocidad());

        for (Personaje p : turno) {
            if (p.estaVivo()) {
                p.realizarTurno(this);
            }
        }

        // Mensaje de estado al final del turno
        if (!hayVivos(heroes) || !hayVivos(enemigos)) {
            if (hayVivos(heroes)) {
                registrarEvento("\n🏆 ¡Los héroes ganaron!");
            } else {
                registrarEvento("\n💀 Los enemigos ganaron...");
            }
        }
    }

    // 🔹 Indica si la batalla sigue activa
    public boolean batallaActiva() {
        return hayVivos(heroes) && hayVivos(enemigos);
    }

    // 🔹 Métodos de selección de objetivos
    public Personaje seleccionarObjetivoEnemigo() {
        return enemigos.stream()
                .filter(Personaje::estaVivo)
                .findFirst()
                .orElse(null);
    }

    public Personaje seleccionarObjetivoHeroe() {
        return heroes.stream()
                .filter(Personaje::estaVivo)
                .findFirst()
                .orElse(null);
    }

    // 🔹 Verifica si hay personajes vivos en una lista
    public boolean hayVivos(List<? extends Personaje> lista) {
        return lista.stream().anyMatch(Personaje::estaVivo);
    }
}
