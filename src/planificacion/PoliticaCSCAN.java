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

public class PoliticaCSCAN implements PoliticaPlanificacion {

    @Override
    public Proceso seleccionarSiguienteProceso(ListaEnlazada<Proceso> cola, GestorDeProcesos gestor) {
        if (cola.estaVacia()) {
            return null;
        }

        int cabezal = gestor.getCabezalActual();
        Proceso proximoProceso = null;
        int distanciaMinima = Integer.MAX_VALUE;

        Proceso candidatoHaciaAdelante = null;
        int min_dist_adelante = Integer.MAX_VALUE;

        for (int i = 0; i < cola.getTamano(); i++) {
            Proceso p = cola.get(i);
            int bloqueObjetivo = p.getBloqueObjetivo();
            
            if (bloqueObjetivo >= cabezal) {
                int distancia = bloqueObjetivo - cabezal;
                if (distancia < min_dist_adelante) {
                    min_dist_adelante = distancia;
                    candidatoHaciaAdelante = p;
                }
            }
        }

        if (candidatoHaciaAdelante != null) {
            return candidatoHaciaAdelante;
        }
        
        Proceso candidatoAlInicio = null;
        int bloqueMasBajo = Integer.MAX_VALUE;

        for (int i = 0; i < cola.getTamano(); i++) {
            Proceso p = cola.get(i);
            if (p.getBloqueObjetivo() < bloqueMasBajo) {
                bloqueMasBajo = p.getBloqueObjetivo();
                candidatoAlInicio = p;
            }
        }
        
        return candidatoAlInicio;
    }
}
