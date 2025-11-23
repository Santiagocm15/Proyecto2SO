package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class GestorPersistencia {

    // 🔹 Registrar los subtipos de EntradaSistemaArchivos
    private static final RuntimeTypeAdapterFactory<EntradaSistemaArchivos> entradaFactory =
            RuntimeTypeAdapterFactory.of(EntradaSistemaArchivos.class, "tipo")
                    .registerSubtype(Archivo.class, "Archivo")
                    .registerSubtype(Directorio.class, "Directorio");

    // 🔹 Gson configurado con el RuntimeTypeAdapterFactory
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(entradaFactory)
            .setPrettyPrinting()
            .create();

    // 🔹 Guardar todo el sistema
    public static void guardarSistema(Directorio raiz, DiscoSimulado disco, String rutaArchivo) throws IOException {
        EstadoSistema estado = new EstadoSistema(raiz, disco);
        try (FileWriter fw = new FileWriter(rutaArchivo)) {
            gson.toJson(estado, fw);
        }
    }

    // 🔹 Cargar todo el sistema
    public static Directorio cargarSistema(String rutaArchivo, DiscoSimulado disco) throws IOException {
        try (FileReader fr = new FileReader(rutaArchivo)) {
            EstadoSistema estado = gson.fromJson(fr, EstadoSistema.class);

            // Reconstruir padres
            reconstruirPadres(estado.raiz);

            // Restaurar bloques del disco
            estado.restaurarDisco(disco);

            return estado.raiz;
        }
    }

    // 🔹 Reconstruye los padres de cada entrada (importante tras cargar JSON)
    private static void reconstruirPadres(Directorio directorio) {
        for (int i = 0; i < directorio.getContenido().getTamano(); i++) {
            EntradaSistemaArchivos hijo = directorio.getContenido().get(i);
            hijo.padre = directorio;
            if (hijo instanceof Directorio subdir) {
                reconstruirPadres(subdir);
            }
        }
    }
}
