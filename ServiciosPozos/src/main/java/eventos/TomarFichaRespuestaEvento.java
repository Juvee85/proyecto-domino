/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Pozo.Ficha;

/**
 *
 * @author Saul Neri
 */
public class TomarFichaRespuestaEvento {
    @JsonProperty("nombre_evento")
    private String nombre;
   
    @JsonProperty("sala")
    private String sala;
    
    @JsonProperty("ficha")
    private Ficha ficha;
    
    @JsonProperty("fichas_restantes")
    private int fichas_restantes;
    
    @JsonProperty("jugador")
    private String jugador;

    public TomarFichaRespuestaEvento() {
        this.nombre = "TomarFichaRespuesta";
    }

    /**
     * 
     * @param sala Nombre de la sala
     * @param ficha Objeto ficha devolvera al usuario
     * @param fichas_restantes Fichas restantes en el pozo
     * @param jugador ID del jugador en la sala.
     */
    public TomarFichaRespuestaEvento(String sala, Ficha ficha, int fichas_restantes, String jugador) {
        super();
        this.sala = sala;
        this.ficha = ficha;
        this.fichas_restantes = fichas_restantes;
        this.jugador = jugador;
    }

    /**
     * @return the sala
     */
    public String getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(String sala) {
        this.sala = sala;
    }

    /**
     * @return the ficha
     */
    public Ficha getFicha() {
        return ficha;
    }

    /**
     * @param ficha the ficha to set
     */
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    /**
     * @return the fichas_restantes
     */
    public int getFichas_restantes() {
        return fichas_restantes;
    }

    /**
     * @param fichas_restantes the fichas_restantes to set
     */
    public void setFichas_restantes(int fichas_restantes) {
        this.fichas_restantes = fichas_restantes;
    }

    /**
     * @return the jugador
     */
    public String getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    
    
}
