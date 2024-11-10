/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * 
 * @author Saul Neri
 */
public class SalaErrorEvento implements Serializable {
    
    @JsonProperty("nombre_evento")
    private String nombre;
        
    @JsonProperty("descripcion")
    private String descripcion;
    
    public SalaErrorEvento(String descripcion) {
        this.nombre = this.getClass().getSimpleName();
        this.descripcion = descripcion;
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

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
