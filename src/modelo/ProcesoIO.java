/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Diego Mendez
 */
public class ProcesoIO extends Proceso {

    public ProcesoIO(Operacion operacion, String nombreArchivo, int tamanoBloques, Directorio directorioPadre) {
        super(operacion, nombreArchivo, tamanoBloques, directorioPadre);
    }

    /**
     * Ejecuta la operación correspondiente en el sistema de archivos.
     * @param sistema el sistema de archivos sobre el que se ejecuta
     */
    public void ejecutar(SistemaDeArchivos sistema) {
        setEstado(Estado.EJECUTANDO);

        boolean exito = false;

        switch (getOperacion()) {
            case CREAR_ARCHIVO:
                exito = sistema.crearArchivo(getNombreArchivo(), getTamanoBloques(), getDirectorioPadre());
                break;
            case ELIMINAR_ARCHIVO:
                exito = sistema.eliminarArchivo(getNombreArchivo(), getDirectorioPadre());
                break;
            case CREAR_DIRECTORIO:
                exito = sistema.crearDirectorio(getNombreArchivo(), getDirectorioPadre());
                break;
            case ELIMINAR_DIRECTORIO:
                exito = sistema.eliminarDirectorio(getNombreArchivo(), getDirectorioPadre());
                break;
        }

        setEstado(Estado.TERMINADO);

        if (!exito) {
            System.out.println("La operación " + getOperacion() + " sobre '" + getNombreArchivo() + "' falló.");
        }
    }
}
