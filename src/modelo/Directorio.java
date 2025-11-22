


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

public class Directorio extends EntradaSistemaArchivos {
    
    private final ListaEnlazada<EntradaSistemaArchivos> contenido;

    public Directorio(String nombre, Directorio padre) {
        super(nombre, padre);
        this.contenido = new ListaEnlazada<>();
    }

    public void agregarEntrada(EntradaSistemaArchivos entrada) {
        this.contenido.agregarAlFinal(entrada);
    }

    public ListaEnlazada<EntradaSistemaArchivos> getContenido() {
        return contenido;
    }
}
