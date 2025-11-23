/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package planificacion;

/**
 *
 * @author santi
 */

import estructuras.ListaEnlazada;

public interface PoliticaPlanificacion {
    Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor);
}
