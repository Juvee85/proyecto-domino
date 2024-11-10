/*
 * Evento.java
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Juventino López García - 00000248547 - 03/11/2024
 */
public class Evento {

    @JsonProperty("nombre_evento")
    protected String nombreEvento;

    public Evento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
    
}
