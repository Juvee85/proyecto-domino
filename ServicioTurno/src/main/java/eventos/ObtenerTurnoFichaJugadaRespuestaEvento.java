/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;

import entidades.Jugador;
import entidades.Pozo.Ficha;
import java.io.Serializable;

/**
 * Evento que se envia a los jugadores despues de que un jugador
 * agrego una ficha en el tablero, en el cual tiene la ficha, nombre de la sala, 
 * direccion en que fue puesta la ficha, y el turno del jugador que sigue.
 * @author diana
 */
public class ObtenerTurnoFichaJugadaRespuestaEvento implements Serializable {

    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("ficha")
    private Ficha ficha;

    @JsonProperty("sala")
    private String nombreSala;

    @JsonProperty("jugador")
    private Jugador jugador;

    @JsonProperty("turno_actual")
    private String turnoActual;

    @JsonProperty("direccion")
    private String direccion;

    /**
     * Crea el evento que sera enviado cuando una ficha es agregada al tablero por un jugador. 
     * Este evento actualizara el turno en los jugadores para saber quien es el siguiente 
     * en realizar una jugada.
     * @param nombreSala Nombre de la sala.
     * @param ficha Objeto ficha que se agrego al tablero.
     * @param jugador Objeto jugador.
     * @param direccion Direccion en la que se puso la ficha (izquierda o derecha)
     * @param turnoActual Turno actual del jugador (quien sigue despues del que puso la ficha)
     */
    public ObtenerTurnoFichaJugadaRespuestaEvento(String nombreSala, Ficha ficha, Jugador jugador, String direccion, String turnoActual) {
        this.nombre = "ObtenerTurnoFichaJugadaRespuesta";
        this.nombreSala = nombreSala;
        this.ficha = ficha;
        this.jugador = jugador;
        this.turnoActual = turnoActual;
        this.direccion = direccion;
    }

    /**
     * Devuelve el jugador que tiene el turno actual.
     *
     * @return el nombre del jugador en el turno actual.
     */
    public String getTurnoActual() {
        return turnoActual;
    }

    /**
     * Establece el jugador que tiene el turno actual.
     *
     * @param turnoActual el nombre del jugador a asignar como turno actual.
     */
    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    /**
     * Devuelve el nombre del evento.
     *
     * @return nombre del evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del evento.
     *
     * @param nombre el nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
