/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author santi
 */

public abstract class EntradaSistemaArchivos {
    protected String nombre;
    protected Date fechaCreacion; 
    protected transient Directorio padre;

    public EntradaSistemaArchivos(String nombre, Directorio padre) {
        this.nombre = nombre;
        this.padre = padre;
        this.fechaCreacion = new Date();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Directorio getPadre() {
        return padre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }
}
