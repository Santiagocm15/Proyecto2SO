/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificacion;

/**
 *
 * @author santi
 */
public class Proceso {
    private static int contadorId = 0; 
    
    private final int id;
    private EstadoProceso estado;
    private final SolicitudES solicitud;
    private int bloqueObjetivo; 

    public Proceso(SolicitudES solicitud) {
        this.id = ++contadorId;
        this.estado = EstadoProceso.NUEVO; 
        this.solicitud = solicitud;
        this.bloqueObjetivo = -1;
    }

    public int getId() {
        return id;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public SolicitudES getSolicitud() {
        return solicitud;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }
    public int getBloqueObjetivo() {
        return bloqueObjetivo;
    }

    public void setBloqueObjetivo(int bloqueObjetivo) {
        this.bloqueObjetivo = bloqueObjetivo;
    }
}

