/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Jugador;
import entidades.Pozo;

/**
 *
 * @author Saul Neri
 */
public class MulaMayorAgregadaRespuestaEvento {
    @JsonProperty("nombre_evento")
    private String nombreEvento;

    @JsonProperty("sala")
    private String sala;

    @JsonProperty("ficha")
    private Pozo.Ficha ficha;

    @JsonProperty("jugador")
    private Jugador jugador;

    public MulaMayorAgregadaRespuestaEvento(String sala, Pozo.Ficha ficha, Jugador jugador) {
        this.nombreEvento = "MulaMayorAgregadaRespuesta";
        
        this.sala = sala;
        this.ficha = ficha;
        this.jugador = jugador;
    }

    /**
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * @param nombreEvento the nombreEvento to set
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
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
    public Pozo.Ficha getFicha() {
        return ficha;
    }

    /**
     * @param ficha the ficha to set
     */
    public void setFicha(Pozo.Ficha ficha) {
        this.ficha = ficha;
    }

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    
}
