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

    boolean exito = false;
    if (getOperacion() == Operacion.CREAR_ARCHIVO) {
        exito = sistema.crearBloqueArchivo(getNombreArchivo(), getDirectorioPadre());
    } else if (getOperacion() == Operacion.ELIMINAR_ARCHIVO) {
        exito = sistema.eliminarBloqueArchivo(getNombreArchivo(), getDirectorioPadre());
    } else {
        exito = true; // directorios se hacen de golpe
    }

    if (exito) {
        bloquesRestantes--;
        try {
            Thread.sleep(1000); // simula 1 segundo por bloque
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    System.out.println("[DEBUG] " + getNombreArchivo() + " bloques restantes: " + bloquesRestantes);

    if (bloquesRestantes == 0) {
        setEstado(Estado.TERMINADO);
        System.out.println("[DEBUG] Proceso terminado: " + getNombreArchivo());
    }
}
}
