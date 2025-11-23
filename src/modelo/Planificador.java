/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego Mendez
 */
import estructuras.ListaEnlazada;
import java.util.Comparator;

public class Planificador {

    public enum Politica {
        FIFO,
        SSTF,
        SCAN,
        CSCAN
    }

    private Politica politicaActual;
    private final ListaEnlazada<Proceso> colaListos;
    private final ListaEnlazada<Proceso> colaBloqueados;
    private Proceso enEjecucion;

    public Planificador() {
        this.politicaActual = Politica.FIFO;
        this.colaListos = new ListaEnlazada<>();
        this.colaBloqueados = new ListaEnlazada<>();
        this.enEjecucion = null;
    }

    public void agregarProceso(Proceso p) {
        p.setEstado(Proceso.Estado.LISTO);
        colaListos.agregarAlFinal(p);
         System.out.println("[DEBUG] Proceso agregado: " + p.getNombreArchivo() + " | Estado: " + p.getEstadoString());
    }

    public void ejecutarSiguiente(SistemaDeArchivos sistema) {
    if (colaListos.estaVacia()) return;

    if (enEjecucion == null) {
        enEjecucion = seleccionarProceso();
        enEjecucion.setEstado(Proceso.Estado.EJECUTANDO);
        System.out.println("[DEBUG] Proceso en ejecución: " + enEjecucion.getNombreArchivo());
    }

    // Ejecutar solo un bloque
    if (enEjecucion instanceof ProcesoIO) {
        ((ProcesoIO) enEjecucion).ejecutarPaso(sistema);
    } else {
        // si fuera otro tipo de proceso, ejecuta normal
    }

    // Si terminó, remover de la cola
    if (enEjecucion.getEstado() == Proceso.Estado.TERMINADO) {
        colaListos.remover(enEjecucion);
        enEjecucion = null;
    }
}


    private Proceso seleccionarProceso() {
        // Por ahora implementamos FIFO
        return colaListos.get(0);
    }

    // Getters
    public ListaEnlazada<Proceso> getColaListos() { return colaListos; }
    public ListaEnlazada<Proceso> getColaBloqueados() { return colaBloqueados; }
    public Proceso getProcesoEnEjecucion() { return enEjecucion; }
    public Politica getPoliticaActual() { return politicaActual; }
    public void setPolitica(Politica p) { this.politicaActual = p; }
}
