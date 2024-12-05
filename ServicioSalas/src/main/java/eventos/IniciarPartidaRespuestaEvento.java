/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Sala;
import entidades.Tablero;

/**
 * Evento que sera enviado a los jugadores con su informacion actualizada.
 * @author Saul Neri
 */
public class IniciarPartidaRespuestaEvento {
    
    @JsonProperty("nombre_evento")
    private String nombreEvento;
    
    @JsonProperty("sala")
    private Sala sala;
    
    @JsonProperty("fichas_restantes")
    private int fichasRestantes;
    
    public IniciarPartidaRespuestaEvento(Sala sala, int fichasRestantes) {
        this.nombreEvento = "IniciarPartidaRespuesta";
        this.sala = sala;
        this.fichasRestantes = fichasRestantes;
    }

    /**
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @return the fichasRestantes
     */
    public int getFichasRestantes() {
        return fichasRestantes;
    }
    
}
