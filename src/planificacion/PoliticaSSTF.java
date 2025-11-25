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

public class PoliticaSSTF implements PoliticaPlanificacion {

    @Override
    public Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor) {
        
        if (cola.estaVacia()) {
            return null;
        }

        int cabezalActual = gestor.getCabezalActual();

        Proceso procesoMasCercano = null;
        int distanciaMinima = Integer.MAX_VALUE;

        for (int i = 0; i < cola.getTamano(); i++) {
            Proceso procesoActual = cola.get(i);
            if (procesoActual.getEstado() == EstadoProceso.BLOQUEADO) {
                continue; 
            }
            int distancia = Math.abs(procesoActual.getBloqueObjetivo() - cabezalActual);

            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                procesoMasCercano = procesoActual;
            }
        }

        return procesoMasCercano;
    }
}