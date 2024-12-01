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
    
    @JsonProperty("tablero")
    private Tablero tablero;
    
    @JsonProperty("fichas_restantes")
    private int fichasRestantes;
    
    public IniciarPartidaRespuestaEvento(Sala sala, Tablero tablero, int fichasRestantes) {
        this.nombreEvento = "PartidaPreparadaRespuesta";
        this.sala = sala;
        this.tablero = tablero;
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
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @return the fichasRestantes
     */
    public int getFichasRestantes() {
        return fichasRestantes;
    }
    
}
