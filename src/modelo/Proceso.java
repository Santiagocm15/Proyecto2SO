/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego Mendez
 */
public class Proceso {

    public enum Estado {
        NUEVO, LISTO, EJECUTANDO, BLOQUEADO, TERMINADO
    }

    public enum Operacion {
        CREAR_ARCHIVO, ELIMINAR_ARCHIVO, CREAR_DIRECTORIO, ELIMINAR_DIRECTORIO
    }

    private Estado estado;
    private Operacion operacion;
    private String nombreArchivo;
    private int tamanoBloques; 
    private Directorio directorioPadre;

    public Proceso(Operacion operacion, String nombreArchivo, int tamanoBloques, Directorio directorioPadre) {
        this.operacion = operacion;
        this.nombreArchivo = nombreArchivo;
        this.tamanoBloques = tamanoBloques;
        this.directorioPadre = directorioPadre;
        this.estado = Estado.NUEVO;
    }

    // Getters y setters
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Operacion getOperacion() { return operacion; }
    public String getNombreArchivo() { return nombreArchivo; }
    public int getTamanoBloques() { return tamanoBloques; }
    public Directorio getDirectorioPadre() { return directorioPadre; }

    // Método auxiliar para obtener el estado como String
    public String getEstadoString() {
        return estado.name(); // Esto devuelve "NUEVO", "LISTO", etc.
    }
}


