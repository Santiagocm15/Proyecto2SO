/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.ListaEnlazada;
/**
 *
 * @author santi
 */
public class SistemaDeArchivos {

    private final DiscoSimulado disco;
    private final Directorio directorioRaiz;

    public SistemaDeArchivos(int cantidadDeBloquesDisco) {
        this.disco = new DiscoSimulado(cantidadDeBloquesDisco);
        this.directorioRaiz = new Directorio("/", null);
    }

    public Directorio getDirectorioRaiz() {
        return directorioRaiz;
    }

    public boolean crearDirectorio(String nombre, Directorio directorioPadre) {
        if (buscarEntradaPorNombre(nombre, directorioPadre) != null) {
            System.err.println("Error: Ya existe un archivo o directorio con el nombre '" + nombre + "'.");
            return false;
        }
        Directorio nuevoDirectorio = new Directorio(nombre, directorioPadre);
        directorioPadre.agregarEntrada(nuevoDirectorio);
        System.out.println("Directorio '" + nombre + "' creado con éxito.");
        return true;
    }

    public boolean crearArchivo(String nombre, int tamanoEnBloques, Directorio directorioPadre) {
        if (buscarEntradaPorNombre(nombre, directorioPadre) != null) {
            System.err.println("Error: Ya existe un archivo o directorio con el nombre '" + nombre + "'.");
            return false;
        }
        if (disco.getBloquesLibres() < tamanoEnBloques) {
            System.err.println("Error: Espacio insuficiente en el disco.");
            return false;
        }
        Bloque bloqueAnterior = null;
        int idBloqueInicial = -1;
        for (int i = 0; i < tamanoEnBloques; i++) {
            Bloque bloqueActual = disco.buscarBloqueLibre();
            if (bloqueActual == null) {
                System.err.println("Error inesperado: No se encontraron bloques libres.");
                return false;
            }
            disco.ocuparBloque(bloqueActual.id);
            if (i == 0) {
                idBloqueInicial = bloqueActual.id;
            } else {
                bloqueAnterior.idSiguienteBloque = bloqueActual.id;
            }
            bloqueAnterior = bloqueActual;
        }
        Archivo nuevoArchivo = new Archivo(nombre, directorioPadre, tamanoEnBloques, idBloqueInicial);
        directorioPadre.agregarEntrada(nuevoArchivo);
        System.out.println("Archivo '" + nombre + "' de " + tamanoEnBloques + " bloques creado con éxito.");
        return true;
    }

    public boolean eliminarArchivo(String nombre, Directorio directorioPadre) {
        EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);

        if (entrada == null) {
            System.err.println("Error: El archivo '" + nombre + "' no existe.");
            return false;
        }
        if (!(entrada instanceof Archivo)) {
            System.err.println("Error: '" + nombre + "' no es un archivo.");
            return false;
        }

        Archivo archivoAEliminar = (Archivo) entrada;
        int idBloqueActual = archivoAEliminar.getIdBloqueInicial();
        while (idBloqueActual != -1) {
            Bloque bloqueActual = disco.bloques[idBloqueActual];
            int idSiguienteBloque = bloqueActual.idSiguienteBloque;
            disco.liberarBloque(idBloqueActual);
            idBloqueActual = idSiguienteBloque;
        }

        directorioPadre.getContenido().remover(archivoAEliminar);
        System.out.println("Archivo '" + nombre + "' eliminado con éxito.");
        return true;
    }
    
    public boolean eliminarDirectorio(String nombre, Directorio directorioPadre) {
        EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);
        
        if (entrada == null) {
            System.err.println("Error: El directorio '" + nombre + "' no existe.");
            return false;
        }
        if (!(entrada instanceof Directorio)) {
            System.err.println("Error: '" + nombre + "' no es un directorio.");
            return false;
        }
        
        Directorio dirAEliminar = (Directorio) entrada;
        
        for (int i = dirAEliminar.getContenido().getTamano() - 1; i >= 0; i--) {
            EntradaSistemaArchivos contenido = dirAEliminar.getContenido().get(i);
            if (contenido instanceof Archivo) {
                eliminarArchivo(contenido.getNombre(), dirAEliminar);
            } else if (contenido instanceof Directorio) {
                eliminarDirectorio(contenido.getNombre(), dirAEliminar);
            }
        }
        
        directorioPadre.getContenido().remover(dirAEliminar);
        System.out.println("Directorio '" + nombre + "' eliminado con éxito.");
        return true;
    }

    private EntradaSistemaArchivos buscarEntradaPorNombre(String nombre, Directorio directorioPadre) {
        if (directorioPadre == null) return null;
        for (int i = 0; i < directorioPadre.getContenido().getTamano(); i++) {
            EntradaSistemaArchivos entrada = directorioPadre.getContenido().get(i);
            if (entrada.getNombre().equals(nombre)) {
                return entrada;
            }
        }
        return null;
    }
    
    public DiscoSimulado getDisco() {
        return disco;
    }
    
    public void guardarEstado() {
    GestorPersistencia.guardarSistema(directorioRaiz);
}
public void cargarEstado() {
    Directorio raizCargada = GestorPersistencia.cargarSistema();
    if (raizCargada != null) {
        // Reemplazamos el directorio raíz actual
        // Esto funciona si quieres sobrescribir el estado actual
        for (int i = directorioRaiz.getContenido().getTamano() - 1; i >= 0; i--) {
            directorioRaiz.getContenido().remover(directorioRaiz.getContenido().get(i));
        }
        // Copiar los contenidos cargados al directorio raíz actual
        for (int i = 0; i < raizCargada.getContenido().getTamano(); i++) {
            directorioRaiz.agregarEntrada(raizCargada.getContenido().get(i));
        }
    }
}
}
