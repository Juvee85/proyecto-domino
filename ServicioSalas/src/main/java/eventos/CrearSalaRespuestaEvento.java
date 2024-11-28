

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import java.io.Serializable;

/**
 * Este evento es usado para mandarse cuando el evento CrearSalaSolicitud es procesado
 * correctamente por el servicio
 * @author Saul Neri
 */
public class CrearSalaRespuestaEvento implements Serializable {
    
    @JsonProperty("nombre_evento")
    private String nombre;
    
    //@JsonProperty("sala")
    //private Sala sala;
    
    public CrearSalaRespuestaEvento() {
        //this.sala = sala;
        this.nombre = "CrearSalaRespuesta";
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
