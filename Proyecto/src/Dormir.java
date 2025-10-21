
public class Dormir extends Habilidad  {

    public Dormir() {
        super("Dormir", 5); 
    }

    @Override
    public void aplicar(Personaje usuario, Personaje objetivo) {
  
        if (!puedeUsar(usuario)) {
            System.out.println(usuario.getNombre() + " no tiene suficiente MP para usar " + getNombre() + ".");
            return;
        }

        usuario.setMp(usuario.getMP() - getCostoMP()); 

     
        if (objetivo.getEfectoActivo() != null && objetivo.getEfectoActivo().estaActivo()) {
            System.out.println(objetivo.getNombre() + " ya tiene un efecto activo.");
            return;
        }

        EfectoAlterado sueno = new EfectoAlterado("Sueño", 3, 0);
        objetivo.setEfectoActivo(sueno);

        System.out.println(usuario.getNombre() + " lanza " + getNombre() + " sobre " + objetivo.getNombre() + "!");
        System.out.println(objetivo.getNombre() + " se ha quedado dormido...");
    }
}
