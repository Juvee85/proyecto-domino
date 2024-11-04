/*
 * RepositorioServicios.java
 */
package projects.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import servicio.ContratoServicio;

/**
 *
 * @author Juventino López García - 00000248547 - 03/11/2024
 */
public class RepositorioServicios {

    private HashMap<String, List<ContratoServicio>> eventos;

    public void agregarServicio(ContratoServicio contrato) {
        List<String> eventosEscuchables = contrato.getEventosEscuchables();
        
        for (String eventoEscuchable : eventosEscuchables) {
            if (eventos.get(eventoEscuchable) == null) {
                eventos.put(eventoEscuchable, new ArrayList<>());
            }

            eventos.get(eventoEscuchable).add(contrato);
        }
    }

    public List<ContratoServicio> obtenerServicios() {

        return null;
    }
}
