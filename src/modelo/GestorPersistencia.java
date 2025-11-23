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

public class GestorPersistencia {

    private static final String RUTA_ARCHIVO = "sistema_archivos.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guardar el directorio raíz en JSON
    public static void guardarSistema(Directorio raiz) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(raiz, writer);
            System.out.println("Sistema de archivos guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar el directorio raíz desde JSON
    public static Directorio cargarSistema() {
        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Directorio raiz = gson.fromJson(reader, Directorio.class);
            System.out.println("Sistema de archivos cargado correctamente.");
            return raiz;
        } catch (IOException e) {
            System.out.println("No se encontró archivo de persistencia, creando nuevo sistema.");
            return null;
        }
    }
}