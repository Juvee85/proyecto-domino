/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evento que contieen el nuevo estado de un jugador, que sirve para saber
 * si esta listo para empezar la partida de la sala.
 * @author Saul Neri
 */
public class CambiarEstadoListoRespuestaEvento {
    
    @JsonProperty("nombre_evento")
    private String nombre;
    
    @JsonProperty("nombre_sala")
    private String nombreSala;
    
    @JsonProperty("id_jugador")
    private String nombreJugador;
    
    @JsonProperty("listo")
    private Boolean listo;

    /**
     * Crea un evento que indica el nuevo estado de un jugador en la sala. 
     * Indica si un jugador esta listo para iniciar la sala.
     * @param nombreSala Nombre de la sala.
     * @param nombreJugador Nombre del jugador.
     * @param listo Estado del jugador, si esta listo o no para empezar la
     * partida.
     */
    public CambiarEstadoListoRespuestaEvento(String nombreSala, String nombreJugador, boolean listo) {
        //this.sala = sala;
        this.nombre = "JugadorCambioEstadoListo";
        this.nombreSala = nombreSala;
        this.nombreJugador = nombreJugador;
        this.listo = listo;
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
     * @return the nombreSala
     */
    public String getNombreSala() {
        return nombreSala;
    }

    /**
     * @param nombreSala the nombreSala to set
     */
    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    /**
     * @return the nombreJugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * @param nombreJugador the nombreJugador to set
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    /**
     * @return the listo
     */
    public Boolean estaListo() {
        return listo;
    }

    /**
     * @param listo the listo to set
     */
    public void setListo(boolean listo) {
        this.listo = listo;
    }
    
}
