/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.ListaEnlazada;

public class SistemaDeArchivos {

    private final DiscoSimulado disco;
    private final Directorio directorioRaiz;
    private final Planificador planificador;

    public SistemaDeArchivos(int cantidadDeBloquesDisco) {
        this.disco = new DiscoSimulado(cantidadDeBloquesDisco);
        this.directorioRaiz = new Directorio("/", null);
        this.planificador = new Planificador();
    }

    public Planificador getPlanificador() { return planificador; }
    public Directorio getDirectorioRaiz() { return directorioRaiz; }
    public DiscoSimulado getDisco() { return disco; }

    // -------------- SOLICITUDES DE E/S (nuevos métodos) ----------------
    public void solicitarCrearArchivo(String nombre, int tamanoEnBloques, Directorio directorioPadre) {
        Proceso p = new Proceso(Proceso.Operacion.CREAR_ARCHIVO, nombre, tamanoEnBloques, directorioPadre);
        planificador.agregarProceso(p);
    }

    public void solicitarEliminarArchivo(String nombre, Directorio directorioPadre) {
        Proceso p = new Proceso(Proceso.Operacion.ELIMINAR_ARCHIVO, nombre, 0, directorioPadre);
        planificador.agregarProceso(p);
    }

    public void solicitarCrearDirectorio(String nombre, Directorio directorioPadre) {
        Proceso p = new Proceso(Proceso.Operacion.CREAR_DIRECTORIO, nombre, 0, directorioPadre);
        planificador.agregarProceso(p);
    }

    public void solicitarEliminarDirectorio(String nombre, Directorio directorioPadre) {
        Proceso p = new Proceso(Proceso.Operacion.ELIMINAR_DIRECTORIO, nombre, 0, directorioPadre);
        planificador.agregarProceso(p);
    }

    // -------------- MÉTODOS ORIGINALES (se mantienen) -----------------
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
            if (i == 0) idBloqueInicial = bloqueActual.id;
            else bloqueAnterior.idSiguienteBloque = bloqueActual.id;
            bloqueAnterior = bloqueActual;
        }
        Archivo nuevoArchivo = new Archivo(nombre, directorioPadre, tamanoEnBloques, idBloqueInicial);
        directorioPadre.agregarEntrada(nuevoArchivo);
        System.out.println("Archivo '" + nombre + "' de " + tamanoEnBloques + " bloques creado con éxito.");
        return true;
    }

    public boolean eliminarArchivo(String nombre, Directorio directorioPadre) {
        EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);
        if (entrada == null || !(entrada instanceof Archivo)) return false;

        Archivo archivo = (Archivo) entrada;
        int idBloque = archivo.getIdBloqueInicial();
        while (idBloque != -1) {
            Bloque bloque = disco.bloques[idBloque];
            int sig = bloque.idSiguienteBloque;
            disco.liberarBloque(idBloque);
            idBloque = sig;
        }
        directorioPadre.getContenido().remover(archivo);
        System.out.println("Archivo '" + nombre + "' eliminado con éxito.");
        return true;
    }

    public boolean eliminarDirectorio(String nombre, Directorio directorioPadre) {
        EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);
        if (entrada == null || !(entrada instanceof Directorio)) return false;

        Directorio dir = (Directorio) entrada;
        for (int i = dir.getContenido().getTamano() - 1; i >= 0; i--) {
            EntradaSistemaArchivos e = dir.getContenido().get(i);
            if (e instanceof Archivo) eliminarArchivo(e.getNombre(), dir);
            else eliminarDirectorio(e.getNombre(), dir);
        }
        directorioPadre.getContenido().remover(dir);
        System.out.println("Directorio '" + nombre + "' eliminado con éxito.");
        return true;
    }

    private EntradaSistemaArchivos buscarEntradaPorNombre(String nombre, Directorio directorioPadre) {
        if (directorioPadre == null) return null;
        for (int i = 0; i < directorioPadre.getContenido().getTamano(); i++) {
            EntradaSistemaArchivos e = directorioPadre.getContenido().get(i);
            if (e.getNombre().equals(nombre)) return e;
        }
        return null;
    }
}
