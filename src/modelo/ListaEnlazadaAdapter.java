package modelo;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import estructuras.ListaEnlazada;
import java.io.IOException;

/**
 * Adapter de Gson para serializar y deserializar ListaEnlazada<T>.
 * Maneja correctamente objetos complejos, incluyendo subclases de EntradaSistemaArchivos.
 */
public class ListaEnlazadaAdapter<T> extends TypeAdapter<ListaEnlazada<T>> {

    private final Class<T> tipo;
    private final Gson gson;

    public ListaEnlazadaAdapter(Class<T> tipo, Gson gson) {
        this.tipo = tipo;
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, ListaEnlazada<T> lista) throws IOException {
        out.beginArray();
        for (int i = 0; i < lista.getTamano(); i++) {
            T elemento = lista.get(i);
            gson.toJson(elemento, tipo, out);
        }
        out.endArray();
    }

    @Override
    public ListaEnlazada<T> read(JsonReader in) throws IOException {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        in.beginArray();
        while (in.hasNext()) {
            T elemento = gson.fromJson(in, tipo);
            lista.agregarAlFinal(elemento);
        }
        in.endArray();
        return lista;
    }
}
