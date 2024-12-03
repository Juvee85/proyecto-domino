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
public class AsignarTurnoRespuestaEvento implements Serializable{
    
    
    @JsonProperty("nombre_evento")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("sala")
    private String sala;

    @JsonProperty("turno")
    private String turno;

    public AsignarTurnoRespuestaEvento(String sala, String turno, String descripcion) {
        this.sala = sala;
        this.turno = turno;
        this.descripcion = descripcion;
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
     * Devuelve el turno asignado.
     *
     * @return turno asignado
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Asigna el turno al evento.
     *
     * @param turno el turno a asignar
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }
}
