/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Pozo;
import java.io.Serializable;

/**
 *
 * @author diana
 */
public class CrearPozoRespuestaEvento implements Serializable {
     @JsonProperty("nombre_evento")
    private String nombre;
    
    @JsonProperty("descripcion")
    private String descripcion;
   
    @JsonProperty("pozo")
    private Pozo pozo;
    
    public CrearPozoRespuestaEvento(Pozo pozo) {
        this.pozo = pozo;
    } 

    /**
     * Devuelve el pozo
     * 
     * @return 
     */
    public Pozo getPozoz() {
        return pozo;
    }

    /**
     * Asigna el pozo al evento
     * @param pozo
     * 
     */
    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
