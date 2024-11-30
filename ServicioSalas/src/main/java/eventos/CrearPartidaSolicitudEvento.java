/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;

/**
 *
 * @author Saul Neri
 */
public class CrearPartidaSolicitudEvento {
    @JsonProperty("nombre_evento")
    private String nombre;
    @JsonProperty("sala")
    private Sala sala;
    
    public CrearPartidaSolicitudEvento(Sala sala) {
        this.nombre = "CrearPartidaSolicitud";
        this.sala = sala;
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
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
