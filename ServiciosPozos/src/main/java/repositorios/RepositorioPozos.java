/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Pozo;
import entidades.Sala;
import java.util.HashMap;
import java.util.Map;
import repositorio.excepciones.RepositorioPozoException;

/**
 *
 * @author diana
 */
public class RepositorioPozos {
    
    private static RepositorioPozos instance;
     /**
     * Contiene los pozos asociados a salas por su nombre
     */
    private Map<String, Pozo> pozos;

    /**
     * Constructor privado oculto
     */
    private RepositorioPozos() {
        this.pozos = new HashMap<>();
    }
    
      /**
     * Obtiene la instancia del repositorio de pozos
     *
     * @return RepositorioPozo
     */
    public static RepositorioPozos getInstance() {
        if (instance == null) {
            instance = new RepositorioPozos();
        }

        return instance;
   
   }

   public Pozo crearPozo(Sala sala) throws RepositorioPozoException {
    try {
        // Verifica si ya existe un pozo para la sala
        boolean pozoExiste = this.pozos.containsKey(sala.getNombre());
        if (pozoExiste) {
            throw new RepositorioPozoException("La sala \"" + sala.getNombre() + "\" ya cuenta con un pozo activo en el sistema.");
        }
        Pozo nuevoPozo = new Pozo();
        this.pozos.put(sala.getNombre(), nuevoPozo);
        return nuevoPozo;
    } catch (RepositorioPozoException e) {
        System.err.println("Error al crear el pozo: " + e.getMessage());
        throw e; 
    } 
        
}
    
    
    public void eliminarPozo(Sala sala) throws RepositorioPozoException {
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.pozos.remove(sala.getNombre());
    }
    
    public void reiniciarPozo(Sala sala) throws RepositorioPozoException {
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
        if (noExiste) {
            throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
        }
        
        this.pozos.put(sala.getNombre(), new Pozo());
    }

    public Pozo obtenerPozo(Sala sala) throws RepositorioPozoException{
        boolean noExiste = !(this.pozos.containsKey(sala.getNombre()));
    if (noExiste) {
        throw new RepositorioPozoException("No existe un pozo asociado a la sala con el nombre: %s".formatted(sala.getNombre()));
    }
    return this.pozos.get(sala.getNombre());
}
    
 
}