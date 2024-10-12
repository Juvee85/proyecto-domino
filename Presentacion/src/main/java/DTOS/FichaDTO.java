/*
 * FichaDTO.java
 */
package DTOS;

import tablero.Orientacion;

/**
 *
 * @author Juventino López García - 00000248547 - 09/10/2024
 */

public class FichaDTO {

    private final boolean esMula;
    private final int puntosCabeza;
    private final int puntosCola;
    private Orientacion orientacion;

    public FichaDTO(int puntosCabeza, int puntosCola) {
        this.puntosCabeza = puntosCabeza;
        this.puntosCola = puntosCola;
        this.esMula = puntosCabeza == puntosCola;
    }

    public boolean esMula() {
        return esMula;
    }

    public int getPuntosCabeza() {
        return puntosCabeza;
    }

    public int getPuntosCola() {
        return puntosCola;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    // Método para verificar si dos fichas pueden conectarse
    public boolean puedeConectarCon(FichaDTO otraFicha) {
        return this.puntosCola == otraFicha.puntosCabeza || this.puntosCabeza == otraFicha.puntosCola;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FichaDTO other = (FichaDTO) obj;
        return (this.puntosCola == other.puntosCola && this.puntosCabeza == other.puntosCabeza)
                || (this.puntosCola == other.puntosCabeza && this.puntosCabeza == other.puntosCola);
    }
}
