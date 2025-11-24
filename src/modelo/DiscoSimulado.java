/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author santi
 */
import estructuras.ListaEnlazada;
import planificacion.Proceso;

public class DiscoSimulado {
    public final Bloque[] bloques; 
    private int bloquesLibres;

    public DiscoSimulado(int cantidadDeBloques) {
        this.bloques = new Bloque[cantidadDeBloques];
        for (int i = 0; i < cantidadDeBloques; i++) {
            this.bloques[i] = new Bloque(i);
        }
        this.bloquesLibres = cantidadDeBloques;
    }

    public int getBloquesLibres() {
        return bloquesLibres;
    }

    public Bloque buscarBloqueLibre() {
        for (Bloque bloque : bloques) {
            if (!bloque.estaOcupado) {
                return bloque;
            }
        }
        return null;
    }
public Bloque buscarBloqueLibreParaPlanificacion(ListaEnlazada<Proceso> colaProcesos) {
        for (Bloque bloque : bloques) {
            if (!bloque.estaOcupado) {
                boolean reservado = false;
                for (int i = 0; i < colaProcesos.getTamano(); i++) {
                    if (colaProcesos.get(i).getBloqueObjetivo() == bloque.id) {
                        reservado = true;
                        break;
                    }
                }
                if (!reservado) {
                    return bloque; 
                }
            }
        }
        return null; 
    }
    public void ocuparBloque(int id) {
        if (!bloques[id].estaOcupado) {
            bloques[id].estaOcupado = true;
            bloquesLibres--;
        }
    }

    public void liberarBloque(int id) {
        if (bloques[id].estaOcupado) {
            bloques[id].estaOcupado = false;
            bloques[id].idSiguienteBloque = -1; 
            bloquesLibres++;
        }
    }
}
