/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego Mendez
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import modelo.Directorio;
import modelo.SistemaDeArchivos;

public class GestorPersistencia {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guarda la raíz (Directorio) a un archivo JSON
    public static void guardarDirectorioRaiz(Directorio raiz, String rutaArchivo) throws IOException {
        try (FileWriter fw = new FileWriter(rutaArchivo)) {
            gson.toJson(raiz, fw);
        }
    }

    // Carga la raíz desde un JSON
    public static Directorio cargarDirectorioRaiz(String rutaArchivo) throws IOException {
        try (FileReader fr = new FileReader(rutaArchivo)) {
            Directorio raiz = gson.fromJson(fr, Directorio.class);
            return raiz;
        }
    }
    
    public static void reconstruirPadres(Directorio directorio) {
    for (int i = 0; i < directorio.getContenido().getTamano(); i++) {
        EntradaSistemaArchivos hijo = directorio.getContenido().get(i);
        // asigna el padre (necesitarás un setter o campo accesible)
        hijo.padre = directorio; // si 'padre' es protected, esto debe estar en mismo paquete o hacer setter

        if (hijo instanceof Directorio) {
            reconstruirPadres((Directorio) hijo);
        }
    }
    
    
}
    
    public static void restaurarEstadoDisco(Directorio raiz, DiscoSimulado disco) {
    // Primero limpia disco
    for (int i = 0; i < disco.bloques.length; i++) {
        disco.liberarBloque(i); // tu método ya resetea estado e idSiguienteBloque
    }

    // Recorre todos los archivos
    recorrerYMarcar(raiz, disco);
}
    
   private static void recorrerYMarcar(Directorio dir, DiscoSimulado disco) {
    for (int i = 0; i < dir.getContenido().getTamano(); i++) {
        EntradaSistemaArchivos e = dir.getContenido().get(i);

        if (e instanceof Archivo) {
            Archivo archivo = (Archivo) e;

            int id = archivo.getIdBloqueInicial();
            int contador = 0;

            while (id != -1 && contador < archivo.getTamanoEnBloques()) {
                disco.ocuparBloque(id);
                id = disco.bloques[id].idSiguienteBloque;
                contador++;
            }
        } 
        else if (e instanceof Directorio) {
            recorrerYMarcar((Directorio) e, disco);
        }
    }

}
}
