import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sonido {

    
    public static void reproducir(String ruta) {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                System.out.println(" No se encontró el archivo de sonido: " + ruta);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println(" Formato de audio no soportado.");
        } catch (IOException e) {
            System.out.println(" Error leyendo el archivo de sonido.");
        } catch (LineUnavailableException e) {
            System.out.println(" Línea de audio no disponible.");
        }
    }

    //  Reproducir música en bucle (para fondo de batalla)
    public static Clip reproducirLoop(String ruta) {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                System.out.println(" No se encontró el archivo de música: " + ruta);
                return null;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            System.out.println(" Musica en bucle: " + ruta);
            return clip; 

        } catch (UnsupportedAudioFileException e) {
            System.out.println(" Formato de audio no soportado.");
        } catch (IOException e) {
            System.out.println(" Error leyendo el archivo de música.");
        } catch (LineUnavailableException e) {
            System.out.println(" Línea de audio no disponible.");
        }
        return null;
    }
}

