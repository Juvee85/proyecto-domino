/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manejadores;

import java.net.Socket;

/**
 * Define un modelo para distintos tipos de manejadores de eventos (mensajes)
 * @author Saul Neri
 */
public abstract class ManejadorEvento extends Thread {
    protected String eventoSerializado;
    protected Socket clienteSck;

    /**
     * Devuelve la informacion del evento con la que se creo el evento
     * @return the eventoSerializado
     */
    protected String getEventoSerializado() {
        return eventoSerializado;
    }
}
