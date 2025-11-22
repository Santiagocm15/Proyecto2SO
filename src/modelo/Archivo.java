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
    private String propietario; 
    private boolean publico;

    public Archivo(String nombre, Directorio padre, int tamanoEnBloques, int idBloqueInicial, String propietario, boolean publico) {
        super(nombre, padre);
        this.tamanoEnBloques = tamanoEnBloques;
        this.idBloqueInicial = idBloqueInicial;
        this.propietario = propietario;
        this.publico = publico;
    }

    public int getTamanoEnBloques() { return tamanoEnBloques; }
    public int getIdBloqueInicial() { return idBloqueInicial; }
    public String getPropietario() { return propietario; }
    public boolean esPublico() { return publico; }
    public void setPublico(boolean publico) { this.publico = publico; }
}
