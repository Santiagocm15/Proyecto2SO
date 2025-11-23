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
    
    public void registrarProcesoES(Proceso.Operacion tipoOperacion, String nombre, int tamano, Directorio directorioPadre) {
    Proceso p = new Proceso(tipoOperacion, nombre, tamano, directorioPadre);
    p.setEstado(Proceso.Estado.LISTO);
    planificador.agregarProceso(p); // usar método existente
}

public boolean crearBloqueArchivo(String nombre, Directorio directorioPadre) {
    EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);
    Archivo archivo;
    if (entrada == null) {
        // Primer bloque, crea el archivo pero sin ocupar todos los bloques
        archivo = new Archivo(nombre, directorioPadre, 0, -1);
        directorioPadre.agregarEntrada(archivo);
    } else if (entrada instanceof Archivo) {
        archivo = (Archivo) entrada;
    } else {
        return false; // hay directorio con ese nombre
    }

    Bloque bloque = disco.buscarBloqueLibre();
    if (bloque == null) return false;

    disco.ocuparBloque(bloque.id);
    if (archivo.getIdBloqueInicial() == -1) archivo.setIdBloqueInicial(bloque.id);
    else {
        // Buscar último bloque y enlazar
        int id = archivo.getIdBloqueInicial();
        while (disco.bloques[id].idSiguienteBloque != -1) id = disco.bloques[id].idSiguienteBloque;
        disco.bloques[id].idSiguienteBloque = bloque.id;
    }
    archivo.incrementarTamano();
    return true;
}

public boolean eliminarBloqueArchivo(String nombre, Directorio directorioPadre) {
    EntradaSistemaArchivos entrada = buscarEntradaPorNombre(nombre, directorioPadre);
    if (!(entrada instanceof Archivo)) return false;
    Archivo archivo = (Archivo) entrada;

    if (archivo.getTamanoEnBloques() <= 0) return false;

    int id = archivo.getIdBloqueInicial();
    int prev = -1;
    while (disco.bloques[id].idSiguienteBloque != -1) {
        prev = id;
        id = disco.bloques[id].idSiguienteBloque;
    }

    disco.liberarBloque(id);
    if (prev != -1) disco.bloques[prev].idSiguienteBloque = -1;
    archivo.decrementarTamano();

    if (archivo.getTamanoEnBloques() == 0) {
        directorioPadre.getContenido().remover(archivo);
    }

    return true;
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
