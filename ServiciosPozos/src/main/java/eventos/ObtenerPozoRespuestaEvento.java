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
public class ObtenerPozoRespuestaEvento implements Serializable {
    @JsonProperty("nombre_evento")
    private String nombre;
   
    @JsonProperty("pozo")
    private Pozo pozo;
    
     @JsonProperty("mensaje")
    private String mensaje;
     
    public ObtenerPozoRespuestaEvento(String nombre, String mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
    }
    
    public ObtenerPozoRespuestaEvento() {
    }
    
    
    
    /**
     * Devuelve el pozo obtenido
     * 
     * @return Pozo consultado
     */
    public Pozo getPozo() {
        return pozo;
    }
    
    /**
     * Asigna el pozo al evento
     * @param pozo Pozo consultado
     */
    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }
    
    /**
     * @return the nombre del evento
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

