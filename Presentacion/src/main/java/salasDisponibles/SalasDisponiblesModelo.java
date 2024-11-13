/*
 * SalasDisponiblesModelo.java
 */
package salasDisponibles;

import DTOS.SalaDTO;
import java.util.List;

/**
 * 
 * @author Juventino López García - 00000248547 - 12/11/2024
 */
public class SalasDisponiblesModelo {

    List<SalaDTO> salasDisponibles;

    public List<SalaDTO> getSalasDisponibles() {
        return salasDisponibles;
    }

    public void setSalasDisponibles(List<SalaDTO> salasDisponibles) {
        this.salasDisponibles = salasDisponibles;
    }
    
}
