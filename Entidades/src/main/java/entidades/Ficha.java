/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package entidades;

import java.io.Serializable;

/**
 *
 * @author neri
 */
public interface Ficha extends Serializable {
    /**
     * Voltea los valores de los extremos de la ficha
     */
    public void flip();
    
    /**
     * @return true si es mula
     */
    public boolean esMula();

    /**
     * @return puntos de la cabeza de la ficha
     */
    public int getPuntosCabeza();

    /**
     * Regresa los puntos de la cola de la ficha
     * @return puntos de la cola
     */
    public int getPuntosCola();
}
