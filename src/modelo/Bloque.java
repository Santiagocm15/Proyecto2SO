/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author santi
 */
public class Bloque {
    public final int id; // ID único para cada bloque
    public boolean estaOcupado;
    public int idSiguienteBloque; // Apunta al ID del siguiente bloque en la cadena. -1 si es el último.

    public Bloque(int id) {
        this.id = id;
        this.estaOcupado = false; // Nace libre
        this.idSiguienteBloque = -1; // No apunta a ningún lado inicialmente
    }
    
    public int getIdSiguienteBloque() {
    return idSiguienteBloque;
}

public void setIdSiguienteBloque(int idSiguienteBloque) {
    this.idSiguienteBloque = idSiguienteBloque;
}

}
