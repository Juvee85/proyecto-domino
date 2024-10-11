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

    
}
