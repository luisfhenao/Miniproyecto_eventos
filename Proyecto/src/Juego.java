import javax.swing.*;
import java.util.*;

public class Juego {
    
    public static void main(String[] args) {
        String nombre = JOptionPane.showInputDialog(
                null,
                "Escribe el nombre de tu héroe:",
                "Nombre del héroe",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Héroe";
        }

     
        Heroe heroePrincipal = new Heroe(nombre, 100, 30, 20, 10, 15);
        Heroe yangus = new Heroe("Yangus", 120, 10, 25, 15, 8);
        Heroe jessica = new Heroe("Jessica", 80, 40, 15, 8, 12);
        Heroe angelo = new Heroe("Angelo", 90, 35, 18, 12, 14);

        List<Heroe> heroes = new ArrayList<>();
        heroes.add(heroePrincipal);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

      
        Enemigo slime = new Enemigo("Slime", 50, 0, 10, 5, 5);
        Enemigo dracky = new Enemigo("Dracky", 60, 10, 12, 6, 10);
        Enemigo golem = new Enemigo("Golem", 120, 0, 25, 20, 4);
        Enemigo dragon = new Enemigo("Dragón", 200, 30, 35, 15, 7);

        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(slime);
        enemigos.add(dracky);
        enemigos.add(golem);
        enemigos.add(dragon);

        SwingUtilities.invokeLater(() -> {
            new InterfazBatalla(heroes, enemigos);
        });

    }
}
