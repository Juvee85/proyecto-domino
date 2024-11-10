/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import entidades.Sala;

/**
 *
 * @author Saul Neri
 */
public class CrearSalaEvento extends Evento {
    
    @JsonProperty("sala")
    private Sala sala;
    
    public CrearSalaEvento(String nombreEvento) {
        super("CrearSalaSolicitud");
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
