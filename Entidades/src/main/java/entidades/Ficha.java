
package entidades;

import java.io.Serializable;

/**
 * Clase la cual representa una Domino de Domino
 * @author neri
 */
public class Ficha implements Serializable {

    private boolean esMula;
    private int puntosCabeza;
    private int puntosCola;
    
    public Ficha(int puntosCabeza, int puntosCola) {
        this.puntosCabeza = puntosCabeza;
        this.puntosCola = puntosCola;
        
        this.esMula =  puntosCabeza == puntosCola;
    }

    public void flip() {
        int tmpCabeza = this.puntosCabeza;
        this.puntosCabeza = this.puntosCola;
        this.puntosCola = tmpCabeza;
    }
    
    /**
     * @return the esMula
     */
    public boolean isEsMula() {
        return esMula;
    }

    /**
     * @return the puntosCabeza
     */
    public int getPuntosCabeza() {
        return puntosCabeza;
    }

    /**
     * @return the puntosCola
     */
    public int getPuntosCola() {
        return puntosCola;
    }
}
