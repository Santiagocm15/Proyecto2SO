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
    }

    public void ejecutarSiguiente(SistemaDeArchivos sistema) {
        if (colaListos.estaVacia()) return;

        enEjecucion = seleccionarProceso();
        enEjecucion.setEstado(Proceso.Estado.EJECUTANDO);

        // Ejecutar operación
        boolean exito = false;
        switch (enEjecucion.getOperacion()) {
            case CREAR_ARCHIVO:
                exito = sistema.crearArchivo(enEjecucion.getNombreArchivo(),
                        enEjecucion.getTamanoBloques(),
                        enEjecucion.getDirectorioPadre());
                break;
            case ELIMINAR_ARCHIVO:
                exito = sistema.eliminarArchivo(enEjecucion.getNombreArchivo(),
                        enEjecucion.getDirectorioPadre());
                break;
            case CREAR_DIRECTORIO:
                exito = sistema.crearDirectorio(enEjecucion.getNombreArchivo(),
                        enEjecucion.getDirectorioPadre());
                break;
            case ELIMINAR_DIRECTORIO:
                exito = sistema.eliminarDirectorio(enEjecucion.getNombreArchivo(),
                        enEjecucion.getDirectorioPadre());
                break;
        }

        // Actualizar estado
        if (exito) {
            enEjecucion.setEstado(Proceso.Estado.TERMINADO);
        } else {
            enEjecucion.setEstado(Proceso.Estado.BLOQUEADO);
            colaBloqueados.agregarAlFinal(enEjecucion);
        }

        colaListos.remover(enEjecucion);
        enEjecucion = null;
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
