import java.util.*;

public class Juego {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creamos héroe principal con nombre por defecto
        Heroe heroePrincipal = new Heroe("Héroe", 100, 30, 20, 10, 15);

        // Preguntamos si quiere renombrar
        System.out.print("Escribe el nombre de tu héroe: ");
        String nuevoNombre = sc.nextLine();
        heroePrincipal.setNombre(nuevoNombre);

        List<Heroe> heroes = Arrays.asList(
            heroePrincipal,
            new Heroe("Yangus", 120, 10, 25, 15, 8),
            new Heroe("Jessica", 80, 40, 15, 8, 12),
            new Heroe("Angelo", 90, 35, 18, 12, 14)
        );

        List<Enemigo> enemigos = Arrays.asList(
            new Enemigo("Slime", 50, 0, 10, 5, 5),
            new Enemigo("Dracky", 60, 10, 12, 6, 10),
            new Enemigo("Golem", 120, 0, 25, 20, 4),
            new Enemigo("Dragon", 200, 30, 35, 15, 7)
        );

        Batalla batalla = new Batalla(heroes, enemigos);
        batalla.iniciar();
    }
}
