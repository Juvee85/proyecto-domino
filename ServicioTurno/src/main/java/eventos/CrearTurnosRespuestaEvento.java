/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author diana
 */
public class CrearTurnosRespuestaEvento implements Serializable {
    
    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("sala")
    private String sala;
    
    @JsonProperty("turno_actual")
    private String turnoActual;

    

    public CrearTurnosRespuestaEvento(String descripcion, String sala, String turnoActual) {
        this.descripcion = descripcion;
        this.sala = sala;
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
     * Devuelve la descripción del evento.
     *
     * @return descripción del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna la descripción del evento.
     *
     * @param descripcion la descripción a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la sala asociada al evento.
     *
     * @return sala asociada
     */
    public String getSala() {
        return sala;
    }

    /**
     * Asigna la sala asociada al evento.
     *
     * @param sala la sala a asignar
     */
    public void setSala(String sala) {
        this.sala = sala;
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


   
   
}