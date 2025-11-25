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
        public Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor) {
            if (cola.estaVacia()) {
                return null;
            }
            for (int i = 0; i < cola.getTamano(); i++) {
                Proceso p = cola.get(i);
                if (p.getEstado() != EstadoProceso.BLOQUEADO) {
                    return p;
                }
            }

            return null;
        }
}
