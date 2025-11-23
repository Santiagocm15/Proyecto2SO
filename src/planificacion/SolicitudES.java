/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificacion;

/**
 *
 * @author santi
 */

import modelo.Directorio;

public class SolicitudES {
    private final TipoSolicitud tipo;
    private final String nombre;
    private final Directorio directorioPadre;
    private final int tamanoEnBloques; 

    public SolicitudES(TipoSolicitud tipo, String nombre, Directorio directorioPadre, int tamanoEnBloques) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.directorioPadre = directorioPadre;
        this.tamanoEnBloques = tamanoEnBloques;
    }

    public SolicitudES(TipoSolicitud tipo, String nombre, Directorio directorioPadre) {
        this(tipo, nombre, directorioPadre, 0);
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Directorio getDirectorioPadre() {
        return directorioPadre;
    }

    public int getTamanoEnBloques() {
        return tamanoEnBloques;
    }
}
