package modelo;

import java.util.Arrays;

public class EstadoSistema {
    public Directorio raiz;
    public boolean[] bloquesOcupados;

    public EstadoSistema(Directorio raiz, DiscoSimulado disco) {
        this.raiz = raiz;
        bloquesOcupados = new boolean[disco.bloques.length];
        for (int i = 0; i < disco.bloques.length; i++) {
            bloquesOcupados[i] = disco.bloques[i].estaOcupado;
        }
    }

    public void restaurarDisco(DiscoSimulado disco) {
        for (int i = 0; i < bloquesOcupados.length; i++) {
            disco.bloques[i].estaOcupado = bloquesOcupados[i];
            if (!bloquesOcupados[i]) {
                disco.bloques[i].idSiguienteBloque = -1; // reseteamos
            }
        }
    }
}
