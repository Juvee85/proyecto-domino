/*
 * TableroDTO.java
 */
package DTOS;

import java.util.List;

/**
 * 
 * @author Juventino López García - 00000248547 - 09/10/2024
 */
public class TableroDTO {

    private List<FichaDTO> fichas;
    private FichaDTO fichaExtremoIzquierda;
    private FichaDTO fichaExtremoDerecha;

    public List<FichaDTO> getFichas() {
        return fichas;
    }

    public void setFichas(List<FichaDTO> fichas) {
        this.fichas = fichas;
    }

    public FichaDTO getFichaExtremoIzquierda() {
        return fichaExtremoIzquierda;
    }

    public void setFichaExtremoIzquierda(FichaDTO fichaExtremoIzquierda) {
        this.fichaExtremoIzquierda = fichaExtremoIzquierda;
    }

    public FichaDTO getFichaExtremoDerecha() {
        return fichaExtremoDerecha;
    }

    public void setFichaExtremoDerecha(FichaDTO fichaExtremoDerecha) {
        this.fichaExtremoDerecha = fichaExtremoDerecha;
    }

    // Método para validar si una ficha puede ser conectada al tablero
    public boolean puedeAgregarFicha(FichaDTO ficha) {
        if (fichas.isEmpty()) {
            fichas.add(ficha);
            fichaExtremoIzquierda = ficha;
            fichaExtremoDerecha = ficha;
            return true;
        } else if (ficha.puedeConectarCon(fichaExtremoIzquierda)) {
            fichas.add(0, ficha);  // Se agrega al inicio
            fichaExtremoIzquierda = ficha;
            return true;
        } else if (ficha.puedeConectarCon(fichaExtremoDerecha)) {
            fichas.add(ficha);  // Se agrega al final
            fichaExtremoDerecha = ficha;
            return true;
        }
        return false;
    }
}
