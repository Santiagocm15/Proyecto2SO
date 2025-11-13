/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author santi
 */
public class Archivo extends EntradaSistemaArchivos {
    private int tamanoEnBloques;
    private int idBloqueInicial;

    public Archivo(String nombre, Directorio padre, int tamanoEnBloques, int idBloqueInicial) {
        super(nombre, padre);
        this.tamanoEnBloques = tamanoEnBloques;
        this.idBloqueInicial = idBloqueInicial;
    }

    public int getTamanoEnBloques() {
        return tamanoEnBloques;
    }

    public int getIdBloqueInicial() {
        return idBloqueInicial;
    }
}
