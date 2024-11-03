/*
 * ObservadorAnhadirFicha.java
 */
package interfacesObservador;

import DTOS.FichaDTO;
import DTOS.JugadorDTO;

/**
 *
 * @author Juventino López García - 00000248547
 */
public interface ObservadorAnhadirFicha {

    public void actualizar(JugadorDTO jugador, FichaDTO ficha);
}
