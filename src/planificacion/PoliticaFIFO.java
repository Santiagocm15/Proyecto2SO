/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificacion;

/**
 *
 * @author santi
 */
import estructuras.ListaEnlazada;

public class PoliticaFIFO implements PoliticaPlanificacion {
    @Override
    public Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor) { // <-- Cambiar firma
        if (cola.estaVacia()) {
            return null;
        }
    return cola.get(0);
    }
}
