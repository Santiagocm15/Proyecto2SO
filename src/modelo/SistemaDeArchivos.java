/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author santi
 */
public class SistemaDeArchivos {

    private final DiscoSimulado disco;
    private final Directorio directorioRaiz;

    // El constructor inicializa nuestro sistema de archivos virtual
    public SistemaDeArchivos(int cantidadDeBloquesDisco) {
        this.disco = new DiscoSimulado(cantidadDeBloquesDisco);
        // El directorio raíz no tiene padre (es null)
        this.directorioRaiz = new Directorio("/", null); 
    }

    public Directorio getDirectorioRaiz() {
        return directorioRaiz;
    }

    // --- LÓGICA DE CREACIÓN DE DIRECTORIOS ---
    public boolean crearDirectorio(String nombre, Directorio directorioPadre) {
        // 1. Validar que no exista una entrada con el mismo nombre
        if (buscarEntradaPorNombre(nombre, directorioPadre) != null) {
            System.err.println("Error: Ya existe un archivo o directorio con el nombre '" + nombre + "'.");
            return false;
        }

        // 2. Crear la nueva instancia de Directorio
        Directorio nuevoDirectorio = new Directorio(nombre, directorioPadre);

        // 3. Agregarla al contenido del directorio padre
        directorioPadre.agregarEntrada(nuevoDirectorio);
        System.out.println("Directorio '" + nombre + "' creado con éxito.");
        return true;
    }

    // --- LÓGICA DE CREACIÓN DE ARCHIVOS (La más importante) ---
    public boolean crearArchivo(String nombre, int tamanoEnBloques, Directorio directorioPadre) {
        // 1. Validar que no exista una entrada con el mismo nombre
        if (buscarEntradaPorNombre(nombre, directorioPadre) != null) {
            System.err.println("Error: Ya existe un archivo o directorio con el nombre '" + nombre + "'.");
            return false;
        }

        // 2. Validar si hay suficientes bloques libres en el disco
        if (disco.getBloquesLibres() < tamanoEnBloques) {
            System.err.println("Error: Espacio insuficiente en el disco.");
            return false;
        }

        // 3. Proceso de asignación encadenada de bloques
        Bloque bloqueAnterior = null;
        int idBloqueInicial = -1;

        for (int i = 0; i < tamanoEnBloques; i++) {
            Bloque bloqueActual = disco.buscarBloqueLibre();
            if (bloqueActual == null) {
                // Esto no debería pasar si la comprobación anterior fue correcta,
                // pero es una buena práctica de programación defensiva.
                System.err.println("Error inesperado: No se encontraron bloques libres a pesar de haber espacio.");
                // Aquí deberíamos liberar los bloques que ya asignamos en este bucle (lógica de rollback)
                return false; 
            }

            // Ocupamos el bloque
            disco.ocuparBloque(bloqueActual.id);

            if (i == 0) {
                // Si es el primer bloque, guardamos su ID. Será el punto de entrada al archivo.
                idBloqueInicial = bloqueActual.id;
            } else {
                // Si no es el primero, encadenamos el bloque anterior a este.
                bloqueAnterior.idSiguienteBloque = bloqueActual.id;
            }
            // El bloque actual se convierte en el "anterior" para la siguiente iteración
            bloqueAnterior = bloqueActual;
        }

        // 4. Crear la instancia del Archivo con la información recolectada
        Archivo nuevoArchivo = new Archivo(nombre, directorioPadre, tamanoEnBloques, idBloqueInicial);

        // 5. Agregar el nuevo archivo al directorio padre
        directorioPadre.agregarEntrada(nuevoArchivo);
        System.out.println("Archivo '" + nombre + "' de " + tamanoEnBloques + " bloques creado con éxito.");
        return true;
    }

    // --- MÉTODO AUXILIAR ---
    // Busca si ya existe un archivo/directorio con ese nombre dentro de un directorio padre
    private EntradaSistemaArchivos buscarEntradaPorNombre(String nombre, Directorio directorioPadre) {
        ListaEnlazada<EntradaSistemaArchivos> contenido = directorioPadre.getContenido();
        for (int i = 0; i < contenido.getTamano(); i++) {
            EntradaSistemaArchivos entrada = contenido.get(i);
            if (entrada.getNombre().equals(nombre)) {
                return entrada; // La encontró
            }
        }
        return null; // No existe
    }
}
