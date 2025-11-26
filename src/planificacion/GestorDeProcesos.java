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
import modelo.Archivo;
import modelo.Directorio; 
import modelo.EntradaSistemaArchivos; 

public class GestorDeProcesos {

    private final ListaEnlazada<Proceso> colaDeES;
    private final SistemaDeArchivos sistemaDeArchivos;
    private PoliticaPlanificacion politicaActual;
    private int cabezalActual;
    private DireccionSCAN direccion;
    private Proceso procesoEnEjecucion = null;
            
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

        switch (solicitud.getTipo()) {
            case CREAR_ARCHIVO:
            case CREAR_DIRECTORIO:
                Bloque bloqueLibre = sistemaDeArchivos.getDisco().buscarBloqueLibreParaPlanificacion(colaDeES);
                if (bloqueLibre != null) {
                    bloqueDestino = bloqueLibre.id;
                }
                break;

            case ELIMINAR:
            case RENOMBRAR:
                EntradaSistemaArchivos entrada = solicitud.getEntradaObjetivo();

                if (entrada != null) {
                    if (entrada instanceof Archivo) {
                        bloqueDestino = ((Archivo) entrada).getIdBloqueInicial();
                    } else {
                        bloqueDestino = 0; 
                    }
                }
                break;
        }

        nuevoProceso.setBloqueObjetivo(bloqueDestino);
        nuevoProceso.setEstado(EstadoProceso.LISTO);
        colaDeES.agregarAlFinal(nuevoProceso);

        System.out.println("Nuevo proceso #" + nuevoProceso.getId() + " encolado. Tipo: " + solicitud.getTipo() + ". Objetivo: Bloque " + bloqueDestino);
    }    
    public void prepararSiguienteProceso() {
        if (procesoEnEjecucion != null || colaDeES.estaVacia()) {
            return; 
        }

        for (int i = 0; i < colaDeES.getTamano(); i++) {
            Proceso p = colaDeES.get(i);
            if (p != null && p.getEstado() == EstadoProceso.BLOQUEADO) {
                int bloquesNecesarios = p.getSolicitud().getTamanoEnBloques();
                int bloquesLibres = sistemaDeArchivos.getDisco().getBloquesLibres();

                if (bloquesLibres >= bloquesNecesarios) {
                    p.setEstado(EstadoProceso.LISTO);
                    System.out.println("Proceso #" + p.getId() + " DESBLOQUEADO. Ahora está LISTO.");
                }
            }
        }
        Proceso procesoSeleccionado = politicaActual.seleccionarSiguienteProceso(colaDeES, this);
        if (procesoSeleccionado == null) {
            return; 
        }
        if (procesoSeleccionado.getSolicitud().getTipo() == TipoSolicitud.CREAR_ARCHIVO) {
            int bloquesNecesarios = procesoSeleccionado.getSolicitud().getTamanoEnBloques();
            int bloquesLibres = sistemaDeArchivos.getDisco().getBloquesLibres();

            if (bloquesLibres < bloquesNecesarios) {
                procesoSeleccionado.setEstado(EstadoProceso.BLOQUEADO);
                System.err.println("Proceso #" + procesoSeleccionado.getId() + " BLOQUEADO por falta de espacio.");
                return; 
            }
        }

        procesoSeleccionado.setEstado(EstadoProceso.EJECUTANDO);
        this.procesoEnEjecucion = procesoSeleccionado;
    }    
    public void ejecutarProcesoActual() {
        if (this.procesoEnEjecucion == null) {
            return;
        }

        SolicitudES solicitud = this.procesoEnEjecucion.getSolicitud();
        boolean exito = false;

        switch (solicitud.getTipo()) {
            case CREAR_ARCHIVO:
                exito = sistemaDeArchivos.crearArchivo(
                        solicitud.getNombre(), 
                        solicitud.getTamanoEnBloques(), 
                        solicitud.getDirectorioPadre());
                break;

            case CREAR_DIRECTORIO:
                exito = sistemaDeArchivos.crearDirectorio(
                        solicitud.getNombre(), 
                        solicitud.getDirectorioPadre());
                break;

            case ELIMINAR:
                EntradaSistemaArchivos entradaAEliminar = solicitud.getEntradaObjetivo();
                if (entradaAEliminar instanceof Archivo) {
                    exito = sistemaDeArchivos.eliminarArchivo(entradaAEliminar.getNombre(), entradaAEliminar.getPadre());
                } else if (entradaAEliminar instanceof Directorio) {
                    exito = sistemaDeArchivos.eliminarDirectorio(entradaAEliminar.getNombre(), entradaAEliminar.getPadre());
                }
                break;

            case RENOMBRAR:
                exito = sistemaDeArchivos.renombrarEntrada(solicitud.getEntradaObjetivo(), solicitud.getNuevoNombre());
                break;
        }

        // Actualizamos cabezal 
        if (exito && this.procesoEnEjecucion.getBloqueObjetivo() != -1) {
            this.cabezalActual = this.procesoEnEjecucion.getBloqueObjetivo();
        }

        // Finalizamos el proceso
        this.procesoEnEjecucion.setEstado(EstadoProceso.TERMINADO);
        System.out.println("Proceso #" + this.procesoEnEjecucion.getId() + " terminado. Nuevo cabezal: " + this.cabezalActual);

        colaDeES.remover(this.procesoEnEjecucion);
        this.procesoEnEjecucion = null;
    }
    public ListaEnlazada<Proceso> getColaDeES() {
        return colaDeES;
    }
}