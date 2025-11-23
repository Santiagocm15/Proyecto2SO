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
import modelo.Bloque;
import modelo.SistemaDeArchivos;

public class GestorDeProcesos {

    private final ListaEnlazada<Proceso> colaDeES;
    private final SistemaDeArchivos sistemaDeArchivos;
    private PoliticaPlanificacion politicaActual;
    private int cabezalActual;
    private DireccionSCAN direccion;
            
    public GestorDeProcesos(SistemaDeArchivos sistemaDeArchivos) {
        this.colaDeES = new ListaEnlazada<>();
        this.sistemaDeArchivos = sistemaDeArchivos;
        this.politicaActual = new PoliticaFIFO(); 
        this.cabezalActual = 0; 
        this.direccion = DireccionSCAN.HACIA_ARRIBA;
    }
    
    public DireccionSCAN getDireccion() {
        return direccion;
    }

    public int getCabezalActual() {
        return this.cabezalActual;
    }
    
    public void setDireccion(DireccionSCAN direccion) {
        this.direccion = direccion;
    }

        public void setPolitica(PoliticaPlanificacion nuevaPolitica) {
        this.politicaActual = nuevaPolitica;
        System.out.println("Política de planificación cambiada a: " + nuevaPolitica.getClass().getSimpleName());
    }
        
    public void registrarNuevaSolicitud(SolicitudES solicitud) {
        Proceso nuevoProceso = new Proceso(solicitud);

        int bloqueDestino = -1;
        if (solicitud.getTipo() == TipoSolicitud.CREAR_ARCHIVO || solicitud.getTipo() == TipoSolicitud.CREAR_DIRECTORIO) {
            Bloque bloqueLibre = sistemaDeArchivos.getDisco().buscarBloqueLibreParaPlanificacion(colaDeES);

            if (bloqueLibre != null) {
                bloqueDestino = bloqueLibre.id;
            }
        }
        // (En el futuro, para leer/eliminar, el bloque objetivo sería el bloque inicial del archivo existente)

        nuevoProceso.setBloqueObjetivo(bloqueDestino);
        nuevoProceso.setEstado(EstadoProceso.LISTO);
        colaDeES.agregarAlFinal(nuevoProceso);

        System.out.println("Nuevo proceso #" + nuevoProceso.getId() + " encolado. Objetivo: Bloque " + bloqueDestino);
    }
    
    public void procesarSiguienteSolicitud() {
        if (colaDeES.estaVacia()) {
            return;
        }
        Proceso procesoActual = politicaActual.seleccionarSiguienteProceso(colaDeES, this);
        if (procesoActual == null) return; 
        procesoActual.setEstado(EstadoProceso.EJECUTANDO);
        SolicitudES solicitud = procesoActual.getSolicitud();
        boolean exito = false;
        int bloqueDestino = -1; 
        switch (solicitud.getTipo()) {
            case CREAR_ARCHIVO:
                Bloque primerBloque = sistemaDeArchivos.getDisco().buscarBloqueLibre();
                if (primerBloque != null) {
                    bloqueDestino = primerBloque.id;
                }
                exito = sistemaDeArchivos.crearArchivo(
                        solicitud.getNombre(), 
                        solicitud.getTamanoEnBloques(), 
                        solicitud.getDirectorioPadre()); 
                break;
            case CREAR_DIRECTORIO:
                Bloque primerBloqueDir = sistemaDeArchivos.getDisco().buscarBloqueLibre();
                if (primerBloqueDir != null) {
                    bloqueDestino = primerBloqueDir.id; 
                }
                exito = sistemaDeArchivos.crearDirectorio(
                    solicitud.getNombre(),
                    solicitud.getDirectorioPadre()
                );
                break;        }

        if (exito && bloqueDestino != -1) {
            this.cabezalActual = bloqueDestino;
        }
        
        procesoActual.setEstado(EstadoProceso.TERMINADO);
        System.out.println("Proceso #" + procesoActual.getId() + " terminado. Nuevo cabezal en: " + this.cabezalActual);
        colaDeES.remover(procesoActual);
    }
    
    public ListaEnlazada<Proceso> getColaDeES() {
        return colaDeES;
    }
}