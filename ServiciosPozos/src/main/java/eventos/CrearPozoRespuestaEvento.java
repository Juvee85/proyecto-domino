/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import java.io.Serializable;

/**
 *
 * @author diana
 */
public class CrearPozoRespuestaEvento implements Serializable {
    @JsonProperty("nombre_evento")
    private String nombre;
   
    @JsonProperty("sala")
    private Sala sala;
    
    @JsonProperty("fichas_restantes")
    private int fichas_restantes;

    
    public CrearPozoRespuestaEvento(Sala sala, int fichas_restantes) {
       this.nombre = "CrearTableroPartidaSolicitud";
        this.sala = sala;
        this.fichas_restantes = fichas_restantes;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
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
    
    
    public int getFichasRestantes() {
        return fichas_restantes;
    }

    public void setFichasRestantes(int fichas_restantes) {
        this.fichas_restantes = fichas_restantes;
    }
    
}
