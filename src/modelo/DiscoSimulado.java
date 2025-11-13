/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author santi
 */

public class DiscoSimulado {
    public final Bloque[] bloques; // El disco es un gran array de bloques
    private int bloquesLibres;

    public DiscoSimulado(int cantidadDeBloques) {
        this.bloques = new Bloque[cantidadDeBloques];
        // Al crear el disco, se inicializan cada uno de sus bloques
        for (int i = 0; i < cantidadDeBloques; i++) {
            this.bloques[i] = new Bloque(i);
        }
        this.bloquesLibres = cantidadDeBloques;
    }

    public int getBloquesLibres() {
        return bloquesLibres;
    }

    // Busca el primer bloque que no esté ocupado
    public Bloque buscarBloqueLibre() {
        for (Bloque bloque : bloques) {
            if (!bloque.estaOcupado) {
                return bloque;
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
            bloques[id].idSiguienteBloque = -1; // Reseteamos su puntero
            bloquesLibres++;
        }
    }
}
