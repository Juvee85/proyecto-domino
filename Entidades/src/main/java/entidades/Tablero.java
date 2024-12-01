package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Pozo.Ficha;
import java.io.Serializable;

/**
 * Representa el tablero en el juego
 * @author Equipo 1
 */
public class Tablero implements Serializable {

    @JsonProperty("tren_fichas")
    private TrenFichas fichas = null;
    
    public Tablero() {
        this.fichas = null;
    }
    
    public Tablero(Ficha primeraMula) {
        if (primeraMula.esMula()) {
            this.fichas = new TrenFichas();
        }
    }

    public boolean agregarFichaExtremoDerecho(Ficha ficha) {
        Ficha fichaDerecha = fichas.obtenerFichaExtremoDerecho();
        int ladoExpuesto = fichaDerecha.getPuntosCabeza();
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
    
    public boolean estaVacio() {
        return this.fichas == null;
    }

}
