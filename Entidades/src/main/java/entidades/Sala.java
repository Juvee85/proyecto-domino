/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class Sala implements Serializable {
    private Partida partida;
    private String nombre;
    private String contrasena;
    
    /**
     * Crea una sala nueva
     * @param anfitrion Anfitrion de la sala
     */
    public Sala(Jugador anfitrion) {
        this.partida = new Partida(anfitrion);
    }
}
