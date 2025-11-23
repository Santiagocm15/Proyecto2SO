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

public class PoliticaSCAN implements PoliticaPlanificacion {

    @Override
    public Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor) {
        if (cola.estaVacia()) {
            return null;
        }

        Proceso proximoProceso = null;
        int distanciaMinima = Integer.MAX_VALUE;
        int cabezal = gestor.getCabezalActual();
        DireccionSCAN direccion = gestor.getDireccion();

        for (int i = 0; i < cola.getTamano(); i++) {
            Proceso p = cola.get(i);
            int bloqueObjetivo = p.getBloqueObjetivo();

            if (direccion == DireccionSCAN.HACIA_ARRIBA && bloqueObjetivo >= cabezal) {
                int distancia = bloqueObjetivo - cabezal;
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    proximoProceso = p;
                }
            } else if (direccion == DireccionSCAN.HACIA_ABAJO && bloqueObjetivo <= cabezal) {
                int distancia = cabezal - bloqueObjetivo;
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    proximoProceso = p;
                }
            }
        }

        if (proximoProceso == null) {
            DireccionSCAN nuevaDireccion = (direccion == DireccionSCAN.HACIA_ARRIBA) ? DireccionSCAN.HACIA_ABAJO : DireccionSCAN.HACIA_ARRIBA;
            gestor.setDireccion(nuevaDireccion);
            
            return seleccionarSiguienteProceso(cola, gestor); 
        }

        return proximoProceso;
    }
}