
package entidades;

import java.io.Serializable;


/**
 *
 * @author neri
 */
public class Tablero implements Serializable {
    private final TrenFichas fichas;
    private final Pozo pozo;
    
    public Tablero() {
        this.fichas = new TrenFichas();
        this.pozo = Pozo.getInstance();
    }

    public boolean agregarFichaExtremoDerecho(Ficha ficha) {
        Ficha fichaDerecha = fichas.obtenerFichaExtremoDerecho();
        int ladoExpuesto = fichaDerecha.getPuntosCola();
        if (ficha.getPuntosCola() == ladoExpuesto) {
            fichas.agregarFichaExtremoDerecho(ficha);
            return true;
        } else if (ficha.getPuntosCabeza() == ladoExpuesto) {
            ficha.flip();
            fichas.agregarFichaExtremoDerecho(ficha);
            return true;
        }
        return false;
    }
    
    public boolean agregarFichaExtremoIzquierdo(Ficha ficha) {
        Ficha fichaIzquierda = fichas.obtenerFichaExtremoIzquierdo();
        int ladoExpuesto = fichaIzquierda.getPuntosCola();
        if (ficha.getPuntosCola() == ladoExpuesto) {
            fichas.agregarFichaExtremoIzquierdo(ficha);
            return true;
        } else if (ficha.getPuntosCabeza() == ladoExpuesto) {
            ficha.flip();
            fichas.agregarFichaExtremoIzquierdo(ficha);
            return true;
        }
        return false;
    }
    
    public Ficha obtenerFichaExtremoDerecho() {
        return this.fichas.obtenerFichaExtremoDerecho();
    }
    
    public Ficha obtenerFichaExtremoIzquierdo() {
        return this.fichas.obtenerFichaExtremoIzquierdo();
    }

    public List<Ficha> obtenerJuegoDeDominos(int cantidadFichas) {
        return this.pozo.obtenerJuegoDeDominos(cantidadFichas);
    }
}
