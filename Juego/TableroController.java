
package com.equipo7.fichasdomino.objetos;

import java.util.List;

/**
 *
 * @author Saul Neri
 */
public class TableroController {
    private Tablero tablero;
    
    public ExtremoValido validarFicha(Ficha ficha) {
        return ExtremoValido.AMBOS;
    }
    
    public void agregarFichaExtremoDerecho(Ficha ficha) {
        this.tablero.agregarFichaExtremoDerecho(ficha);
    }
    
    public void agregarFichaExtremoIzquierdo(Ficha ficha) {
        this.tablero.agregarFichaExtremoIzquierdo(ficha);
    }
    
     public List<Ficha> obtenerJuegoFichas(int cantidadFichas) {
        return this.tablero.obtenerJuegoFichas(cantidadFichas);
    }
    
    public Ficha sacarFichaDePozo() {
        return this.tablero.sacarFicha();
    }
    
    public void meterFichaAPozo(Ficha ficha) {
        this.tablero.meterFicha(ficha);
    }
    
    public void meterListaFichas(List<Ficha> listaFichas) {
        this.tablero.meterListaFichas(listaFichas);
    }
}
