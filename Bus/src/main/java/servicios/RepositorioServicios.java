/*
 * RepositorioServicios.java
 */
package servicios;

import projects.bus.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import servicio.ContratoServicio;

/**
 *
 * @author neri
 */
public class RepositorioServicios {

    private static RepositorioServicios instance;
    
    private List<Servicio> servicios;
    
    /**
     * Constructor privado oculto
     */
    private RepositorioServicios() {
        this.servicios = new ArrayList<>();
    }
    
    /**
     * Devuelve la instancia unica del repositorio de servicios
     * @return 
     */
    public static RepositorioServicios getInstance() {
        if (instance == null) {
            instance = new RepositorioServicios();
        }
        
        return instance;
    }
    
    /**
     * Agrega un nuevo servicio al repositorio de servicios
     * @param servicio Servicio a integrar
     */
    public void agregarServicio(Servicio servicio) throws IllegalArgumentException {
        String nombreServicio = servicio.getContrato().getNombreServicio();
        
        if (nombreServicio == null || nombreServicio.isBlank() || nombreServicio.isEmpty()) {
            throw new IllegalArgumentException("El servicio que se intenta agregar no tiene un nombre definido");
        }
        
        String hostServicio = servicio.getContrato().getHost();
        if (hostServicio.isBlank() || hostServicio.isEmpty()) {
            throw new IllegalArgumentException("El servicio no tiene un nombre de host o IP definido");
        }
        
        int puerto = servicio.getContrato().getPuerto();
        if (puerto < 1) {
            throw new IllegalArgumentException("El puerto del servicio no es valido");
        }
        
        boolean servicioExiste = this.servicios.stream()
                .filter(s -> s.getContrato()
                        .getNombreServicio()
                        .equalsIgnoreCase(nombreServicio)
                )
                .findAny()
                .orElse(null) != null;
        if (servicioExiste) {
            throw new IllegalArgumentException("El servicio que se intenta agregar ya esta registrado");
        }
        
        this.servicios.add(servicio);
    }

    /**
     * Obtiene los servicios reponsables de atender el evento con nombre especificado
     * @param nombreEvento Nombre del evento entrante
     * @return Lista con los servicios responsables de atender el evento
     */
    public List<Servicio> obtenerResponsablesEvento(String nombreEvento) {
        return this.servicios.stream()
                .filter(s -> s.getContrato()
                        .getNombreServicio()
                        .equalsIgnoreCase(nombreEvento)
                )
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los servicios registrados en el repositorio de servicios
     * @return Lista de servicios
     */
    public List<Servicio> obtenerServicios() {
        return this.servicios;
    }
}
