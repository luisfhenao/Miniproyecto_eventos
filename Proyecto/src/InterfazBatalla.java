import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.sampled.Clip;

public class InterfazBatalla extends JFrame {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private JTextArea textoBatalla;
    private JButton btnAtacar, btnDefender, btnHabilidad, btnPasar;
    private JComboBox<String> comboHabilidades;
    private JPanel panelEstado;

    private int heroeActivoIndex = 0;
    private Clip musicaFondo;

    // Cola de mensajes para mostrar el texto en orden
    private final BlockingQueue<String> colaMensajes = new LinkedBlockingQueue<>();

    public InterfazBatalla(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;

        setTitle("Batalla RPG");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(10, 25, 60));

        
        musicaFondo = Sonido.reproducirLoop("Proyecto/Sonidos/batalla.wav");

        // Panel superior con estado de los héroes
        panelEstado = new JPanel();
        panelEstado.setLayout(new GridLayout(1, heroes.size()));
        panelEstado.setBackground(new Color(10, 25, 60));
        actualizarPanelEstado();
        add(panelEstado, BorderLayout.NORTH);

        // Área central de texto
        textoBatalla = new JTextArea();
        textoBatalla.setEditable(false);
        textoBatalla.setLineWrap(true);
        textoBatalla.setWrapStyleWord(true);
        textoBatalla.setBackground(new Color(15, 30, 70));
        textoBatalla.setForeground(Color.WHITE);
        textoBatalla.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 255), 3));
        textoBatalla.setFont(new Font("Serif", Font.BOLD, 16));
        JScrollPane scroll = new JScrollPane(textoBatalla);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelAcciones = new JPanel();
        panelAcciones.setBackground(new Color(20, 50, 100));

        btnAtacar = crearBoton("Atacar", new Color(0, 90, 200));
        btnDefender = crearBoton("Defender", new Color(30, 120, 60));
        btnHabilidad = crearBoton("Habilidad", new Color(180, 140, 0));
        btnPasar = crearBoton("Pasar", new Color(100, 100, 100));

        comboHabilidades = new JComboBox<>();
        comboHabilidades.setBackground(new Color(230, 220, 150));
        comboHabilidades.setForeground(Color.BLACK);
        comboHabilidades.setEnabled(false);

        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnDefender);
        panelAcciones.add(comboHabilidades);
        panelAcciones.add(btnHabilidad);
        panelAcciones.add(btnPasar);

        add(panelAcciones, BorderLayout.SOUTH);

        // Eventos de botones
        btnAtacar.addActionListener(e -> atacar());
        btnDefender.addActionListener(e -> defender());
        btnHabilidad.addActionListener(e -> usarHabilidad());
        btnPasar.addActionListener(e -> pasarTurno());

        // Hilo para texto ordenado
        iniciarHiloTexto();

        iniciarTurno();
        setVisible(true);
    }

    private void iniciarHiloTexto() {
        Thread hiloTexto = new Thread(() -> {
            while (true) {
                try {
                    String texto = colaMensajes.take(); 
                    for (char c : texto.toCharArray()) {
                        SwingUtilities.invokeLater(() -> textoBatalla.append(String.valueOf(c)));
                        Thread.sleep(15);
                    }
                    SwingUtilities.invokeLater(() -> {
                        textoBatalla.append("\n");
                        textoBatalla.setCaretPosition(textoBatalla.getDocument().getLength());
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        hiloTexto.setDaemon(true);
        hiloTexto.start();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Serif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        return boton;
    }

    private Heroe getHeroeActivo() {
        if (heroeActivoIndex < heroes.size()) {
            return heroes.get(heroeActivoIndex);
        }
        return null;
    }

    private void iniciarTurno() {
        Heroe heroe = getHeroeActivo();

        if (heroe == null) {
            mostrarTextoLento("--- Turno de enemigos ---");
            turnoEnemigos();
            heroeActivoIndex = 0;
            iniciarTurno();
            return;
        }

        if (!heroe.estaVivo()) {
            heroeActivoIndex++;
            iniciarTurno();
            return;
        }

        mostrarTextoLento("Turno de " + heroe.getNombre());
        actualizarPanelEstado();

        // Efecto de estado (como sueño)
        if (heroe.getEfectoActivo() != null) {
            heroe.getEfectoActivo().aplicar(heroe);
            heroe.getEfectoActivo().reducirDuracion();
            if (!heroe.getEfectoActivo().estaActivo()) heroe.setEfectoActivo(null);
            if (!heroe.puedeActuar()) {
                mostrarTextoLento(heroe.getNombre() + " está dormido y pierde el turno...");
                heroe.setPuedeActuar(true);
                avanzarTurno();
                return;
            }
        }

        comboHabilidades.removeAllItems();
        for (Habilidad h : heroe.getHabilidades()) {
            comboHabilidades.addItem(h.getNombre() + " (MP " + h.getCostoMP() + ")");
        }
        comboHabilidades.setEnabled(!heroe.getHabilidades().isEmpty());
        btnHabilidad.setEnabled(!heroe.getHabilidades().isEmpty());
        habilitarBotones(true);
    }

    private void habilitarBotones(boolean estado) {
        btnAtacar.setEnabled(estado);
        btnDefender.setEnabled(estado);
        btnHabilidad.setEnabled(estado && comboHabilidades.getItemCount() > 0);
        btnPasar.setEnabled(estado);
        comboHabilidades.setEnabled(estado && comboHabilidades.getItemCount() > 0);
    }

    private void atacar() {
        Heroe heroe = getHeroeActivo();
        if (heroe == null) return;

        Personaje enemigo = seleccionarEnemigoVivo();
        if (enemigo == null) {
            mostrarTextoLento("No quedan enemigos vivos.");
            return;
        }

        Sonido.reproducir("Proyecto/Sonidos/ataque.wav");
        mostrarTextoLento(heroe.getNombre() + " ataca a " + enemigo.getNombre() + ".");
        enemigo.recibirDanio(heroe.getAtaque());
        if (!enemigo.estaVivo()) mostrarTextoLento(enemigo.getNombre() + " ha sido derrotado.");
        verificarFinBatalla();
        avanzarTurno();
    }

    private void defender() {
        Heroe heroe = getHeroeActivo();
        if (heroe == null) return;

        heroe.setDefensa(heroe.getDefensa() + 5);
        mostrarTextoLento(heroe.getNombre() + " se defiende (Defensa +5).");
        avanzarTurno();
    }

    private void usarHabilidad() {
        Heroe heroe = getHeroeActivo();
        if (heroe == null) return;

        int idx = comboHabilidades.getSelectedIndex();
        if (idx < 0) {
            mostrarTextoLento("Selecciona una habilidad.");
            return;
        }

        Habilidad habilidad = heroe.getHabilidades().get(idx);

        if (!habilidad.puedeUsar(heroe)) {
            mostrarTextoLento("MP insuficiente para usar " + habilidad.getNombre() + ".");
            return;
        }

        Personaje objetivo = (habilidad instanceof Curacion)
                ? seleccionarHeroeVivo()
                : seleccionarEnemigoVivo();

        if (objetivo == null) {
            mostrarTextoLento("No hay objetivo válido.");
            return;
        }

        habilidad.aplicar(heroe, objetivo);
        mostrarTextoLento(heroe.getNombre() + " usa " + habilidad.getNombre() + " sobre " + objetivo.getNombre() + ".");

        if (habilidad.getNombre().equalsIgnoreCase("Curación")) {
            Sonido.reproducir("Proyecto/Sonidos/curacion.wav");
        } else if (habilidad.getNombre().equalsIgnoreCase("Dormir")) {
            Sonido.reproducir("Proyecto/Sonidos/dormir.wav");
        } else {
            Sonido.reproducir("Proyecto/Sonidos/ataque.wav");
        }

        verificarFinBatalla();
        avanzarTurno();
    }

    private void pasarTurno() {
        mostrarTextoLento(getHeroeActivo().getNombre() + " pasa el turno.");
        avanzarTurno();
    }

    private void avanzarTurno() {
        heroeActivoIndex++;
        habilitarBotones(false);
        iniciarTurno();
    }

    private Personaje seleccionarEnemigoVivo() {
        return enemigos.stream().filter(Personaje::estaVivo).findFirst().orElse(null);
    }

    private Personaje seleccionarHeroeVivo() {
        return heroes.stream().filter(Personaje::estaVivo).findFirst().orElse(null);
    }

    private void turnoEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                if (enemigo.getEfectoActivo() != null) {
                    enemigo.getEfectoActivo().aplicar(enemigo);
                    enemigo.getEfectoActivo().reducirDuracion();
                    if (!enemigo.getEfectoActivo().estaActivo()) enemigo.setEfectoActivo(null);
                    if (!enemigo.puedeActuar()) {
                        mostrarTextoLento(enemigo.getNombre() + " está dormido y pierde el turno...");
                        enemigo.setPuedeActuar(true);
                        continue;
                    }
                }

                Personaje heroe = seleccionarHeroeVivo();
                if (heroe == null) break;

                Sonido.reproducir("Proyecto/Sonidos/Ataque.wav");
                mostrarTextoLento(enemigo.getNombre() + " ataca a " + heroe.getNombre() + ".");
                heroe.recibirDanio(enemigo.getAtaque());
                if (!heroe.estaVivo()) mostrarTextoLento(heroe.getNombre() + " ha muerto.");
                verificarFinBatalla();
            }
        }
    }

    private void verificarFinBatalla() {
        boolean heroesVivos = heroes.stream().anyMatch(Personaje::estaVivo);
        boolean enemigosVivos = enemigos.stream().anyMatch(Personaje::estaVivo);

        if (!heroesVivos) {
            Sonido.reproducir("Proyecto/Sonidos/derrota.wav");
            mostrarTextoLento("Los enemigos han ganado la batalla.");
            JOptionPane.showMessageDialog(this, "Los enemigos ganaron...");
            detenerMusica();
            System.exit(0);
        } else if (!enemigosVivos) {
            Sonido.reproducir("Proyecto/Sonidos/victoria.wav");
            mostrarTextoLento("¡Los héroes han ganado la batalla!");
            JOptionPane.showMessageDialog(this, "¡Los héroes ganaron!");
            detenerMusica();
            System.exit(0);
        }
        actualizarPanelEstado();
    }

    private void mostrarTextoLento(String texto) {
        colaMensajes.add(texto);
    }

    private void actualizarPanelEstado() {
        panelEstado.removeAll();
        for (Heroe h : heroes) {
            JLabel label = new JLabel(
                "<html><center><b>" + h.getNombre() + "</b><br>" +
                "HP: " + h.getHP() + "/" + h.getMaxHP() + " | " +
                "MP: " + h.getMP() + "/" + h.getMaxMP() + "</center></html>",
                SwingConstants.CENTER
            );
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Serif", Font.BOLD, 14));
            panelEstado.add(label);
        }
        panelEstado.revalidate();
        panelEstado.repaint();
    }

    private void detenerMusica() {
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
            musicaFondo.close();
        }
    }
}
