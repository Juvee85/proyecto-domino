/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author diana
 */
public class EliminarPozoRespuestaEvento implements Serializable {
    @JsonProperty("nombre_evento")
    private String nombre;
    
    @JsonProperty("pozo")
    private String pozo;
    
    @JsonProperty("eliminado")
    private boolean eliminado;
    
    public EliminarPozoRespuestaEvento(String pozo) {
        this.pozo = pozo;
        this.eliminado = true;
    }

    
    public EliminarPozoRespuestaEvento(String pozo, String mensaje) {
        this.pozo = pozo;
        this.nombre = mensaje;
        this.eliminado = true;
    }
    
   
    /**
     * Devuelve el pozo eliminado
     * 
     * @return 
     */
    public String getPozoId() {
        return pozo;
    }
    
    /**
     * Asigna el pozo a eliminar
     * @param pozo 
     */
    public void setPozoId(String pozo) {
        this.pozo = pozo;
    }
    
    /**
     * @return true si el pozo fue eliminado
     */
    public boolean isEliminado() {
        return eliminado;
    }
    
    /**
     * @param eliminado estado de la eliminación
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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

