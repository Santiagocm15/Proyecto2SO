package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class GestorPersistencia {

    // Configuración del RuntimeTypeAdapterFactory para distinguir subtipos
    private static final RuntimeTypeAdapterFactory<EntradaSistemaArchivos> entradaFactory =
            RuntimeTypeAdapterFactory.of(EntradaSistemaArchivos.class, "tipo")
                    .registerSubtype(Archivo.class, "Archivo")
                    .registerSubtype(Directorio.class, "Directorio");

    // Gson configurado con el TypeAdapterFactory
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(entradaFactory)
            .setPrettyPrinting()
            .create();

    // Guarda la raíz (Directorio) a un archivo JSON
    public static void guardarDirectorioRaiz(Directorio raiz, String rutaArchivo) throws IOException {
    try (FileWriter fw = new FileWriter(rutaArchivo)) {
        // Usa el Gson configurado con RuntimeTypeAdapterFactory
        gson.toJson(raiz, EntradaSistemaArchivos.class, fw);
    }
}

    // Carga la raíz desde un JSON
    public static Directorio cargarDirectorioRaiz(String rutaArchivo) throws IOException {
    try (FileReader fr = new FileReader(rutaArchivo)) {
        // Carga como EntradaSistemaArchivos para que el RuntimeTypeAdapterFactory sepa qué subclase instanciar
        EntradaSistemaArchivos entrada = gson.fromJson(fr, EntradaSistemaArchivos.class);

        // Como sabemos que la raíz es un Directorio, casteamos
        if (!(entrada instanceof Directorio)) {
            throw new IOException("La raíz JSON no es un Directorio válido");
        }
        Directorio raiz = (Directorio) entrada;

        // Reconstruir padres
        reconstruirPadres(raiz);
        return raiz;
    }
}

    // Reconstruye los padres de cada entrada (importante tras cargar JSON)
    public static void reconstruirPadres(Directorio directorio) {
        for (int i = 0; i < directorio.getContenido().getTamano(); i++) {
            EntradaSistemaArchivos hijo = directorio.getContenido().get(i);
            hijo.padre = directorio; // protected, funciona porque está en el mismo paquete
            if (hijo instanceof Directorio) {
                reconstruirPadres((Directorio) hijo);
            }
        }
    }

    // Restaura el estado del disco según los archivos cargados
    public static void restaurarEstadoDisco(Directorio raiz, DiscoSimulado disco) {
        for (int i = 0; i < disco.bloques.length; i++) {
            disco.liberarBloque(i);
        }
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
            } else if (e instanceof Directorio) {
                recorrerYMarcar((Directorio) e, disco);
            }
        }
    }
}
