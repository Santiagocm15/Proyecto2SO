/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author santi
 */
public class Nodo<T> { 

    public T datos;           // Dato guardado
    public Nodo<T> siguiente; 

    public Nodo(T datos) {
        this.datos = datos;
        this.siguiente = null; 
    }
}
