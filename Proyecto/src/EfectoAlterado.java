
public class EfectoAlterado {
    private String nombre;
    private int duracion;
    private int valor;
    private int turnosRestantes;
    private int ataqueOriginal;

    public EfectoAlterado(String nombre, int duracion, int valor) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.turnosRestantes = duracion;
        this.valor = valor;
        this.ataqueOriginal = 0;
    }

    public String getNombre() { return nombre; }
    public boolean estaActivo() { return turnosRestantes > 0; }

    public void reducirDuracion() { turnosRestantes--; }

    public void aplicar(Personaje objetivo) {
        switch (nombre.toLowerCase()) {
            case "quemadura":
                objetivo.recibirDanio(valor);
                System.out.println(objetivo.getNombre() + " sufre " + valor + " de daño por la quemadura.");
                break;
            case "bendicion":
                if (turnosRestantes == duracion) {
                    ataqueOriginal = objetivo.getAtaque();
                    objetivo.setAtaque(objetivo.getAtaque() + valor);
                    System.out.println(objetivo.getNombre() + " recibe una bendición. Ataque +"+valor+" durante "+duracion+" turnos.");
                } else if (turnosRestantes == 1) {
                    objetivo.setAtaque(ataqueOriginal);
                    System.out.println("La bendición en " + objetivo.getNombre() + " se desvanece.");
                }
                break;
            case "paralisis":
                System.out.println(objetivo.getNombre() + " está paralizado y no puede atacar este turno.");
                break;
             case "sueño":
           
            if (Math.random() < 0.5) {
                System.out.println(objetivo.getNombre() + " sigue dormido y pierde el turno.");
                objetivo.setPuedeActuar(false);
            } else {
                System.out.println(objetivo.getNombre() + " se ha despertado del sueño.");
                turnosRestantes = 0; 
                objetivo.setPuedeActuar(true);
            }
            break;
        }
        reducirDuracion();
    }
}
