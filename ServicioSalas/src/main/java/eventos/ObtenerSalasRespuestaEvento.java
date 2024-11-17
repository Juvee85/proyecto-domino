
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import java.util.List;

/**
 *
 * @author Saul Neri
 */
public class ObtenerSalasRespuestaEvento {
    @JsonProperty("nombre_evento")
    private String nombre;
    
    @JsonProperty("salas")
    private List<Sala> salas;
    
    /**
     * 
     * @param salas 
     */
    public ObtenerSalasRespuestaEvento(List<Sala> salas) {
        this.nombre = "ObtenerSalasRespuesta";
        this.salas = salas;
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
     * @return the salas
     */
    public List<Sala> getSalas() {
        return salas;
    }

    /**
     * @param salas the salas to set
     */
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
    
}
