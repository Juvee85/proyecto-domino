/*
 * ObservadorUnirASala.java
 */
package interfacesObservador;

import DTOS.SalaDTO;

/**
 *
 * @author Juventino López García - 00000248547 - 07/11/2024
 */
public interface ObservadorUnirASala {

    public void actualizar(String nombreSala, String contrasenha, SalaDTO sala);
}
