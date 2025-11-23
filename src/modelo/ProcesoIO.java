/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego Mendez
 */
public class ProcesoIO extends Proceso {
    private int bloquesRestantes;

    public ProcesoIO(Operacion operacion, String nombreArchivo, int tamanoBloques, Directorio dirPadre) {
        super(operacion, nombreArchivo, tamanoBloques, dirPadre);
        this.bloquesRestantes = tamanoBloques; // Solo archivos, para directorios será 0
    }

    public int getBloquesRestantes() { return bloquesRestantes; }

    public void ejecutarPaso(SistemaDeArchivos sistema) {
        if (bloquesRestantes <= 0) return;

        // Simular la creación/eliminación de un bloque
        if (getOperacion() == Operacion.CREAR_ARCHIVO) {
            // crear un bloque (internamente en el SistemaDeArchivos)
            // podrías tener un método interno que reserve un bloque
        } else if (getOperacion() == Operacion.ELIMINAR_ARCHIVO) {
            // eliminar un bloque
        }

        bloquesRestantes--; // un bloque menos por crear/eliminar
        System.out.println("[DEBUG] " + getNombreArchivo() + " bloques restantes: " + bloquesRestantes);

        // Si terminó todos los bloques, marcar como terminado
        if (bloquesRestantes == 0) {
            setEstado(Estado.TERMINADO);
            System.out.println("[DEBUG] Proceso terminado: " + getNombreArchivo());
        }
    }
}
