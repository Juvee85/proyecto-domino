
package entidades;

import entidades.Pozo.Ficha;
import java.io.Serializable;

/**
 *
 * @author
 */
public class Tablero implements Serializable {
    private final TrenFichas fichas;
    
    public Tablero() {
        this.fichas = new TrenFichas(new Pozo().sacarFicha());
    }

    public boolean agregarFichaExtremoDerecho(Ficha ficha) {
        Ficha fichaDerecha = fichas.obtenerFichaExtremoDerecho();
        int ladoExpuesto = fichaDerecha.getPuntosCola();
        if (ficha.getPuntosCabeza() == ladoExpuesto) {
            fichas.agregarFichaExtremoDerecho(ficha);
            return true;
        } else if (ficha.getPuntosCola() == ladoExpuesto) {
            ficha.flip();
            fichas.agregarFichaExtremoDerecho(ficha);
            return true;
        }
        return false;
    }
    
    public boolean agregarFichaExtremoIzquierdo(Ficha ficha) {
        Ficha fichaIzquierda = fichas.obtenerFichaExtremoIzquierdo();
        int ladoExpuesto = fichaIzquierda.getPuntosCola();
        if (ficha.getPuntosCabeza() == ladoExpuesto) {
            fichas.agregarFichaExtremoIzquierdo(ficha);
            return true;
        } else if (ficha.getPuntosCola() == ladoExpuesto) {
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

    public TrenFichas getFichas() {
        return fichas;
    }
   
}
