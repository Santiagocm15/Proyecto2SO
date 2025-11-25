/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificacion;

/**
 *
 * @author santi
 */
import modelo.EntradaSistemaArchivos;
import modelo.Directorio;

public class SolicitudES {
    private TipoSolicitud tipo;
    private String nombre;
    private Directorio directorioPadre;
    private int tamanoEnBloques;
    
    // Campos para Eliminar/Renombrar
    private EntradaSistemaArchivos entradaObjetivo;
    private String nuevoNombre;

    // --- CONSTRUCTOR 1: Para CREAR DIRECTORIO (Tipo, Nombre, Padre) ---
    public SolicitudES(TipoSolicitud tipo, String nombre, Directorio directorioPadre) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.directorioPadre = directorioPadre;
        this.tamanoEnBloques = 0; // Los directorios no suelen pedir tamaño inicial en esta simulación
    }

    // --- CONSTRUCTOR 2: Para CREAR ARCHIVO (Tipo, Nombre, Tamaño, Padre) ---
    public SolicitudES(TipoSolicitud tipo, String nombre, int tamanoEnBloques, Directorio directorioPadre) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.tamanoEnBloques = tamanoEnBloques;
        this.directorioPadre = directorioPadre;
    }

    // --- CONSTRUCTOR 3: Para ELIMINAR (Tipo, Objeto a eliminar) ---
    public SolicitudES(TipoSolicitud tipo, EntradaSistemaArchivos entradaObjetivo) {
        this.tipo = tipo;
        this.entradaObjetivo = entradaObjetivo;
    }

    // --- CONSTRUCTOR 4: Para RENOMBRAR (Tipo, Objeto, Nuevo Nombre) ---
    public SolicitudES(TipoSolicitud tipo, EntradaSistemaArchivos entradaObjetivo, String nuevoNombre) {
        this.tipo = tipo;
        this.entradaObjetivo = entradaObjetivo;
        this.nuevoNombre = nuevoNombre;
    }

    // --- GETTERS ---
    public TipoSolicitud getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public Directorio getDirectorioPadre() { return directorioPadre; }
    public int getTamanoEnBloques() { return tamanoEnBloques; }
    public EntradaSistemaArchivos getEntradaObjetivo() { return entradaObjetivo; }
    public String getNuevoNombre() { return nuevoNombre; }
    
}